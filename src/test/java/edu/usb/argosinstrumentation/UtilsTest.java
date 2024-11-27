package edu.usb.argosinstrumentation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class UtilsTest {
    @Test
    void verifyPackageName() {
        String className = "org/example/App";
        String packageName = Utils.getPackageName(className);
        assertEquals("org/example/", packageName);

        className = "org/example/domain/ClassData";
        packageName = Utils.getPackageName(className);
        assertEquals("org/example/domain/", packageName);
    }

    @Test
    void verifySourceFileName() {
        String className = "org/example/App";
        String sourceFileName = Utils.getSourceFileName(className);
        assertEquals("App.java", sourceFileName);
    }

    @Test
    void checkFileCreation() {
        byte[] file = new byte[] {};
        String pathName = "example-coverage.json";
        Path path = Paths.get(pathName);
        assertFalse(Files.exists(path));

        try {
            Utils.writeToPath(pathName, file);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertTrue(Files.exists(path));

        File createdFile = new File(pathName);
        assertTrue(createdFile.delete());
    }
}
