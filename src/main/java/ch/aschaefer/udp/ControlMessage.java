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

/**
 * Data exchange object used to communicate by websocket.
 *
 * @author aschaefer
 * @since 19.12.15.
 */
public class ControlMessage {

    /**
     * Payload in hex representation of bytes.
     */
    private String hex;

    private String timestamp;

    /**
     * generic source field.
     */
    private String source;

    /**
     * target host (either ip or hostname) depending on context.
     */
    private String targetHost;
    private int targetPort = 10000;


    public String getHex() {
        return hex;
    }

    public String getTimestamp() {
        return timestamp;
    }


    public String getSource() {
        return source;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public int getTargetPort() {
        return targetPort;
    }

    @Override
    public String toString() {
        return "ControlMessage{" +
                "hex='" + hex + '\'' +
                ", source='" + source + '\'' +
                ", targetHost='" + targetHost + '\'' +
                ", targetPort=" + targetPort +
                '}';
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public ControlMessage hex(String hex) {
        setHex(hex);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ControlMessage source(String source) {
        setSource(source);
        return this;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public ControlMessage targetHost(String targetHost) {
        setTargetHost(targetHost);
        return this;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ControlMessage timestamp(String timestamp) {
        setTimestamp(timestamp);
        return this;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public ControlMessage targetPort(int targetPort) {
        setTargetPort(targetPort);
        return this;
    }
}
