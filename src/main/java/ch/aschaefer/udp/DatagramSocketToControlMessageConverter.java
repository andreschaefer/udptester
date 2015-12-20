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

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ch.aschaefer.udp.ByteUtil.toHex;

/**
 * Converter to convert UDP Datagram Packet into domain representation ControlMessage.
 *
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
