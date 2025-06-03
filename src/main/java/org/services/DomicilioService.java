package org.services;

import org.dao.DomicilioDAOImpl;
import org.entities.Domicilio;

import java.sql.SQLException;
import java.util.List;

public class DomicilioService implements GenericService<Domicilio> {

    private final DomicilioDAOImpl domicilioDAOImpl;

    public DomicilioService(DomicilioDAOImpl domicilioDAOImpl) {
        this.domicilioDAOImpl = domicilioDAOImpl;
    }

    @Override
    public void guardar(Domicilio domicilio) throws SQLException {
        validar(domicilio);
        domicilioDAOImpl.guardar(domicilio);
    }

    @Override
    public void actualizar(Domicilio domicilio) throws SQLException {
        validar(domicilio);
        domicilioDAOImpl.actualizar(domicilio);
    }

    @Override
    public void eliminar(long id) throws SQLException {
        domicilioDAOImpl.eliminar(id);
    }

    @Override
    public Domicilio buscarPorId(long id) throws SQLException {
        return domicilioDAOImpl.buscarPorId(id);
    }

    @Override
    public List<Domicilio> buscarTodos() throws SQLException {
        return domicilioDAOImpl.buscarTodos();
    }

    private void validar(Domicilio d) {
        if (d.getCalle() == null || d.getCalle().isEmpty()) throw new IllegalArgumentException("Calle vacía");
        if (d.getNumero() == null || d.getNumero() <= 0) throw new IllegalArgumentException("Número inválido");
        if (d.getLocalidad() == null || d.getLocalidad().isEmpty()) throw new IllegalArgumentException("Localidad vacía");
        if (d.getProvincia() == null || d.getProvincia().isEmpty()) throw new IllegalArgumentException("Provincia vacía");
        if (d.getPais() == null || d.getPais().isEmpty()) throw new IllegalArgumentException("País vacío");
    }
}
