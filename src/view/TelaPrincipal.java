
package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Usuario;
import service.GerenciadorFindU;

public class TelaPrincipal extends JFrame {
    private final GerenciadorFindU gerenciador =
        new GerenciadorFindU();

    public TelaPrincipal() {
        setTitle("FindU - Sistema de Achados e Perdidos");
        setSize(730, 580);
        setMinimumSize(new Dimension(620, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(241, 245, 249));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.insets = new Insets(7, 15, 7, 15);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel(
            "FindU", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 38));
        titulo.setForeground(new Color(35, 75, 110));

        g.gridy = 0;
        painel.add(titulo, g);

        JLabel subtitulo = new JLabel(
            "Sistema de Achados e Perdidos",
            SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

        g.gridy = 1;
        painel.add(subtitulo, g);

        adicionarBotao(
            painel, g, 2, "Cadastrar usuario",
            this::cadastrarUsuario
        );

        adicionarBotao(
            painel, g, 3, "Cadastrar objeto perdido",
            () -> abrirCadastro(true)
        );

        adicionarBotao(
            painel, g, 4, "Cadastrar objeto encontrado",
            () -> abrirCadastro(false)
        );

        adicionarBotao(
            painel, g, 5, "Consultar objetos",
            () -> new TelaConsulta(this, gerenciador)
                .setVisible(true)
        );

        adicionarBotao(
            painel, g, 6, "Ver correspondencias",
            this::mostrarCorrespondencias
        );

        adicionarBotao(
            painel, g, 7, "Ver notificacoes",
            this::mostrarNotificacoes
        );

        add(painel);
    }

    private void adicionarBotao(
        JPanel painel, GridBagConstraints g,
        int linha, String texto, Runnable acao
    ) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(310, 39));
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.addActionListener(e -> acao.run());

        g.gridy = linha;
        painel.add(botao, g);
    }

    private void cadastrarUsuario() {
        JTextField nome = new JTextField();
        JTextField email = new JTextField();

        JPanel campos = new JPanel(
            new GridLayout(0, 1, 4, 4)
        );

        campos.add(new JLabel("Nome:"));
        campos.add(nome);
        campos.add(new JLabel("Email:"));
        campos.add(email);

        int escolha = JOptionPane.showConfirmDialog(
            this, campos, "Cadastrar usuario",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (escolha != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Usuario u = gerenciador.cadastrarUsuario(
                nome.getText(), email.getText()
            );

            JOptionPane.showMessageDialog(
                this, "Usuario cadastrado: " + u.getNome()
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, ex.getMessage(),
                "Erro", JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void abrirCadastro(boolean perdido) {
        if (gerenciador.getUsuarios().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Cadastre um usuario antes de registrar objetos."
            );
            return;
        }

        new TelaCadastro(
            this, gerenciador, perdido
        ).setVisible(true);
    }

    private void mostrarCorrespondencias() {
        List<String> combinacoes =
            gerenciador.buscarCorrespondencias();

        JOptionPane.showMessageDialog(
            this,
            combinacoes.isEmpty()
                ? "Nenhuma correspondencia encontrada."
                : String.join("\n", combinacoes),
            "Possiveis correspondencias",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarNotificacoes() {
        StringBuilder texto = new StringBuilder();

        gerenciador.getNotificacoes().forEach(
            n -> texto.append(
                n.exibirMensagem()
            ).append("\n")
        );

        JTextArea area = new JTextArea(
            texto.length() == 0
                ? "Nenhuma notificacao."
                : texto.toString(),
            10, 42
        );

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JOptionPane.showMessageDialog(
            this,
            new JScrollPane(area),
            "Notificacoes",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
