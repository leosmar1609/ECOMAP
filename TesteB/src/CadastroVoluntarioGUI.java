import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import entity.*;
import DAO.*;

public class CadastroVoluntarioGUI extends JFrame implements ActionListener {
    private JTextField nomeField, emailField, foneField;
    private JFormattedTextField cpfField;
    private JPasswordField senhaField;
    private JButton cadastrarButton, sairButton;
    private final String emailRegex = "^[a-z]{3,}([._]?[a-z0-9]+)*@[a-z]+\\.[a-z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(emailRegex);

    public CadastroVoluntarioGUI() {
        super("Cadastro de Voluntário");

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
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 30, 150, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Área de Cadastro");
        titleLabel.setBounds(200, 30, 400, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Acesse o ECOMAP");
        subtitleLabel.setBounds(200, 70, 400, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(250, 180, 80, 25);
        nomeLabel.setForeground(new Color(0, 102, 51));
        nomeLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(nomeLabel);
        nomeField = new JTextField();
        nomeField.setBounds(350, 180, 200, 25);
        add(nomeField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(250, 220, 80, 25);
        cpfLabel.setForeground(new Color(0, 102, 51));
        cpfLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(cpfLabel);
        cpfField = new JFormattedTextField();
        cpfField.setBounds(350, 220, 200, 25);
        add(cpfField);
        formatCPFField();

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(250, 260, 80, 25);
        emailLabel.setForeground(new Color(0, 102, 51));
        emailLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(350, 260, 200, 25);
        add(emailField);

        JLabel foneLabel = new JLabel("Telefone:");
        foneLabel.setBounds(250, 300, 80, 25);
        foneLabel.setForeground(new Color(0, 102, 51));
        foneLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(foneLabel);
        foneField = new JTextField();
        foneField.setBounds(350, 300, 200, 25);
        add(foneField);
        formatTelefoneField();

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(250, 340, 80, 25);
        senhaLabel.setForeground(new Color(0, 102, 51));
        senhaLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        add(senhaLabel);
        senhaField = new JPasswordField();
        senhaField.setBounds(350, 340, 200, 25);
        add(senhaField);

        cadastrarButton = createButton("Cadastrar", 345, 400, 110, 30);
        cadastrarButton.addActionListener(this);
        add(cadastrarButton);

        sairButton = createButton("Voltar", 460, 400, 100, 30);
        sairButton.addActionListener(this);
        add(sairButton);

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

    private void formatCPFField() {
        cpfField.getDocument().addDocumentListener(new DocumentListener() {
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
                        String text = cpfField.getText().replaceAll("[^\\d]", "");
                        StringBuilder formatted = new StringBuilder();
    
                        for (int i = 0; i < text.length(); i++) {
                            if (i == 3 || i == 6) {
                                formatted.append('.');
                            } else if (i == 9) {
                                formatted.append('-');
                            }
                            
                            formatted.append(text.charAt(i));
                        }
    
                        if (formatted.length() > 14) { // Max length of formatted CPF
                            formatted.setLength(14);
                        }
    
                        cpfField.setText(formatted.toString());
                    } finally {
                        isUpdating = false;
                    }
                });
            }
        });
    }
    
    private void formatTelefoneField() {
        foneField.getDocument().addDocumentListener(new DocumentListener() {
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
                        String text = foneField.getText().replaceAll("[^\\d]", "");
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
    
                        if (formatted.length() > 15) { // Max length of formatted phone number
                            formatted.setLength(15);
                        }
    
                        foneField.setText(formatted.toString());
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
                String email = emailField.getText();
                if (!emailPattern.matcher(email).matches()) {
                    JOptionPane.showMessageDialog(this, "Email inválido! Certifique-se de que atende ao formato correto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                voluntarios v = new voluntarios();
                v.setNomevol(nomeField.getText());
                v.setCpfvol(cpfField.getText().replaceAll("[^\\d]", "")); // Enviar apenas números
                v.setEmailvol(email);
                v.setFonevol(foneField.getText().replaceAll("[^\\d]", "")); // Enviar apenas números
                v.setSenhavol(new String(senhaField.getPassword()));

                new ecobancodao().cadastrarOuAtualizarVoluntario(v);

                JOptionPane.showMessageDialog(this, "Voluntário cadastrado com sucesso!");

                nomeField.setText("");
                cpfField.setText("");
                emailField.setText("");
                foneField.setText("");
                senhaField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar voluntário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == sairButton) {
            new Menu();
            dispose();
        }
    }

    private boolean validateFields() {
        return !nomeField.getText().isEmpty() &&
                !cpfField.getText().isEmpty() &&
                !emailField.getText().isEmpty() &&
                !foneField.getText().isEmpty() &&
                senhaField.getPassword().length > 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroVoluntarioGUI());
    }
}
