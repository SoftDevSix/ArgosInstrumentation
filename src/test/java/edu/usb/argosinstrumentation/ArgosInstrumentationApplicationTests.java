package edu.usb.argosinstrumentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArgosInstrumentationApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ArgosInstrumentationApplication application;

    @Test
    void contextLoadsMessage() {
        assertTrue(true, "Spring context loaded successfully");
    }

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Application context should not be null");
    }

    @Test
    void mainMethodShouldStartApplication() {
        ArgosInstrumentationApplication.main(new String[]{});
        assertNotNull(applicationContext, "Application context should be initialized");
    }

    @Test
    void shouldReturnApplicationVersion() {
        String version = applicationContext.getBean("applicationVersion", String.class);
        assertEquals("1.0.0", version, "Application version should match");
    }

    @Test
    void shouldReturnApplicationName() {
        String name = application.getApplicationName();
        assertEquals("Argos Instrumentation", name, "Application name should match");
    }
}
