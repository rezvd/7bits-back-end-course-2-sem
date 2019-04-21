package it.sevenbits.hwspring.core.service.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UUIDValidatorTest {

    @Test
    public void testAnyCase() {
        String status = "aABCefab-47f6-3847-4759-abcdefabcdef";
        assertTrue(UUIDValidator.isValid(status));
    }

    @Test
    public void testHighCase() {
        String status = "aABCefab-47f6-3847-4759-abcdefabcdef";
        assertTrue(UUIDValidator.isValid(status));
    }

    @Test
    public void testWithoutDash() {
        String status = "15375839-47563847-4759-abCdefabcdef";
        assertFalse(UUIDValidator.isValid(status));
    }

    @Test
    public void testExtraDash() {
        String status = "15375839-4756--3847-4759-abcdefabcdef";
        assertFalse(UUIDValidator.isValid(status));
    }

    @Test
    public void testForbiddenLetter() {
        String status = "15375839-4756-3847-4759-abcdefaTcdef";
        assertFalse(UUIDValidator.isValid(status));
    }
}