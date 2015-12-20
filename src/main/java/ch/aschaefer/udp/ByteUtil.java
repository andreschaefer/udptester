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

import javax.xml.bind.DatatypeConverter;

/**
 * Util to convert byte arrays to hex string representation and vise versa.
 */
public class ByteUtil {
    protected static final char[] HEX_ALPHAPET = "0123456789ABCDEF".toCharArray();

    /**
     * Convert provided byte array to hex string representation in format AA:FF:47:11.
     *
     * @param bytes array to convert to string
     * @return hex string representation in format AA:FF:47:11
     */
    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ALPHAPET[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ALPHAPET[v & 0x0F];
            hexChars[(j * 3) + 2] = ':';
        }

        String hex = new String(hexChars);
        if (hex.endsWith(":")) {
            hex = hex.substring(0, hex.length() - 1);
        }
        return hex;
    }

    /**
     * Convert provided hex string representation in format AA:FF:47:11 byte array.
     * All non alphabet characters are stripped before parsing.
     *
     * @param hex string representation in format AA:FF:47:11
     * @return byte array
     */
    public static byte[] fromHex(String hex) {
        if (hex != null) {
            return DatatypeConverter.parseHexBinary(hex.replaceAll("[^0-9a-fA-F]+", ""));
        }
        return new byte[0];
    }
}
