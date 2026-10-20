
package model;

import java.time.LocalDate;

public class Registro {
    private final int id;
    private final Usuario usuario;
    private final Objeto objeto;
    private final LocalDate data;
    private String status;

    public Registro(int id, Usuario usuario, Objeto objeto, LocalDate data) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo.");
        }

        if (usuario == null || objeto == null || data == null) {
            throw new IllegalArgumentException("Dados do registro incompletos.");
        }

        this.id = id;
        this.usuario = usuario;
        this.objeto = objeto;
        this.data = data;
        this.status = "Aberto";
    }

    public void atualizarStatus(String novoStatus) {
        if (!"Aberto".equals(novoStatus) &&
            !"Devolvido".equals(novoStatus)) {
            throw new IllegalArgumentException("Status invalido.");
        }

        status = novoStatus;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public LocalDate getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
