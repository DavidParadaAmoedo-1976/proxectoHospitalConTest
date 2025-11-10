import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests básicos que llaman a tus métodos con parámetros.
 *
 * IMPORTANTE:
 * - Estos tests tocan la BD real (MySQL y PostgreSQL). Ejecutarlos en un entorno de pruebas.
 * - Si no tienes una BD de pruebas, crea una copia o ten en cuenta que los tests insertarán datos.
 */
public class ProgramaTest {

    // Nombres únicos para evitar colisiones con datos existentes.
    private static final String UNIQUE = UUID.randomUUID().toString().substring(0, 8);
    private static final String TEST_ESPECIALIDAD = "EspTest_" + UNIQUE;
    private static final String TEST_MEDICO_NOMBRE = "MedTest_" + UNIQUE;
    private static final String TEST_MEDICO_NIF = "T" + UNIQUE; // Ten en cuenta formato NIF si tu validación exige dígitos/letra
    private static final String TEST_MEDICO_TELEFONO = "600000000"; // ajusta si tu validador exige rango
    private static final String TEST_MEDICO_EMAIL = "medtest_" + UNIQUE + "@test.local";

    private static final String TEST_PACIENTE_NOMBRE = "PacTest_" + UNIQUE;
    private static final String TEST_PACIENTE_EMAIL = "paciente_" + UNIQUE + "@test.local";
    private static final LocalDate TEST_PACIENTE_FN = LocalDate.of(1990, 1, 1);

    private static final String TEST_TRATAMIENTO_NOMBRE = "TratTest_" + UNIQUE;
    private static final String TEST_TRATAMIENTO_DESC = "Descripción de prueba " + UNIQUE;

    // Si tu Programa usa métodos estáticos: Programa.metodo(...)
    // Si no son estáticos, crea una instancia y adapta las llamadas.

    @BeforeAll
    static void antesDeTodo() {
        System.out.println("=== INICIANDO TESTS ===");
        // opcional: aquí podrías inicializar conexiones específicas si tu app no lo hace automáticamente.
    }

    @AfterAll
    static void despuesDeTodo() {
        System.out.println("=== FIN DE TESTS ===");
        // limpieza mínima: intenta eliminar el tratamiento creado por nombre (si existe)
        try {
            // Llama al método de eliminación que ya tienes (eliminarTratamientoPorNombre).
            // Si tu método está en otra clase (DAO), ajusta la llamada.
            Programa.eliminarTratamientoPorNombre(TEST_TRATAMIENTO_NOMBRE);
        } catch (Exception e) {
            // ignora: es solo intento de limpieza
        }
        // NOTA: no intentamos eliminar la especialidad/medico/paciente por seguridad.
        // Si quieres limpieza, implementa métodos de borrado por nombre o busca su id y elimínalos.
    }

    @Test
    void t01_crearEspecialidad() {
        assertDoesNotThrow(() -> Programa.crearEspecialidad(TEST_ESPECIALIDAD),
                "crearEspecialidad lanza excepción");
    }

    @Test
    void t02_crearMedico() {
        // según tu firma: crearMedico(String nombreMedico, String nif, string telefono, String email)
        assertDoesNotThrow(() -> Programa.crearMedico(TEST_MEDICO_NOMBRE, TEST_MEDICO_NIF, TEST_MEDICO_TELEFONO, TEST_MEDICO_EMAIL),
                "crearMedico lanza excepción");
    }

    @Test
    void t03_eliminarMedico_idNoExistente() {
        // Prueba eliminarMedico con un id improbable (debe manejarlo sin petar)
        assertDoesNotThrow(() -> Programa.eliminarMedico(-99999),
                "eliminarMedico con id inexistente lanza excepción");
    }

    @Test
    void t04_crearPaciente() {
        assertDoesNotThrow(() -> Programa.crearPaciente(TEST_PACIENTE_NOMBRE, TEST_PACIENTE_EMAIL, TEST_PACIENTE_FN),
                "crearPaciente lanza excepción");
    }

    @Test
    void t05_eliminarPaciente_idNoExistente() {
        assertDoesNotThrow(() -> Programa.eliminarPaciente(-99999),
                "eliminarPaciente con id inexistente lanza excepción");
    }

    @Test
    void t06_crearTratamiento_completo() {
        // crea tratamiento usando especialidad y nif de médico (los hemos creado antes en tests)
        assertDoesNotThrow(() -> Programa.crearTratamiento(TEST_TRATAMIENTO_NOMBRE, TEST_TRATAMIENTO_DESC, TEST_ESPECIALIDAD, TEST_MEDICO_NIF),
                "crearTratamiento lanza excepción");
    }

    @Test
    void t07_eliminarTratamientoPorNombre() {
        // intenta eliminar el tratamiento de prueba (si no existe, no debe petar)
        assertDoesNotThrow(() -> Programa.eliminarTratamientoPorNombre(TEST_TRATAMIENTO_NOMBRE),
                "eliminarTratamientoPorNombre lanza excepción");
    }

    @Test
    void t08_listarTratamientosConPocosPacientes() {
        assertDoesNotThrow(() -> Programa.listarTratamientosConPocosPacientes(5),
                "listarTratamientosConPocosPacientes lanza excepción");
    }

    @Test
    void t09_obtenerTotalCitasPorPaciente() {
        assertDoesNotThrow(() -> Programa.obtenerTotalCitasPorPaciente(),
                "obtenerTotalCitasPorPaciente lanza excepción");
    }

    @Test
    void t10_obtenerCantidadTratamientosPorSala() {
        assertDoesNotThrow(() -> Programa.obtenerCantidadTratamientosPorSala(),
                "obtenerCantidadTratamientosPorSala lanza excepción");
    }

    @Test
    void t11_listarTratamientosConEspecialidadYMedico() {
        assertDoesNotThrow(() -> Programa.listarTratamientosConEspecialidadYMedico(),
                "listarTratamientosConEspecialidadYMedico lanza excepción");
    }

    @Test
    void t12_obtenerPacientesPorEspecialidad() {
        // usa 1 como id de especialidad de prueba; si no existe, el método debe manejarlo sin petar
        assertDoesNotThrow(() -> Programa.obtenerPacientesPorEspecialidad(1),
                "obtenerPacientesPorEspecialidad lanza excepción");
    }
}



