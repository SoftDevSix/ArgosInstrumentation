package edu.usb.argosinstrumentation.transformer;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        properties =
                "coverage.collector.path=edu.usb.argosinstrumentation.transformer.CoverageCollector")
public class CoverageTransformerTest {

    @Value("${coverage.collector.path}")
    private String collectorPath;

    private static final String PROJECT_NAME = "TestProject";
    private static final String TEST_CLASS_NAME =
            "edu.usb.argosinstrumentation.transformer.TestClass";
    private static final String CLASS_NOT_FOUND_MSG =
            "No se encontró la clase " + TEST_CLASS_NAME + " en el classpath";

    @Test
    public void testPassTwoNotNull() throws IOException {
        CoverageTransformer driver = new CoverageTransformer(PROJECT_NAME);
        byte[] classBytes = getClassBytes(TEST_CLASS_NAME);

        byte[] result = driver.passTwo(classBytes, TEST_CLASS_NAME);
        assertNotNull(result, "The result of passTwo should not be null");
    }

    @Test
    public void testPassTwoWithValidClassBytes() throws IOException {
        CoverageTransformer driver = new CoverageTransformer(PROJECT_NAME);

        byte[] classBytes = getClassBytes(TEST_CLASS_NAME);

        String collectorPathString = collectorPath.replace(".", "/");
        byte[] result = driver.passTwo(classBytes, collectorPathString);
        assertNotNull(result, "The result of passTwo should not be null");
        assertNotEquals(0, result.length, "The result of passTwo should not be empty");
    }

    private byte[] getClassBytes(String className) throws IOException {
        String path = className.replace('.', '/') + ".class";

        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        assertNotNull(is, CLASS_NOT_FOUND_MSG);

        return is.readAllBytes();
    }
}
