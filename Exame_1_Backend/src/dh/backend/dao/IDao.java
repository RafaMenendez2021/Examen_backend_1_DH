package dh.backend.dao;

import java.util.List;

public interface IDao <T>{
    T registrar(T t);
    T buscar(Integer id);
    T eliminar(Integer id);
    List<T> buscarTodos();
}