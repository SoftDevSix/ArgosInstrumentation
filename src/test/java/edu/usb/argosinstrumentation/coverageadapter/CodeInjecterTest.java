package edu.usb.argosinstrumentation.coverageadapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodeInjecterTest {

    @Value("${coverage.collector.path}")
    private String collectorPath;

    private CodeInjecter codeInjecter;

    @Test
    void testVisitLineNumber() {
        ClassWriter classWriter = new ClassWriter(0);

        String methodTestName = "testMethod";
        // Setup of simulation
        classWriter.visit(
                Opcodes.V1_8, Opcodes.ACC_PUBLIC, "TestClass", null, "java/lang/Object", null);

        MethodVisitor originalMethodVisitor =
                classWriter.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", null, null);
        originalMethodVisitor.visitCode();

        // Inject simulation of a TestClass with a testMethod with no aparemetrs
        codeInjecter =
                new CodeInjecter(
                        originalMethodVisitor, "testMethod", "()V", "TestClass", collectorPath);

        // we are going to inject in 42 line of class
        Label label = new Label();
        codeInjecter.visitLineNumber(42, label);

        originalMethodVisitor.visitEnd();
        classWriter.visitEnd();

        // Creating a class Reader from the simulated ClassWriter
        byte[] classBytes = classWriter.toByteArray();
        ClassReader classReader = new ClassReader(classBytes);

        // Reading the simulated class
        classReader.accept(
                new ClassVisitor(Opcodes.ASM9) {
                    @Override
                    public MethodVisitor visitMethod(
                            int access,
                            String name,
                            String descriptor,
                            String signature,
                            String[] exceptions) {
                        if ((methodTestName).equals(name)) {
                            return new MethodVisitor(Opcodes.ASM9) {

                                // visiting the injected line 42
                                @Override
                                public void visitIntInsn(int opcode, int operand) {
                                    assertEquals(
                                            Opcodes.SIPUSH,
                                            opcode,
                                            "Debe usar SIPUSH para cargar el número de línea.");
                                    assertEquals(42, operand, "The injected line has to be 42.");
                                }

                                // Creating a visitMethodInsn to assure that the injection of the
                                // method was successful
                                @Override
                                public void visitMethodInsn(
                                        int opcode,
                                        String owner,
                                        String name,
                                        String descriptor,
                                        boolean isInterface) {
                                    assertEquals(
                                            collectorPath.replace(".", "/"),
                                            owner,
                                            "class path has to be 'driver/CoverageCollect'.");
                                    assertEquals(
                                            "collect",
                                            name,
                                            "The method has to be collect 'collect'.");
                                    assertEquals(
                                            "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V",
                                            descriptor,
                                            "The description has to be the same.");
                                }
                            };
                        }
                        return super.visitMethod(access, name, descriptor, signature, exceptions);
                    }
                },
                0);
    }
}
