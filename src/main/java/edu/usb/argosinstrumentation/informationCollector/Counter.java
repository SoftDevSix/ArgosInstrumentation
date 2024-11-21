package edu.usb.argosinstrumentation.informationCollector;

import edu.usb.argosinstrumentation.domain.ClassData;
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
}