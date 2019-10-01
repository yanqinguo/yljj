/**
 * Created by Bin on 2017/12/22.
 */
/**
 * 引进的包都是Java自带的jar包
 * 秘钥相关包
 * base64 编解码
 * 这里只用到了编码
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import static com.sun.org.apache.xerces.internal.impl.dv.util.Base64.*;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class CreateSecrteKey {

    public static final String KEY_ALGORITHM = "RSA";
    //public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    //获得公钥
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得私钥
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //map对象中存放公私钥
    public static Map<String, Object> initKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            String publicKeyHexString=base64ToHexString(publicKey);
            System.out.println("publicKeyStr="+publicKey);
            System.out.println("publicKeyHexString:"+publicKeyHexString);
            System.out.println("是否是合法公钥:"+isValidPublicKey(publicKeyHexString));
            String privateKey = getPrivateKey(keyMap);
            System.out.println("privateKeyStr="+privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidPublicKey(String publickeyHex) {
        //hexStringToBase64
        String publickey="";
        if(StringUtils.isEmpty(publickeyHex)){
            return false;
        }
        try {
            byte[] keyBytes = hexString2Byte(publickeyHex);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey=keyFactory.generatePublic(keySpec);
            if(publicKey!=null){
                return true;
            }else{
                return false;
            }
        } catch(Exception e){
            return false;
        }
    }
    public static String base64ToHexString(String base64Str) {
        byte[] bs = new org.apache.commons.codec.binary.Base64().decode(base64Str.getBytes());
        return byte2Hex(bs);
    }

    /**
     * Byte数组转十六进制字符串，字节间用空格分�?
     *
     * @param aBytes
     * @return
     */
    public static String byte2Hex(byte[] aBytes) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < aBytes.length; n++) {
            stmp = (java.lang.Integer.toHexString(aBytes[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < aBytes.length - 1) {
                hs = hs;
            }
        }

        return hs.toUpperCase();
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
     * 0xD9}
     *
     * @param src
     *            String
     * @return byte[]
     */
    public static byte[] hexString2Byte(String src) {
        if (src.length() % 2 != 0) {
            src = src + "0";
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < (src.length() / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     *
     * @param src0
     *            byte
     * @param src1
     *            byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }




}
