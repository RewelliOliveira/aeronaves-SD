package model;

public abstract class Aeronave {
    private int id;
    private String modelo;
    private String fabricante;
    private String prefixo;
    private double autonomiaKm;
    private int anoFabricacao;

    public Aeronave(int id, String modelo, String fabricante,
                    String prefixo, double autonomiaKm, int anoFabricacao) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.prefixo = prefixo;
        this.autonomiaKm = autonomiaKm;
        this.anoFabricacao = anoFabricacao;
    }

    // Getters
    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public String getFabricante() { return fabricante; }
    public String getPrefixo() { return prefixo; }
    public double getAutonomiaKm() { return autonomiaKm; }
    public int getAnoFabricacao() { return anoFabricacao; }

    @Override
    public String toString() {
        return "[" + id + "] " + modelo + " (" + fabricante + ") - " + prefixo;
    }
}