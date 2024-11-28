package edu.usb.argosinstrumentation.domain.report;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Report {
    private String name;
    @Builder.Default private List<ReportPackage> reportPackages = new ArrayList<>();
}
