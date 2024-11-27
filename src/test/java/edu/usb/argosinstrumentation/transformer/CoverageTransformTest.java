package edu.usb.argosinstrumentation.transformer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.security.ProtectionDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CoverageTransformTest {
    private CoverageTransformer coverageTransformer;

    @BeforeEach
    void setUp() {
        coverageTransformer = new CoverageTransformer("projectName");
    }

    @Test
    void testTransform_ValidClassName() {
        String className = "projectName/MyClass";
        byte[] classfileBuffer = new byte[] {1, 2, 3};
        ClassLoader loader = mock(ClassLoader.class);
        ProtectionDomain protectionDomain = mock(ProtectionDomain.class);

        CoverageTransformer spyTransformer = Mockito.spy(coverageTransformer);
        doReturn(new byte[] {4, 5, 6}).when(spyTransformer).passOne(any(), any());
        doReturn(new byte[] {7, 8, 9}).when(spyTransformer).passTwo(any(), any());

        byte[] result =
                spyTransformer.transform(
                        loader, className, null, protectionDomain, classfileBuffer);

        verify(spyTransformer).passOne(eq(classfileBuffer), any());
        verify(spyTransformer).passTwo(any(), eq(className));
        assertArrayEquals(new byte[] {7, 8, 9}, result);
    }

    @Test
    void testTransform_InvalidClassName() {
        String className = "test/MyClass";
        byte[] classfileBuffer = new byte[] {1, 2, 3};
        ClassLoader loader = mock(ClassLoader.class);
        ProtectionDomain protectionDomain = mock(ProtectionDomain.class);

        byte[] result =
                coverageTransformer.transform(
                        loader, className, null, protectionDomain, classfileBuffer);

        assertArrayEquals(classfileBuffer, result);
    }
}
