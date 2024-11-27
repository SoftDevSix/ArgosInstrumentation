package edu.usb.argosinstrumentation.transformer;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CoverageTransformerTest {
    private static final String PROJECT_NAME = "TestProject";
    private static final String TEST_CLASS_NAME = "edu.usb.argosinstrumentation.transformer.TestClass";
    private static final String CLASS_NOT_FOUND_MSG = "No se encontró la clase " + TEST_CLASS_NAME + " en el classpath";

    @Test
    public void testPassTwoNotNull() throws IOException {
        CoverageTransformer driver = new CoverageTransformer(PROJECT_NAME);
        byte[] classBytes = getClassBytes(TEST_CLASS_NAME);

        byte[] result = driver.passTwo(classBytes, TEST_CLASS_NAME);
        assertNotNull(result);
    }

    @Test
    public void testPassTwoWithValidClassBytes() throws IOException {
        CoverageTransformer driver = new CoverageTransformer(PROJECT_NAME);

        byte[] classBytes = getClassBytes(TEST_CLASS_NAME);

        byte[] result = driver.passTwo(classBytes, TEST_CLASS_NAME);
        assertNotNull(result);
        assertNotEquals(0, result.length);
    }

    private byte[] getClassBytes(String className) throws IOException {
        String path = className.replace('.', '/') + ".class";

        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        assertNotNull(is, CLASS_NOT_FOUND_MSG);

        return is.readAllBytes();
    }
}
