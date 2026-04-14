package model;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorFrota {
    private List<Aeronave> aeronaves;

    public GerenciadorFrota() {
        this.aeronaves = new ArrayList<>();
    }

    public void adicionarAeronave(Aeronave aeronave) {
        aeronaves.add(aeronave);
        System.out.println("Aeronave adicionada: " + aeronave);
    }

    public boolean removerAeronave(int id) {
        boolean removida = aeronaves.removeIf(a -> a.getId() == id);
        if (removida) System.out.println("Aeronave " + id + " removida.");
        else System.out.println("Aeronave " + id + " não encontrada.");
        return removida;
    }

    public Aeronave buscarPorId(int id) {
        return aeronaves.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Aeronave> listarTodas() {
        return new ArrayList<>(aeronaves);
    }

    public void exibirFrota() {
        if (aeronaves.isEmpty()) {
            System.out.println("Nenhuma aeronave cadastrada.");
            return;
        }
        System.out.println("=== Frota Completa ===");
        aeronaves.forEach(System.out::println);
    }
}