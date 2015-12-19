package ch.aschaefer.udp;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static ch.aschaefer.udp.ByteUtil.fromHex;
import static ch.aschaefer.udp.ByteUtil.toHex;

public class UdpSender {
    public static void send(String host, int port, byte[] data){
        try {
            System.out.println("send: " + toHex(data));
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setBroadcast(true);
            InetAddress IPAddress =InetAddress.getByName(host);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
            clientSocket.send(sendPacket);
        } catch (Exception e) {
            System.err.println(e.getMessage());
           e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if ( args != null && args.length == 3){
            send(args[0],Integer.parseInt(args[1]), fromHex(args[2]));
        }
        else {
            send("localhost",10000, fromHex("AA"));
        }
    }
}
