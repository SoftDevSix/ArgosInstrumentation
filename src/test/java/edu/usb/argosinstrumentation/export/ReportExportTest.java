package edu.usb.argosinstrumentation.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.usb.argosinstrumentation.domain.report.Report;
import org.junit.jupiter.api.Test;

public class ReportExportTest {
    @Test
    void verifyJSONSerialization(){
        Report report = new Report();
        String serialized = null;
        try {
            serialized = new ObjectMapper().writeValueAsString(report);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(serialized);
    }
}
