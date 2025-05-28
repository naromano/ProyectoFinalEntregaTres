package org.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    void guardar(T t) throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(long id) throws SQLException;
    T buscarPorId(long id) throws SQLException;
    List<T> buscarTodos() throws SQLException;
}
