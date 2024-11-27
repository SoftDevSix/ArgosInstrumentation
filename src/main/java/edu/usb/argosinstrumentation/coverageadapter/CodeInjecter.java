package edu.usb.argosinstrumentation.coverageadapter;

import lombok.Getter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

@Getter
public class CodeInjecter extends MethodVisitor implements Opcodes {
    private final String methodName;
    private final String methodDesc;
    private final String className;
    private String collectorPath;

    public CodeInjecter(
            MethodVisitor mv, String name, String desc, String className, String collectorPath) {
        super(ASM5, mv);
        this.methodName = name;
        this.methodDesc = desc;
        this.className = className;
        this.collectorPath = collectorPath;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        System.out.println(collectorPath);
        mv.visitLdcInsn(className);
        mv.visitLdcInsn(methodName);
        mv.visitLdcInsn(methodDesc);
        mv.visitIntInsn(SIPUSH, line);
        mv.visitMethodInsn(
                INVOKESTATIC,
                collectorPath.replace(".", "/"),
                "collect",
                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V",
                false);
        super.visitLineNumber(line, start);
    }
}
