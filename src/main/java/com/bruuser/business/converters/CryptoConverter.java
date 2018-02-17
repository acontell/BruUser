package com.bruuser.business.converters;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

    private static final String INIT_VECTOR = "RandomInitVector";
    private static final String KEY = "Bar12345Bar12345";
    private static final String CHAR_ENCODING = "UTF-8";
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

    private static SecretKeySpec getSecretKeySpec() throws UnsupportedEncodingException {
        return new SecretKeySpec(KEY.getBytes(CHAR_ENCODING), ALGORITHM);
    }

    private static IvParameterSpec getIv() throws UnsupportedEncodingException {
        return new IvParameterSpec(INIT_VECTOR.getBytes(CHAR_ENCODING));
    }

    private static byte[] doCipher(int mode, byte[] byteArr) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(mode, getSecretKeySpec(), getIv());
            return cipher.doFinal(byteArr);
        } catch (UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String convertToDatabaseColumn(String strToEncode) {
        return printBase64Binary(doCipher(Cipher.ENCRYPT_MODE, strToEncode.getBytes()));
    }

    @Override
    public String convertToEntityAttribute(String strToDecode) {
        return new String(doCipher(Cipher.DECRYPT_MODE, parseBase64Binary(strToDecode)));
    }
}
