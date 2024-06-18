import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DAO.ecobancodao;
import entity.residuos;

public class GerenciarResiduos extends JFrame {
    private JTextField tipoField;
    private JTextField categoriaField;
    private JTextField descricaoField;
    private JTextField descarteField;

    public GerenciarResiduos() {
        setTitle("Adicionar Residuos");
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
        Image logoImage = logoIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(logoImage);
        JLabel logo = new JLabel(resizedLogoIcon);
        logo.setBounds(20, 25, 100, 100);
        topPanel.add(logo);

        JLabel titleLabel = new JLabel("Gerenciar Resíduos");
        titleLabel.setBounds(140, 30, 600, 30);
        titleLabel.setForeground(new Color(0xD3B88C));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Adicionar e gerenciar resíduos");
        subtitleLabel.setBounds(140, 70, 600, 30);
        subtitleLabel.setForeground(new Color(0xD3B88C));
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(50, 200, 700, 300);
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel);

        JLabel tipoLabel = new JLabel("Tipo de Resíduo:");
        tipoLabel.setForeground(new Color(0, 102, 51));
        tipoLabel.setBounds(50, 30, 150, 30);
        centerPanel.add(tipoLabel);

        tipoField = new JTextField();
        tipoField.setBounds(200, 30, 300, 30);
        centerPanel.add(tipoField);

        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaLabel.setForeground(new Color(0, 102, 51));
        categoriaLabel.setBounds(50, 80, 150, 30);
        centerPanel.add(categoriaLabel);

        categoriaField = new JTextField();
        categoriaField.setBounds(200, 80, 300, 30);
        centerPanel.add(categoriaField);

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoLabel.setForeground(new Color(0, 102, 51));
        descricaoLabel.setBounds(50, 130, 150, 30);
        centerPanel.add(descricaoLabel);

        descricaoField = new JTextField();
        descricaoField.setBounds(200, 130, 300, 30);
        centerPanel.add(descricaoField);

        JLabel descarteLabel = new JLabel("Descarte:");
        descarteLabel.setForeground(new Color(0, 102, 51));
        descarteLabel.setBounds(50, 180, 150, 30);
        centerPanel.add(descarteLabel);

        descarteField = new JTextField();
        descarteField.setBounds(200, 180, 300, 30);
        centerPanel.add(descarteField);

        JButton cadastrarBotao = createButton("Cadastrar Resíduo", 250, 230, 200, 40);
        centerPanel.add(cadastrarBotao);

        cadastrarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoResiduo = tipoField.getText();
                String categoria = categoriaField.getText();
                String descricao = descricaoField.getText();
                String descarte = descarteField.getText();

                residuos novoResiduo = new residuos(tipoResiduo, categoria, descricao, descarte);
                ecobancodao banco = new ecobancodao();
                banco.cadResiduos(novoResiduo);
                JOptionPane.showMessageDialog(null, "Resíduo cadastrado com sucesso!");

                tipoField.setText("");
                categoriaField.setText("");
                descricaoField.setText("");
                descarteField.setText("");
            }
        });

        JButton voltarBotao = createButton("Voltar", 470, 230, 100, 40);
        centerPanel.add(voltarBotao);

        voltarBotao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdmGUI();
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

    public static void main(String[] args) {
        new GerenciarResiduos();
    }
}