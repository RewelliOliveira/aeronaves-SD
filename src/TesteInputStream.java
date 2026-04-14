import model.AeronavePassageiros;
import streams.AeronavePassageirosInputStream;
import streams.AeronavePassageirosOutputStream;

import java.io.*;
import java.net.*;
import java.util.List;

public class TesteInputStream {

    // TESTE B
    static void testeB() throws IOException {
        System.out.println("\n=== TESTE B — FileInputStream ===");

        FileInputStream fis = new FileInputStream("aeronaves.bin");
        AeronavePassageirosInputStream stream =
                new AeronavePassageirosInputStream(fis);

        List<AeronavePassageiros> lista = stream.readAll();
        stream.close();

        System.out.println("Objetos lidos do arquivo: " + lista.size());
        for (AeronavePassageiros ap : lista) {
            System.out.println("  -> " + ap);
        }
    }

    // TESTE C — servidor envia com OutputStream, cliente lê com InputStream
    static void testeC() throws IOException, InterruptedException {
        System.out.println("\n=== TESTE C — Socket TCP ===");

        // Thread do servidor — envia os objetos
        Thread servidor = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(9998)) {
                System.out.println("[SERVIDOR] Aguardando conexão na porta 9998...");
                Socket conn = serverSocket.accept();
                System.out.println("[SERVIDOR] Cliente conectado! Enviando dados...");

                AeronavePassageiros ap1 = new AeronavePassageiros(
                        1, "A320", "Airbus", "PT-MHG", 6150.0, 2015,
                        150, List.of("Econômica", "Executiva"), 6
                );
                AeronavePassageiros ap2 = new AeronavePassageiros(
                        2, "B737-800", "Boeing", "PR-GXJ", 5765.0, 2018,
                        189, List.of("Econômica"), 5
                );
                ap1.ativarPilotoAutomatico();

                AeronavePassageiros[] aeronaves = {ap1, ap2};
                AeronavePassageirosOutputStream out =
                        new AeronavePassageirosOutputStream(
                                aeronaves, 2, 4, conn.getOutputStream()
                        );
                out.writeAll();
                out.close();
                System.out.println("[SERVIDOR] Dados enviados!");

            } catch (IOException e) {
                System.out.println("[SERVIDOR] Erro: " + e.getMessage());
            }
        });

        servidor.start();
        Thread.sleep(500);

        // Cliente — recebe e reconstrói os objetos
        try (Socket socket = new Socket("127.0.0.1", 9998)) {
            System.out.println("[CLIENTE] Conectado! Lendo dados...");

            AeronavePassageirosInputStream in =
                    new AeronavePassageirosInputStream(socket.getInputStream());

            Thread.sleep(300);

            List<AeronavePassageiros> lista = in.readAll();
            in.close();

            System.out.println("[CLIENTE] Objetos recebidos: " + lista.size());
            for (AeronavePassageiros ap : lista) {
                System.out.println("  -> " + ap);
            }
        }

        servidor.join();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        testeB();
        testeC();
    }
}