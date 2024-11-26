package edu.usb.argosinstrumentation.transformer;

import edu.usb.argosinstrumentation.coverageadapter.CoverageAdapter;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

@RequiredArgsConstructor
public class CoverageTransformer {
    private final String projectName;

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
