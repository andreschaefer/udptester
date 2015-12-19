package ch.aschaefer.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author aschaefer
 * @since 19.12.15.
 */
@Controller
public class UdpController {
    private static final Logger LOG = LoggerFactory.getLogger(UdpController.class);

    @MessageMapping("/udp")
    public void udp() throws Exception {
        LOG.debug("connected");
    }
}
