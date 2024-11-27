package edu.usb.argosinstrumentation.domain.report;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportMethod {
    private String name;
    private String returnType;
    private List<ReportLine> statements;
}
