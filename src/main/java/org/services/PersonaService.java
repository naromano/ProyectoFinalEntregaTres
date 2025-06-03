package org.services;

import org.dao.PersonaDAOImpl;
import org.entities.Persona;

import java.sql.SQLException;
import java.util.List;

public class PersonaService implements GenericService<Persona> {

    private final PersonaDAOImpl personaDAOImpl;

    public PersonaService(PersonaDAOImpl personaDAO) {
        this.personaDAOImpl = personaDAO;
    }

    @Override
    public void guardar(Persona persona) throws SQLException {
        validar(persona);
        personaDAOImpl.guardar(persona);
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        validar(persona);
        personaDAOImpl.actualizar(persona);
    }

    @Override
    public void eliminar(long id) throws SQLException {
        personaDAOImpl.eliminar(id);
    }

    @Override
    public Persona buscarPorId(long id) throws SQLException {
        return personaDAOImpl.buscarPorId(id);
    }

    @Override
    public List<Persona> buscarTodos() throws SQLException {
        return personaDAOImpl.buscarTodos();
    }

    private void validar(Persona p)  {
        if (p.getNombre() == null || p.getNombre().isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        if (p.getApellido() == null || p.getApellido().isEmpty()) throw new IllegalArgumentException("Apellido vacío");
        if (p.getDni() == null || p.getDni().isEmpty()) throw new IllegalArgumentException("DNI vacío");
        if (p.getDomicilio() == null || p.getDomicilio().getId() == 0) throw new IllegalArgumentException("Domicilio obligatorio");
    }
}
