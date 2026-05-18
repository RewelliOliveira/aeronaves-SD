package rmi;

import model.CompanhiaAerea;

import java.util.List;

public class RmiDemoCliente {
    public static void main(String[] args) {
        RemoteObjectRef ref = new RemoteObjectRef("127.0.0.1", 7000, "ServicoCompanhia");
        ServicoCompanhiaRmiProxy proxy = new ServicoCompanhiaRmiProxy(ref);

        CompanhiaAerea latam = new CompanhiaAerea(1, "LATAM Airlines", "LA", "Brasil", 1929);
        CompanhiaAerea gol = new CompanhiaAerea(2, "GOL Linhas Aereas", "G3", "Brasil", 2000);

        proxy.cadastrarCompanhia(latam);
        proxy.cadastrarCompanhia(gol);

        CompanhiaAerea encontrada = proxy.buscarPorIata("G3");
        System.out.println("Busca por IATA G3: " + encontrada);

        List<CompanhiaAerea> lista = proxy.listarCompanhias();
        System.out.println("Total de companhias remotas: " + lista.size());
        for (CompanhiaAerea c : lista) {
            System.out.println(c);
        }

        boolean removida = proxy.removerCompanhia(1);
        System.out.println("Companhia id=1 removida? " + removida);

        List<CompanhiaAerea> listaFinal = proxy.listarCompanhias();
        System.out.println("Total final: " + listaFinal.size());
    }
}
