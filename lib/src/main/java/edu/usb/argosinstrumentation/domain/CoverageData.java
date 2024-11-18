package edu.usb.argosinstrumentation.domain;

import lombok.Getter;

@Getter
public class CoverageData {
    private final ClassData classData;
    private final ClassData probeData;

    public CoverageData(ClassData classData, ClassData probeData) {
        this.classData = classData;
        this.probeData = probeData;
    }
}
