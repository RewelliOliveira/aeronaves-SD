package rmi;

import model.CompanhiaAerea;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ServicoCompanhiaRmiProxy {
    private final RemoteObjectRef remoteRef;
    private final RMIClient client;

    public ServicoCompanhiaRmiProxy(RemoteObjectRef remoteRef) {
        this.remoteRef = remoteRef;
        this.client = new RMIClient();
    }

    public CompanhiaAerea cadastrarCompanhia(CompanhiaAerea companhia) {
        String requestJson = JsonUtil.companhiaToJson(companhia);
        byte[] responseBytes = client.doOperation(
                remoteRef,
                "cadastrarCompanhia",
                requestJson.getBytes(StandardCharsets.UTF_8)
        );

        return parseCompanhiaResponse(responseBytes);
    }

    public boolean removerCompanhia(int id) {
        String requestJson = JsonUtil.simpleIntJson("id", id);
        byte[] responseBytes = client.doOperation(
                remoteRef,
                "removerCompanhia",
                requestJson.getBytes(StandardCharsets.UTF_8)
        );

        String responseJson = parseTextResponse(responseBytes);
        ensureNoError(responseJson);
        return JsonUtil.extractBoolean(responseJson, "removida");
    }

    public CompanhiaAerea buscarPorIata(String iata) {
        String requestJson = JsonUtil.simpleStringJson("iata", iata);
        byte[] responseBytes = client.doOperation(
                remoteRef,
                "buscarPorIata",
                requestJson.getBytes(StandardCharsets.UTF_8)
        );

        return parseCompanhiaResponse(responseBytes);
    }

    public List<CompanhiaAerea> listarCompanhias() {
        byte[] responseBytes = client.doOperation(
                remoteRef,
                "listarCompanhias",
                new byte[0]
        );

        String responseJson = parseTextResponse(responseBytes);
        ensureNoError(responseJson);
        return JsonUtil.jsonToListaCompanhias(responseJson);
    }

    private CompanhiaAerea parseCompanhiaResponse(byte[] responseBytes) {
        String responseJson = parseTextResponse(responseBytes);
        ensureNoError(responseJson);

        if ("null".equals(responseJson.trim())) {
            return null;
        }

        return JsonUtil.jsonToCompanhia(responseJson);
    }

    private String parseTextResponse(byte[] responseBytes) {
        if (responseBytes == null) {
            throw new IllegalStateException("Resposta RMI nula");
        }
        return new String(responseBytes, StandardCharsets.UTF_8);
    }

    private void ensureNoError(String responseJson) {
        String conteudo = responseJson.trim();
        if (conteudo.contains("\"erro\"")) {
            String erro = JsonUtil.extractString(conteudo, "erro");
            throw new IllegalStateException("Erro remoto: " + erro);
        }
    }
}
