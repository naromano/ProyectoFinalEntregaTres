package org.dao;

import config.DataBaseConnection;
import org.entities.Domicilio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAOImpl implements DomicilioDAO {

    @Override
    public void guardar(Domicilio domicilio) throws SQLException {
        String sql = "INSERT INTO domicilio (calle, numero, localidad, provincia, pais) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.setString(5, domicilio.getPais());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                domicilio.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public void actualizar(Domicilio domicilio) throws SQLException {
        String sql = "UPDATE domicilio SET calle=?, numero=?, localidad=?, provincia=?, pais=? WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.setString(5, domicilio.getPais());
            ps.setLong(6, domicilio.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id) throws SQLException {
        String sql = "DELETE FROM domicilio WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Domicilio buscarPorId(long id) throws SQLException {
        String sql = "SELECT * FROM domicilio WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Domicilio.builder()
                        .id(rs.getLong("id"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getInt("numero"))
                        .localidad(rs.getString("localidad"))
                        .provincia(rs.getString("provincia"))
                        .pais(rs.getString("pais"))
                        .build();
            }
        }
        return null;
    }

    @Override
    public List<Domicilio> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM domicilio";
        List<Domicilio> lista = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(Domicilio.builder()
                        .id(rs.getLong("id"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getInt("numero"))
                        .localidad(rs.getString("localidad"))
                        .provincia(rs.getString("provincia"))
                        .pais(rs.getString("pais"))
                        .build());
            }
        }
        return lista;
    }
}
