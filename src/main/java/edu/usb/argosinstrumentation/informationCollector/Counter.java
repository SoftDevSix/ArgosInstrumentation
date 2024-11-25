package edu.usb.argosinstrumentation.informationCollector;

import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.MethodData;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Counter extends MethodVisitor implements Opcodes {
    private final ClassData classData;
    private final String methodName;
    private final String methodDesc;

    public Counter(MethodVisitor mv, ClassData classData, String methodName, String methodDesc) {
        super(ASM5, mv);
        this.classData = classData;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        MethodData methodData = classData.createMethodData(methodName, methodDesc);
        classData.saveMethodData(methodData, line);
        mv.visitLineNumber(line, start);
    }
}
