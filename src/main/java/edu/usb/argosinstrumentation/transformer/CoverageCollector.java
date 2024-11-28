package edu.usb.argosinstrumentation.transformer;

import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoverageCollector {

    private static final Logger logger = Logger.getLogger(CoverageCollector.class.getName());
    private static HashMap<String, CoverageData> finalInfo = new HashMap<>();

    public static void collect(String className, String methodName, String mDesc, int line) {
        if (finalInfo.get(className) != null) {
            System.out.println("declaracion: " + mDesc +"ejecutada: en linea" + line);
            MethodData methodData = MethodData.builder().name(methodName).desc(mDesc).build();
            finalInfo.get(className).getProbeData().saveMethodData(methodData, line);
        } else {
            logger.log(Level.SEVERE, "finalInfo.get(className) is null at collectMethod");
        }
    }

    public HashMap<String, CoverageData> getFinalInfo() {
        return finalInfo;
    }

    public void printResult() {
        if (finalInfo == null || finalInfo.isEmpty()) {
            System.out.println("No hay información de cobertura disponible para imprimir.");
            return;
        }

        System.out.println("Resultados de cobertura:");

        for (Map.Entry<String, CoverageData> entry : finalInfo.entrySet()) {
            String className = entry.getKey();
            CoverageData coverageData = entry.getValue();

            System.out.println("Clase: " + className);

            ClassData classData = coverageData.getClassData();
            ClassData probeData = coverageData.getProbeData();

            if (classData != null && classData.getMethods() != null && !classData.getMethods().isEmpty()) {
                for (Map.Entry<MethodData, SortedSet<Integer>> methodEntry : classData.getMethods().entrySet()) {
                    MethodData methodData = methodEntry.getKey();
                    SortedSet<Integer> allStatements = methodEntry.getValue();

                    System.out.println("  Método: " + methodData);
                    System.out.println("    Todas las declaraciones: " +
                            (allStatements != null ? allStatements.toString() : "N/A"));

                    if (probeData != null && probeData.getMethods() != null) {
                        SortedSet<Integer> coveredStatements = probeData.getMethods().get(methodData);

                        System.out.println("    Declaraciones cubiertas: " +
                                (coveredStatements != null ? coveredStatements.toString() : "Ninguna"));
                    } else {
                        System.out.println("    Declaraciones cubiertas: No disponible.");
                    }

                    System.out.println("    --------------------------------------------");
                }
            } else {
                System.out.println("  No se encontró información de métodos para esta clase.");
            }

            System.out.println();
        }
    }

}
