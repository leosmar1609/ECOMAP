import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import DAO.ecobancodao;
import entity.pontosdecoleta;
import entity.funcionarios;

public class CadastroPontoColetaGUI extends JFrame implements ActionListener {
    private JTextField enderecoField, cepField, bairroField, cpfFuncField, nomeFuncField, foneFuncField, emailFuncField;
    private JPasswordField senhaFuncField;
    private JButton cadastrarButton, sairButton;

    public CadastroPontoColetaGUI() {
        super("Cadastro de Ponto de Coleta");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(20, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Cadastro de Ponto de Coleta");
        titleLabel.setBounds(140, 30, 600, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Acesse o ECOMAP");
        subtitleLabel.setBounds(140, 70, 600, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        JPanel formPanel = new JPanel();
        formPanel.setBounds(50, 170, 700, 300);
        formPanel.setLayout(new GridLayout(5, 4, 20, 20));
        formPanel.setBackground(Color.WHITE);
        add(formPanel);

        Font labelFont = new Font("Helvetica", Font.PLAIN, 14);
        Font textFieldFont = new Font("Helvetica", Font.PLAIN, 12);
        Dimension textFieldSize = new Dimension(150, 24);

        formPanel.add(createLabel("Endereço:", labelFont));
        enderecoField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(enderecoField);

        formPanel.add(createLabel("CPF Funcionário:", labelFont));
        cpfFuncField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(cpfFuncField);

        formPanel.add(createLabel("CEP:", labelFont));
        cepField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(cepField);

        formPanel.add(createLabel("Nome Funcionário:", labelFont));
        nomeFuncField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(nomeFuncField);

        formPanel.add(createLabel("Bairro:", labelFont));
        bairroField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(bairroField);

        formPanel.add(createLabel("Telefone Funcionário:", labelFont));
        foneFuncField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(foneFuncField);

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        formPanel.add(createLabel("Email Funcionário:", labelFont));
        emailFuncField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(emailFuncField);

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        formPanel.add(createLabel("Senha Funcionário:", labelFont));
        senhaFuncField = createPasswordField(textFieldFont, textFieldSize);
        formPanel.add(senhaFuncField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 480, 800, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel);

        cadastrarButton = createStyledButton("Cadastrar");
        cadastrarButton.addActionListener(this);
        buttonPanel.add(cadastrarButton);

        sairButton = createStyledButton("Voltar");
        sairButton.addActionListener(this);
        buttonPanel.add(sairButton);

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(0, 102, 51));
        return label;
    }

    private JTextField createTextField(Font font, Dimension size) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        textField.setPreferredSize(size);
        return textField;
    }

    private JPasswordField createPasswordField(Font font, Dimension size) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(font);
        passwordField.setPreferredSize(size);
        return passwordField;
    }

    private JButton createStyledButton(String text) {
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
        button.setForeground(new Color(246, 234, 215));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(120, 30));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarButton) {
            try {
                pontosdecoleta pontoColeta = new pontosdecoleta();
                pontoColeta.setEndpontocoleta(enderecoField.getText());
                pontoColeta.setCeppontocoleta(cepField.getText());
                pontoColeta.setBairropontocoleta(bairroField.getText());

                funcionarios funcionario = new funcionarios();
                funcionario.setCpffunc(cpfFuncField.getText());
                funcionario.setNomefunc(nomeFuncField.getText());
                funcionario.setFonefunc(foneFuncField.getText());
                funcionario.setEmailfunc(emailFuncField.getText());
                funcionario.setSenhafunc(new String(senhaFuncField.getPassword()));

                new ecobancodao().cadastrarPontoColeta(pontoColeta, funcionario);

                JOptionPane.showMessageDialog(this, "Ponto de Coleta cadastrado com sucesso!");

                enderecoField.setText("");
                cepField.setText("");
                bairroField.setText("");
                cpfFuncField.setText("");
                nomeFuncField.setText("");
                foneFuncField.setText("");
                emailFuncField.setText("");
                senhaFuncField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar Ponto de Coleta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == sairButton) {
            int escolha = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja Voltar?", "Voltar", JOptionPane.YES_NO_OPTION);
            if (escolha == JOptionPane.YES_OPTION) {
                dispose();
                new AdmMainGUI().setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroPontoColetaGUI());
    }
}