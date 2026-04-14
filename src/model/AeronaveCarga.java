package model;

public class AeronaveCarga extends Aeronave implements Automatica {
    private double capacidadeCargaKg;
    private String tipoMercadoria;
    private boolean temperaturaControlada;
    private boolean pilotoAutomaticoAtivo;

    public AeronaveCarga(int id, String modelo, String fabricante,
                         String prefixo, double autonomiaKm, int anoFabricacao,
                         double capacidadeCargaKg, String tipoMercadoria,
                         boolean temperaturaControlada) {
        super(id, modelo, fabricante, prefixo, autonomiaKm, anoFabricacao);
        this.capacidadeCargaKg = capacidadeCargaKg;
        this.tipoMercadoria = tipoMercadoria;
        this.temperaturaControlada = temperaturaControlada;
        this.pilotoAutomaticoAtivo = false;
    }

    // Getters
    public double getCapacidadeCargaKg() { return capacidadeCargaKg; }
    public String getTipoMercadoria() { return tipoMercadoria; }
    public boolean isTemperaturaControlada() { return temperaturaControlada; }

    //interface Automatica
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
        return super.toString() + " | Carga | Capacidade: " + capacidadeCargaKg
                + "kg | Mercadoria: " + tipoMercadoria
                + " | Temp. controlada: " + temperaturaControlada;
    }
}