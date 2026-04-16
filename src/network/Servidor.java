
package network;
import model.CompanhiaAerea;
import model.ServicoCompanhia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Servidor {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        ServicoCompanhia servico = new ServicoCompanhia();
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
                        String pais = dis.readUTF();
                        int anoFundacao = dis.readInt();
                        CompanhiaAerea companhia = new CompanhiaAerea(id, nome, iata, pais, anoFundacao);
                        servico.cadastrarCompanhia(companhia);
                        dos.writeUTF("RESPOSTA_OK");
                        dos.writeInt(companhia.getId());
                        dos.writeUTF(companhia.getNome());
                        dos.writeUTF(companhia.getCodigoIATA());
                        dos.writeUTF(companhia.getPais());
                        dos.writeInt(companhia.getAnoFundacao());
                    } else if (operacao.equals("LISTAR")) {
                        List<CompanhiaAerea> lista = servico.listarCompanhias();
                        dos.writeUTF("RESPOSTA_LISTA");
                        dos.writeInt(lista.size());
                        for (CompanhiaAerea c : lista) {
                            dos.writeInt(c.getId());
                            dos.writeUTF(c.getNome());
                            dos.writeUTF(c.getCodigoIATA());
                            dos.writeUTF(c.getPais());
                            dos.writeInt(c.getAnoFundacao());
                        }
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
