package mz.oficina.AutoCheck.dao;

import mz.oficina.AutoCheck.model.Cliente;
import mz.oficina.AutoCheck.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean salvar(Cliente c) {
        String sql;
        boolean insercao = (c.getId() == 0);
        if (insercao) {
            sql = "INSERT INTO clientes (nome, telefone, email) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE clientes SET nome=?, telefone=?, email=? WHERE id=?";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTelefone());
            stmt.setString(3, c.getEmail());

            if (!insercao) {
                stmt.setInt(4, c.getId());
            }

            int affected = stmt.executeUpdate();
            if (insercao) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) c.setId(rs.getInt(1));
            }
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(Cliente c) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
