import conexao.Conexao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RastreamentoGUI extends JFrame {
    private JComboBox<String> comboResiduos;
    private JTextArea txtQuantidade;
    private JTextArea txtEndereco;
    private JTextArea txtVoluntario;
    private String cpfUsuarioLogado;
    private int codPontoColetaFuncionario;

    public RastreamentoGUI(String cpf) {
        this.cpfUsuarioLogado = cpf;
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

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(20, 25, 100, 100);
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

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(0, 102, 51));
        btnVoltar.setForeground(Color.WHITE);
        centerPanel.add(new JLabel());
        centerPanel.add(btnVoltar);

        loadFuncionarioDetails();

        comboResiduos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedResiduos = (String) comboResiduos.getSelectedItem();
                if (selectedResiduos != null) {
                    mostrarDetalhes(selectedResiduos);
                }
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaFunc(cpfUsuarioLogado);
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

    private void loadFuncionarioDetails() {
        String query = "SELECT codpontocoleta FROM funcionarios WHERE cpffunc = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpfUsuarioLogado);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                codPontoColetaFuncionario = rs.getInt("codpontocoleta");
                loadResiduos();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadResiduos() {
        String query = "SELECT DISTINCT tiporesiduo FROM rastreamento WHERE codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codPontoColetaFuncionario);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum resíduo encontrado na tabela rastreamento.");
            }
            while (rs.next()) {
                comboResiduos.addItem(rs.getString("tiporesiduo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalhes(String tiporesiduo) {
        String query = "SELECT quantcoletada, codvol FROM rastreamento WHERE tiporesiduo = ? AND codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, tiporesiduo);
            statement.setInt(2, codPontoColetaFuncionario);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String quantcoletada = rs.getString("quantcoletada");
                int codvol = rs.getInt("codvol");

                txtQuantidade.setText(quantcoletada);

                String pontoColetaQuery = "SELECT endpontocoleta, ceppontocoleta, bairropontocoleta FROM pontosdecoleta WHERE codpontocoleta = ?";
                try (PreparedStatement pontoColetaStmt = Conexao.getConexao().prepareStatement(pontoColetaQuery)) {
                    pontoColetaStmt.setInt(1, codPontoColetaFuncionario);
                    ResultSet pontoColetaRs = pontoColetaStmt.executeQuery();
                    if (pontoColetaRs.next()) {
                        String endereco = pontoColetaRs.getString("endpontocoleta");
                        String cep = pontoColetaRs.getString("ceppontocoleta");
                        String bairro = pontoColetaRs.getString("bairropontocoleta");
                        txtEndereco.setText(endereco + ", " + bairro + " - " + cep);
                    }
                }

                String voluntarioQuery = "SELECT nomevol, cpfvol FROM voluntarios WHERE codvol = ?";
                try (PreparedStatement voluntarioStmt = Conexao.getConexao().prepareStatement(voluntarioQuery)) {
                    voluntarioStmt.setInt(1, codvol);
                    ResultSet voluntarioRs = voluntarioStmt.executeQuery();
                    if (voluntarioRs.next()) {
                        String nomeVoluntario = voluntarioRs.getString("nomevol");
                        String cpfVoluntario = voluntarioRs.getString("cpfvol");
                        txtVoluntario.setText(nomeVoluntario + "\n CPF: " + cpfVoluntario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String cpfUsuarioLogado = "user's CPF here";
        new RastreamentoGUI(cpfUsuarioLogado);
    }
}
