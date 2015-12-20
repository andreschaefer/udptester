package ch.aschaefer.udp;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ch.aschaefer.udp.ByteUtil.toHex;

/**
 * @author aschaefer
 * @since 20.12.15.
 */
@Component("datagramSocketToControlMessageConverter")
public class DatagramSocketToControlMessageConverter implements Converter<DatagramPacket, ControlMessage> {
    @Override
    public ControlMessage convert(DatagramPacket source) {
        return new ControlMessage()
                .timestamp(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()))
                .hex(toHex(source.getData()))
                .source(source.getSocketAddress().toString())
                .targetHost(source.getAddress().getHostAddress())
                .targetPort(source.getPort())
                ;
    }
}
