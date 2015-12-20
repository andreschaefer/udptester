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
import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.DatagramPacket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Dispatcher to create UDP listener upon request and dispatch received UDP Packets to websocket messages.
 *
 * @author aschaefer
 * @since 20.12.15.
 */
@Component
public class UdpMessageDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(UdpMessageDispatcher.class);

    /**
     * helper to send messages via websocket.
     */
    protected final SimpMessagingTemplate messagingTemplate;

    protected final Converter<DatagramPacket, ControlMessage> converter;
    /**
     * Thread pool manager to run udp listener in background thread.
     */
    protected final ExecutorService executor = Executors.newFixedThreadPool(2);

    /**
     * current active udp listener, is null if non is active.
     */
    protected UdpReceiver receiver;

    @Inject
    public UdpMessageDispatcher(SimpMessagingTemplate messagingTemplate,
                                @Named("datagramSocketToControlMessageConverter") Converter<DatagramPacket, ControlMessage> converter) {
        this.messagingTemplate = messagingTemplate;
        this.converter = converter;
    }

    /**
     * Create and start an udp listener on the provided port.
     *
     * @param port port to listen for upd packets
     * @return listener for packets on provided port.
     */
    public UdpReceiver udpReceiver(int port) {
        UdpReceiver receiver = new UdpReceiver();
        receiver.setProcessor(packet -> {
            ControlMessage message = converter.convert(packet);
            messagingTemplate.convertAndSend("/topic/receive", message);
        });
        receiver.setPort(port);
        executor.submit(receiver);
        return receiver;
    }

    /**
     * Start a new listener on specified port.
     * This will stop previous active udp listener and replace with new one.
     *
     * @param port port to start listening for udp packets.
     */
    public void start(int port) {
        LOG.debug("Start port {}", port);
        if (receiver != null) {
            LOG.debug("Running {} ... stop", receiver);
            receiver.setRun(false);
        }
        receiver = udpReceiver(port);
    }

    /**
     * Stop current listener if present.
     */
    public void stop() {
        LOG.debug("Stop {}", receiver);
        if (receiver != null) {
            receiver.setRun(false);
        }
        receiver = null;
    }

    /**
     * Shutdown background processes.
     */
    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}
