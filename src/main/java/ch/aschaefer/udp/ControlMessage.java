package ch.aschaefer.udp;

/**
 * @author aschaefer
 * @since 19.12.15.
 */
public class ControlMessage {
    private String hex;
    private String timestamp;
    private String source;
    private String targetHost;
    private int targetPort = 10000;


    public String getHex() {
        return hex;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public String getSource() {
        return source;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public int getTargetPort() {
        return targetPort;
    }

    @Override
    public String toString() {
        return "ControlMessage{" +
                "hex='" + hex + '\'' +
                ", source='" + source + '\'' +
                ", targetHost='" + targetHost + '\'' +
                ", targetPort=" + targetPort +
                '}';
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public ControlMessage hex(String hex) {
        setHex(hex);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ControlMessage source(String source) {
        setSource(source);
        return this;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public ControlMessage targetHost(String targetHost) {
        setTargetHost(targetHost);
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ControlMessage timestamp(String timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public ControlMessage targetPort(int targetPort) {
        setTargetPort(targetPort);
        return this;
    }
}
