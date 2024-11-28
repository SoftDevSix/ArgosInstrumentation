package edu.usb.argosinstrumentation.export;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class IExportTest {
    @Test
    void checkJsonExport() {
        ClassData app = ClassData.builder().className("org/example/App").build();
        MethodData initMethod = app.createMethodData("<init>", "()V");
        app.saveMethodData(initMethod, 3);
        ClassData appProbe = ClassData.builder().className("org/example/App").build();
        MethodData appPInitMethod = appProbe.createMethodData("<init>", "()V");
        appProbe.saveMethodData(appPInitMethod, 3);

        ClassData car = ClassData.builder().className("org/example/domain/Car").build();
        MethodData cInitMethod = car.createMethodData("<init>", "()V");
        car.saveMethodData(cInitMethod, 3);

        ClassData carProbe = ClassData.builder().className("org/example/domain/Car").build();
        MethodData cpInitMethod = carProbe.createMethodData("<init>", "()V");
        carProbe.saveMethodData(cpInitMethod, 3);

        CoverageData appData = CoverageData.builder().classData(app).probeData(appProbe).build();
        CoverageData carData = CoverageData.builder().classData(car).probeData(carProbe).build();

        HashMap<String, CoverageData> packageClassesMap = new HashMap<>();
        packageClassesMap.put("org/example/domain/Car", carData);
        packageClassesMap.put("org/example/App", appData);

        IExport jsonExport = new JsonExport();
        jsonExport.export("org/example", packageClassesMap);

        Path path = Paths.get(JsonExport.EXPORT_PATH);
        assertTrue(Files.exists(path));

        File createdFile = new File(JsonExport.EXPORT_PATH);
        assertTrue(createdFile.delete());
    }
}
