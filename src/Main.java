import model.*;
import rmi.RemoteObjectRef;
import rmi.ServicoCompanhiaRmiProxy;
import rmi.JsonUtil;

import java.util.Locale;
import java.util.Scanner;
import java.util.List;

public class Main {
        private static final Scanner SCAN = new Scanner(System.in);

    public static void main(String[] args) {
                if (args.length > 0) {
                        String modo = args[0].toLowerCase(Locale.ROOT);
                        if ("rmi".equals(modo)) {
                                executarMenuRmi();
                                return;
                        }
                        if ("rmi-demo".equals(modo)) {
                                executarTesteRmiDemo();
                                return;
                        }
                        if ("local".equals(modo)) {
                                executarTesteLocal();
                                return;
                        }
                }

                executarMenuPrincipal();
        }

        private static void executarMenuPrincipal() {
                while (true) {
                        System.out.println("\n========================================");
                        System.out.println("     SISTEMA AERONAVES - MENU MAIN");
                        System.out.println("========================================");
                        System.out.println("1 - Rodar demo local");
                        System.out.println("2 - Abrir menu RMI");
                        System.out.println("3 - Rodar demo RMI automatica");
                        System.out.println("0 - Sair");
                        System.out.print("Escolha: ");

                        String opcao = SCAN.nextLine().trim();
                        switch (opcao) {
                                case "1" -> executarTesteLocal();
                                case "2" -> executarMenuRmi();
                                case "3" -> executarTesteRmiDemo();
                                case "0" -> {
                                        System.out.println("Encerrando aplicacao.");
                                        return;
                                }
                                default -> System.out.println("Opcao invalida.");
                        }
                }
        }

        private static void executarTesteLocal() {

        // --- Criando aeronaves ---
        AeronavePassageiros ap1 = new AeronavePassageiros(
                1, "A320", "Airbus", "PT-MHG", 6150.0, 2015,
                150, List.of("Econômica", "Executiva"), 6
        );

        AeronavePassageiros ap2 = new AeronavePassageiros(
                2, "B737-800", "Boeing", "PR-GXJ", 5765.0, 2018,
                189, List.of("Econômica"), 5
        );

        AeronaveCarga ac1 = new AeronaveCarga(
                3, "B777F", "Boeing", "PT-MQA", 9200.0, 2020,
                102000.0, "Geral", false
        );

        // --- Testando interface Automatica ---
        System.out.println("=== Teste Piloto Automático ===");
        ap1.ativarPilotoAutomatico();
        System.out.println("Automático ativo? " + ap1.isAutomatico());
        ap1.desativarPilotoAutomatico();
        System.out.println("Automático ativo? " + ap1.isAutomatico());

        // --- Criando companhias ---
        CompanhiaAerea latam = new CompanhiaAerea(1, "LATAM Airlines", "LA", "Brasil", 1929);
        CompanhiaAerea gol   = new CompanhiaAerea(2, "GOL Linhas Aéreas", "G3", "Brasil", 2000);

        // --- Adicionando aeronaves às companhias ---
        System.out.println("\n=== Adicionando à frota ===");
        latam.adicionarAeronave(ap1);
        latam.adicionarAeronave(ac1);
        gol.adicionarAeronave(ap2);

        // --- Testando GerenciadorFrota ---
        System.out.println("\n=== Gerenciador de Frota ===");
        GerenciadorFrota gerenciador = new GerenciadorFrota();
        gerenciador.adicionarAeronave(ap1);
        gerenciador.adicionarAeronave(ap2);
        gerenciador.adicionarAeronave(ac1);
        gerenciador.exibirFrota();

        System.out.println("\nBuscando aeronave id=2: " + gerenciador.buscarPorId(2));
        gerenciador.removerAeronave(2);
        gerenciador.exibirFrota();

        // --- Testando ServicoCompanhia ---
        System.out.println("\n=== Serviço Companhia ===");
        ServicoCompanhia servico = new ServicoCompanhia();
        servico.cadastrarCompanhia(latam);
        servico.cadastrarCompanhia(gol);
        servico.exibirCompanhias();

        System.out.println("\nBuscando por IATA 'G3': " + servico.buscarPorIata("G3"));
        servico.removerCompanhia(1);
        servico.exibirCompanhias();
    }

