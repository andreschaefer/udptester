package ch.aschaefer.udp;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ch.aschaefer.udp.ByteUtil.toHex;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author aschaefer
 * @since 20.12.15.
 */
public class UdpReceiverTest {

    @Test
    public void testSendReceive() throws Exception {
        final boolean[] received = {false};
        UdpReceiver receiver = new UdpReceiver();
        receiver.setProcessor((packet -> {
            assertThat(packet, hasProperty("data"));
            System.out.println(toHex(packet.getData()));
            received[0] = true;
        }));
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(receiver);
        Thread.sleep(100);
        UdpSender.send("127.0.0.1", 10000, "AA");
        Thread.sleep(100);
        assertThat(received[0], is(true));
    }

}