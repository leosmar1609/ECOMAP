import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.regex.Pattern;
import DAO.ecobancodao;
import entity.pontosdecoleta;
import entity.funcionarios;

public class CadastroPontoColetaGUI extends JFrame implements ActionListener {
    private JTextField enderecoField, cepField, bairroField, cpfFuncField, nomeFuncField, foneFuncField, emailFuncField;
    private JPasswordField senhaFuncField;
    private JButton cadastrarButton, sairButton;
    private final String emailRegex = "^[a-z]{3,}([._][a-z0-9]+)*@[a-z]+\\.[a-z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(emailRegex);

    public CadastroPontoColetaGUI() {
        super("Cadastro de Ponto de Coleta");

        String basePath = System.getProperty("user.dir");
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

        String imagePath = basePath + File.separator + "images" + File.separator + "logoc.png";
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 25, 100, 100);
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
        formatCPFField();
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
        formatTelefoneField();
        formPanel.add(foneFuncField);

        formPanel.add(createLabel("Email Funcionário:", labelFont));
        emailFuncField = createTextField(textFieldFont, textFieldSize);
        formPanel.add(emailFuncField);

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
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica", Font.BOLD, 14));
        button.setForeground(new Color(246, 234, 215));
        button.setBackground(new Color(0, 102, 51));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 30));
        return button;
    }

    private void formatCPFField(){
        cpfFuncField.getDocument().addDocumentListener(new DocumentListener() {
        private boolean isUpdating = false;

        @Override
        public void insertUpdate(DocumentEvent e) {
            formatCPF();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            formatCPF();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            formatCPF();
        }

        private void formatCPF() {
            if (isUpdating) {
                return;
            }

            isUpdating = true;
            SwingUtilities.invokeLater(() -> {
                try {
                    String text = cpfFuncField.getText().replaceAll("[^\\d]", "");
                    StringBuilder formatted = new StringBuilder();

                    for (int i = 0; i < text.length(); i++) {
                        if (i == 3 || i == 6) {
                            formatted.append('.');
                        } else if (i == 9) {
                            formatted.append('-');
                        }
                        
                        formatted.append(text.charAt(i));
                    }

                    if (formatted.length() > 14) {
                        formatted.setLength(14);
                    }

                    cpfFuncField.setText(formatted.toString());
                } finally {
                    isUpdating = false;
                }
            });
        }
    });
}

private void formatTelefoneField() {
    foneFuncField.getDocument().addDocumentListener(new DocumentListener() {
        private boolean isUpdating = false;

        @Override
        public void insertUpdate(DocumentEvent e) {
            formatTelefone();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            formatTelefone();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            formatTelefone();
        }

        private void formatTelefone() {
            if (isUpdating) {
                return;
            }

            isUpdating = true;
            SwingUtilities.invokeLater(() -> {
                try {
                    String text = foneFuncField.getText().replaceAll("[^\\d]", "");
                    StringBuilder formatted = new StringBuilder();

                    if (text.length() > 0) {
                        formatted.append("(");
                        formatted.append(text.substring(0, Math.min(2, text.length())));
                        formatted.append(")");

                        if (text.length() > 2) {
                            formatted.append(" ");
                            formatted.append(text.substring(2, Math.min(7, text.length())));
                        }

                        if (text.length() > 7) {
                            formatted.append("-");
                            formatted.append(text.substring(7, Math.min(11, text.length())));
                        }
                    }

                    if (formatted.length() > 15) {
                        formatted.setLength(15);
                    }

                    foneFuncField.setText(formatted.toString());
                } finally {
                    isUpdating = false;
                }
            });
        }
    });
}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarButton) {
            if (!validateFields()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String email = emailFuncField.getText();
                if (!emailPattern.matcher(email).matches()) {
                    JOptionPane.showMessageDialog(this, "Email inválido! Certifique-se de que atende ao formato correto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pontosdecoleta p = new pontosdecoleta();
                p.setEndpontocoleta(enderecoField.getText());
                p.setBairropontocoleta(bairroField.getText());
                p.setCeppontocoleta(cepField.getText());

                funcionarios f = new funcionarios();
                f.setNomefunc(nomeFuncField.getText());
                f.setCpffunc(cpfFuncField.getText().replaceAll("[^\\d]", ""));
                f.setEmailfunc(email);
                f.setFonefunc(foneFuncField.getText().replaceAll("[^\\d]", ""));
                f.setSenhafunc(new String(senhaFuncField.getPassword()));

                new ecobancodao().cadastrarPontoColeta(p, f);

                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");

                enderecoField.setText("");
                cepField.setText("");
                bairroField.setText("");
                nomeFuncField.setText("");
                cpfFuncField.setText("");
                emailFuncField.setText("");
                foneFuncField.setText("");
                senhaFuncField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar voluntário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == sairButton) {
            new Menu();
            dispose();
        }
    }

    private boolean validateFields() {
        return !enderecoField.getText().isEmpty() &&
        !cepField.getText().isEmpty() &&
        !bairroField.getText().isEmpty() &&
        !nomeFuncField.getText().isEmpty() &&
                !cpfFuncField.getText().isEmpty() &&
                !emailFuncField.getText().isEmpty() &&
                !foneFuncField.getText().isEmpty() &&
                senhaFuncField.getPassword().length > 0;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(CadastroPontoColetaGUI::new);
    // }
}