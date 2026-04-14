package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastAvisoSender {
    public static void main(String[] args) throws Exception {
        String msg = "Aviso global para todas as aeronaves";
        InetAddress group = InetAddress.getByName("230.0.0.1");
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
    }
}
