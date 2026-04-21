package network;
import model.CompanhiaAerea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String operacao = args.length > 0 ? args[0].toUpperCase() : "CADASTRAR";

        if (operacao.equals("LISTAR")) {
            dos.writeUTF("LISTAR");
            dos.flush();

            String status = dis.readUTF();

            if (status.equals("RESPOSTA_LISTA")) {
                int quantidade = dis.readInt();
                System.out.println("Total de companhias: " + quantidade);

                for (int i = 0; i < quantidade; i++) {
                    int id = dis.readInt();
                    String nome = dis.readUTF();
                    String iata = dis.readUTF();
                    String pais = dis.readUTF();
                    int anoFundacao = dis.readInt();
                    System.out.println(id + " " + nome + " " + iata + " " + pais + " " + anoFundacao);
                }
            } else {
                System.out.println("Erro na operacao");
            }
        } else {
            CompanhiaAerea companhia = new CompanhiaAerea(10, "LATAM Airlines", "LA", "Brasil", 1929);

        dos.writeUTF("CADASTRAR");
        dos.writeInt(companhia.getId());
        dos.writeUTF(companhia.getNome());
        dos.writeUTF(companhia.getCodigoIATA());
        dos.writeUTF(companhia.getPais());
        dos.writeInt(companhia.getAnoFundacao());

        dos.flush();

        String status = dis.readUTF();

        if (status.equals("RESPOSTA_OK")) {
            int id = dis.readInt();
            String nome = dis.readUTF();
            String iata = dis.readUTF();
            String pais = dis.readUTF();
            int anoFundacao = dis.readInt();

            System.out.println("Sucesso: " + id + " " + nome + " " + iata + " " + pais + " " + anoFundacao);
        } else {
            System.out.println("Erro na operação");
        }

        }

        dis.close();
        dos.close();
        socket.close();
    }
}
