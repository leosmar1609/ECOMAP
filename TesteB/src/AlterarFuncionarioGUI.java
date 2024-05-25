import DAO.ecobancodao;
import entity.funcionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarFuncionarioGUI extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton;
    private ecobancodao dao;
    private funcionarios funcionarioAtual;

    public AlterarFuncionarioGUI() {
        setTitle("Alterar Dados do Funcionário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2));

        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(15);

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
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "CPF encontrado. Selecione o campo a ser alterado.");
                } else {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "CPF não encontrado.");
                }
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (funcionarioAtual == null) {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "Verifique um CPF válido primeiro.");
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
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "Funcionário atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(AlterarFuncionarioGUI.this, "Erro ao atualizar os dados do funcionário.");
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlterarFuncionarioGUI();
            }
        });
    }
}
