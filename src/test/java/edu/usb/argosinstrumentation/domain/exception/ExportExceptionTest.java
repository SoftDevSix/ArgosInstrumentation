package edu.usb.argosinstrumentation.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.usb.argosinstrumentation.exceptions.ExportException;
import org.junit.jupiter.api.Test;

class ExportExceptionTest {

    @Test
    void testConstructor_withMessage() {
        String message = "Export failed";

        ExportException exception = new ExportException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructor_withMessageAndCause() {
        String message = "Export failed";
        Throwable cause = new RuntimeException("Root cause");

        ExportException exception = new ExportException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructor_withNullMessageAndCause() {
        ExportException exception = new ExportException(null, null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
