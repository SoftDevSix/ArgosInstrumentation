package edu.usb.argosinstrumentation.transformer;

import edu.usb.argosinstrumentation.collector.InformationCollector;
import edu.usb.argosinstrumentation.coverageadapter.CoverageAdapter;
import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.CoverageData;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

@RequiredArgsConstructor
public class CoverageTransformer implements ClassFileTransformer {
    private final String projectName;

    @Override
    public byte[] transform(
            ClassLoader loader,
            String className,
            Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain,
            byte[] classfileBuffer) {
        if (className.contains(projectName)
                && !className.contains("/test/")
                && !className.contains("Tests")) {
            ClassData classData = ClassData.builder().className(className).build();
            byte[] ret = passOne(classfileBuffer, classData);

            CoverageCollector coverageCollector = new CoverageCollector();
            ClassData probeData = ClassData.builder().className("probe_" + className).build();
            CoverageData coverageData =
                    CoverageData.builder().classData(classData).probeData(probeData).build();

            coverageCollector.getFinalInfo().put(className, coverageData);

            ret = passTwo(ret, className);
            return ret;
        } else {
            return classfileBuffer;
        }
    }

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
