import DAO.ecobancodao;
import entity.funcionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarDadosFunc extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton;
    private ecobancodao dao;
    private funcionarios funcionarioAtual;
    private String cpfUsuarioLogado;

    public AlterarDadosFunc(String cpf) {
        this.cpfUsuarioLogado = cpf;
        setTitle("Alterar Dados do Funcionário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(cpf);
        cpfField.setEditable(false);

        verificarButton = new JButton("Verificar CPF");

        JLabel campoLabel = new JLabel("Campo a Alterar:");
        String[] campos = {"Nome", "Telefone", "Email", "Senha"};
        campoComboBox = new JComboBox<>(campos);

        JLabel novoValorLabel = new JLabel("Novo Valor:");
        novoValorField = new JTextField(15);

        salvarButton = new JButton("Salvar");

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpffunc = cpfField.getText();
                funcionarioAtual = dao.pesquisarFuncionario(cpffunc);

                if (funcionarioAtual != null) {
                    JOptionPane.showMessageDialog(AlterarDadosFunc.this, "CPF encontrado. Selecione o campo a ser alterado.");
                } else {
                    JOptionPane.showMessageDialog(AlterarDadosFunc.this, "CPF não encontrado.");
                }
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (funcionarioAtual == null) {
                    JOptionPane.showMessageDialog(AlterarDadosFunc.this, "Verifique um CPF válido primeiro.");
                    return;
                }

                String campoSelecionado = (String) campoComboBox.getSelectedItem();
                String novoValor = novoValorField.getText();

                switch (campoSelecionado) {
                    case "Nome":
                        funcionarioAtual.setNomefunc(novoValor);
                        break;
                    case "Telefone":
                        funcionarioAtual.setFonefunc(novoValor);
                        break;
                    case "Email":
                        funcionarioAtual.setEmailfunc(novoValor);
                        break;
                    case "Senha":
                        funcionarioAtual.setSenhafunc(novoValor);
                        break;
                }

                funcionarios updatedFuncionario = dao.alterarDadosFunc(
                        funcionarioAtual.getCpffunc(),
                        funcionarioAtual.getNomefunc(),
                        funcionarioAtual.getFonefunc(),
                        funcionarioAtual.getEmailfunc(),
                        funcionarioAtual.getSenhafunc()
                );

                if (updatedFuncionario != null) {
                    JOptionPane.showMessageDialog(AlterarDadosFunc.this, "Funcionário atualizado com sucesso.");
                    dispose();
                    new TelaFunc(cpfUsuarioLogado).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(AlterarDadosFunc.this, "Erro ao atualizar os dados do funcionário.");
                }
            }
        });

        mainPanel.add(cpfLabel);
        mainPanel.add(cpfField);
        mainPanel.add(new JLabel());
        mainPanel.add(verificarButton);
        mainPanel.add(campoLabel);
        mainPanel.add(campoComboBox);
        mainPanel.add(novoValorLabel);
        mainPanel.add(novoValorField);
        mainPanel.add(new JLabel());
        mainPanel.add(salvarButton);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AlterarDadosFunc("CPF_TESTE"); // Para teste
    }
}
