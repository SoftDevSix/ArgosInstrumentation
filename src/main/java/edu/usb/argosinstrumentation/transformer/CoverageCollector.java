package edu.usb.argosinstrumentation.transformer;

import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import java.util.HashMap;
import java.util.Map;

public class CoverageCollector {

    private static Map<String, CoverageData> finalInfo = new HashMap<>();

    public CoverageCollector(Map<String, CoverageData> finalInfo) {
        this.finalInfo = finalInfo;
    }

    public static void collect(String className, String methodName, String mDesc, int line) {
        if (finalInfo.get(className) != null) {
            MethodData methodData = new MethodData(methodName, mDesc);
            finalInfo.get(className).getClassData().saveMethodData(methodData, line);
        } else {
            System.out.println("final info get className is null at collectMethod");
        }
    }
}
