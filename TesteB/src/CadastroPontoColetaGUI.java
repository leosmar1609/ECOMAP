import javax.swing.*;
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
        setSize(400, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        // Componentes do formulário
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoLabel.setBounds(50, 30, 80, 25);
        add(enderecoLabel);
        enderecoField = new JTextField();
        enderecoField.setBounds(150, 30, 200, 25);
        add(enderecoField);

        JLabel cepLabel = new JLabel("CEP:");
        cepLabel.setBounds(50, 70, 80, 25);
        add(cepLabel);
        cepField = new JTextField();
        cepField.setBounds(150, 70, 200, 25);
        add(cepField);

        JLabel bairroLabel = new JLabel("Bairro:");
        bairroLabel.setBounds(50, 110, 80, 25);
        add(bairroLabel);
        bairroField = new JTextField();
        bairroField.setBounds(150, 110, 200, 25);
        add(bairroField);

        JLabel cpfFuncLabel = new JLabel("CPF Funcionário:");
        cpfFuncLabel.setBounds(50, 150, 120, 25);
        add(cpfFuncLabel);
        cpfFuncField = new JTextField();
        cpfFuncField.setBounds(180, 150, 170, 25);
        add(cpfFuncField);

        JLabel nomeFuncLabel = new JLabel("Nome Funcionário:");
        nomeFuncLabel.setBounds(50, 190, 120, 25);
        add(nomeFuncLabel);
        nomeFuncField = new JTextField();
        nomeFuncField.setBounds(180, 190, 170, 25);
        add(nomeFuncField);

        JLabel foneFuncLabel = new JLabel("Telefone Funcionário:");
        foneFuncLabel.setBounds(50, 230, 140, 25);
        add(foneFuncLabel);
        foneFuncField = new JTextField();
        foneFuncField.setBounds(190, 230, 160, 25);
        add(foneFuncField);

        JLabel emailFuncLabel = new JLabel("Email Funcionário:");
        emailFuncLabel.setBounds(50, 270, 120, 25);
        add(emailFuncLabel);
        emailFuncField = new JTextField();
        emailFuncField.setBounds(180, 270, 170, 25);
        add(emailFuncField);

        JLabel senhaFuncLabel = new JLabel("Senha Funcionário:");
        senhaFuncLabel.setBounds(50, 310, 120, 25);
        add(senhaFuncLabel);
        senhaFuncField = new JPasswordField();
        senhaFuncField.setBounds(180, 310, 170, 25);
        add(senhaFuncField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(100, 370, 100, 30);
        cadastrarButton.addActionListener(this);
        add(cadastrarButton);

        sairButton = new JButton("Sair");
        sairButton.setBounds(220, 370, 100, 30);
        sairButton.addActionListener(this);
        add(sairButton);

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