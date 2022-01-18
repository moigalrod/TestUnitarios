package org.test.junitapp.ejemplo.models;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.test.junitapp.ejemplo.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void InitMetodoTest() {
        System.out.println("Inicialización de Tests");
        this.cuenta = new Cuenta("Andres", new BigDecimal("1100.12345"));
    }

    @AfterEach
    void FinMetodoTest() {
        System.out.println("Finalizando el metodo");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Init beforeAll");

    }

    @AfterAll
    static void afterAll() {
        System.out.println("Fin afterAll");
    }

    @Test
    @DisplayName("Test Nombre Cuenta")
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Moises");
        String esperado = "Moises";
        String real = cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser null");
        assertEquals(esperado, real, "El nombre de la cuenta no es el esperado");
        assertTrue(real.equals(esperado), "Nombre de la cuenta debe ser igual al esperado");
    }

    @Test
    @DisplayName("Test Saldo Cuenta")
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("13.44444"));
        assertEquals(13.44444, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Test Referencia Cuenta")
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("9999.99999"));
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("9999.99999"));
        assertEquals(cuenta, cuenta1);
    }

    @Test
    @DisplayName("Test Debito Cuenta")
    void testDebitoCuenta() {
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000, cuenta.getSaldo().intValue());
        assertEquals("1000.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Test Credito Cuenta")
    void testCreditoCuenta() {
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1200, cuenta.getSaldo().intValue());
        assertEquals("1200.12345", cuenta.getSaldo().toPlainString());
    }

    @Test
    @DisplayName("Test Insuficiente ExceptionCuenta")
    void dineroInsuficienteExceptionCuenta() {
        Exception e = assertThrows(DineroInsuficienteException.class, () -> cuenta.debito(new BigDecimal("1101.12345")));
        String actual = e.getMessage();
        String esperado = "Dinero inSuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Test transferir DineroCuentas")
    void transferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Andres", new BigDecimal("100"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("0"));
        Banco b = new Banco();
        b.addCuenta(cuenta1);
        b.addCuenta(cuenta2);
        b.setNombre("Banco del estado");
        b.transferir(cuenta1, cuenta2, new BigDecimal(100));

        assertAll(() -> assertEquals(new BigDecimal("0"), cuenta1.getSaldo())
                , () -> assertEquals(new BigDecimal("100"), cuenta2.getSaldo())
                , () -> assertEquals(2, b.getCuentas().size())
                , () -> assertEquals("Banco del estado", cuenta1.getBanco().getNombre())
                , () -> assertTrue(b.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Andres"))));

    }

    @Nested
    class SistemaOperativoTest {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {
        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMAc() {
        }
    }

    @Nested
    class JreTest {
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void testSoloJava8() {
        }

        @Test
        @EnabledOnJre(JRE.JAVA_17)
        void testSoloJava17() {
        }

        @Test
        @DisabledOnJre(JRE.JAVA_17)
        void testNoJDK17() {
        }
    }

    @Nested
    class SystemPropertiesTest {
        @Test
        @Disabled
        void imprimirSystemProperties() {
            Properties p = System.getProperties();
            p.forEach((k, v) -> System.out.println(k + " : " + v));
        }

        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = "17.0.1")
        void testJavaVErsion17() {
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = "jdk1.8.0_202")
        void imprimirVarEvironment() {
        }

    }

    @Nested
    @DisplayName("Assumtions Test")
    class AssumtionsTest {
        @Test
        @DisplayName("Test Debito Cuenta Assumtion")
        void testDebitoCuentaAssumtion() {
            cuenta.debito(new BigDecimal(100));
            boolean esDev = "dev".equals(System.getProperty("ENV"));
            // Condicional all el metodo si es DEV
            // assumeTrue(esDev);

            // Codicional parte del metodo, si no se cumple sale como disable el test
            assumingThat(esDev, () -> {
                assertNotNull(cuenta.getSaldo());
                assertEquals(1000, cuenta.getSaldo().intValue());
                assertEquals("1000.12345", cuenta.getSaldo().toPlainString());
            });
        }
    }

}