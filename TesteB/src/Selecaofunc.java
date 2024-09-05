import javax.swing.*;

import conexao.Conexao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Selecaofunc extends JFrame {
    private JComboBox<String> cbFuncionarios;
    private JButton btnProseguir;

    public Selecaofunc() {
        setTitle("Selecionar Funcionário - ECOMAP");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblFuncionario = new JLabel("Selecione o Funcionário:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelPrincipal.add(lblFuncionario, gbc);

        cbFuncionarios = new JComboBox<>(getFuncionarios().toArray(new String[0]));
        gbc.gridx = 1;
        painelPrincipal.add(cbFuncionarios, gbc);

        btnProseguir = new JButton("Prosseguir");
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelPrincipal.add(btnProseguir, gbc);

        btnProseguir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String funcionarioSelecionado = (String) cbFuncionarios.getSelectedItem();
                if (funcionarioSelecionado != null) {
                    new RastreamentoADM(funcionarioSelecionado).setVisible(true);
                    dispose();
                }
            }
        });

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private ArrayList<String> getFuncionarios() {
        ArrayList<String> funcionarios = new ArrayList<>();
        String sql = "SELECT nomefunc FROM funcionarios";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                funcionarios.add(rs.getString("nomefunc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionarios;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Selecaofunc().setVisible(true);
            }
        });
    }
}
