package dh.backend.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import dh.backend.dao.impl.OdontologoDaoH2;
import dh.backend.dao.impl.OdontologoEnMemoria;
import dh.backend.model.Odontologo;
import dh.backend.service.OdontologoService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OdontologoEnMemoriaTest {
    private static Logger LOGGER = Logger.getLogger(OdontologoEnMemoriaTest.class);
    private static OdontologoService odontologoService = new OdontologoService(new OdontologoEnMemoria());
    @Test
    @DisplayName("Testear que un odontologo fue guardado en memoria")
    void testOdontologoGuardado() {
        Odontologo odontologo = new Odontologo("15975369", "JULIAN", "ALVAREZ");
        Odontologo odontologoDesdeLaMemoria = odontologoService.registrar(odontologo);
        assertNotNull(odontologoDesdeLaMemoria);
    }
    @Test
    @DisplayName("Testear que un odontologo fue encontrado en memoria")
    void testOdontologoEncontrado() {
        Odontologo odontologo = new Odontologo("147258369", "AZUL", "CORTES");
        odontologoService.registrar(odontologo);
        Integer id = 1;
        Odontologo odontologoDesdeLaMemoria = odontologoService.buscar(id);
        assertEquals(id, odontologoDesdeLaMemoria.getId());
    }
    @Test
    @DisplayName("Testear busqueda todos los odontologoes en memoria")
    void testBuscarTodos() {
        Odontologo odontologo = new Odontologo("123789456", "VANESA", "LEAL");
        Odontologo odontologo1 = new Odontologo("145236987", "FLORENCIA", "ESTRADA");
        odontologoService.registrar(odontologo);
        odontologoService.registrar(odontologo1);
        List<Odontologo> odontologoes = odontologoService.buscarTodos();
        assertEquals(2, odontologoes.size());
    }
    @Test
    @DisplayName("Testear que un odontologo fue eliminado de memoria")
    void testOdontologoEliminado() {
        Integer id = 2;
        Odontologo odontologo = new Odontologo("114522369", "JUAN", "SANCHEZ");
        Odontologo odontologo1 = new Odontologo("121212122", "LUCIA", "OLIVARES");
        Odontologo odontologo2 = new Odontologo("158745874", "LUNA", "RUIZ");

        // Guardar el odontologo en la base de datos
        odontologoService.registrar(odontologo);
        odontologoService.registrar(odontologo1);
        odontologoService.registrar(odontologo2);

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
