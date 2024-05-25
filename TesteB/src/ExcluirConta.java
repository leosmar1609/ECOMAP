import DAO.ecobancodao;
import entity.voluntarios;
import entity.funcionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExcluirConta extends JFrame {
    private String cpfUsuario;
    private JButton deleteButton;

    private ecobancodao dao;

    public ExcluirConta(String cpf) {
        this.cpfUsuario = cpf;

        setTitle("Excluir Conta");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JLabel cpfLabel = new JLabel("CPF: " + cpfUsuario);
        deleteButton = new JButton("Excluir Conta");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(ExcluirConta.this, "Tem certeza que deseja excluir a sua conta?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    voluntarios voluntario = dao.pesquisarUsuario(cpfUsuario);
                    funcionarios funcionario = dao.pesquisarFuncionario(cpfUsuario);

                    if (voluntario != null) {
                        dao.deletarVoluntario(voluntario);
                        JOptionPane.showMessageDialog(ExcluirConta.this, "Voluntário excluído com sucesso.");
                    } else if (funcionario != null) {
                        dao.deletarFuncionario(funcionario);
                        JOptionPane.showMessageDialog(ExcluirConta.this, "Funcionário excluído com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(ExcluirConta.this, "Usuário não encontrado.");
                    }
                    dispose();
                    new Menu().setVisible(true);
                }
            }
        });

        mainPanel.add(cpfLabel).setVisible(false);
        mainPanel.add(deleteButton);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExcluirConta("CPF_TESTE"); // Para teste
            }
        });
    }
}
