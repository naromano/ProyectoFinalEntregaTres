package org.services;

import org.dao.PersonaDAO;
import org.entities.Persona;

import java.sql.SQLException;
import java.util.List;

public class PersonaService implements GenericService<Persona> {

    private final PersonaDAO personaDAO;

    public PersonaService(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    @Override
    public void guardar(Persona persona) throws SQLException {
        validar(persona);
        personaDAO.guardar(persona);
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        validar(persona);
        personaDAO.actualizar(persona);
    }

    @Override
    public void eliminar(long id) throws SQLException {
        personaDAO.eliminar(id);
    }

    @Override
    public Persona buscarPorId(long id) throws SQLException {
        return personaDAO.buscarPorId(id);
    }

    @Override
    public List<Persona> buscarTodos() throws SQLException {
        return personaDAO.buscarTodos();
    }

    private void validar(Persona p) {
        if (p.getNombre() == null || p.getNombre().isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        if (p.getApellido() == null || p.getApellido().isEmpty()) throw new IllegalArgumentException("Apellido vacío");
        if (p.getDni() == null || p.getDni().isEmpty()) throw new IllegalArgumentException("DNI vacío");
        if (p.getDomicilio() == null) throw new IllegalArgumentException("Domicilio obligatorio");
    }
}
