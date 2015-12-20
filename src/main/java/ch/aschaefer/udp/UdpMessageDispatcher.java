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
 * @author aschaefer
 * @since 20.12.15.
 */
@Component
public class UdpMessageDispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(UdpMessageDispatcher.class);

    protected final SimpMessagingTemplate messagingTemplate;
    protected final Converter<DatagramPacket, ControlMessage> converter;

    protected final ExecutorService executor = Executors.newFixedThreadPool(2);

    protected UdpReceiver receiver;

    @Inject
    public UdpMessageDispatcher(SimpMessagingTemplate messagingTemplate,
                                @Named("datagramSocketToControlMessageConverter") Converter<DatagramPacket, ControlMessage> converter) {
        this.messagingTemplate = messagingTemplate;
        this.converter = converter;
    }

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

    public void start(int port) {
        LOG.debug("Start port {}", port);
        if (receiver != null) {
            LOG.debug("Running {} ... stop", receiver);
            receiver.setRun(false);
        }
        receiver = udpReceiver(port);
    }

    public void stop() {
        LOG.debug("Stop {}", receiver);
        if (receiver != null) {
            receiver.setRun(false);
        }
        receiver = null;
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}
