package edu.usb.argosinstrumentation.CoverageAdapter;

import lombok.Getter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

@Getter
public class CodeInjecter extends MethodVisitor implements Opcodes{
    private final String methodName;
    private final String methodDesc;
    private final String className;

    public CodeInjecter(MethodVisitor mv, String name, String desc, String className) {
        super(ASM5, mv);
        this.methodName = name;
        this.methodDesc = desc;
        this.className = className;
    }
}

