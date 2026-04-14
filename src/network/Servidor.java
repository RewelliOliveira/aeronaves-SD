
package network;
import java.io.*;
import java.net.*;
import model.*;

public class Servidor {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);

        while (true) {
            Socket socket = serverSocket.accept();

            new Thread(() -> {
                try {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    String operacao = dis.readUTF();

                    if (operacao.equals("CADASTRAR")) {

                        int id = dis.readInt();
                        String nome = dis.readUTF();
                        String iata = dis.readUTF();

                        CompanhiaAerea companhia = new CompanhiaAerea(id, nome, iata, "", 0);

                        dos.writeUTF("RESPOSTA_OK");
                        dos.writeInt(companhia.getId());
                        dos.writeUTF(companhia.getNome());
                        dos.writeUTF(companhia.getIata());
                    } else {
                        dos.writeUTF("OPERACAO_INVALIDA");
                    }

                    dos.flush();

                    dis.close();
                    dos.close();
                    socket.close();
                } catch (Exception e) {
                    try { socket.close(); } catch (Exception ex) {}
                }
            }).start();
        }
    }
}