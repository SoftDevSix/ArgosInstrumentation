package edu.usb.argosinstrumentation.CoverageAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CoverageAdapter extends ClassVisitor implements Opcodes {
    String className;

    public CoverageAdapter(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new CodeInjecter(mv, name, desc, className);
    }
}
