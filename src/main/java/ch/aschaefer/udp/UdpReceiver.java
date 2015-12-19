package ch.aschaefer.udp;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static ch.aschaefer.udp.ByteUtil.toHex;

/**
 * Created by René Schäfer on 10.10.2015.
 */
public class UdpReceiver implements Runnable {

    private boolean run = true;
    private int packetSize = 20;
    private int port = 10000;
    private Consumer<Datagram> processor = System.out::println;
    private Consumer<String> error = System.err::println;

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
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            serverSocket.setBroadcast(true);
            while (run) {
                error.accept("wait for package");
                byte[] data = new byte[packetSize];
                DatagramPacket packet = new DatagramPacket(data, packetSize);
                serverSocket.receive(packet);
                data = packet.getData();
                Datagram datagram = new Datagram(packet.getData(), packet.getSocketAddress().toString(), packet.getAddress().toString());
                processor.accept(datagram);
            }
        } catch (Exception e) {
            error.accept("UDP Server error:" + e.getMessage());
            e.printStackTrace();
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

    public Consumer<Datagram> getProcessor() {
        return processor;
    }

    public void setProcessor(Consumer<Datagram> processor) {
        this.processor = processor;
    }

    public Consumer<String> getError() {
        return error;
    }

    public void setError(Consumer<String> error) {
        this.error = error;
    }
}
