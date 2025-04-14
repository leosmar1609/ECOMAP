<<<<<<< HEAD
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import conexao.Conexao;
import java.awt.print.PrinterException;
import java.io.File;

public class RastreamentoGUI extends JFrame {
    private JComboBox<String> comboResiduos;
    private JEditorPane txtQuantidade;
    private JEditorPane txtEndereco;
    private JEditorPane txtVoluntario;
    private String cpfUsuarioLogado;
    private int codPontoColetaFuncionario;

    public RastreamentoGUI(String cpf) {
        this.cpfUsuarioLogado = cpf;
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
        centerPanel.setLayout(new GridLayout(6, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel);

        JLabel lblResiduos = new JLabel("Resíduos Coletados:");
        comboResiduos = new JComboBox<>();
        customizeComboBox(comboResiduos);
        centerPanel.add(lblResiduos);
        centerPanel.add(comboResiduos);

        JLabel lblQuantidade = new JLabel("Quantidade Coletada:");
        txtQuantidade = new JEditorPane();
        txtQuantidade.setContentType("text/html");
        txtQuantidade.setEditable(false);
        centerPanel.add(lblQuantidade);
        centerPanel.add(txtQuantidade);

        JLabel lblEndereco = new JLabel("Endereço Ponto Coleta:");
        txtEndereco = new JEditorPane();
        txtEndereco.setContentType("text/html");
        txtEndereco.setEditable(false);
        centerPanel.add(lblEndereco);
        centerPanel.add(txtEndereco);

        JLabel lblVoluntario = new JLabel("Voluntário:");
        txtVoluntario = new JEditorPane();
        txtVoluntario.setContentType("text/html");
        txtVoluntario.setEditable(false);
        centerPanel.add(lblVoluntario);
        centerPanel.add(txtVoluntario);

        JButton btnImprimirRelatorio = new JButton("Imprimir Relatório");
        btnImprimirRelatorio.setBackground(new Color(0, 102, 51));
        btnImprimirRelatorio.setForeground(Color.WHITE);
        centerPanel.add(new JLabel()); 
        centerPanel.add(btnImprimirRelatorio);
        
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

        btnImprimirRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioDeColetas();
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
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
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

    private void gerarRelatorioDeColetas() {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        StringBuilder relatorio = new StringBuilder("<html><h1>Relatório de Coletas</h1><ul>");

        String query = "SELECT r.tiporesiduo, r.quantcoletada, v.nomevol, " +
                "pc.endpontocoleta, pc.bairropontocoleta, pc.ceppontocoleta, " +
                "r.datahora_coleta " +
                "FROM rastreamento r " +
                "JOIN voluntarios v ON r.codvol = v.codvol " +
                "JOIN pontosdecoleta pc ON r.codpontocoleta = pc.codpontocoleta " +
                "WHERE r.codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codPontoColetaFuncionario);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                relatorio.append("<li>")
                        .append("<strong>Tipo de Resíduo:</strong> ").append(rs.getString("tiporesiduo")).append("<br>")
                        .append("<strong>Quantidade Coletada:</strong> ").append(rs.getString("quantcoletada"))
                        .append("<br>")
                        .append("<strong>Voluntário:</strong> ").append(rs.getString("nomevol")).append("<br>")
                        .append("<strong>Endereço Ponto Coleta:</strong> ")
                        .append(rs.getString("endpontocoleta")).append(", ")
                        .append(rs.getString("bairropontocoleta")).append(" - ")
                        .append(rs.getString("ceppontocoleta")).append("<br>")
                        .append("<strong>Data e Hora da Coleta:</strong> ")
                        .append(rs.getTimestamp("datahora_coleta")).append("<br><br>")
                        .append("</li>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        relatorio.append("</ul></html>");
        textPane.setText(relatorio.toString());

        try {
            boolean complete = textPane.print();
            if (complete) {
                JOptionPane.showMessageDialog(null, "Relatório impresso com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Impressão cancelada.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

private Map<String, Integer> residuosMap = new HashMap<>();

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
    String query = "SELECT codcoleta, tiporesiduo FROM rastreamento WHERE codpontocoleta = ?";
    try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
        statement.setInt(1, codPontoColetaFuncionario);
        ResultSet rs = statement.executeQuery();
        if (!rs.isBeforeFirst()) {
            System.out.println("Nenhum resíduo encontrado na tabela rastreamento.");
        }
        
        residuosMap.clear();
        comboResiduos.removeAllItems();
        
        while (rs.next()) {
            int codColeta = rs.getInt("codcoleta");
            String tipoResiduo = rs.getString("tiporesiduo");
            
            residuosMap.put(tipoResiduo + "-" + codColeta, codColeta);
            comboResiduos.addItem(tipoResiduo + " - Coleta " + codColeta); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void mostrarDetalhes(String selectedItem) {
    String[] parts = selectedItem.split(" - Coleta ");
    if (parts.length < 2) return;

    String tiporesiduo = parts[0];
    int codColetaSelecionado = Integer.parseInt(parts[1]);

    String query = "SELECT quantcoletada, codvol FROM rastreamento WHERE tiporesiduo = ? AND codcoleta = ?";
    try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
        statement.setString(1, tiporesiduo);
        statement.setInt(2, codColetaSelecionado);
        ResultSet rs = statement.executeQuery();
        
        if (rs.next()) {
            String quantcoletada = rs.getString("quantcoletada");
            int codvol = rs.getInt("codvol");

            txtQuantidade.setText("<html>Quantidade: " + quantcoletada + "</html>");

            String pontoColetaQuery = "SELECT endpontocoleta, ceppontocoleta, bairropontocoleta FROM pontosdecoleta WHERE codpontocoleta = ?";
            try (PreparedStatement pontoColetaStmt = Conexao.getConexao().prepareStatement(pontoColetaQuery)) {
                pontoColetaStmt.setInt(1, codPontoColetaFuncionario);
                ResultSet pontoColetaRs = pontoColetaStmt.executeQuery();
                if (pontoColetaRs.next()) {
                    String endereco = pontoColetaRs.getString("endpontocoleta");
                    String cep = pontoColetaRs.getString("ceppontocoleta");
                    String bairro = pontoColetaRs.getString("bairropontocoleta");
                    txtEndereco.setText("<html>" + endereco + ", " + bairro + " - " + cep + "</html>");
                }
            }

            String voluntarioQuery = "SELECT nomevol, cpfvol FROM voluntarios WHERE codvol = ?";
            try (PreparedStatement voluntarioStmt = Conexao.getConexao().prepareStatement(voluntarioQuery)) {
                voluntarioStmt.setInt(1, codvol);
                ResultSet voluntarioRs = voluntarioStmt.executeQuery();
                if (voluntarioRs.next()) {
                    String nomevol = voluntarioRs.getString("nomevol");
                    String cpfvol = voluntarioRs.getString("cpfvol");
                    txtVoluntario.setText("<html>" + nomevol + " - CPF: " + cpfvol + "</html>");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new RastreamentoGUI("12345678900"));
    // }
=======
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import conexao.Conexao;
import java.awt.print.PrinterException;
import java.io.File;

public class RastreamentoGUI extends JFrame {
    private JComboBox<String> comboResiduos;
    private JEditorPane txtQuantidade;
    private JEditorPane txtEndereco;
    private JEditorPane txtVoluntario;
    private String cpfUsuarioLogado;
    private int codPontoColetaFuncionario;

    public RastreamentoGUI(String cpf) {
        this.cpfUsuarioLogado = cpf;
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
        centerPanel.setLayout(new GridLayout(6, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel);

        JLabel lblResiduos = new JLabel("Resíduos Coletados:");
        comboResiduos = new JComboBox<>();
        customizeComboBox(comboResiduos);
        centerPanel.add(lblResiduos);
        centerPanel.add(comboResiduos);

        JLabel lblQuantidade = new JLabel("Quantidade Coletada:");
        txtQuantidade = new JEditorPane();
        txtQuantidade.setContentType("text/html");
        txtQuantidade.setEditable(false);
        centerPanel.add(lblQuantidade);
        centerPanel.add(txtQuantidade);

        JLabel lblEndereco = new JLabel("Endereço Ponto Coleta:");
        txtEndereco = new JEditorPane();
        txtEndereco.setContentType("text/html");
        txtEndereco.setEditable(false);
        centerPanel.add(lblEndereco);
        centerPanel.add(txtEndereco);

        JLabel lblVoluntario = new JLabel("Voluntário:");
        txtVoluntario = new JEditorPane();
        txtVoluntario.setContentType("text/html");
        txtVoluntario.setEditable(false);
        centerPanel.add(lblVoluntario);
        centerPanel.add(txtVoluntario);

        JButton btnImprimirRelatorio = new JButton("Imprimir Relatório");
        btnImprimirRelatorio.setBackground(new Color(0, 102, 51));
        btnImprimirRelatorio.setForeground(Color.WHITE);
        centerPanel.add(new JLabel()); 
        centerPanel.add(btnImprimirRelatorio);
        
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

        btnImprimirRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioDeColetas();
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
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
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

    private void gerarRelatorioDeColetas() {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        StringBuilder relatorio = new StringBuilder("<html><h1>Relatório de Coletas</h1><ul>");

        String query = "SELECT r.tiporesiduo, r.quantcoletada, v.nomevol, " +
                "pc.endpontocoleta, pc.bairropontocoleta, pc.ceppontocoleta, " +
                "r.datahora_coleta " +
                "FROM rastreamento r " +
                "JOIN voluntarios v ON r.codvol = v.codvol " +
                "JOIN pontosdecoleta pc ON r.codpontocoleta = pc.codpontocoleta " +
                "WHERE r.codpontocoleta = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setInt(1, codPontoColetaFuncionario);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                relatorio.append("<li>")
                        .append("<strong>Tipo de Resíduo:</strong> ").append(rs.getString("tiporesiduo")).append("<br>")
                        .append("<strong>Quantidade Coletada:</strong> ").append(rs.getString("quantcoletada"))
                        .append("<br>")
                        .append("<strong>Voluntário:</strong> ").append(rs.getString("nomevol")).append("<br>")
                        .append("<strong>Endereço Ponto Coleta:</strong> ")
                        .append(rs.getString("endpontocoleta")).append(", ")
                        .append(rs.getString("bairropontocoleta")).append(" - ")
                        .append(rs.getString("ceppontocoleta")).append("<br>")
                        .append("<strong>Data e Hora da Coleta:</strong> ")
                        .append(rs.getTimestamp("datahora_coleta")).append("<br><br>")
                        .append("</li>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        relatorio.append("</ul></html>");
        textPane.setText(relatorio.toString());

        try {
            boolean complete = textPane.print();
            if (complete) {
                JOptionPane.showMessageDialog(null, "Relatório impresso com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Impressão cancelada.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

private Map<String, Integer> residuosMap = new HashMap<>();

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
    String query = "SELECT codcoleta, tiporesiduo FROM rastreamento WHERE codpontocoleta = ?";
    try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
        statement.setInt(1, codPontoColetaFuncionario);
        ResultSet rs = statement.executeQuery();
        if (!rs.isBeforeFirst()) {
            System.out.println("Nenhum resíduo encontrado na tabela rastreamento.");
        }
        
        residuosMap.clear();
        comboResiduos.removeAllItems();
        
        while (rs.next()) {
            int codColeta = rs.getInt("codcoleta");
            String tipoResiduo = rs.getString("tiporesiduo");
            
            residuosMap.put(tipoResiduo + "-" + codColeta, codColeta);
            comboResiduos.addItem(tipoResiduo + " - Coleta " + codColeta); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void mostrarDetalhes(String selectedItem) {
    String[] parts = selectedItem.split(" - Coleta ");
    if (parts.length < 2) return;

    String tiporesiduo = parts[0];
    int codColetaSelecionado = Integer.parseInt(parts[1]);

    String query = "SELECT quantcoletada, codvol FROM rastreamento WHERE tiporesiduo = ? AND codcoleta = ?";
    try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
        statement.setString(1, tiporesiduo);
        statement.setInt(2, codColetaSelecionado);
        ResultSet rs = statement.executeQuery();
        
        if (rs.next()) {
            String quantcoletada = rs.getString("quantcoletada");
            int codvol = rs.getInt("codvol");

            txtQuantidade.setText("<html>Quantidade: " + quantcoletada + "</html>");

            String pontoColetaQuery = "SELECT endpontocoleta, ceppontocoleta, bairropontocoleta FROM pontosdecoleta WHERE codpontocoleta = ?";
            try (PreparedStatement pontoColetaStmt = Conexao.getConexao().prepareStatement(pontoColetaQuery)) {
                pontoColetaStmt.setInt(1, codPontoColetaFuncionario);
                ResultSet pontoColetaRs = pontoColetaStmt.executeQuery();
                if (pontoColetaRs.next()) {
                    String endereco = pontoColetaRs.getString("endpontocoleta");
                    String cep = pontoColetaRs.getString("ceppontocoleta");
                    String bairro = pontoColetaRs.getString("bairropontocoleta");
                    txtEndereco.setText("<html>" + endereco + ", " + bairro + " - " + cep + "</html>");
                }
            }

            String voluntarioQuery = "SELECT nomevol, cpfvol FROM voluntarios WHERE codvol = ?";
            try (PreparedStatement voluntarioStmt = Conexao.getConexao().prepareStatement(voluntarioQuery)) {
                voluntarioStmt.setInt(1, codvol);
                ResultSet voluntarioRs = voluntarioStmt.executeQuery();
                if (voluntarioRs.next()) {
                    String nomevol = voluntarioRs.getString("nomevol");
                    String cpfvol = voluntarioRs.getString("cpfvol");
                    txtVoluntario.setText("<html>" + nomevol + " - CPF: " + cpfvol + "</html>");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new RastreamentoGUI("12345678900"));
    // }
>>>>>>> 61b75dfac6d30311dee39921307db1f08f788225
}