package model;

import java.util.ArrayList;
import java.util.List;

public class CompanhiaAerea {
    private int id;
    private String nome;
    private String codigoIATA;
    private String pais;
    private int anoFundacao;
    private List<Aeronave> frota;

    public CompanhiaAerea(int id, String nome, String codigoIATA,
                          String pais, int anoFundacao) {
        this.id = id;
        this.nome = nome;
        this.codigoIATA = codigoIATA;
        this.pais = pais;
        this.anoFundacao = anoFundacao;
        this.frota = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCodigoIATA() { return codigoIATA; }
    public String getPais() { return pais; }
    public int getAnoFundacao() { return anoFundacao; }
    public List<Aeronave> getFrota() { return frota; }

    public void adicionarAeronave(Aeronave aeronave) {
        frota.add(aeronave);
        System.out.println("Aeronave " + aeronave.getPrefixo()
                + " adicionada à " + nome);
    }

    public boolean removerAeronave(int aeronaveId) {
        return frota.removeIf(a -> a.getId() == aeronaveId);
    }

    public Aeronave buscarAeronave(int aeronaveId) {
        return frota.stream()
                .filter(a -> a.getId() == aeronaveId)
                .findFirst()
                .orElse(null);
    }

    public void listarFrota() {
        if (frota.isEmpty()) {
            System.out.println("Frota vazia.");
            return;
        }
        frota.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " (" + codigoIATA + ") - "
                + pais + " | Aeronaves: " + frota.size();
    }
}