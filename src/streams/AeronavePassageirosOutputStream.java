package streams;

import model.AeronavePassageiros;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AeronavePassageirosOutputStream extends OutputStream {

    private final AeronavePassageiros[] objetos;
    private final int quantidade;
    private final DataOutputStream saida;

    public AeronavePassageirosOutputStream(AeronavePassageiros[] objetos,
                                           int quantidade,
                                           OutputStream destino) {
        this.objetos = objetos;
        this.quantidade = quantidade;
        this.saida = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        saida.write(b);
    }

    public void writeAll() throws IOException {
        int total = Math.min(quantidade, objetos.length);
        saida.writeInt(total);

        for (int i = 0; i < total; i++) {
            AeronavePassageiros ap = objetos[i];
            saida.writeInt(ap.getId());
            saida.writeUTF(ap.getModelo());
            saida.writeUTF(ap.getFabricante());
            saida.writeUTF(ap.getPrefixo());
            saida.writeDouble(ap.getAutonomiaKm());
            saida.writeInt(ap.getAnoFabricacao());
            saida.writeInt(ap.getNumAssentos());
            saida.writeInt(ap.getClassesDisponiveis().size());

            for (String classe : ap.getClassesDisponiveis()) {
                saida.writeUTF(classe);
            }

            saida.writeInt(ap.getTripulacaoMinima());
            saida.writeBoolean(ap.isAutomatico());
        }

        saida.flush();
    }

    @Override
    public void close() throws IOException {
        saida.close();
    }
}
