import DAO.ecobancodao;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarVoluntarioGUI extends JFrame {
    private JTextField cpfField;
    private JButton verificarButton;
    private JComboBox<String> campoComboBox;
    private JTextField novoValorField;
    private JButton salvarButton;
    private ecobancodao dao;
    private voluntarios voluntarioAtual;

    public AlterarVoluntarioGUI() {
        setTitle("Alterar Dados do Voluntário");
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
                String cpfvol = cpfField.getText();
                voluntarioAtual = dao.pesquisarUsuario(cpfvol);

                if (voluntarioAtual != null) {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "CPF encontrado. Selecione o campo a ser alterado.");
                } else {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "CPF não encontrado.");
                }
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (voluntarioAtual == null) {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "Verifique um CPF válido primeiro.");
                    return;
                }

                String campoSelecionado = (String) campoComboBox.getSelectedItem();
                String novoValor = novoValorField.getText();

                switch (campoSelecionado) {
                    case "Nome":
                        voluntarioAtual.setNomevol(novoValor);
                        break;
                    case "Telefone":
                        voluntarioAtual.setFonevol(novoValor);
                        break;
                    case "Email":
                        voluntarioAtual.setEmailvol(novoValor);
                        break;
                    case "Senha":
                        voluntarioAtual.setSenhavol(novoValor);
                        break;
                }

                voluntarios updatedVoluntario = dao.alterarDadosVol(
                        voluntarioAtual.getCpfvol(),
                        voluntarioAtual.getNomevol(),
                        voluntarioAtual.getFonevol(),
                        voluntarioAtual.getEmailvol(),
                        voluntarioAtual.getSenhavol()
                );

                if (updatedVoluntario != null) {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "Voluntário atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(AlterarVoluntarioGUI.this, "Erro ao atualizar os dados do voluntário.");
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
                new AlterarVoluntarioGUI();
            }
        });
    }
}
