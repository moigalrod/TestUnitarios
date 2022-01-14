package org.plexus.junitapp.ejemplo.models;

import org.junit.jupiter.api.Test;
import org.plexus.junitapp.ejemplo.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Moises");
        String esperado = "Moises";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertEquals(esperado, real);
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("13.44444"));
        assertEquals(13.44444, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("9999.99999"));
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("9999.99999"));
        assertEquals(cuenta, cuenta1);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    void dineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        Exception e = assertThrows(DineroInsuficienteException.class, () -> cuenta.debito(new BigDecimal("1001.12345")));
        String actual = e.getMessage();
        String esperado = "Dinero inSuficiente";
        assertEquals(esperado, actual);
    }
}