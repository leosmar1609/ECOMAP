import DAO.ecobancodao;
import entity.funcionarios;
import entity.voluntarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdmExcluir extends JFrame {
    private JTextField cpfField;
    private JButton searchButton;
    private JButton deleteButton;

    private ecobancodao dao;

    public AdmExcluir() {
        setTitle("Excluir Usuário");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new ecobancodao();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JLabel cpfLabel = new JLabel("CPF");
        cpfField = new JTextField(15);
        searchButton = new JButton("Pesquisar");
        deleteButton = new JButton("Excluir");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                pesquisarUsuario(cpf);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();

                if (cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(AdmExcluir.this, "Preencha o campo CPF.");
                    return;
                }

                int option = JOptionPane.showConfirmDialog(AdmExcluir.this, "Tem certeza que deseja excluir o usuário?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    voluntarios voluntario = dao.pesquisarUsuario(cpf);
                    funcionarios funcionario = dao.pesquisarFuncionario(cpf);

                    if (voluntario != null) {
                        dao.deletarVoluntario(voluntario);
                        JOptionPane.showMessageDialog(AdmExcluir.this, "Voluntário excluído com sucesso.");
                    } else if (funcionario != null) {
                        dao.deletarFuncionario(funcionario);
                        JOptionPane.showMessageDialog(AdmExcluir.this, "Funcionário excluído com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(AdmExcluir.this, "Usuário não encontrado.");
                   }
                }
            }
        });

        mainPanel.add(cpfLabel);
        mainPanel.add(cpfField);
        mainPanel.add(searchButton);
        mainPanel.add(deleteButton);

        add(mainPanel);
        setVisible(true);
    }

    public void pesquisarUsuario(String cpf) {
        voluntarios voluntario = dao.pesquisarUsuario(cpf);
        funcionarios funcionario = dao.pesquisarFuncionario(cpf);

        if (voluntario != null || funcionario != null) {
            JOptionPane.showMessageDialog(this, "Usuário encontrado. Você pode excluí-lo agora.");
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdmExcluir();
            }
        });
    }
}