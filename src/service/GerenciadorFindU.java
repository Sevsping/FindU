
package service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import model.*;

public class GerenciadorFindU {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Registro> registros = new ArrayList<>();
    private final List<Notificacao> notificacoes = new ArrayList<>();

    private int proximoUsuario = 1;
    private int proximoObjeto = 1;
    private int proximoRegistro = 1;

    public Usuario cadastrarUsuario(String nome, String email) {
        Usuario usuario = new Usuario(proximoUsuario, nome, email);

        for (Usuario existente : usuarios) {
            if (existente.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                throw new IllegalArgumentException(
                    "Ja existe usuario com este email."
                );
            }
        }

        usuarios.add(usuario);
        proximoUsuario++;
        return usuario;
    }

    public int gerarIdObjeto() {
        return proximoObjeto++;
    }

    public int gerarIdRegistro() {
        return proximoRegistro++;
    }

    public void cadastrarObjeto(Registro registro) {
        if (registro == null ||
            !usuarios.contains(registro.getUsuario())) {
            throw new IllegalArgumentException(
                "Cadastre um usuario valido primeiro."
            );
        }

        registros.add(registro);

        for (Registro outro : registros) {
            if (outro == registro ||
                !possivelCorrespondencia(registro, outro)) {
                continue;
            }

            String aviso = "Possivel correspondencia: "
                + registro.getObjeto().getNome()
                + " (registros #" + registro.getId()
                + " e #" + outro.getId() + ").";

            notificacoes.add(
                new Notificacao(aviso, registro.getUsuario())
            );

            if (outro.getUsuario() != registro.getUsuario()) {
                notificacoes.add(
                    new Notificacao(aviso, outro.getUsuario())
                );
            }
        }
    }

    private static String normalizar(String valor) {
        String semAcento = Normalizer.normalize(
            valor, Normalizer.Form.NFD
        ).replaceAll("\\p{M}", "");

        return semAcento.trim().toLowerCase(Locale.ROOT);
    }

    public static boolean possivelCorrespondencia(
        Registro a, Registro b
    ) {
        if (!a.getStatus().equals("Aberto") ||
            !b.getStatus().equals("Aberto")) {
            return false;
        }

        Objeto x = a.getObjeto();
        Objeto y = b.getObjeto();

        return !x.getTipoRegistro().equals(y.getTipoRegistro())
            && normalizar(x.getNome()).equals(normalizar(y.getNome()))
            && normalizar(x.getCategoria()).equals(
                normalizar(y.getCategoria())
            );
    }

    public List<Registro> buscarObjetos(String termo) {
        String busca = normalizar(termo == null ? "" : termo);

        return registros.stream()
            .filter(r ->
                normalizar(r.getObjeto().getNome()).contains(busca)
                || normalizar(r.getObjeto().getCategoria()).contains(busca)
            )
            .collect(Collectors.toList());
    }

    public List<String> buscarCorrespondencias() {
        List<String> resultado = new ArrayList<>();

        for (int i = 0; i < registros.size(); i++) {
            for (int j = i + 1; j < registros.size(); j++) {
                Registro a = registros.get(i);
                Registro b = registros.get(j);

                if (possivelCorrespondencia(a, b)) {
                    resultado.add(
                        "Registros #" + a.getId()
                        + " e #" + b.getId()
                        + " - " + a.getObjeto().getNome()
                    );
                }
            }
        }

        return resultado;
    }

    public void registrarDevolucao(int idRegistro) {
        for (Registro registro : registros) {
            if (registro.getId() == idRegistro) {
                registro.atualizarStatus("Devolvido");
                return;
            }
        }

        throw new IllegalArgumentException(
            "Registro nao encontrado."
        );
    }

    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    public List<Registro> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    public List<Notificacao> getNotificacoes() {
        return Collections.unmodifiableList(notificacoes);
    }
}
