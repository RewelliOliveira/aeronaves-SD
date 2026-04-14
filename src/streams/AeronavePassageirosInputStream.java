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

    // Obrigatório por ser subclasse de InputStream
    @Override
    public int read() throws IOException {
        return entrada.read();
    }

    // Método principal — lê os bytes e reconstrói os objetos
    public List<AeronavePassageiros> readAll() throws IOException {
        List<AeronavePassageiros> lista = new ArrayList<>();

        try {
            while (entrada.available() > 0) {
                // Atributo 1 — id (4 bytes)
                int id = entrada.readInt();

                // Atributo 2 — numAssentos (4 bytes)
                int numAssentos = entrada.readInt();

                // Atributo 3 — tripulacaoMinima (4 bytes)
                int tripulacaoMinima = entrada.readInt();

                // Atributo 4 — prefixo (2 bytes tamanho + N bytes UTF-8)
                short lenPrefixo = entrada.readShort();
                byte[] prefixoBytes = new byte[lenPrefixo];
                entrada.readFully(prefixoBytes);
                String prefixo = new String(prefixoBytes, "UTF-8");

                // Atributo 5 — isAutomatico (1 byte)
                boolean automatico = entrada.readByte() == 1;

                // Reconstruindo o objeto
                AeronavePassageiros ap = new AeronavePassageiros(
                        id, "desconhecido", "desconhecido", prefixo,
                        0.0, 0, numAssentos, List.of(), tripulacaoMinima
                );

                if (automatico) ap.ativarPilotoAutomatico();

                lista.add(ap);
            }
        } catch (java.io.EOFException e) {
            // Fim do stream — normal
        }

        return lista;
    }

    @Override
    public void close() throws IOException {
        entrada.close();
    }
}