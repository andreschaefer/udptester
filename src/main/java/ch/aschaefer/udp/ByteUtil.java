package ch.aschaefer.udp;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;

/**
 * Created by René Schäfer on 10.10.2015.
 */
public class ByteUtil {
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for ( int j = 0; j < bytes.length; j++ ) {

            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[(j * 3) + 2] = ':';
        }
        return new String(hexChars);
    }

    public static byte[] fromHex(String hex){
        if ( hex != null){
            return DatatypeConverter.parseHexBinary(hex.replaceAll("[^0-9a-fA-F]+",""));
        }
        return new byte[0];
    }
}
