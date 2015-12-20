package ch.aschaefer.udp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ch.aschaefer.udp.ByteUtil.toHex;

/**
 * @author aschaefer
 * @since 19.12.15.
 */
public class ControlMessage {
    private String hex;
    private String timestamp;
    private String source;
    private String target;


    public String getHex() {
        return hex;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }



    @Override
    public String toString() {
        return "Datagram{" +
                "hex='" + hex + '\'' +
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

    public void setTarget(String target) {
        this.target = target;
    }

    public ControlMessage target(String target) {
        setTarget(target);
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ControlMessage timestamp(String timestamp) {
        setTimestamp(timestamp);
        return this;
    }
}
