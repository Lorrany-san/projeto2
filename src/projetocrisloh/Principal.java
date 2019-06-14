package projetocrisloh;

import java.awt.EventQueue;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import projetocrisloh.IFCategoria;

public class Principal {

    public Principal() {

    }

    public static void main(String[] args) throws SQLException {
        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {}

        new IFCategoria().setVisible(true);
    }
}
