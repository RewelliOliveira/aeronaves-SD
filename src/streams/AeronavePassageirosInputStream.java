package streams;

import model.AeronavePassageiros;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

public class AeronavePassageirosInputStream extends InputStream {

    private final PushbackInputStream origemComRetorno;
    private final DataInputStream entrada;

    public AeronavePassageirosInputStream(InputStream origem) {
        this.origemComRetorno = new PushbackInputStream(origem, 1);
        this.entrada = new DataInputStream(origemComRetorno);
    }

    @Override
    public int read() throws IOException {
        return entrada.read();
    }

    public List<AeronavePassageiros> readAll() throws IOException {
        List<AeronavePassageiros> lista = new ArrayList<>();
        int bytesPorAtributo;

        try {
            int possivelCabecalho = entrada.readUnsignedByte();
            if (possivelCabecalho >= 1 && possivelCabecalho <= 4) {
                bytesPorAtributo = possivelCabecalho;
            } else {
                bytesPorAtributo = 4;
                origemComRetorno.unread(possivelCabecalho);
            }

            while (true) {
                int id = readIntByConfiguredSize(bytesPorAtributo);
                int numAssentos = readIntByConfiguredSize(bytesPorAtributo);
                int tripulacaoMinima = readIntByConfiguredSize(bytesPorAtributo);
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

    private int readIntByConfiguredSize(int bytesPorAtributo) throws IOException {
        int valor = 0;
        for (int i = 0; i < bytesPorAtributo; i++) {
            valor = (valor << 8) | entrada.readUnsignedByte();
        }
        return valor;
    }

    @Override
    public void close() throws IOException {
        entrada.close();
    }
}