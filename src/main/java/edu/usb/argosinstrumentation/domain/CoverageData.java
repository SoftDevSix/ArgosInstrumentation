package edu.usb.argosinstrumentation.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoverageData {
    private final ClassData classData;
    private final ClassData probeData;
}
