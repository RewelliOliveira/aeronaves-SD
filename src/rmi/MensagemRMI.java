package rmi;
import java.io.*;

public class MensagemRMI {
    private int messageType;
    private int requestId;
    private String objectReference;
    private String methodId;
    private byte[] arguments;

    public MensagemRMI(int messageType, int requestId, String objectReference, String methodId, byte[] arguments) {
        this.messageType = messageType;
        this.requestId = requestId;
        this.objectReference = objectReference;
        this.methodId = methodId;
        this.arguments = arguments != null ? arguments : new byte[0];
    }

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(messageType);
        dos.writeInt(requestId);
        dos.writeUTF(objectReference);
        dos.writeUTF(methodId);

        dos.writeInt(arguments.length);
        if (arguments.length > 0) {
            dos.write(arguments);
        }

        dos.flush();
        return baos.toByteArray();
    }

    public static MensagemRMI fromBytes(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        int type = dis.readInt();
        int reqId = dis.readInt();
        String objRef = dis.readUTF();
        String metId = dis.readUTF();

        int argLength = dis.readInt();
        byte[] args = new byte[argLength];
        if (argLength > 0) {
            dis.readFully(args);
        }

        return new MensagemRMI(type, reqId, objRef, metId, args);
    }

    public int getMessageType() {
        return messageType;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public String getMethodId() {
        return methodId;
    }

    public byte[] getArguments() {
        return arguments;
    }
}
