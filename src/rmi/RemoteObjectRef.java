package rmi;
public class RemoteObjectRef {
    private String ip;
    private int porta;
    private String nomeObjeto;

    public RemoteObjectRef(String ip, int porta, String nomeObjeto) {
        this.ip = ip;
        this.porta = porta;
        this.nomeObjeto = nomeObjeto;
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }

    public String getNomeObjeto() {
        return nomeObjeto;
    }
}
