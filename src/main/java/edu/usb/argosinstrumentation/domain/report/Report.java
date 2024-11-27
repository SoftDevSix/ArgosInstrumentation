package edu.usb.argosinstrumentation.domain.report;

import lombok.Getter;

import java.util.List;

@Getter
public class Report {
    private String name;
    private List<ReportPackage> reportPackages;
}
