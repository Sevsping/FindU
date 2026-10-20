
package model;

public class Notificacao {
    private final String mensagem;
    private final Usuario destinatario;

    public Notificacao(String mensagem, Usuario destinatario) {
        this.mensagem = Objeto.validar(mensagem, "Mensagem");

        if (destinatario == null) {
            throw new IllegalArgumentException("Destinatario ausente.");
        }

        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String exibirMensagem() {
        return destinatario.getNome() + ": " + mensagem;
    }
}
