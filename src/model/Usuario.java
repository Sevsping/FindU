
package model;

public class Usuario {
    private final int id;
    private final String nome;
    private final String email;

    public Usuario(int id, String nome, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo.");
        }

        this.id = id;
        this.nome = Objeto.validar(nome, "Nome");
        this.email = Objeto.validar(email, "Email");

        if (!this.email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Informe um email valido.");
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nome + " (" + email + ")";
    }
}
