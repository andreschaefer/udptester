package ch.aschaefer.udp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ch.aschaefer.udp.ByteUtil.fromHex;
import static org.springframework.util.StringUtils.hasText;

public class UdpSender {
    private static final Logger LOG = LoggerFactory.getLogger(UdpSender.class);
    public static final int DEFAULT_PORT = 10000;
    public static final String DEFAULT_HOST = "localhost";

    public static void send(String host, int port, String hex) {
        try {
            LOG.info("send: {}", hex);
            byte[] data = fromHex(hex);

            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setBroadcast(true);
            InetAddress IPAddress = InetAddress.getByName(host);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
            clientSocket.send(sendPacket);
        } catch (Exception e) {
            LOG.error("Send failed {}:{} {}", host, port, hex);
        }
    }

    public static void main(String[] args) {
        if (args != null && args.length == 3) {
            send(args[0], Integer.parseInt(args[1]), args[2]);
        } else {
            send(DEFAULT_HOST, DEFAULT_PORT, "AA");
        }
    }
}