        private static void executarMenuRmi() {
                System.out.println("\n=== MENU RMI (CLIENTE) ===");
                RemoteObjectRef ref = new RemoteObjectRef("127.0.0.1", 7000, "ServicoCompanhia");
                ServicoCompanhiaRmiProxy proxy = new ServicoCompanhiaRmiProxy(ref);

                while (true) {
                        System.out.println("\n----------------------------------------");
                        System.out.println("1 - Cadastrar LATAM (id 1)");
                        System.out.println("2 - Cadastrar GOL (id 2)");
                        System.out.println("3 - Cadastrar AZUL (id 3)");
                        System.out.println("4 - Listar companhias");
                        System.out.println("5 - Buscar por IATA G3");
                        System.out.println("6 - Remover companhia id 1");
                        System.out.println("7 - Rodar demo automatica");
                        System.out.println("0 - Voltar ao menu principal");
                        System.out.print("Escolha: ");

                        String opcao = SCAN.nextLine().trim();
                        try {
                                switch (opcao) {
                                        case "1" -> cadastrarSeNaoExiste(proxy, criarLatam());
                                        case "2" -> cadastrarSeNaoExiste(proxy, criarGol());
                                        case "3" -> cadastrarSeNaoExiste(proxy, criarAzul());
                                        case "4" -> listarCompanhiasRemotas(proxy);
                                        case "5" -> System.out.println("Busca G3 => " + proxy.buscarPorIata("G3"));
                                        case "6" -> System.out.println("Remocao id 1 => " + proxy.removerCompanhia(1));
                                        case "7" -> executarTesteRmiDemo(proxy);
                                        case "0" -> {
                                                return;
                                        }
                                        default -> System.out.println("Opcao invalida.");
                                }
                        } catch (Exception e) {
                                System.out.println("Erro na operacao remota: " + e.getMessage());
                        }
                }
        }

        private static void executarTesteRmiDemo() {
                RemoteObjectRef ref = new RemoteObjectRef("127.0.0.1", 7000, "ServicoCompanhia");
                ServicoCompanhiaRmiProxy proxy = new ServicoCompanhiaRmiProxy(ref);
                executarTesteRmiDemo(proxy);
        }

        private static void executarTesteRmiDemo(ServicoCompanhiaRmiProxy proxy) {
                System.out.println("\n=== DEMO RMI AUTOMATICA ===");
                cadastrarSeNaoExiste(proxy, criarLatam());
                cadastrarSeNaoExiste(proxy, criarGol());

                CompanhiaAerea encontrada = proxy.buscarPorIata("G3");
                System.out.println("Busca por IATA G3: " + encontrada);

                listarCompanhiasRemotas(proxy);

                boolean removida = proxy.removerCompanhia(1);
                System.out.println("Companhia id=1 removida? " + removida);

                List<CompanhiaAerea> listaFinal = proxy.listarCompanhias();
                System.out.println("Total final: " + listaFinal.size());
        }

        private static void listarCompanhiasRemotas(ServicoCompanhiaRmiProxy proxy) {
                List<CompanhiaAerea> lista = proxy.listarCompanhias();
                System.out.println("Total de companhias remotas: " + lista.size());
                System.out.println("JSON:");
                System.out.println(JsonUtil.listaCompanhiasToJson(lista));
        }

        private static void cadastrarSeNaoExiste(ServicoCompanhiaRmiProxy proxy, CompanhiaAerea companhia) {
                CompanhiaAerea existente = proxy.buscarPorIata(companhia.getCodigoIATA());
                if (existente != null) {
                        System.out.println("Ja existe companhia com IATA " + companhia.getCodigoIATA() + ": " + existente);
                        return;
                }
                proxy.cadastrarCompanhia(companhia);
                System.out.println("Cadastrada: " + companhia.getNome() + " (" + companhia.getCodigoIATA() + ")");
        }

        private static CompanhiaAerea criarLatam() {
                return new CompanhiaAerea(1, "LATAM Airlines", "LA", "Brasil", 1929);
        }

        private static CompanhiaAerea criarGol() {
                return new CompanhiaAerea(2, "GOL Linhas Aereas", "G3", "Brasil", 2000);
        }

        private static CompanhiaAerea criarAzul() {
                return new CompanhiaAerea(3, "Azul Linhas Aereas", "AD", "Brasil", 2008);
        }
}