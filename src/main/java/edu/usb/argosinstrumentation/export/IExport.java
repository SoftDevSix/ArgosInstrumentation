package edu.usb.argosinstrumentation.export;

import edu.usb.argosinstrumentation.domain.CoverageData;
import java.util.Map;

public interface IExport {
    void export(String projectName, Map<String, CoverageData> coverageData);
}
