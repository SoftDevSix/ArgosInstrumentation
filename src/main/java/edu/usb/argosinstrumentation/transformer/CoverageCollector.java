package edu.usb.argosinstrumentation.transformer;

import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CoverageCollector {

    private static final Logger logger = Logger.getLogger(CoverageCollector.class.getName());
    private static Map<String, CoverageData> finalInfo = new HashMap<>();

    public CoverageCollector(Map<String, CoverageData> finalInfo) {
        this.finalInfo = finalInfo;
    }

    public static void collect(String className, String methodName, String mDesc, int line) {
        if (finalInfo.get(className) != null) {
            MethodData methodData = MethodData.builder().name(methodName).desc(mDesc).build();
            finalInfo.get(className).getClassData().saveMethodData(methodData, line);
        } else {
            logger.log(Level.SEVERE, "finalInfo.get(className) is null at collectMethod");
        }
    }
}