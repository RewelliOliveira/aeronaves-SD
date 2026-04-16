package network;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class MulticastAvisoListener {
    private static final String GRUPO = "230.0.0.1";
    private static final int PORTA = 4446;

    public static void main(String[] args) throws Exception {
        MulticastSocket socket = new MulticastSocket(PORTA);
        InetAddress group = InetAddress.getByName(GRUPO);
        socket.joinGroup(group);
        byte[] buf = new byte[256];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
            System.out.println("Aviso recebido: " + received);
        }
    }
}
