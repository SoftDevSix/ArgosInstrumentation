package edu.usb.argosinstrumentation.coverageadapter;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CoverageAdapterTest {

    @Test
    void testVisitMethodCreatesCodeInjecter() {
        ClassVisitor mockCv = mock(ClassVisitor.class);
        MethodVisitor mockMv = mock(MethodVisitor.class);
        when(mockCv.visitMethod(anyInt(), anyString(), anyString(), any(), any()))
                .thenReturn(mockMv);

        CoverageAdapter adapter = new CoverageAdapter(mockCv, "TestClass");

        MethodVisitor result = adapter.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", null, null);

        assertNotNull(result);
        assertTrue(result instanceof CodeInjecter);
    }

    @Test
    void testVisitMethodPassesArguments() {
        ClassVisitor mockCv = mock(ClassVisitor.class);
        CoverageAdapter adapter = new CoverageAdapter(mockCv, "TestClass");

        adapter.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", "signature", new String[]{"Exception"});

        verify(mockCv).visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", "signature", new String[]{"Exception"});
    }

    @Test
    void testClassNameInitialization() {
        ClassVisitor mockCv = mock(ClassVisitor.class);
        String className = "SampleClass";

        CoverageAdapter adapter = new CoverageAdapter(mockCv, className);

        assertEquals(className, adapter.getClassName());
    }

    @Test
    void testDelegationToParentClassVisitor() {
        ClassVisitor mockCv = mock(ClassVisitor.class);
        CoverageAdapter adapter = new CoverageAdapter(mockCv, "TestClass");

        adapter.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", null, null);

        verify(mockCv).visitMethod(eq(Opcodes.ACC_PUBLIC), eq("testMethod"), eq("()V"), isNull(), isNull());
    }

    @Test
    void testCodeInjecterInitialization() {
        ClassVisitor mockCv = mock(ClassVisitor.class);
        MethodVisitor mockMv = mock(MethodVisitor.class);
        when(mockCv.visitMethod(anyInt(), anyString(), anyString(), any(), any()))
                .thenReturn(mockMv);

        CoverageAdapter adapter = new CoverageAdapter(mockCv, "TestClass");

        MethodVisitor mv = adapter.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", null, null);

        assertTrue(mv instanceof CodeInjecter);
        CodeInjecter codeInjecter = (CodeInjecter) mv;
        assertEquals("testMethod", codeInjecter.getMethodName());
        assertEquals("()V", codeInjecter.getMethodDesc());
        assertEquals("TestClass", codeInjecter.getClassName());
    }
}
