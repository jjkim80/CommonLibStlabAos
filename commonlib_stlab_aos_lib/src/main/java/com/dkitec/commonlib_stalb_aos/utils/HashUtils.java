package com.dkitec.commonlib_stalb_aos.utils;

import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class HashUtils {

    //AES
    public static String Alg = "AES/CBC/PKCS5Padding";
    public static String PK = "01234567890123456789012345678901"; //32byte
    public static String IV128 = PK.substring(0, 16); //16byte
    public static String IV192 = PK.substring(0, 24);
    public static String IV256 = PK.substring(0, 32);

    //RSA
    public static final int KEY_SIZE = 1024;

    public static String getEncryptData(String data) {
        String hexUserPw = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes(StandardCharsets.UTF_8));
            hexUserPw = String.format("%064x", new BigInteger(1, md.digest()));
            Logger.d("UserPw --> " + hexUserPw);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexUserPw;
    }

    //Base64 인코딩
    public static String getBase64Encrypt(String data) {
        return Base64.encodeToString(data.getBytes(), 0);
    }

    //Base64 디코딩
    public static String getBase64Decrypt(String data) {
        return new String(Base64.decode(data, 0));
    }

    public static String getAESEncrypt(String data, AESEncType encType) {
        try {
            byte[] secretKeySpec = IV128.getBytes();

            if (encType == AESEncType.AES_192) {
                secretKeySpec = IV192.getBytes();
            } else if (encType == AESEncType.AES_256) {
                secretKeySpec = IV256.getBytes();
            }

            Cipher cipher = Cipher.getInstance(Alg);
            SecretKeySpec keySpec = new SecretKeySpec(secretKeySpec, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV128.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeToString(encrypted, 0);
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
        return data;
    }

    public static String getAESDecrypt(String data, AESEncType encType) {
        try {
            byte[] secretKeySpec = IV128.getBytes();

            if (encType == AESEncType.AES_192) {
                secretKeySpec = IV192.getBytes();
            } else if (encType == AESEncType.AES_256) {
                secretKeySpec = IV256.getBytes();
            }

            Cipher cipher = Cipher.getInstance(Alg);
            SecretKeySpec keySpec = new SecretKeySpec(secretKeySpec, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV128.getBytes(StandardCharsets.UTF_8
            ));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            byte[] decodeBytes = Base64.decode(data, 0);
            byte[] decrypted = cipher.doFinal(decodeBytes);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     *  1024비트 RSA 키쌍을 생성
     */
    public static KeyPair getRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(KEY_SIZE, new SecureRandom());
        return gen.genKeyPair();
    }

    /**
     *  public key로 RSA 암호화를 수행
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encryptRSA(String plainText, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());
        return java.util.Base64.getEncoder().encodeToString(bytePlain);
    }

    /**
     *  privite key로 RSA 복호화를 수행
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decryptRSA(String encrypted, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = java.util.Base64.getDecoder().decode(encrypted.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        return new String(bytePlain, "utf-8");
    }
}
