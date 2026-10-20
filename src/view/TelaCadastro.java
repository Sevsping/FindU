
package view;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import model.*;
import service.GerenciadorFindU;

public class TelaCadastro extends JDialog {

    public TelaCadastro(JFrame parent,
                        GerenciadorFindU gerenciador,
                        boolean perdido) {

        super(parent,
            perdido ? "Cadastrar objeto perdido"
                    : "Cadastrar objeto encontrado",
            true
        );

        setSize(470, 390);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));
        painel.setBorder(
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        );

        JComboBox<Usuario> usuarios = new JComboBox<>();

        for (Usuario u : gerenciador.getUsuarios()) {
            usuarios.addItem(u);
        }

        JTextField nome = new JTextField();
        JTextField categoria = new JTextField();
        JTextField descricao = new JTextField();
        JTextField local = new JTextField();
        JTextField retirada = new JTextField();

        painel.add(new JLabel("Responsavel:"));
        painel.add(usuarios);

        painel.add(new JLabel("Nome do objeto:"));
        painel.add(nome);

        painel.add(new JLabel("Categoria:"));
        painel.add(categoria);

        painel.add(new JLabel("Descricao:"));
        painel.add(descricao);

        painel.add(new JLabel(
            perdido ? "Ultimo local conhecido:" : "Local encontrado:"
        ));
        painel.add(local);

        if (!perdido) {
            painel.add(new JLabel("Local para retirada:"));
            painel.add(retirada);
        }

        JButton salvar = new JButton("Salvar registro");
        painel.add(new JLabel(""));
        painel.add(salvar);

        add(painel);

        salvar.addActionListener(e -> {
            try {
                Usuario usuario =
                    (Usuario) usuarios.getSelectedItem();

                if (usuario == null) {
                    throw new IllegalArgumentException(
                        "Cadastre um usuario primeiro."
                    );
                }

                if (nome.getText().isBlank()
                    || categoria.getText().isBlank()
                    || descricao.getText().isBlank()
                    || local.getText().isBlank()
                    || (!perdido && retirada.getText().isBlank())) {

                    throw new IllegalArgumentException(
                        "Preencha todos os campos."
                    );
                }

                int id = gerenciador.gerarIdObjeto();

                Objeto objeto = perdido
                    ? new ObjetoPerdido(
                        id, nome.getText(), categoria.getText(),
                        descricao.getText(), local.getText()
                    )
                    : new ObjetoEncontrado(
                        id, nome.getText(), categoria.getText(),
                        descricao.getText(), local.getText(),
                        retirada.getText()
                    );

                int avisosAnteriores =
                    gerenciador.getNotificacoes().size();

                Registro registro = new Registro(
                    gerenciador.gerarIdRegistro(),
                    usuario,
                    objeto,
                    LocalDate.now()
                );

                gerenciador.cadastrarObjeto(registro);

                List<Notificacao> avisos =
                    gerenciador.getNotificacoes();

                String mensagem =
                    "Registro #" + registro.getId()
                    + " cadastrado com sucesso!";

                if (avisos.size() > avisosAnteriores) {
                    mensagem += "\n\n"
                        + avisos.get(avisos.size() - 1).getMensagem();
                }

                JOptionPane.showMessageDialog(this, mensagem);
                dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Verifique os dados",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }
}
