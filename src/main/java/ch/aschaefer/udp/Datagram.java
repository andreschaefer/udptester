package ch.aschaefer.udp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ch.aschaefer.udp.ByteUtil.toHex;

/**
 * @author aschaefer
 * @since 19.12.15.
 */
public class Datagram {
    private final String hex;
    private final String timestamp;
    private final String source;
    private final String target;

    public Datagram(byte[] content,  String source, String target) {
        this.hex = toHex(content);
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.source = source;
        this.target = target;
    }

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
}
