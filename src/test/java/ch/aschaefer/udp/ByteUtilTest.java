package ch.aschaefer.udp;

import org.junit.Test;

import static ch.aschaefer.udp.ByteUtil.fromHex;
import static ch.aschaefer.udp.ByteUtil.toHex;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author aschaefer
 * @since 20.12.15.
 */
public class ByteUtilTest {

    @Test
    public void testToHex() throws Exception {
        assertThat(toHex(new byte[]{1, 9, 5}), equalTo("01:09:05"));
    }

    @Test
    public void testToToNull() throws Exception {
        assertThat(toHex(null), equalTo(""));
    }

    @Test
    public void testFromHex() throws Exception {
        assertThat(fromHex("01:09:05"), equalTo(new byte[]{1, 9, 5}));
    }
    @Test
    public void testFromHexNull() throws Exception {
        assertThat(fromHex(null), equalTo(new byte[]{}));

    }
}