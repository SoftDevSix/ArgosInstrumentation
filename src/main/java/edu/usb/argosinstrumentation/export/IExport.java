package edu.usb.argosinstrumentation.export;

import edu.usb.argosinstrumentation.domain.CoverageData;
import java.util.HashMap;

public interface IExport {
    void export(String projectName, HashMap<String, CoverageData> coverageData);
}
