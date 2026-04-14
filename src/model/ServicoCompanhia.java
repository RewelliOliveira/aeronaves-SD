package model;

import java.util.ArrayList;
import java.util.List;

public class ServicoCompanhia {
    private List<CompanhiaAerea> companhias;

    public ServicoCompanhia() {
        this.companhias = new ArrayList<>();
    }

    public void cadastrarCompanhia(CompanhiaAerea companhia) {
        companhias.add(companhia);
        System.out.println("Companhia cadastrada: " + companhia.getNome());
    }

    public boolean removerCompanhia(int id) {
        boolean removida = companhias.removeIf(c -> c.getId() == id);
        if (removida) System.out.println("Companhia " + id + " removida.");
        else System.out.println("Companhia " + id + " não encontrada.");
        return removida;
    }

    public CompanhiaAerea buscarPorIata(String iata) {
        return companhias.stream()
                .filter(c -> c.getCodigoIATA().equalsIgnoreCase(iata))
                .findFirst()
                .orElse(null);
    }

    public List<CompanhiaAerea> listarCompanhias() {
        return new ArrayList<>(companhias);
    }

    public void exibirCompanhias() {
        if (companhias.isEmpty()) {
            System.out.println("Nenhuma companhia cadastrada.");
            return;
        }
        System.out.println("=== Companhias Aéreas ===");
        companhias.forEach(System.out::println);
    }
}