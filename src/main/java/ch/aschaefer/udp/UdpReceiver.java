/*
The MIT License (MIT)

Copyright (c) 2015 Andre Schaefer

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
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
 * Udp packet listener / receiver to monitor for udp datagram packets.
 */
public class UdpReceiver implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(UdpReceiver.class);

    /**
     * Flag to enable soft shutdown.
     */
    private boolean run = true;

    /**
     * Packet size to read frames from socket (byte array size).
     */
    private int packetSize = 20;

    /**
     * Port to listen for udp packets
     */
    private int port = 10000;

    /**
     * Processor to perform some operation on the received packet.
     * Default ist to print hex string to console,
     * in web server configuration packets will be translated and messaged via websocket.
     */
    private Consumer<DatagramPacket> processor = packet -> System.out.println(toHex(packet.getData()));

    /**
     * Run receiver in standalone mode.
     *
     * @param args command line arguments, first argument is port to listen on.
     */
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

    @Override
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
