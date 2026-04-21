package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class MulticastAvisoSender {
    private static final String GRUPO = "230.0.0.1";
    private static final int PORTA = 4446;

    public static void main(String[] args) throws Exception {
        String msg = args.length > 0 ? String.join(" ", args) : "AVISO_GERAL";
        InetAddress group = InetAddress.getByName(GRUPO);
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = msg.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORTA);
        socket.send(packet);
        socket.close();
        System.out.println("Aviso enviado: " + msg);
    }
}
