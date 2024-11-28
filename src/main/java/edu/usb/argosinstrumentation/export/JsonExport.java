package edu.usb.argosinstrumentation.export;

import static edu.usb.argosinstrumentation.export.ReportLineUtils.populateCoveredStatements;
import static edu.usb.argosinstrumentation.export.ReportLineUtils.populateStatements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.usb.argosinstrumentation.Utils;
import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import edu.usb.argosinstrumentation.domain.report.*;
import edu.usb.argosinstrumentation.exceptions.ExportException;
import java.io.IOException;
import java.util.*;

public class JsonExport implements IExport {
    public static final String EXPORT_PATH = "coverage.json";

    public void handleReportMethod(
            ReportClass reportClass,
            MethodData methodData,
            List<Integer> statements,
            Optional<List<Integer>> optionalCoveredStatements) {
        List<ReportMethod> methods = reportClass.getMethods();
        HashMap<Integer, ReportLine> reportLineMap = new HashMap<>();
        ReportMethod method =
                ReportMethod.builder()
                        .name(methodData.getName())
                        .returnType(methodData.getDesc())
                        .build();
        populateStatements(statements, reportLineMap);

        if (optionalCoveredStatements.isPresent()){
            populateCoveredStatements(optionalCoveredStatements.get(), reportLineMap);
        }

        for (ReportLine reportLine : reportLineMap.values()) {
            method.getStatements().add(reportLine);
        }

        methods.add(method);
    }

    public void handleReportClass(ReportPackage reportPackage, CoverageData coverageData) {
        List<ReportClass> reportClassList = reportPackage.getReportClass();
        String className = coverageData.getClassData().getClassName();
        ReportClass reportClass =
                ReportClass.builder()
                        .name(className)
                        .sourceFileName(Utils.getSourceFileName(className))
                        .build();
        HashMap<MethodData, SortedSet<Integer>> data = coverageData.getClassData().getMethods();
        HashMap<MethodData, SortedSet<Integer>> probeData =
                coverageData.getProbeData().getMethods();

        for (Map.Entry<MethodData, SortedSet<Integer>> entry : data.entrySet()) {
            if (probeData.get(entry.getKey()) == null) {
                handleReportMethod(
                        reportClass,
                        entry.getKey(),
                        data.get(entry.getKey()).stream().toList(),
                        Optional.empty());
            }else {
                handleReportMethod(
                        reportClass,
                        entry.getKey(),
                        data.get(entry.getKey()).stream().toList(),
                        Optional.of(probeData.get(entry.getKey()).stream().toList()));
            }
        }

        reportClassList.add(reportClass);
    }

    public void handleReportPackage(Report report, Map<String, CoverageData> packageClassMap) {
        List<ReportPackage> reportPackageList = report.getReportPackages();
        HashMap<String, ReportPackage> reportPackageMap = new HashMap<>();

        for (Map.Entry<String, CoverageData> entry : packageClassMap.entrySet()) {
            String className = entry.getKey();
            String packageName = Utils.getPackageName(className);
            ReportPackage reportPackage;
            if (!reportPackageMap.containsKey(packageName)) {
                reportPackageMap.put(
                        packageName, ReportPackage.builder().name(packageName).build());
            }
            reportPackage = reportPackageMap.get(packageName);
            handleReportClass(reportPackage, packageClassMap.get(className));
        }

        reportPackageList.addAll(reportPackageMap.values());
    }

    @Override
    public void export(String projectName, HashMap<String, CoverageData> coverageData) {
        Report report = Report.builder().name(projectName).build();
        handleReportPackage(report, coverageData);
        try {
            String string = new ObjectMapper().writeValueAsString(report);
            Utils.writeToPath(EXPORT_PATH, string.getBytes());
        } catch (JsonProcessingException e) {
            throw new ExportException("Could serialize into JSON file", e);
        } catch (IOException e) {
            throw new ExportException("Could not write to file", e);
        }
    }
}
