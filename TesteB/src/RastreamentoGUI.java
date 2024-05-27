import conexao.Conexao;
import entity.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RastreamentoGUI extends JFrame {
    private JComboBox<String> comboResiduos;
    private JTextArea txtQuantidade;
    private JTextArea txtEndereco;
    private JTextArea txtVoluntario;
    private JButton btnDetalhes;

    public RastreamentoGUI() {
        setTitle("Rastreamento de Resíduos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblResiduos = new JLabel("Resíduos Coletados:");
        lblResiduos.setBounds(30, 30, 150, 25);
        add(lblResiduos);

        comboResiduos = new JComboBox<>();
        comboResiduos.setBounds(180, 30, 170, 25);
        add(comboResiduos);

        JLabel lblQuantidade = new JLabel("Quantidade Coletada:");
        lblQuantidade.setBounds(30, 70, 150, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextArea();
        txtQuantidade.setBounds(180, 70, 170, 25);
        txtQuantidade.setEditable(false);
        add(txtQuantidade);

        JLabel lblEndereco = new JLabel("Endereço Ponto Coleta:");
        lblEndereco.setBounds(30, 110, 150, 25);
        add(lblEndereco);

        txtEndereco = new JTextArea();
        txtEndereco.setBounds(180, 110, 170, 60);
        txtEndereco.setLineWrap(true);
        txtEndereco.setWrapStyleWord(true);
        txtEndereco.setEditable(false);
        add(txtEndereco);

        JLabel lblVoluntario = new JLabel("Voluntário:");
        lblVoluntario.setBounds(30, 180, 150, 25);
        add(lblVoluntario);

        txtVoluntario = new JTextArea();
        txtVoluntario.setBounds(180, 180, 170, 60);
        txtVoluntario.setLineWrap(true);
        txtVoluntario.setWrapStyleWord(true);
        txtVoluntario.setEditable(false);
        add(txtVoluntario);

        btnDetalhes = new JButton("Mostrar Detalhes");
        btnDetalhes.setBounds(130, 250, 150, 25);
        add(btnDetalhes);

        loadResiduos();

        btnDetalhes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedResiduos = (String) comboResiduos.getSelectedItem();
                if (selectedResiduos != null) {
                    mostrarDetalhes(selectedResiduos);
                }
            }
        });

        setVisible(true);
    }

    private void loadResiduos() {
        String query = "SELECT tiporesiduo FROM rastreamento";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                comboResiduos.addItem(rs.getString("tiporesiduo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalhes(String tiporesiduo) {
        String query = "SELECT * FROM rastreamento WHERE tiporesiduo = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, tiporesiduo);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                txtQuantidade.setText(rs.getString("quantcoletares"));
                int codPontoColeta = rs.getInt("codpontocoleta");
                int codVol = rs.getInt("codvol");

                pontosdecoleta pontoColeta = getPontoColeta(codPontoColeta);
                voluntarios voluntario = getVoluntario(codVol);

                if (pontoColeta != null) {
                    txtEndereco.setText(pontoColeta.getEndpontocoleta());
                }

                if (voluntario != null) {
                    txtVoluntario.setText(voluntario.getNomevol() + "\nCPF: " + voluntario.getCpfvol());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private pontosdecoleta getPontoColeta(int codpontocoleta) {
        String query = "SELECT * FROM pontosdecoleta WHERE codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codpontocoleta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                pontosdecoleta pontoColeta = new pontosdecoleta();
                pontoColeta.setCeppontocoleta("codpontocoleta");
                pontoColeta.setEndpontocoleta(rs.getString("endpontocoleta"));
                return pontoColeta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private voluntarios getVoluntario(int codVol) {
        String query = "SELECT * FROM voluntarios WHERE codvol = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codVol);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                voluntarios voluntario = new voluntarios();
                voluntario.setCodvol(codVol);
                voluntario.setNomevol(rs.getString("nomevol"));
                voluntario.setCpfvol(rs.getString("cpfvol"));
                return voluntario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        new RastreamentoGUI();
    }
}
