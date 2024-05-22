package dh.backend.dao.impl;

import dh.backend.dao.IDao;
import dh.backend.model.Odontologo;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


public class OdontologoEnMemoria implements IDao<Odontologo> {
    private Logger LOGGER = Logger.getLogger(OdontologoEnMemoria.class);
    List<Odontologo> odontologos = new ArrayList<>();
    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Integer id = odontologos.size()+1;
        odontologo.setId(id);
        odontologos.add(odontologo);
        LOGGER.info("Odontologo agregado: "+ odontologo);
        return odontologo;
    }

    @Override
    public Odontologo buscar(Integer id) {
        for(Odontologo odontologo: odontologos){
            if(odontologo.getId().equals(id)){
                LOGGER.info("Odontologo encontrado: "+ odontologo);
                return odontologo;
            }
        }
        LOGGER.info(("Odontologo no encontrado :c"));
        return null;
    }
    @Override
    public Odontologo eliminar(Integer id) {
        for (Odontologo odontologo : odontologos) {
            if (odontologo.getId().equals(id)) {
                odontologos.remove(odontologo);
                LOGGER.info("Odontologo eliminado: " + odontologo);
                return odontologo;
            }
        }
        LOGGER.info("Odontologo no encontrado para eliminar: " + id);
        return null;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return odontologos;
    }
}

