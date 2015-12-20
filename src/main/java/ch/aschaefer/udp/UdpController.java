package ch.aschaefer.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author aschaefer
 * @since 19.12.15.
 */
@Controller
public class UdpController {
    private static final Logger LOG = LoggerFactory.getLogger(UdpController.class);

    protected final UdpMessageDispatcher dispatcher;

    @Inject
    public UdpController(UdpMessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @MessageMapping("/start")
    public void start(@Payload int port) throws Exception {
        LOG.debug("Request start port {}", port);
        dispatcher.start(port);
    }

    @MessageMapping("/stop")
    public void stop() throws Exception {
        LOG.debug("Request stop");
        dispatcher.stop();
    }

    @MessageMapping("/send")
    public void send(@Payload ControlMessage message) throws Exception {
        LOG.debug("Send {}", message);
        UdpSender.send(message.getTargetHost(), message.getTargetPort(), message.getHex());
    }
}
