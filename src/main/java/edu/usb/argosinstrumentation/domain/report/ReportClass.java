package edu.usb.argosinstrumentation.domain.report;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportClass {
    private String name;
    private String sourceFileName;
    @Builder.Default private List<ReportMethod> methods = new ArrayList<>();
}
