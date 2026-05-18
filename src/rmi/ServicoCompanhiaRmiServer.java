package rmi;

import model.CompanhiaAerea;
import model.ServicoCompanhia;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ServicoCompanhiaRmiServer {
    private static final String OBJECT_NAME = "ServicoCompanhia";

    public static void main(String[] args) {
        RMIServer middleware = new RMIServer(7000);
        ServicoCompanhia servico = new ServicoCompanhia();

        System.out.println("[RMI Server] Pronto para receber chamadas remotas.");

        while (true) {
            try {
                byte[] requestData = middleware.getRequest();
                if (requestData == null) {
                    continue;
                }

                MensagemRMI request = MensagemRMI.fromBytes(requestData);
                String responseJson = processarRequisicao(request, servico);

                MensagemRMI reply = new MensagemRMI(
                        1,
                        request.getRequestId(),
                        request.getObjectReference(),
                        request.getMethodId(),
                        responseJson.getBytes(StandardCharsets.UTF_8)
                );

                middleware.sendReply(
                        reply.toBytes(),
                        middleware.getLastClientHost(),
                        middleware.getLastClientPort()
                );
            } catch (Exception e) {
                System.err.println("[RMI Server] Erro ao processar requisicao: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static String processarRequisicao(MensagemRMI request, ServicoCompanhia servico) {
        if (!OBJECT_NAME.equals(request.getObjectReference())) {
            return JsonUtil.simpleStringJson("erro", "Objeto remoto desconhecido: " + request.getObjectReference());
        }

        String argsJson = new String(request.getArguments(), StandardCharsets.UTF_8);

        try {
            return switch (request.getMethodId()) {
                case "cadastrarCompanhia" -> {
                    CompanhiaAerea companhia = JsonUtil.jsonToCompanhia(argsJson);
                    servico.cadastrarCompanhia(companhia);
                    yield JsonUtil.companhiaToJson(companhia);
                }
                case "removerCompanhia" -> {
                    int id = JsonUtil.extractInt(argsJson, "id");
                    boolean removida = servico.removerCompanhia(id);
                    yield JsonUtil.simpleBooleanJson("removida", removida);
                }
                case "buscarPorIata" -> {
                    String iata = JsonUtil.extractString(argsJson, "iata");
                    CompanhiaAerea encontrada = servico.buscarPorIata(iata);
                    yield JsonUtil.companhiaToJson(encontrada);
                }
                case "listarCompanhias" -> {
                    List<CompanhiaAerea> lista = servico.listarCompanhias();
                    yield JsonUtil.listaCompanhiasToJson(lista);
                }
                default -> JsonUtil.simpleStringJson("erro", "Metodo remoto desconhecido: " + request.getMethodId());
            };
        } catch (Exception e) {
            return JsonUtil.simpleStringJson("erro", "Falha ao executar metodo: " + e.getMessage());
        }
    }
}
