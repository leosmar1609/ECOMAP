import javax.swing.*;
import java.awt.event.*;
import DAO.ecobancodao;
import entity.voluntarios;

public class CadastroVoluntarioGUI extends JFrame implements ActionListener {
    private JTextField nomeField, cpfField, emailField, foneField;
    private JPasswordField senhaField;
    private JButton cadastrarButton, sairButton;

    public CadastroVoluntarioGUI() {
        super("Cadastro de Voluntário");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(50, 30, 80, 25);
        add(nomeLabel);
        nomeField = new JTextField();
        nomeField.setBounds(150, 30, 200, 25);
        add(nomeField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(50, 70, 80, 25);
        add(cpfLabel);
        cpfField = new JTextField();
        cpfField.setBounds(150, 70, 200, 25);
        add(cpfField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 80, 25);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(150, 110, 200, 25);
        add(emailField);

        JLabel foneLabel = new JLabel("Telefone:");
        foneLabel.setBounds(50, 150, 80, 25);
        add(foneLabel);
        foneField = new JTextField();
        foneField.setBounds(150, 150, 200, 25);
        add(foneField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(50, 190, 80, 25);
        add(senhaLabel);
        senhaField = new JPasswordField();
        senhaField.setBounds(150, 190, 200, 25);
        add(senhaField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(100, 250, 100, 30);
        cadastrarButton.addActionListener(this);
        add(cadastrarButton);

        sairButton = new JButton("Sair");
        sairButton.setBounds(220, 250, 100, 30);
        sairButton.addActionListener(this);
        add(sairButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarButton) {
            try {
                voluntarios v = new voluntarios();
                v.setNomevol(nomeField.getText());
                v.setCpfvol(cpfField.getText());
                v.setEmailvol(emailField.getText());
                v.setFonevol(foneField.getText());
                v.setSenhavol(new String(senhaField.getPassword()));

                new ecobancodao().cadastrarVoluntario(v);

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
            int escolha = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (escolha == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroVoluntarioGUI());
    }
}