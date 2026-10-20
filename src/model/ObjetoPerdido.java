
package model;

public class ObjetoPerdido extends Objeto {
    private final String ultimoLocalConhecido;

    public ObjetoPerdido(int id, String nome, String categoria,
                         String descricao, String ultimoLocalConhecido) {
        super(id, nome, categoria, descricao);
        this.ultimoLocalConhecido = validar(
            ultimoLocalConhecido, "Ultimo local conhecido"
        );
    }

    public String getUltimoLocalConhecido() {
        return ultimoLocalConhecido;
    }

    @Override
    public String getTipoRegistro() {
        return "Perdido";
    }
}
