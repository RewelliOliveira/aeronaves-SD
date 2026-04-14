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
        for (int i = 0; i < quantidade && i < objetos.length; i++) {
            AeronavePassageiros ap = objetos[i];

            saida.writeInt(ap.getId());
            saida.writeInt(ap.getNumAssentos());
            saida.writeInt(ap.getTripulacaoMinima());

            byte[] prefixoBytes = ap.getPrefixo().getBytes("UTF-8");
            saida.writeShort(prefixoBytes.length);
            saida.write(prefixoBytes);

            saida.writeByte(ap.isAutomatico() ? 1 : 0);
        }
        saida.flush();
    }

    @Override
    public void close() throws IOException {
        saida.close();
    }
}