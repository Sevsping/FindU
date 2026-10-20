
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import service.GerenciadorFindU;

public class TelaConsulta extends JDialog {

    private final GerenciadorFindU gerenciador;
    private final JTextField pesquisa = new JTextField(22);
    private final DefaultTableModel tabelaModelo;
    private final JTable tabela;

    public TelaConsulta(JFrame parent,
                        GerenciadorFindU gerenciador) {

        super(parent, "Consultar objetos - FindU", true);
        this.gerenciador = gerenciador;

        setSize(900, 430);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Campo de pesquisa
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topo.add(new JLabel("Pesquisar por nome ou categoria:"));
        topo.add(pesquisa);

        JButton buscar = new JButton("Buscar");
        topo.add(buscar);

        add(topo, BorderLayout.NORTH);

        // Tabela dos objetos cadastrados
        String[] colunas = {
            "Registro",
            "Tipo",
            "Nome",
            "Categoria",
            "Local",
            "Responsável",
            "Status"
        };

        tabelaModelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(tabelaModelo);
        tabela.setRowHeight(25);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões inferiores
        JPanel rodape = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        JButton devolver = new JButton("Registrar devolução");
        JButton atualizar = new JButton("Atualizar lista");

        rodape.add(devolver);
        rodape.add(atualizar);

        add(rodape, BorderLayout.SOUTH);

        // Eventos dos botões
        buscar.addActionListener(e -> carregar());
        pesquisa.addActionListener(e -> carregar());
        atualizar.addActionListener(e -> carregar());

        devolver.addActionListener(e -> registrarDevolucao());

        carregar();
    }

    // Carrega os registros na tabela
    private void carregar() {
        tabelaModelo.setRowCount(0);

        List<Registro> registros =
            gerenciador.buscarObjetos(pesquisa.getText());

        for (Registro registro : registros) {

            Objeto objeto = registro.getObjeto();

            String local;

            if (objeto instanceof ObjetoPerdido) {
                local = ((ObjetoPerdido) objeto)
                    .getUltimoLocalConhecido();
            } else {
                local = ((ObjetoEncontrado) objeto)
                    .getLocalRetirada();
            }

            tabelaModelo.addRow(new Object[] {
                registro.getId(),
                objeto.getTipoRegistro(),
                objeto.getNome(),
                objeto.getCategoria(),
                local,
                registro.getUsuario().getNome(),
                registro.getStatus()
            });
        }
    }

    // Confirma e registra a devolução de um objeto
    private void registrarDevolucao() {

        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um registro na tabela.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linha);

        int id = (Integer) tabelaModelo.getValueAt(
            linhaModelo, 0
        );

        String status = (String) tabelaModelo.getValueAt(
            linhaModelo, 6
        );

        if ("Devolvido".equals(status)) {
            JOptionPane.showMessageDialog(
                this,
                "Este objeto já foi devolvido.",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        // Opções em português
        Object[] opcoes = {"Sim", "Não"};

        int confirmar = JOptionPane.showOptionDialog(
            this,
            "Deseja confirmar a devolução do registro #" + id + "?",
            "Confirmar devolução",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[1]
        );

        if (confirmar == JOptionPane.YES_OPTION) {

            try {
                gerenciador.registrarDevolucao(id);

                carregar();

                JOptionPane.showMessageDialog(
                    this,
                    "Devolução registrada com sucesso!",
                    "FindU",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
