package streams;

import model.AeronavePassageiros;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AeronavePassageirosInputStream extends InputStream {

    private final DataInputStream entrada;

    public AeronavePassageirosInputStream(InputStream origem) {
        this.entrada = new DataInputStream(origem);
    }

    @Override
    public int read() throws IOException {
        return entrada.read();
    }

    public List<AeronavePassageiros> readAll() throws IOException {
        List<AeronavePassageiros> lista = new ArrayList<>();
        int total = entrada.readInt();

        for (int i = 0; i < total; i++) {
            int id = entrada.readInt();
            String modelo = entrada.readUTF();
            String fabricante = entrada.readUTF();
            String prefixo = entrada.readUTF();
            double autonomiaKm = entrada.readDouble();
            int anoFabricacao = entrada.readInt();
            int numAssentos = entrada.readInt();
            int quantidadeClasses = entrada.readInt();
            List<String> classesDisponiveis = new ArrayList<>();

            for (int j = 0; j < quantidadeClasses; j++) {
                classesDisponiveis.add(entrada.readUTF());
            }

            int tripulacaoMinima = entrada.readInt();
            boolean automatico = entrada.readBoolean();

            AeronavePassageiros aeronave = new AeronavePassageiros(
                    id,
                    modelo,
                    fabricante,
                    prefixo,
                    autonomiaKm,
                    anoFabricacao,
                    numAssentos,
                    classesDisponiveis,
                    tripulacaoMinima
            );

            if (automatico) {
                aeronave.ativarPilotoAutomatico();
            }

            lista.add(aeronave);
        }

        return lista;
    }

    @Override
    public void close() throws IOException {
        entrada.close();
    }
}
