
package model;

public class ObjetoEncontrado extends Objeto {
    private final String localEncontrado;
    private final String localRetirada;

    public ObjetoEncontrado(int id, String nome, String categoria,
                            String descricao, String localEncontrado,
                            String localRetirada) {
        super(id, nome, categoria, descricao);
        this.localEncontrado = validar(localEncontrado, "Local encontrado");
        this.localRetirada = validar(localRetirada, "Local de retirada");
    }

    public String getLocalEncontrado() {
        return localEncontrado;
    }

    public String getLocalRetirada() {
        return localRetirada;
    }

    @Override
    public String getTipoRegistro() {
        return "Encontrado";
    }
}
