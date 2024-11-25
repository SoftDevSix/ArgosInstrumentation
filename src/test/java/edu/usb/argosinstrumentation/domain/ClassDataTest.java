package edu.usb.argosinstrumentation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ClassDataTest {

    @Test
    void verifyEqualsComparison() {
        ClassData classData = ClassData.builder().className("Example").build();
        MethodData methodA = classData.createMethodData("<init>", "()V");
        MethodData methodB = classData.createMethodData("getGreeting", "()Ljava/lang/String");
        MethodData methodC = classData.createMethodData("false", "()V");
        MethodData methodD = classData.createMethodData("<init>", "false");

        assertEquals(methodA, methodA); // true true
        assertNotEquals(methodA, methodC); // false true
        assertNotEquals(methodA, methodD); // true false
        assertNotEquals(methodA, methodB); // false false

        assertNotEquals(null, methodA);
    }

    @Test
    void verifySameKeyInsertion() {
        ClassData classData = ClassData.builder().className("Example").build();
        MethodData methodA = classData.createMethodData("<init>", "()V");
        classData.saveMethodData(methodA, 4);
        classData.saveMethodData(methodA, 4);
        assertEquals(1, classData.getMethods().size());
    }

    @Test
    void verifyEmptyParametersMethodDataCreation() {
        ClassData classData = ClassData.builder().className("Example").build();
        try {
            classData.createMethodData("", "NotEmpty");
            fail();
        } catch (RuntimeException e) {
            assertEquals("Cannot accept empty names", e.getMessage());
        }

        try {
            classData.createMethodData("NotEmpty", "");
            fail();
        } catch (RuntimeException e) {
            assertEquals("Cannot accept empty names", e.getMessage());
        }
    }
}
