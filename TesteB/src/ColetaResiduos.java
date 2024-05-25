import DAO.ecobancodao;
import entity.rastreamento;
import entity.residuos;
import conexao.Conexao;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ColetaResiduos extends JFrame {
    private JComboBox<String> comboResiduos;
    private JLabel lblCategoria;
    private JLabel lblDescricao;
    private JLabel lblDescarte;
    private JTextArea txtCategoria;
    private JTextArea txtDescricao;
    private JTextArea txtDescarte;
    private JTextField txtQuantidade;
    private JComboBox<String> comboBairros;
    private JButton btnColetar;
    private String cpfUsuarioLogado;

    public ColetaResiduos(String cpf) {
        this.cpfUsuarioLogado = cpf;
        setTitle("Coleta de Resíduos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblResiduos = new JLabel("Tipo de Resíduo:");
        lblResiduos.setBounds(30, 30, 120, 25);
        add(lblResiduos);

        comboResiduos = new JComboBox<>();
        comboResiduos.setBounds(150, 30, 200, 25);
        add(comboResiduos);

        lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(30, 70, 120, 25);
        add(lblCategoria);

        txtCategoria = new JTextArea();
        txtCategoria.setBounds(150, 70, 200, 25);
        txtCategoria.setEditable(false);
        add(txtCategoria);

        lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(30, 110, 120, 25);
        add(lblDescricao);

        txtDescricao = new JTextArea();
        txtDescricao.setBounds(150, 110, 200, 60);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setEditable(false);
        add(txtDescricao);

        lblDescarte = new JLabel("Descarte:");
        lblDescarte.setBounds(30, 180, 120, 25);
        add(lblDescarte);

        txtDescarte = new JTextArea();
        txtDescarte.setBounds(150, 180, 200, 60);
        txtDescarte.setLineWrap(true);
        txtDescarte.setWrapStyleWord(true);
        txtDescarte.setEditable(false);
        add(txtDescarte);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(30, 250, 120, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(150, 250, 200, 25);
        add(txtQuantidade);

        JLabel lblBairros = new JLabel("Bairro do Ponto de Coleta:");
        lblBairros.setBounds(30, 290, 150, 25);
        add(lblBairros);

        comboBairros = new JComboBox<>();
        comboBairros.setBounds(180, 290, 170, 25);
        add(comboBairros);

        btnColetar = new JButton("Coletar");
        btnColetar.setBounds(150, 330, 100, 25);
        add(btnColetar);

        loadResiduos();

        loadPontosColeta();

        comboResiduos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTipo = (String) comboResiduos.getSelectedItem();
                if (selectedTipo != null) {
                    residuos res = coletarResiduos(selectedTipo);
                    if (res != null) {
                        txtCategoria.setText(res.getCategoria());
                        txtDescricao.setText(res.getDescricao());
                        txtDescarte.setText(res.getDescarte());
                    } else {
                        txtCategoria.setText("");
                        txtDescricao.setText("");
                        txtDescarte.setText("");
                    }
                }
            }
        });

        btnColetar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!cpfUsuarioLogado.startsWith("VOL")) {
                    JOptionPane.showMessageDialog(null, "Apenas voluntários podem realizar coletas.");
                    return;
                }

                String tiporesiduo = (String) comboResiduos.getSelectedItem();
                String quantcoletares = txtQuantidade.getText();
                String bairropontocoleta = (String) comboBairros.getSelectedItem();

                ecobancodao banco = new ecobancodao();

                int codresiduo = getCodResiduo(tiporesiduo);
                int codpontocoleta = getCodPontoColeta(bairropontocoleta);

                rastreamento rast = new rastreamento();
                rast.setCodresiduo(codresiduo);
                rast.setTiporesiduo(tiporesiduo);
                rast.setCodpontocoleta(codpontocoleta);
                rast.setQuantcoletares(quantcoletares);

                banco.inserirRastreamento(rast);

                JOptionPane.showMessageDialog(null, "Coleta realizada com sucesso!\nTipo de Resíduo: " + tiporesiduo +
                        "\nQuantidade: " + quantcoletares + "\nPonto de Coleta: " + bairropontocoleta);
            }
        });

        setVisible(true);
    }

    private void loadResiduos() {
        String query = "SELECT TIPORESIDUO FROM RESIDUOS";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                comboResiduos.addItem(rs.getString("TIPORESIDUO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPontosColeta() {
        String query = "SELECT BAIRROPONTOCOLETA FROM PONTOSDECOLETA";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                comboBairros.addItem(rs.getString("BAIRROPONTOCOLETA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public residuos coletarResiduos(String tiporesiduo) {
        residuos cvol = null;
        String alter = "SELECT * FROM RESIDUOS WHERE TIPORESIDUO = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(alter)) {
            statement.setString(1, tiporesiduo);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                cvol = new residuos();
                cvol.setTiporesiduo(rs.getString("TIPORESIDUO"));
                cvol.setCategoria(rs.getString("CATEGORIA"));
                cvol.setDescricao(rs.getString("DESCRICAO"));
                cvol.setDescarte(rs.getString("DESCARTE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cvol;
    }

    private int getCodResiduo(String tiporesiduo) {
        int codresiduo = -1;
        String query = "SELECT CODRESIDUO FROM RESIDUOS WHERE TIPORESIDUO = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, tiporesiduo);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                codresiduo = rs.getInt("CODRESIDUO");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codresiduo;
    }

    private int getCodPontoColeta(String bairropontocoleta) {
        int codpontocoleta = -1;
        String query = "SELECT CODPONTOCOLETA FROM PONTOSDECOLETA WHERE BAIRROPONTOCOLETA = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, bairropontocoleta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                codpontocoleta = rs.getInt("CODPONTOCOLETA");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codpontocoleta;
    }

    public static void main(String[] args) {
        new ColetaResiduos("CPF_TESTE"); // Para teste
    }
}