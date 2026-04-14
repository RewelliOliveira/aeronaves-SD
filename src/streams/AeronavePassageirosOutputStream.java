package streams;

import model.AeronavePassageiros;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AeronavePassageirosOutputStream extends OutputStream {

    private final AeronavePassageiros[] objetos;
    private final int quantidade;
    private final int bytesPorAtributo;
    private final DataOutputStream saida;

    public AeronavePassageirosOutputStream(AeronavePassageiros[] objetos,
                                           int quantidade,
                                           int bytesPorAtributo,
                                           OutputStream destino) {
        if (bytesPorAtributo < 1 || bytesPorAtributo > 4) {
            throw new IllegalArgumentException("bytesPorAtributo deve estar entre 1 e 4");
        }
        this.objetos = objetos;
        this.quantidade = quantidade;
        this.bytesPorAtributo = bytesPorAtributo;
        this.saida = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        saida.write(b);
    }

    public void writeAll() throws IOException {
        // Header para que o InputStream saiba como reconstruir os inteiros.
        saida.writeByte(bytesPorAtributo);

        for (int i = 0; i < quantidade && i < objetos.length; i++) {
            AeronavePassageiros ap = objetos[i];

            writeIntByConfiguredSize(ap.getId());
            writeIntByConfiguredSize(ap.getNumAssentos());
            writeIntByConfiguredSize(ap.getTripulacaoMinima());

            byte[] prefixoBytes = ap.getPrefixo().getBytes("UTF-8");
            saida.writeShort(prefixoBytes.length);
            saida.write(prefixoBytes);

            saida.writeByte(ap.isAutomatico() ? 1 : 0);
        }
        saida.flush();
    }

    private void writeIntByConfiguredSize(int valor) throws IOException {
        for (int shift = (bytesPorAtributo - 1) * 8; shift >= 0; shift -= 8) {
            saida.writeByte((valor >> shift) & 0xFF);
        }
    }

    @Override
    public void close() throws IOException {
        saida.close();
    }
}