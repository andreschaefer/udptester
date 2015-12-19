package ch.aschaefer.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Configuration
public class WebServer {

    private static final Logger LOG = LoggerFactory.getLogger(WebServer.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebServer.class, args);
    }


    @Autowired
    protected SimpMessagingTemplate messagingTemplate;

    @Bean
    public UdpReceiver udpReceiver() {
        UdpReceiver receiver = new UdpReceiver();
        receiver.setProcessor(message -> {
            LOG.debug("dispatch {}", message);
            messagingTemplate.convertAndSend("/topic/receive", message);
        });
        receiver.setError(LOG::debug);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(receiver);
        return receiver;
    }
}
