package network;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF("CADASTRAR");
        dos.writeInt(10);
        dos.writeUTF("LATAM Airlines");
        dos.writeUTF("LA");

        dos.flush();

        String status = dis.readUTF();

        if (status.equals("RESPOSTA_OK")) {
            int id = dis.readInt();
            String nome = dis.readUTF();
            String iata = dis.readUTF();

            System.out.println("Sucesso: " + id + " " + nome + " " + iata);
        } else {
            System.out.println("Erro na operação");
        }

        dis.close();
        dos.close();
        socket.close();
    }
}