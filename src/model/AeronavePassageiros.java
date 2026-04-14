package model;

import java.util.List;

public class AeronavePassageiros extends Aeronave implements Automatica {
    private int numAssentos;
    private List<String> classesDisponiveis;
    private int tripulacaoMinima;
    private boolean pilotoAutomaticoAtivo;

    public AeronavePassageiros(int id, String modelo, String fabricante,
                               String prefixo, double autonomiaKm, int anoFabricacao,
                               int numAssentos, List<String> classesDisponiveis,
                               int tripulacaoMinima) {
        super(id, modelo, fabricante, prefixo, autonomiaKm, anoFabricacao);
        this.numAssentos = numAssentos;
        this.classesDisponiveis = classesDisponiveis;
        this.tripulacaoMinima = tripulacaoMinima;
        this.pilotoAutomaticoAtivo = false;
    }

    // Getters
    public int getNumAssentos() { return numAssentos; }
    public List<String> getClassesDisponiveis() { return classesDisponiveis; }
    public int getTripulacaoMinima() { return tripulacaoMinima; }

    // Implementação da interface Automatica
    @Override
    public void ativarPilotoAutomatico() {
        this.pilotoAutomaticoAtivo = true;
        System.out.println("Piloto automático ATIVADO em " + getPrefixo());
    }

    @Override
    public void desativarPilotoAutomatico() {
        this.pilotoAutomaticoAtivo = false;
        System.out.println("Piloto automático DESATIVADO em " + getPrefixo());
    }

    @Override
    public boolean isAutomatico() { return pilotoAutomaticoAtivo; }

    @Override
    public String toString() {
        return super.toString() + " | Passageiros | Assentos: " + numAssentos
                + " | Classes: " + classesDisponiveis;
    }
}