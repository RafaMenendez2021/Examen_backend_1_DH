package dh.backend.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import dh.backend.model.Odontologo;
import dh.backend.service.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import dh.backend.dao.impl.OdontologoDaoH2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
class OdontologoServiceTest {
    private static Logger LOGGER = Logger.getLogger(OdontologoServiceTest.class);
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/db_examen_BE_Kimberley_y_Rafael;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    @Test
    @DisplayName("Testear que un odontologo fue guardado")
    void testOdontologoGuardado() {
        Odontologo odontologo = new Odontologo( "15365821", "ROBIN", "JUAREZ");
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologoDesdeLaDB = odontologoService.registrar(odontologo);
        assertNotNull(odontologoDesdeLaDB);
    }
    @Test
    @DisplayName("Testear que un odontologo fue encontrado")
    void testOdontologoEncontrado() {
        Integer id = 1;
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologoDesdeLaDB = odontologoService.buscar(id);
        assertEquals(id, odontologoDesdeLaDB.getId());
    }
    @Test
    @DisplayName("Testear busqueda todos los odontologoes")
    void testBuscarTodos() {
        Odontologo odontologo = new Odontologo("12345678", "ROSA", "LOPEZ");
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
        odontologoService.registrar(odontologo);
        List<Odontologo> odontologoes = odontologoService.buscarTodos();
        assertEquals(2, odontologoes.size());
    }
    @Test
    @DisplayName("Testear que un odontologo fue eliminado")
    void testOdontologoEliminado() {
        Integer id = 2;
        Odontologo odontologo = new Odontologo("12345679", "FERNANDO", "FERNANDEZ");
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());

        // Guardar el odontologo en la base de datos
        odontologoService.registrar(odontologo);

        // Verificar que el odontologo fue guardado correctamente
        Odontologo odontologoRecuperado = odontologoService.buscar(id);
        assertNotNull(odontologoRecuperado, "El odontologo no debería ser nulo después de guardarlo");

        // Eliminar el odontologo
        odontologoService.eliminar(id);

        // Intentar recuperar el odontologo eliminado
        odontologoRecuperado = odontologoService.buscar(id);
        assertNull(odontologoRecuperado, "El odontologo debería ser nulo después de eliminarlo");
    }


}