
package model;

public abstract class Objeto {
    private final int id;
    private final String nome;
    private final String categoria;
    private final String descricao;

    protected Objeto(int id, String nome, String categoria, String descricao) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo.");
        }

        this.id = id;
        this.nome = validar(nome, "Nome");
        this.categoria = validar(categoria, "Categoria");
        this.descricao = validar(descricao, "Descricao");
    }

    protected static String validar(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " nao pode ficar vazio.");
        }
        return valor.trim();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public abstract String getTipoRegistro();
}
