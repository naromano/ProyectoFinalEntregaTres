package org.services;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T> {
    void guardar(T t) throws SQLException;
    void actualizar(T t) throws SQLException;
    void eliminar(long id) throws SQLException;
    T buscarPorId(long id) throws SQLException;
    List<T> buscarTodos() throws SQLException;
}
