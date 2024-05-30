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

        // Configurações do frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Painel superior para o título e logo
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 51));
        topPanel.setBounds(0, 0, 800, 150);
        topPanel.setLayout(null);
        add(topPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\logoc.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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

        // Painel de formulário
        JPanel formPanel = new JPanel();
        formPanel.setBounds(50, 170, 700, 300);
        formPanel.setLayout(new GridLayout(5, 4, 20, 20));
        formPanel.setBackground(Color.WHITE);
        add(formPanel);

        // Componentes do formulário
        formPanel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        formPanel.add(enderecoField);

        formPanel.add(new JLabel("CPF Funcionário:"));
        cpfFuncField = new JTextField();
        formPanel.add(cpfFuncField);

        formPanel.add(new JLabel("CEP:"));
        cepField = new JTextField();
        formPanel.add(cepField);

        formPanel.add(new JLabel("Nome Funcionário:"));
        nomeFuncField = new JTextField();
        formPanel.add(nomeFuncField);

        formPanel.add(new JLabel("Bairro:"));
        bairroField = new JTextField();
        formPanel.add(bairroField);

        formPanel.add(new JLabel("Telefone Funcionário:"));
        foneFuncField = new JTextField();
        formPanel.add(foneFuncField);

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("")); // Espaço vazio

        formPanel.add(new JLabel("Email Funcionário:"));
        emailFuncField = new JTextField();
        formPanel.add(emailFuncField);

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("")); // Espaço vazio

        formPanel.add(new JLabel("Senha Funcionário:"));
        senhaFuncField = new JPasswordField();
        formPanel.add(senhaFuncField);

        // Painel para os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 480, 800, 50);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setPreferredSize(new Dimension(120, 30));
        cadastrarButton.addActionListener(this);
        cadastrarButton.setBackground(new Color(0, 102, 51));
        cadastrarButton.setForeground(Color.WHITE);
        buttonPanel.add(cadastrarButton);

        sairButton = new JButton("Sair");
        sairButton.setPreferredSize(new Dimension(120, 30));
        sairButton.addActionListener(this);
        sairButton.setBackground(new Color(0, 102, 51));
        sairButton.setForeground(Color.WHITE);
        buttonPanel.add(sairButton);

        setVisible(true);
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

                // Limpar os campos após o cadastro
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
            int escolha = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
            if (escolha == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroPontoColetaGUI());
    }
}
