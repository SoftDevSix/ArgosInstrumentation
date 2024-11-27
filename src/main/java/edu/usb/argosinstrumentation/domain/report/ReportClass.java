package edu.usb.argosinstrumentation.domain.report;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportClass {
    private String name;
    private String sourceFileName;
    private List<ReportMethod> methods;
}
