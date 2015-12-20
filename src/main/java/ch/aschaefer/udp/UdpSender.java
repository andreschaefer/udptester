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
import java.net.InetAddress;

import static ch.aschaefer.udp.ByteUtil.fromHex;

/**
 * Helper to send udp packets to a receiver.
 */
public class UdpSender {
    private static final Logger LOG = LoggerFactory.getLogger(UdpSender.class);
    public static final int DEFAULT_PORT = 10000;
    public static final String DEFAULT_HOST = "localhost";

    /**
     * Send a message (hex string converted to bytes) to a host:port destination via UPD.
     *
     * @param host destination host
     * @param port destination port
     * @param hex  message payload in hex representation
     */
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

    /**
     * Run standalone version of sender and send a message.
     *
     * @param args 0=host 1=port 3=payload in hex
     */
    public static void main(String[] args) {
        if (args != null && args.length == 3) {
            send(args[0], Integer.parseInt(args[1]), args[2]);
        } else {
            send(DEFAULT_HOST, DEFAULT_PORT, "AA");
        }
    }
}
