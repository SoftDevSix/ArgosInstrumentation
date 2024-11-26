package edu.usb.argosinstrumentation.collector;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.usb.argosinstrumentation.domain.ClassData;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class InformationCollectorTest {

    @Test
    void testConstructorInitializesClassData() {

        ClassVisitor mockClassVisitor = mock(ClassVisitor.class);
        ClassData mockClassData = mock(ClassData.class);

        InformationCollector informationCollector =
                new InformationCollector(mockClassVisitor, mockClassData);

        assertNotNull(informationCollector);
    }

    @Test
    void testVisitMethodPassesCorrectValuesToCounter() {
        ClassVisitor mockClassVisitor = mock(ClassVisitor.class);
        MethodVisitor mockMethodVisitor = mock(MethodVisitor.class);
        when(mockClassVisitor.visitMethod(
                        anyInt(), anyString(), anyString(), anyString(), any(String[].class)))
                .thenReturn(mockMethodVisitor);

        ClassData mockClassData = mock(ClassData.class);
        InformationCollector informationCollector =
                new InformationCollector(mockClassVisitor, mockClassData);

        int access = Opcodes.ACC_PRIVATE;
        String methodName = "calculate";
        String methodDesc = "(I)I";
        String signature = null;
        String[] exceptions = new String[] {"java/lang/Exception"};

        MethodVisitor result =
                informationCollector.visitMethod(
                        access, methodName, methodDesc, signature, exceptions);

        assertNotNull(result, "The returned MethodVisitor should not be null.");
        assertTrue(
                result instanceof Counter,
                "The returned MethodVisitor should be an instance of Counter.");
    }
}
