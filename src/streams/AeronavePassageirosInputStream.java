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

        try {
            while (entrada.available() > 0) {
                int id = entrada.readInt();
                int numAssentos = entrada.readInt();
                int tripulacaoMinima = entrada.readInt();
                short lenPrefixo = entrada.readShort();
                byte[] prefixoBytes = new byte[lenPrefixo];
                entrada.readFully(prefixoBytes);
                String prefixo = new String(prefixoBytes, "UTF-8");
                boolean automatico = entrada.readByte() == 1;
                AeronavePassageiros ap = new AeronavePassageiros(
                        id, "desconhecido", "desconhecido", prefixo,
                        0.0, 0, numAssentos, List.of(), tripulacaoMinima
                );

                if (automatico) ap.ativarPilotoAutomatico();

                lista.add(ap);
            }
        } catch (java.io.EOFException e) {
        }

        return lista;
    }

    @Override
    public void close() throws IOException {
        entrada.close();
    }
}