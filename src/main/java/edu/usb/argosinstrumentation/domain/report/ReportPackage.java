package edu.usb.argosinstrumentation.domain.report;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportPackage {
    private String name;
    private List<ReportClass> reportClass;
}
