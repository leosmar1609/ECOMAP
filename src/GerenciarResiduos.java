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

        // Painel superior para o título e logo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png"); // caminho da imagem do logo
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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

        // Painel para os campos de entrada e botão
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(50, 200, 700, 300);
        centerPanel.setLayout(new GridLayout(5, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel);

        JLabel tipoLabel = new JLabel("Tipo de Resíduo:");
        tipoField = new JTextField();
        centerPanel.add(tipoLabel);
        centerPanel.add(tipoField);

        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaField = new JTextField();
        centerPanel.add(categoriaLabel);
        centerPanel.add(categoriaField);

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoField = new JTextField();
        centerPanel.add(descricaoLabel);
        centerPanel.add(descricaoField);

        JLabel descarteLabel = new JLabel("Descarte:");
        descarteField = new JTextField();
        centerPanel.add(descarteLabel);
        centerPanel.add(descarteField);

        JButton cadastrarBotao = new JButton("Cadastrar Resíduo");
        cadastrarBotao.setBackground(new Color(0, 102, 51));
        cadastrarBotao.setForeground(Color.WHITE);
        centerPanel.add(new JLabel()); // Placeholder
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

        setVisible(true);
    }

    public static void main(String[] args) {
        new GerenciarResiduos();
    }
}
