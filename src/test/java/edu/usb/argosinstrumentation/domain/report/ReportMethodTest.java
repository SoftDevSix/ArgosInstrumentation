package edu.usb.argosinstrumentation.domain.report;

import static org.junit.jupiter.api.Assertions.*;

import edu.usb.argosinstrumentation.domain.MethodData;
import edu.usb.argosinstrumentation.export.JsonExport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ReportMethodTest {
    @Test
    void verifyMethodsAreSaved() {
        JsonExport export = new JsonExport();
        ReportClass sumClass =
                ReportClass.builder().name("org/example/Sum").sourceFileName("Sum.java").build();

        MethodData methodData =
                MethodData.builder().name("getGreeting").desc("()Ljava/lang/String;").build();
        List<Integer> statements = new ArrayList<>();
        statements.addAll(List.of(6, 7, 8, 9, 11, 12, 14));
        Optional<List<Integer>> coveredStatements = Optional.of(new ArrayList<>());
        statements.addAll(List.of(6, 11, 12, 14));

        export.handleReportMethod(sumClass, methodData, statements, coveredStatements);
        assertEquals(1, sumClass.getMethods().size());
        assertEquals(7, sumClass.getMethods().get(0).getStatements().size());
    }
}
