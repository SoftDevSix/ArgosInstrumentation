package edu.usb.argosinstrumentation.domain.report;

import static org.junit.jupiter.api.Assertions.*;

import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import edu.usb.argosinstrumentation.export.JsonExport;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportClassTest {

    @Test
    void verifyReportClassHappyPath() {
        JsonExport export = new JsonExport();
        ReportPackage reportPackage = ReportPackage.builder().name("org/example").build();

        ClassData classData = ClassData.builder().className("org/example/Sum").build();
        MethodData initMethod = classData.createMethodData("<init>", "()V");
        classData.saveMethodData(initMethod, 3);

        MethodData sumMethod = classData.createMethodData("sum", "(II)I");
        classData.saveMethodData(sumMethod, 6);
        classData.saveMethodData(sumMethod, 7);
        classData.saveMethodData(sumMethod, 8);
        classData.saveMethodData(sumMethod, 9);
        classData.saveMethodData(sumMethod, 11);
        classData.saveMethodData(sumMethod, 12);
        classData.saveMethodData(sumMethod, 14);

        ClassData coverageData = ClassData.builder().className("org/example/Sum").build();
        MethodData coveredInitMethod = classData.createMethodData("<init>", "()V");
        coverageData.saveMethodData(coveredInitMethod, 3);

        MethodData coveredSumMethod = coverageData.createMethodData("sum", "(II)I");
        coverageData.saveMethodData(coveredSumMethod, 6);
        coverageData.saveMethodData(coveredSumMethod, 7);
        coverageData.saveMethodData(coveredSumMethod, 8);
        coverageData.saveMethodData(coveredSumMethod, 9);
        coverageData.saveMethodData(coveredSumMethod, 11);
        coverageData.saveMethodData(coveredSumMethod, 12);
        coverageData.saveMethodData(coveredSumMethod, 14);

        CoverageData data =
                CoverageData.builder().classData(classData).probeData(coverageData).build();
        export.handleReportClass(reportPackage, data);

        assertEquals(1, reportPackage.getReportClass().size());
        ReportClass reportClass = reportPackage.getReportClass().get(0);
        List<ReportMethod> reportMethodList = reportClass.getMethods();
        assertEquals(2, reportMethodList.size());
        ReportMethod reportInitMethod = reportMethodList.get(0);
        assertEquals(1, reportInitMethod.getStatements().size());
        ReportMethod reportSumMethod = reportMethodList.get(1);
        assertEquals(7, reportSumMethod.getStatements().size());
    }
}
