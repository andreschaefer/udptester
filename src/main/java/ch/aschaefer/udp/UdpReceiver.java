package ch.aschaefer.udp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static ch.aschaefer.udp.ByteUtil.toHex;

/**
 * Created by René Schäfer on 10.10.2015.
 */
public class UdpReceiver implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(UdpReceiver.class);

    private boolean run = true;
    private int packetSize = 20;
    private int port = 10000;
    private Consumer<DatagramPacket> processor = packet -> System.out.println(toHex(packet.getData()));
    ;

    public static void main(String[] args) {
        int port = 10000;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        UdpReceiver udpReceiver = new UdpReceiver();
        udpReceiver.setPort(port);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        UdpReceiver server = new UdpReceiver();
        executor.submit(server);
        while (!executor.isTerminated()) {
        }
    }

    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            LOG.info("wait for packages");
            while (run && !Thread.currentThread().isInterrupted()) {
                LOG.trace("wait for package");
                byte[] data = new byte[packetSize];
                DatagramPacket packet = new DatagramPacket(data, packetSize);
                try {
                    serverSocket.setBroadcast(true);
                    serverSocket.setSoTimeout(500);
                    serverSocket.receive(packet);
                    processor.accept(packet);
                } catch (SocketTimeoutException e) {
                    LOG.trace("Timeout, allow interrupt");
                }
            }
        } catch (Exception e) {
            LOG.error("UDP Server error: {}", e.getMessage(), e);
        }
    }


    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public int getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Consumer<DatagramPacket> getProcessor() {
        return processor;
    }

    public void setProcessor(Consumer<DatagramPacket> processor) {
        this.processor = processor;
    }


    @Override
    public String toString() {
        return "UdpReceiver{" +
                "port=" + port +
                '}';
    }
}
