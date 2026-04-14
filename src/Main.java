import model.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

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
}