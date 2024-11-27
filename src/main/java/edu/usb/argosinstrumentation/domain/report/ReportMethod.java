package edu.usb.argosinstrumentation.domain.report;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportMethod {
    private String name;
    private String returnType;
    @Builder.Default private List<ReportLine> statements = new ArrayList<>();
}
