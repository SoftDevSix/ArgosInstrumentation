package edu.usb.argosinstrumentation.domain.report;

import static org.junit.jupiter.api.Assertions.*;

import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import edu.usb.argosinstrumentation.export.JsonExport;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class ReportPackageTest {
    @Test
    void verifyNoPopulationReportPackage() {
        Report report = Report.builder().name("org/example").build();
        JsonExport export = new JsonExport();
        HashMap<String, CoverageData> packageClassesMap = new HashMap<>();
        export.handleReportPackage(report, packageClassesMap);
        assertEquals(0, report.getReportPackages().size());
    }

    @Test
    void verifyPopulationReportPackage() {
        ClassData app = ClassData.builder().className("org/example/App").build();
        MethodData initMethod = app.createMethodData("<init>", "()V");
        app.saveMethodData(initMethod, 3);
        ClassData appProbe = ClassData.builder().className("org/example/App").build();
        MethodData appPInitMethod = appProbe.createMethodData("<init>", "()V");
        appProbe.saveMethodData(appPInitMethod, 3);

        ClassData car = ClassData.builder().className("org/example/domain/Car").build();
        MethodData cInitMethod = car.createMethodData("<init>", "()V");
        car.saveMethodData(cInitMethod, 3);

        ClassData carProbe = ClassData.builder().className("org/example/domain/Car").build();
        MethodData cpInitMethod = carProbe.createMethodData("<init>", "()V");
        carProbe.saveMethodData(cpInitMethod, 3);

        CoverageData appData = CoverageData.builder().classData(app).probeData(appProbe).build();
        CoverageData carData = CoverageData.builder().classData(car).probeData(carProbe).build();

        HashMap<String, CoverageData> packageClassesMap = new HashMap<>();
        packageClassesMap.put("org/example/domain/Car", carData);
        packageClassesMap.put("org/example/App", appData);

        Report report = Report.builder().name("org/example").build();
        JsonExport export = new JsonExport();
        export.handleReportPackage(report, packageClassesMap);
        assertEquals(2, report.getReportPackages().size());
    }
}
