package edu.usb.argosinstrumentation.domain.report;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ReportLine {
    private int statementNumber;

    @Setter private boolean isCovered;
}
