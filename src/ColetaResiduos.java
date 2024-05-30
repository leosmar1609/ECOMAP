import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import entity.*;
import DAO.*;
import conexao.Conexao;
import java.sql.*;

public class ColetaResiduos extends JFrame implements ActionListener {
    private JComboBox<String> comboResiduos, comboBairros;
    private JTextArea txtCategoria, txtDescricao, txtDescarte, endereco;
    private JTextField txtQuantidade;
    private JButton coletarButton, sairButton;
    private String cpfUsuarioLogado;
    private int codvol;

    public ColetaResiduos(String cpf) {
        super("Coleta de Resíduos");
        this.cpfUsuarioLogado = cpf;
        this.codvol = getCodVoluntario(cpf);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 30, 150, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Coleta de Resíduos");
        titleLabel.setBounds(200, 30, 400, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Informe os detalhes da coleta");
        subtitleLabel.setBounds(200, 70, 400, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        JLabel lblResiduos = new JLabel("Tipo de Resíduo:");
        lblResiduos.setBounds(250, 180, 120, 25);
        lblResiduos.setForeground(new Color(0, 102, 51));
        lblResiduos.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(lblResiduos);
        comboResiduos = new JComboBox<>();
        comboResiduos.setBounds(400, 180, 200, 25);
        add(comboResiduos);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(250, 220, 120, 25);
        lblCategoria.setForeground(new Color(0, 102, 51));
        lblCategoria.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(lblCategoria);
        txtCategoria = new JTextArea();
        txtCategoria.setBounds(400, 220, 200, 25);
        txtCategoria.setEditable(false);
        add(txtCategoria);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(250, 260, 120, 25);
        lblDescricao.setForeground(new Color(0, 102, 51));
        lblDescricao.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(lblDescricao);
        txtDescricao = new JTextArea();
        txtDescricao.setBounds(400, 260, 200, 60);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setEditable(false);
        add(txtDescricao);

        JLabel lblDescarte = new JLabel("Descarte:");
        lblDescarte.setBounds(250, 340, 120, 25);
        lblDescarte.setForeground(new Color(0, 102, 51));
        lblDescarte.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(lblDescarte);
        txtDescarte = new JTextArea();
        txtDescarte.setBounds(400, 340, 200, 60);
        txtDescarte.setLineWrap(true);
        txtDescarte.setWrapStyleWord(true);
        txtDescarte.setEditable(false);
        add(txtDescarte);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(250, 420, 120, 25);
        lblQuantidade.setForeground(new Color(0, 102, 51));
        lblQuantidade.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(lblQuantidade);
        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(400, 420, 200, 25);
        add(txtQuantidade);

        JLabel lblBairros = new JLabel("Bairro do Ponto de Coleta:");
        lblBairros.setBounds(250, 460, 160, 25);
        lblBairros.setForeground(new Color(0, 102, 51));
        lblBairros.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(lblBairros);
        comboBairros = new JComboBox<>();
        comboBairros.setBounds(400, 460, 200, 25);
        add(comboBairros);

        endereco = new JTextArea();
        endereco.setBounds(250, 500, 350, 40);
        endereco.setLineWrap(true);
        endereco.setWrapStyleWord(true);
        endereco.setEditable(false);
        add(endereco);

        coletarButton = createButton("Coletar", 350, 550, 100, 30);
        coletarButton.addActionListener(this);
        add(coletarButton);

        sairButton = createButton("Sair", 460, 550, 100, 30);
        sairButton.addActionListener(this);
        add(sairButton);

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

        comboBairros.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedBairro = (String) comboBairros.getSelectedItem();
                if (selectedBairro != null) {
                    pontosdecoleta pdc = Bairros(selectedBairro);
                    if (pdc != null) {
                        endereco.setText(pdc.getEndpontocoleta() + " - SP");
                    } else {
                        endereco.setText("");
                    }
                }
            }
        });

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 102, 51));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        button.setFont(new Font("Helvetica", Font.BOLD, 14));
        button.setBounds(x, y, width, height);
        button.setForeground(new Color(246, 234, 215));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
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

    private residuos coletarResiduos(String tiporesiduo) {
        residuos cvol = null;
        String query = "SELECT CATEGORIA, DESCRICAO, DESCARTE FROM RESIDUOS WHERE TIPORESIDUO = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, tiporesiduo);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                cvol = new residuos();
                cvol.setTiporesiduo(tiporesiduo);
                cvol.setCategoria(rs.getString("CATEGORIA"));
                cvol.setDescricao(rs.getString("DESCRICAO"));
                cvol.setDescarte(rs.getString("DESCARTE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cvol;
    }

    private pontosdecoleta Bairros(String bairropontocoleta) {
        pontosdecoleta pdc = null;
        String query = "SELECT ENDPONTOCOLETA FROM PONTOSDECOLETA WHERE BAIRROPONTOCOLETA = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, bairropontocoleta);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                pdc = new pontosdecoleta();
                pdc.setEndpontocoleta(rs.getString("ENDPONTOCOLETA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pdc;
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

    private int getCodVoluntario(String cpfvol) {
        int codvol = -1;
        String query = "SELECT CODVOL FROM VOLUNTARIOS WHERE CPFVOL = ?";
        try (PreparedStatement statement = Conexao.getConexao().prepareStatement(query)) {
            statement.setString(1, cpfvol);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                codvol = rs.getInt("CODVOL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codvol;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == coletarButton) {
            try {
                String tipoResiduo = (String) comboResiduos.getSelectedItem();
                String bairroPontoColeta = (String) comboBairros.getSelectedItem();
                int codResiduo = getCodResiduo(tipoResiduo);
                int codPontoColeta = getCodPontoColeta(bairroPontoColeta);

                rastreamento rastreamento = new rastreamento();
                rastreamento.setCodresiduo(codResiduo);
                rastreamento.setCodvol(codvol);
                rastreamento.setCodpontocoleta(codPontoColeta);
                rastreamento.setQuantcoletada(bairroPontoColeta);

                new ecobancodao().inserirRastreamento(rastreamento);

                JOptionPane.showMessageDialog(this, "Coleta registrada com sucesso!");

                txtCategoria.setText("");
                txtDescricao.setText("");
                txtDescarte.setText("");
                txtQuantidade.setText("");
                endereco.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao registrar coleta: " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == sairButton) {
            int escolha = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?", "Sair",
                    JOptionPane.YES_NO_OPTION);
            if (escolha == JOptionPane.YES_OPTION) {
                new Menu();
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColetaResiduos("CPF_TESTE"));
    }
}
