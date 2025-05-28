package org.dao;

import config.DataBaseConnection;
import org.entities.Domicilio;
import org.entities.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {

    private final DomicilioDAO domicilioDAO = new DomicilioDAOImpl();

    @Override
    public void guardar(Persona persona) throws SQLException {
        Domicilio dom = persona.getDomicilio();
        if (dom != null && dom.getId() == null) {
            domicilioDAO.guardar(dom);
        }

        String sql = "INSERT INTO persona (nombre, apellido, dni, domicilio_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getDni());
            if (dom != null) {
                ps.setLong(4, dom.getId());
            } else {
                ps.setNull(4, Types.BIGINT);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                persona.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        Domicilio dom = persona.getDomicilio();
        if (dom != null && dom.getId() != null) {
            domicilioDAO.actualizar(dom);
        }

        String sql = "UPDATE persona SET nombre=?, apellido=?, dni=?, domicilio_id=? WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getDni());
            if (dom != null) {
                ps.setLong(4, dom.getId());
            } else {
                ps.setNull(4, Types.BIGINT);
            }
            ps.setLong(5, persona.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Persona buscarPorId(long id) throws SQLException {
        String sql = "SELECT p.*, d.id AS dom_id, d.calle, d.numero, d.localidad, d.provincia, d.pais " +
                "FROM persona p LEFT JOIN domicilio d ON p.domicilio_id = d.id WHERE p.id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Domicilio domicilio = Domicilio.builder()
                        .id(rs.getLong("dom_id"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getInt("numero"))
                        .localidad(rs.getString("localidad"))
                        .provincia(rs.getString("provincia"))
                        .pais(rs.getString("pais"))
                        .build();

                return Persona.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .dni(rs.getString("dni"))
                        .domicilio(domicilio)
                        .build();
            }
        }
        return null;
    }

    @Override
    public List<Persona> buscarTodos() throws SQLException {
        String sql = "SELECT p.*, d.id AS dom_id, d.calle, d.numero, d.localidad, d.provincia, d.pais " +
                "FROM persona p LEFT JOIN domicilio d ON p.domicilio_id = d.id";
        List<Persona> lista = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Domicilio domicilio = Domicilio.builder()
                        .id(rs.getLong("dom_id"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getInt("numero"))
                        .localidad(rs.getString("localidad"))
                        .provincia(rs.getString("provincia"))
                        .pais(rs.getString("pais"))
                        .build();

                Persona persona = Persona.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .dni(rs.getString("dni"))
                        .domicilio(domicilio)
                        .build();

                lista.add(persona);
            }
        }
        return lista;
    }
}
