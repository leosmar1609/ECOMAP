import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;
    private JPanel panel1, panel2;
    private JLabel titleLabel;
    private JButton nextButton, closeButton, backButton;
    private Timer timer;
    private int xPosition;
    private static final int ANIMATION_SPEED = 10;
    private static final int TIMER_DELAY = 10;
    private static final int BAR_WIDTH = 800;
    private static final int BAR_WIDTH2 = 400;
    private JPanel greenBar;
    // Adiciona uma referência para o segundo greenBar
    private JPanel secondGreenBar;

    public Menu() {
        setTitle("ECOMAP");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Painel 1
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new Color(0, 102, 51)); // Fundo bege

        titleLabel = new JLabel("BEM VINDO(A) AO ECOMAP");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        titleLabel.setForeground(new Color(246, 234, 215)); // Verde
        titleLabel.setBounds(70, 200, 300, 50); // Ajuste as coordenadas conforme necessário
        panel1.add(titleLabel);
        JLabel subtitleLabel = new JLabel("Reciclagem e gestão de resíduos");
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(246, 234, 215));
        subtitleLabel.setBounds(84, 250, 300, 50); // Ajuste as coordenadas conforme necessário
        panel1.add(subtitleLabel);

        nextButton = new JButton("<");
        nextButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        nextButton.setForeground(new Color(246, 234, 215));
        nextButton.setBounds(10, 520, 50, 30);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animateTransition();
            }
        });
        panel1.add(nextButton);
        closeButton = new JButton("X");
        closeButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        closeButton.setForeground(new Color(0, 102, 51));
        closeButton.setBounds(730, 10, 50, 30);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(Menu.this, "Tem certeza que deseja sair do ECOMAP?", "Sair", JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        panel1.add(closeButton);

        greenBar = new JPanel();
        greenBar.setBackground(new Color(255, 255, 255)); // Verde
        greenBar.setBounds(400, 0, BAR_WIDTH, getHeight());
        panel1.add(greenBar);

        // Painel 2
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(new Color(0, 102, 51)); // Bege

        closeButton = new JButton("X");
        closeButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        closeButton.setForeground(new Color(246, 234, 215));
        closeButton.setBounds(730, 10, 50, 30);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(Menu.this, "Tem certeza que deseja sair do ECOMAP?", "Sair", JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        panel2.add(closeButton);

        JLabel loginText = new JLabel("Faça o login para continuar");
        loginText.setFont(new Font("Helvetica", Font.BOLD, 14));
        loginText.setForeground(new Color(246, 234, 225)); // Verde
        loginText.setBounds(500, 150, 200, 50);
        panel2.add(loginText);

        // Botão de login com fundo verde e texto bege
        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Desenha o fundo arredondado verde
                g2.setColor(new Color(246, 234, 215)); // Verde
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Desenha a borda arredondada
                g2.setColor(new Color(246, 234, 215)); // Verde (mesma cor do fundo)
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        loginButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        loginButton.setBounds(515, 200, 150, 30);
        loginButton.setForeground(new Color(0, 102, 51)); // Bege
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Main().setVisible(true);
            }
        });
        panel2.add(loginButton);

        JLabel resgitertxt = new JLabel("Se não tem Login, clique aqui para cadastrar-se!");
        resgitertxt.setFont(new Font("Helvetica", Font.BOLD, 14));
        resgitertxt.setForeground(new Color(246, 236, 215)); // Verde
        resgitertxt.setBounds(430, 250, 400, 50);
        panel2.add(resgitertxt);

        JButton registerButton = new JButton("Cadastrar-se como voluntário") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);                // Desenha o fundo arredondado verde
                g2.setColor(new Color(246, 234, 215)); // Verde
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Desenha a borda arredondada
                g2.setColor(new Color(246, 234, 215)); // Verde (mesma cor do fundo)
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        registerButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        registerButton.setBounds(465, 300, 250, 30);
        registerButton.setForeground(new Color(0, 102, 51)); // Bege
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CadastroVoluntarioGUI().setVisible(true);
            }
        });
        panel2.add(registerButton);

        backButton = new JButton(">");
        backButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        backButton.setForeground(new Color(0, 102, 51)); // Verde
        backButton.setBounds(10, 520, 50, 30);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animateTransitionBack();
            }
        });
        panel2.add(backButton);

        // Adicionar os painéis ao CardLayout
        cards.add(panel1, "panel1");
        cards.add(panel2, "panel2");

        add(cards);
        setVisible(true);
    }

    private void animateTransition() {
        xPosition = 0;
        timer = new Timer(TIMER_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (xPosition < getWidth()) {
                    panel1.setBounds(-xPosition, 0, getWidth(), getHeight());
                    panel2.setBounds(getWidth() - xPosition, 0, getWidth(), getHeight());
                    greenBar.setBounds(400 - xPosition, 0, BAR_WIDTH, getHeight());
                    xPosition += ANIMATION_SPEED;
                } else {
                    timer.stop();
                    cardLayout.show(cards, "panel2");
                    // Remover o segundo greenBar se existir
                    if (secondGreenBar != null) {
                        panel2.remove(secondGreenBar);
                        secondGreenBar = null; // Limpa a referência
                    }
                    secondGreenBar = new JPanel();
                    secondGreenBar.setBackground(new Color(255, 255, 255)); // Verde
                    secondGreenBar.setBounds(0, 0, BAR_WIDTH2, getHeight());
                    panel2.add(secondGreenBar);
                    panel2.revalidate();
                    panel2.repaint();
                }
            }
        });
        timer.start();
    }

    private void animateTransitionBack() {
        xPosition = getWidth();
        timer = new Timer(TIMER_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (xPosition > 0) {
                    panel1.setBounds(-(getWidth() - xPosition), 0, getWidth(), getHeight());
                    panel2.setBounds(xPosition - getWidth(), 0, getWidth(), getHeight());
                    greenBar.setBounds(xPosition - BAR_WIDTH, 0, BAR_WIDTH, getHeight()); // Move o greenBar para a direita
                    xPosition -= ANIMATION_SPEED;
                } else {
                    timer.stop();
                    cardLayout.show(cards, "panel1");
                    // Ajuste para restaurar o estado original do painel1
                    greenBar.setBounds(400, 0, BAR_WIDTH, getHeight());
                    // Remove o segundo greenBar se existir
                    if (secondGreenBar != null) {
                        panel2.remove(secondGreenBar);
                        secondGreenBar = null; // Limpa a referência
                        panel2.revalidate();
                        panel2.repaint();
                    }
                }
            }
        });
        timer.start();
    }
    

    public static void main(String[] args) {
        new Menu();
    }
}
