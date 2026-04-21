import model.AeronavePassageiros;
import streams.AeronavePassageirosOutputStream;

import java.io.*;
import java.net.*;
import java.util.List;

public class TesteOutputStream {
    static AeronavePassageiros[] criarAeronaves() {
        AeronavePassageiros ap1 = new AeronavePassageiros(
                1, "A320", "Airbus", "PT-MHG", 6150.0, 2015,
                150, List.of("Econômica", "Executiva"), 6
        );
        AeronavePassageiros ap2 = new AeronavePassageiros(
                2, "B737-800", "Boeing", "PR-GXJ", 5765.0, 2018,
                189, List.of("Econômica"), 5
        );
        ap1.ativarPilotoAutomatico();
        return new AeronavePassageiros[]{ap1, ap2};
    }

    // TESTE A — saída padrão (System.out)
    static void testeA() throws IOException {
        System.out.println("\n=== TESTE A — System.out ===");
        AeronavePassageiros[] aeronaves = criarAeronaves();
        OutputStream wrapper = new FilterOutputStream(System.out) {
            @Override
            public void close() throws IOException {
                flush();
            }
        };
        AeronavePassageirosOutputStream stream =
                new AeronavePassageirosOutputStream(aeronaves, 2, wrapper);
        stream.writeAll();
        stream.close();
        System.out.println("\n(bytes acima sao a serializacao binaria)");
    }

    // TESTE B — arquivo (FileOutputStream)
    static void testeB() throws IOException {
        System.out.println("\n=== TESTE B — FileOutputStream ===");
        AeronavePassageiros[] aeronaves = criarAeronaves();
        FileOutputStream fos = new FileOutputStream("aeronaves.bin");
        AeronavePassageirosOutputStream stream =
                new AeronavePassageirosOutputStream(aeronaves, 2, fos);
        stream.writeAll();
        stream.close();
        System.out.println("Arquivo 'aeronaves.bin' criado com sucesso!");
    }

    // TESTE C — servidor TCP
    static void testeC() throws IOException, InterruptedException {
        System.out.println("\n=== TESTE C — Socket TCP ===");

        Thread servidor = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(9999)) {
                System.out.println("[SERVIDOR] Aguardando conexão na porta 9999...");
                Socket conn = serverSocket.accept();
                System.out.println("[SERVIDOR] Cliente conectado!");
                InputStream in = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesLidos = in.read(buffer);
                System.out.println("[SERVIDOR] Bytes recebidos: " + bytesLidos);
                conn.close();
            } catch (IOException e) {
                System.out.println("[SERVIDOR] Erro: " + e.getMessage());
            }
        });
        servidor.start();
        Thread.sleep(500);

        // Cliente — envia os objetos via socket
        try (Socket socket = new Socket("127.0.0.1", 9999)) {
            System.out.println("[CLIENTE] Conectado ao servidor!");
            AeronavePassageiros[] aeronaves = criarAeronaves();
            AeronavePassageirosOutputStream stream =
                    new AeronavePassageirosOutputStream(aeronaves, 2,
                            socket.getOutputStream());
            stream.writeAll();
            stream.close();
            System.out.println("[CLIENTE] Dados enviados com sucesso!");
        }

        servidor.join();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        testeA();
        testeB();
        testeC();
    }
}
