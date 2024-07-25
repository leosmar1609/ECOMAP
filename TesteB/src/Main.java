import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DAO.ecobancodao;
import entity.voluntarios;
import entity.funcionarios;
import entity.administradores;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main extends JFrame {
    private int tentativas = 0;
    private String cpfUsuarioLogado;
    private JLabel errorMsg;
    private JLabel senhaLabel;
    private JPasswordField senhaField;
    private JButton loginBotao;
    private JButton sairBotao;
    private JButton closeButton;

    public Main() {
        setTitle("Login ECOMAP");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 51));
        panel.setBounds(0, 0, 800, 100);
        panel.setLayout(null);
        add(panel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(30, 5, 100, 100);
        panel.add(logo);

        JLabel titleLabel = new JLabel("Área de Login");
        titleLabel.setBounds(340, 20, 200, 30);
        titleLabel.setForeground(new Color(246, 234, 215));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        panel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Acesse o ECOMAP");
        subtitleLabel.setBounds(360, 50, 200, 30);
        subtitleLabel.setForeground(new Color(246, 234, 215));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        panel.add(subtitleLabel);
        
        closeButton = new JButton("X");
        closeButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        closeButton.setForeground(new Color(246, 234, 215));
        closeButton.setBounds(730, 10, 50, 30);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(Main.this, "Tem certeza que deseja sair do ECOMAP?", "Sair",
                        JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        panel.add(closeButton);

        JLabel userLabel = new JLabel("Email:");
        userLabel.setForeground(new Color(0, 102, 51));
        userLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        userLabel.setBounds(340, 120, 80, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(340, 150, 200, 25);
        add(userField);

        errorMsg = new JLabel("Campo de email está vazio!");
        errorMsg.setBounds(340, 180, 200, 25);
        errorMsg.setForeground(new Color(255, 0, 0));
        errorMsg.setFont(new Font("Helvetica", Font.BOLD, 14));
        errorMsg.setVisible(false);
        add(errorMsg);

        senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(340, 180, 80, 25);
        senhaLabel.setForeground(new Color(0, 102, 51));
        senhaLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        add(senhaLabel);

        senhaField = new JPasswordField();
        senhaField.setBounds(340, 200, 200, 25);
        add(senhaField);

        loginBotao = createButton("LOGIN", 340, 250, 100, 30);
        add(loginBotao);

        sairBotao = createButton("VOLTAR", 450, 250, 100, 30);
        add(sairBotao);

        loginBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = userField.getText();
                String senha = new String(senhaField.getPassword());

                String emailRegex = "^[a-z]{3,}([._][a-z0-9]+)*@[a-z]+\\.[a-z]{2,}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);

                if (email.isEmpty()) {
                    errorMsg.setText("Campo de email está vazio!");
                    errorMsg.setVisible(true);
                    moveComponentsDown();
                    return;
                } else {
                    errorMsg.setVisible(false);
                }
                panel.revalidate();
                panel.repaint();

                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "Email inválido. Insira o email cadastrado");
                    return;
                }

                ecobancodao banco = new ecobancodao();

                voluntarios usuarioVoluntario = banco.buscarUsuario(email, senha);
                funcionarios usuarioFuncionario = banco.buscarFuncionario(email, senha);
                administradores usuarioAdministrador = banco.buscarAdministrador(email, senha);

                if (usuarioVoluntario != null) {
                    JOptionPane.showMessageDialog(null,
                            "Bem vindo(a) Voluntário: " + usuarioVoluntario.getNomevol() + "!");
                    cpfUsuarioLogado = usuarioVoluntario.getCpfvol();
                    new TelaVol(cpfUsuarioLogado);
                    dispose();
                } else if (usuarioFuncionario != null) {
                    JOptionPane.showMessageDialog(null,
                            "Bem vindo(a) Funcionário: " + usuarioFuncionario.getNomefunc() + "!");
                    cpfUsuarioLogado = usuarioFuncionario.getCpffunc();
                    new TelaFunc(cpfUsuarioLogado);
                    dispose();
                } else if (usuarioAdministrador != null) {
                    dispose();
                    new AdmGUI();
                } else {
                    tentativas++;
                    if (tentativas >= 3) {
                        JOptionPane.showMessageDialog(null,
                                "Número máximo de tentativas atingido. A aplicação será fechada.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Usuário e senha inválidos ou usuário desconhecido. Tentativas restantes: "
                                        + (3 - tentativas));
                    }
                }
            }
        });

        sairBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Menu();
            }
        });

        userField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (errorMsg.isVisible()) {
                    errorMsg.setVisible(false);
                    resetComponentsPosition();
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

    private void moveComponentsDown() {
        senhaLabel.setBounds(senhaLabel.getX(), senhaLabel.getY() + 30, senhaLabel.getWidth(), senhaLabel.getHeight());
        senhaField.setBounds(senhaField.getX(), senhaField.getY() + 30, senhaField.getWidth(), senhaField.getHeight());
        loginBotao.setBounds(loginBotao.getX(), loginBotao.getY() + 30, loginBotao.getWidth(), loginBotao.getHeight());
        sairBotao.setBounds(sairBotao.getX(), sairBotao.getY() + 30, sairBotao.getWidth(), sairBotao.getHeight());
    }

    private void resetComponentsPosition() {
        senhaLabel.setBounds(340, 180, 80, 25);
        senhaField.setBounds(340, 200, 200, 25);
        loginBotao.setBounds(340, 250, 100, 30);
        sairBotao.setBounds(450, 250, 100, 30);
    }

    public static void main(String[] args) {
        new Main();
    }
}
