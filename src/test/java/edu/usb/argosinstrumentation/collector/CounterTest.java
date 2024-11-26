package edu.usb.argosinstrumentation.collector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.usb.argosinstrumentation.domain.ClassData;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;

class CounterTest {

    @Test
    void verifyVisitLineNumberMethod() {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(
                Opcodes.V1_8, Opcodes.ACC_PUBLIC, "TestClass", null, "java/lang/Object", null);
        MethodVisitor originalMethodVisitor =
                classWriter.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", null, null);
        originalMethodVisitor.visitCode();

        ClassData data = ClassData.builder().className("TestClass").build();
        Counter injecter = new Counter(originalMethodVisitor, data, "testMethod", "()V");

        Label label = new Label();
        injecter.visitLineNumber(42, label);
        originalMethodVisitor.visitEnd();
        classWriter.visitEnd();
        byte[] classBytes = classWriter.toByteArray();
        ClassReader classReader = new ClassReader(classBytes);

        classReader.accept(
                new ClassVisitor(Opcodes.ASM9) {
                    @Override
                    public MethodVisitor visitMethod(
                            int access,
                            String name,
                            String descriptor,
                            String signature,
                            String[] exceptions) {
                        if (name.equals("testMethod")) {
                            return new MethodVisitor(Opcodes.ASM9) {

                                @Override
                                public void visitIntInsn(int opcode, int operand) {
                                    assertEquals(
                                            Opcodes.SIPUSH,
                                            opcode,
                                            "Its neccesary use SIPUSH to load the line number");
                                    assertEquals(42, operand, "The injected line has to be 42.");
                                }

                                @Override
                                public void visitMethodInsn(
                                        int opcode,
                                        String owner,
                                        String name,
                                        String descriptor,
                                        boolean isInterface) {
                                    assertEquals(
                                            "driver/CoverageCollect",
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
