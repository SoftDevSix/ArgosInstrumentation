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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoverageCollectorTest {

    private Map<String, CoverageData> finalInfo;
    private CoverageCollector coverageCollector;

    @BeforeEach
    void setUp() {
        finalInfo = new HashMap<>();
        coverageCollector = new CoverageCollector(finalInfo);
    }

    @Test
    void testCollect_withValidClassName() {
        // Arrange
        String className = "TestClass";
        String methodName = "testMethod";
        String methodDesc = "desc";
        int line = 42;

        // Mock coverage data and its methods
        CoverageData mockCoverageData = mock(CoverageData.class);
        when(mockCoverageData.getClassData()).thenReturn(mock(ClassData.class));

        // Add mocked coverageData to finalInfo
        finalInfo.put(className, mockCoverageData);

        // Collection
        CoverageCollector.collect(className, methodName, methodDesc, line);

        // Assert mocked data
        verify(mockCoverageData.getClassData(), times(1))
                .saveMethodData(any(MethodData.class), eq(line));
    }

    @Test
    void testCollect_withInvalidClassName() {
        String className = "NonExistentClass";
        String methodName = "testMethod";
        String methodDesc = "desc";
        int line = 42;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        CoverageCollector.collect(className, methodName, methodDesc, line);

        String output = outputStream.toString().trim();
        assertTrue(output.contains("final info get className is null at collectMethod"));
    }
}
