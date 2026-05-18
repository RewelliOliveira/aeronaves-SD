package rmi;

import model.CompanhiaAerea;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonUtil {
    private JsonUtil() {
    }

    public static String companhiaToJson(CompanhiaAerea c) {
        if (c == null) {
            return "null";
        }

        return "{" +
                "\"id\":" + c.getId() + "," +
                "\"nome\":\"" + escape(c.getNome()) + "\"," +
                "\"codigoIATA\":\"" + escape(c.getCodigoIATA()) + "\"," +
                "\"pais\":\"" + escape(c.getPais()) + "\"," +
                "\"anoFundacao\":" + c.getAnoFundacao() +
                "}";
    }

    public static CompanhiaAerea jsonToCompanhia(String json) {
        int id = extractInt(json, "id");
        String nome = extractString(json, "nome");
        String codigoIata = extractString(json, "codigoIATA");
        String pais = extractString(json, "pais");
        int anoFundacao = extractInt(json, "anoFundacao");

        return new CompanhiaAerea(id, nome, codigoIata, pais, anoFundacao);
    }

    public static String listaCompanhiasToJson(List<CompanhiaAerea> companhias) {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < companhias.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(companhiaToJson(companhias.get(i)));
        }

        sb.append(']');
        return sb.toString();
    }

    public static List<CompanhiaAerea> jsonToListaCompanhias(String json) {
        List<CompanhiaAerea> resultado = new ArrayList<>();
        String conteudo = json == null ? "" : json.trim();

        if (conteudo.isEmpty() || "[]".equals(conteudo)) {
            return resultado;
        }

        int nivel = 0;
        int inicioObjeto = -1;

        for (int i = 0; i < conteudo.length(); i++) {
            char atual = conteudo.charAt(i);
            if (atual == '{') {
                if (nivel == 0) {
                    inicioObjeto = i;
                }
                nivel++;
            } else if (atual == '}') {
                nivel--;
                if (nivel == 0 && inicioObjeto >= 0) {
                    String objeto = conteudo.substring(inicioObjeto, i + 1);
                    resultado.add(jsonToCompanhia(objeto));
                    inicioObjeto = -1;
                }
            }
        }

        return resultado;
    }

    public static String simpleStringJson(String key, String value) {
        return "{\"" + key + "\":\"" + escape(value) + "\"}";
    }

    public static String simpleIntJson(String key, int value) {
        return "{\"" + key + "\":" + value + "}";
    }

    public static String simpleBooleanJson(String key, boolean value) {
        return "{\"" + key + "\":" + value + "}";
    }

    public static int extractInt(String json, String campo) {
        Pattern pattern = Pattern.compile("\\\"" + Pattern.quote(campo) + "\\\"\\s*:\\s*(-?\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Campo inteiro ausente: " + campo + " em " + json);
        }
        return Integer.parseInt(matcher.group(1));
    }

    public static boolean extractBoolean(String json, String campo) {
        Pattern pattern = Pattern.compile("\\\"" + Pattern.quote(campo) + "\\\"\\s*:\\s*(true|false)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Campo boolean ausente: " + campo + " em " + json);
        }
        return Boolean.parseBoolean(matcher.group(1));
    }

    public static String extractString(String json, String campo) {
        Pattern pattern = Pattern.compile("\\\"" + Pattern.quote(campo) + "\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"])*)\\\"");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Campo string ausente: " + campo + " em " + json);
        }
        return unescape(matcher.group(1));
    }

    private static String escape(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String unescape(String valor) {
        return valor.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
