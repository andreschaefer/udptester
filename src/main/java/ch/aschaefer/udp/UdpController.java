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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * Controller for web socket client interaction.
 *
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

    /**
     * Triggered by websocket client to start UDP listener.
     *
     * @param port port to listen for UDP datagrams.
     * @throws Exception on error, processed by framework.
     */
    @MessageMapping("/start")
    public void start(@Payload int port) throws Exception {
        LOG.debug("Request start port {}", port);
        dispatcher.start(port);
    }

    /**
     * Triggered by websocket client to stop running UDP listener.
     *
     * @throws Exception on error, processed by framework
     */
    @MessageMapping("/stop")
    public void stop() throws Exception {
        LOG.debug("Request stop");
        dispatcher.stop();
    }

    /**
     * Triggered by websocket client to send an UDP-Datagram to a destination.
     *
     * @param message message to be send (target, payload in hex)
     * @throws Exception on error.
     */
    @MessageMapping("/send")
    public void send(@Payload ControlMessage message) throws Exception {
        LOG.debug("Send {}", message);
        UdpSender.send(message.getTargetHost(), message.getTargetPort(), message.getHex());
    }
}
