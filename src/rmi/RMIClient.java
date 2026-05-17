package rmi;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RMIClient {
    private int currentRequestId = 1;

    public byte[] doOperation(RemoteObjectRef o, String methodId, byte[] arguments) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(10000);

            MensagemRMI request = new MensagemRMI(0, currentRequestId++, o.getNomeObjeto(), methodId, arguments);
            byte[] requestData = request.toBytes();

            InetAddress ip = InetAddress.getByName(o.getIp());
            DatagramPacket sendPacket = new DatagramPacket(requestData, requestData.length, ip, o.getPorta());
            socket.send(sendPacket);

            byte[] buffer = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);

            byte[] responseData = new byte[receivePacket.getLength()];
            System.arraycopy(receivePacket.getData(), 0, responseData, 0, receivePacket.getLength());

            MensagemRMI reply = MensagemRMI.fromBytes(responseData);

            return reply.getArguments();

        } catch (Exception e) {
            System.err.println("Erro na comunicação RMI: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
