package edu.usb.argosinstrumentation.transformer;

import edu.usb.argosinstrumentation.collector.InformationCollector;
import edu.usb.argosinstrumentation.coverageadapter.CoverageAdapter;
import edu.usb.argosinstrumentation.domain.ClassData;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

@RequiredArgsConstructor
public class CoverageTransformer {
    private final String projectName;

    byte[] passOne(byte[] classByte, ClassData classData) {
        byte[] ret;
        ClassReader classReader = new ClassReader(classByte);
        ClassWriter classWriter = new ClassWriter(classReader, 0);
        InformationCollector classVisitor = new InformationCollector(classWriter, classData);
        classReader.accept(classVisitor, 0);
        ret = classWriter.toByteArray();
        return ret;
    }

    byte[] passTwo(byte[] classByte, String name) {
        byte[] ret;
        ClassReader cr = new ClassReader(classByte);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
        CoverageAdapter ca = new CoverageAdapter(cw, name);
        cr.accept(ca, 0);
        ret = cw.toByteArray();
        return ret;
    }
}
