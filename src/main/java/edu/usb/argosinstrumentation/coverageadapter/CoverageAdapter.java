package edu.usb.argosinstrumentation.coverageadapter;

import lombok.Getter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class CoverageAdapter extends ClassVisitor implements Opcodes {
    private String className;

    @Value("${coverage.collector.path}")
    private String collectorPath;

    public CoverageAdapter(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(
            int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new CodeInjecter(mv, name, desc, className, collectorPath);
    }
}
