package dh.backend.dao.impl;

import dh.backend.dao.IDao;
import dh.backend.model.Odontologo;
import org.apache.log4j.Logger;
import dh.backend.db.H2Connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES(DEFAULT, ?, ?, ?)";
    private static String SQL_SELECT_ID = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    private static String SQL_DELETE_ID = "DELETE FROM ODONTOLOGOS WHERE ID = ?";
    private static String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoARetornar = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(),
                        odontologo.getApellido());
            }
            LOGGER.info("Odontologo registrado: "+ odontologoARetornar);
            connection.commit();
            connection.setAutoCommit(true);
        }catch (Exception e){
            if(connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                }
            }
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoARetornar;
    }
    @Override
    public Odontologo buscar(Integer id) {
        Connection connection = null;
        Odontologo odontologoARetornar = null;
        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer idDevuelto = resultSet.getInt(1);
                String matricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                odontologoARetornar = new Odontologo(idDevuelto, matricula, nombre, apellido);
            }
            LOGGER.info("El odontologo encontrado es: "+ odontologoARetornar);

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoARetornar;
    }
    @Override
    public Odontologo eliminar(Integer id) {
        Connection connection = null;
        Odontologo odontologoEliminado = null;
        try{
            connection = H2Connection.getConnection();
            // Primero, selecciona el registro
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM ODONTOLOGOS WHERE ID = ?");
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                // Crea un objeto Odontologo a partir del registro seleccionado
                String matricula = resultSet.getString("MATRICULA");
                String nombre = resultSet.getString("NOMBRE");
                String apellido = resultSet.getString("APELLIDO");
                odontologoEliminado = new Odontologo(id, matricula, nombre, apellido);
                // Luego, elimina el registro
                PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_ID);
                deleteStatement.setInt(1, id);
                int borrar_reg = deleteStatement.executeUpdate();
                if (borrar_reg > 0) {
                    LOGGER.info("El avión eliminado es: "+ odontologoEliminado);
                }
            } else {
                LOGGER.info("No se encontró ningún registro con el ID: "+ id);
            }

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoEliminado;
    }@Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                Integer idDevuelto = resultSet.getInt(1);
                String matricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                Odontologo odontologo = new Odontologo(idDevuelto, matricula, nombre, apellido);
                LOGGER.info("El odontologo listado es: " + odontologo);
                odontologos.add(odontologo);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologos;
    }

}