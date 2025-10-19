package mz.oficina.AutoCheck.controller;

import mz.oficina.AutoCheck.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label lblUsuario; // mostra o nome do usuário logado

    private Usuario usuario;

    // Método para receber o usuário logado do App.java
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (lblUsuario != null) {
            lblUsuario.setText("Bem-vindo, " + usuario.getNome() + " (" + usuario.getNivelAcesso() + ")");
        }
    }

    @FXML
    private void abrirClientes() {
        abrirTela("clientes.fxml", "Gestão de Clientes");
    }

    @FXML
    private void abrirVeiculos() {
        abrirTela("veiculos.fxml", "Gestão de Veículos");
    }

    @FXML
    private void sair() {
        System.exit(0);
    }

    private void abrirTela(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mz/oficina/AutoCheck/" + fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
