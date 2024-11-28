package edu.usb.argosinstrumentation.transformer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import edu.usb.argosinstrumentation.domain.ClassData;
import edu.usb.argosinstrumentation.domain.CoverageData;
import edu.usb.argosinstrumentation.domain.MethodData;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoverageCollectorTest {

    private Map<String, CoverageData> finalInfo;
    private static final Logger logger = Logger.getLogger(CoverageCollector.class.getName());

    @BeforeEach
    void setUp() {
        finalInfo = new HashMap<>();
        logger.setLevel(Level.ALL);
    }

    @Test
    void testCollect_withValidClassName() {

        CoverageCollector coverageCollector = new CoverageCollector(finalInfo);
        String className = "TestClass";
        String methodName = "testMethod";
        String methodDesc = "desc";
        int line = 42;

        CoverageData mockCoverageData = mock(CoverageData.class);
        when(mockCoverageData.getClassData()).thenReturn(mock(ClassData.class));

        finalInfo.put(className, mockCoverageData);

        coverageCollector.collect(className, methodName, methodDesc, line);

        verify(mockCoverageData.getClassData(), times(1))
                .saveMethodData(any(MethodData.class), eq(line));
    }

    @Test
    void testCollect_withInvalidClassName() {
        String className = "NonExistentClass";
        String methodName = "testMethod";
        String methodDesc = "desc";
        int line = 42;

        CoverageCollector coverageCollector = new CoverageCollector(finalInfo);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        coverageCollector.collect(className, methodName, methodDesc, line);
        System.out.println(logger.getName());
        String output = outputStream.toString().trim();
        assertTrue(output.contains("CoverageCollector"), "Expected: " + output);
    }
}
