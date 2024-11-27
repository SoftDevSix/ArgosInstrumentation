package edu.usb.argosinstrumentation.export;

import edu.usb.argosinstrumentation.domain.CoverageData;

import java.util.HashMap;
import java.util.List;

public interface IExport {
    void export(String projectName, HashMap<String, List<CoverageData>> coverageData);
}
