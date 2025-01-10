import conexao.Conexao;
import entity.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;

public class RastreamentoADM extends JFrame {
    private JComboBox<String> comboResiduos;
    private JTextArea txtQuantidade;
    private JTextArea txtEndereco;
    private JTextArea txtVoluntario;
    @SuppressWarnings("unused")
    private String funcionarioSelecionado;
    private int codPontoColeta;

    public RastreamentoADM(String funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
        this.codPontoColeta = getCodPontoColetaPorFuncionario(funcionarioSelecionado);
        String basePath = System.getProperty("user.dir");

        setTitle("Rastreamento de Resíduos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        String imagePath = basePath + File.separator + "images" + File.separator + "logoc.png";
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Rastreamento de Resíduos");
        titleLabel.setBounds(140, 30, 600, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Acompanhe os resíduos coletados");
        subtitleLabel.setBounds(140, 70, 600, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(50, 200, 700, 300);
        centerPanel.setLayout(new GridLayout(5, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel);

        JLabel lblResiduos = new JLabel("Resíduos Coletados:");
        comboResiduos = new JComboBox<>();
        customizeComboBox(comboResiduos);
        centerPanel.add(lblResiduos);
        centerPanel.add(comboResiduos);

        JLabel lblQuantidade = new JLabel("Quantidade Coletada:");
        txtQuantidade = new JTextArea();
        txtQuantidade.setEditable(false);
        centerPanel.add(lblQuantidade);
        centerPanel.add(txtQuantidade);

        JLabel lblEndereco = new JLabel("Endereço Ponto Coleta:");
        txtEndereco = new JTextArea();
        txtEndereco.setLineWrap(true);
        txtEndereco.setWrapStyleWord(true);
        txtEndereco.setEditable(false);
        centerPanel.add(lblEndereco);
        centerPanel.add(txtEndereco);

        JLabel lblVoluntario = new JLabel("Voluntário:");
        txtVoluntario = new JTextArea();
        txtVoluntario.setLineWrap(true);
        txtVoluntario.setWrapStyleWord(true);
        txtVoluntario.setEditable(false);
        centerPanel.add(lblVoluntario);
        centerPanel.add(txtVoluntario);

        JButton btnDetalhes = new JButton("Voltar");
        btnDetalhes.setBackground(new Color(0, 102, 51));
        btnDetalhes.setForeground(Color.WHITE);
        centerPanel.add(new JLabel());
        centerPanel.add(btnDetalhes);

        loadResiduos();

        comboResiduos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedResiduos = (String) comboResiduos.getSelectedItem();
                if (selectedResiduos != null) {
                    mostrarDetalhes(selectedResiduos);
                }
            }
        });

        btnDetalhes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdmGUI();
            }
        });
        setVisible(true);
    }

    private void customizeComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51), 1));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setPreferredSize(new Dimension(200, 30));

        comboBox.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                if (isSelected) {
                    setBackground(new Color(0, 102, 51));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
                return this;
            }
        });
    }

    private void loadResiduos() {
        String query = "SELECT tiporesiduo FROM rastreamento WHERE codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codPontoColeta);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum resíduo encontrado para o ponto de coleta.");
            }
            while (rs.next()) {
                String tipoResiduo = rs.getString("tiporesiduo");
                comboResiduos.addItem(tipoResiduo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalhes(String tiporesiduo) {
        String query = "SELECT * FROM rastreamento r "
                     + "JOIN funcionarios f ON r.codpontocoleta = f.codpontocoleta "
                     + "WHERE r.tiporesiduo = ? AND r.codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, tiporesiduo);
            statement.setInt(2, codPontoColeta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String quantColetada = rs.getString("quantcoletada");
                int codVol = rs.getInt("codvol");

                pontosdecoleta pontoColeta = getPontoColeta(codPontoColeta);
                voluntarios voluntario = getVoluntario(codVol);

                txtQuantidade.setText(quantColetada);

                if (pontoColeta != null) {
                    txtEndereco.setText(pontoColeta.getEndpontocoleta() + " - " + pontoColeta.getBairropontocoleta() + " - SP");
                }

                if (voluntario != null) {
                    txtVoluntario.setText(voluntario.getNomevol() + "\nCPF: " + voluntario.getCpfvol());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCodPontoColetaPorFuncionario(String nomeFuncionario) {
        String query = "SELECT codpontocoleta FROM funcionarios WHERE nomefunc = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, nomeFuncionario);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("codpontocoleta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private pontosdecoleta getPontoColeta(int codpontocoleta) {
        String query = "SELECT * FROM pontosdecoleta WHERE codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codpontocoleta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                pontosdecoleta pontoColeta = new pontosdecoleta();
                pontoColeta.setCeppontocoleta(rs.getString("ceppontocoleta"));
                pontoColeta.setEndpontocoleta(rs.getString("endpontocoleta"));
                pontoColeta.setBairropontocoleta(rs.getString("bairropontocoleta"));
                return pontoColeta;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private voluntarios getVoluntario(int codvol) {
        String query = "SELECT * FROM voluntarios WHERE codvol = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codvol);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                voluntarios voluntario = new voluntarios();
                voluntario.setNomevol(rs.getString("nomevol"));
                voluntario.setCpfvol(rs.getString("cpfvol"));
                return voluntario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // public static void main(String[] args) {
    //     new RastreamentoADM("Nome do Funcionario");
    // }
}