package edu.usb.argosinstrumentation.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CoverageDataTest {
    @Test
    void verifyEqualsComparison() {
        ClassData example = new ClassData("Example");
        CoverageData coverageData = new CoverageData(example, example);

        ClassData classData = coverageData.getClassData();
        MethodData methodA = classData.createMethodData("<init>", "()V");
        MethodData methodB = classData.createMethodData("<init>", "()V");
        Assertions.assertEquals(methodA, methodB);
    }
}
