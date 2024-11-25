package edu.usb.argosinstrumentation.informationCollector;

import edu.usb.argosinstrumentation.domain.ClassData;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class InformationCollector extends ClassVisitor implements Opcodes {
    private final ClassData classData;

    public InformationCollector(ClassVisitor classVisitor, ClassData classData) {
        super(Opcodes.ASM5, classVisitor);
        this.classData = classData;
    }

    @Override
    public MethodVisitor visitMethod(
            int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        Counter counter = new Counter(mv, classData, name, desc);
        return counter;
    }
}
