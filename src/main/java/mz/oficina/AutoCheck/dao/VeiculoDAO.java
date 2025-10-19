package mz.oficina.AutoCheck.dao;

import mz.oficina.AutoCheck.model.Veiculo;
import mz.oficina.AutoCheck.model.Cliente;
import mz.oficina.AutoCheck.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public boolean salvar(Veiculo v) {
        String sql;
        boolean insercao = (v.getId() == 0);
        if (insercao) {
            sql = "INSERT INTO veiculos (matricula, marca, modelo, cliente_id) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE veiculos SET matricula=?, marca=?, modelo=?, cliente_id=? WHERE id=?";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, v.getMatricula());
            stmt.setString(2, v.getMarca());
            stmt.setString(3, v.getModelo());
            stmt.setInt(4, v.getCliente().getId());

            if (!insercao) stmt.setInt(5, v.getId());

            int affected = stmt.executeUpdate();
            if (insercao) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) v.setId(rs.getInt(1));
            }
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(Veiculo v) {
        String sql = "DELETE FROM veiculos WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, v.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Veiculo> listarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.id as cid, c.nome as cnome, c.telefone, c.email " +
                "FROM veiculos v JOIN clientes c ON v.cliente_id=c.id";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("cid"),
                        rs.getString("cnome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                Veiculo v = new Veiculo(
                        rs.getInt("id"),
                        rs.getString("matricula"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        c
                );
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
