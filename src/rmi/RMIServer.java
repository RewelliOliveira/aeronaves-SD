package rmi;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RMIServer {
    private DatagramSocket socket;

    private InetAddress lastClientHost;
    private int lastClientPort;

    public RMIServer(int port) {
        try {
            this.socket = new DatagramSocket(port);
            System.out.println("[Middleware] Servidor RMI iniciado na porta " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getRequest() {
        try {
            byte[] buffer = new byte[65535];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            this.lastClientHost = packet.getAddress();
            this.lastClientPort = packet.getPort();

            byte[] data = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendReply(byte[] reply, InetAddress clientHost, int clientPort) {
        try {
            DatagramPacket packet = new DatagramPacket(reply, reply.length, clientHost, clientPort);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InetAddress getLastClientHost() {
        return lastClientHost;
    }

    public int getLastClientPort() {
        return lastClientPort;
    }
}
