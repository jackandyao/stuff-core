/*
 * Copyright (C), 2002-2016
 * FileName: RSAUtil.java
 * Author:   luwanchuan
 * Date:     2016年4月14日 上午10:45:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.qbao.aisr.stuff.util.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 *
 * @author luwanchuan
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class RSAUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * RSA签名方式
     */
    private static final String RSA = "RSA";

    /**
     * RSA加密，长度支持512-2048
     */
    private static final int RSA_SIZE_2048 = 2048;

    /**
     * RSA加密，长度支持512-2048
     */
    private static final int RSA_SIZE_1024 = 1024;

    /**
     * Md5 RSA算法
     */
    @SuppressWarnings("unused")
    private static final String MD5_WITH_RSA = "MD5withRSA";

    /**
     * SHA1 RSA算法
     */
    private static final String SHA1_WITH_RSA = "SHA1WithRSA";

    /**
     * 公钥对象KEY
     */
    public static final String PUBLIC_KEY = "publicKey";

    /**
     * Base64编码后的公钥对象KEY
     */
    public static final String PUBLIC_KEY_B64 = "publicKeyBase64";

    /**
     * 私钥对象KEY
     */
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * Base64编码后的私钥对象KEY
     */
    public static final String PRIVATE_KEY_B64 = "privateKeyBase64";

    /**
     * 生成1024位RSA密钥对
     * 
     * @return 公私钥对象和Base64编码后的公私钥String字符串
     */
    public static Map<String, Object> create1024Key() {
        return createKey(RSA_SIZE_1024);
    }

    /**
     * 生成2048位RSA密钥对
     * 
     * @return 公私钥对象和Base64编码后的公私钥String字符串
     */
    public static Map<String, Object> create2048Key() {
        return createKey(RSA_SIZE_2048);
    }

    /**
     * 生成公私钥对
     * 
     * @param keySize 公私钥对大小
     * @return 公私钥对象和Base64编码后的公私钥String字符串
     */
    public static Map<String, Object> createKey(int keySize) {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("初始化密钥工具异常", e);
            return null;
        }
        keyGen.initialize(keySize, new SecureRandom());
        KeyPair key = keyGen.generateKeyPair();
        PublicKey pubKey = key.getPublic();
        PrivateKey priKey = key.getPrivate();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(PUBLIC_KEY, pubKey);
        map.put(PRIVATE_KEY, priKey);
        map.put(PUBLIC_KEY_B64, Base64.encodeBase64String(pubKey.getEncoded()));
        map.put(PRIVATE_KEY_B64, Base64.encodeBase64String(priKey.getEncoded()));
        return map;
    }

    /**
     * 生成公私钥文件
     * 
     * @param publicFilePath 存储公钥的路径和文件名,例如：D:/pub/public.key
     * @param privateFilePath 存储私钥的路径和文件名,例如：D:/pri/private.key
     */
    public static void createKey(String publicFilePath, String privateFilePath, int keySize) {
        try {
            // 生成密钥对
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
            keyGen.initialize(keySize, new SecureRandom());
            KeyPair pair = keyGen.generateKeyPair();
            // 将已编码的密钥（公钥和私钥）字节写到文件中
            write(publicFilePath, pair.getPublic());
            write(privateFilePath, pair.getPrivate());
        } catch (Exception e) {
            LOGGER.error("密钥处理异常", e);
        }
    }

    /**
     * 写入一个对象
     * 
     * @param path 路径
     * @param key 密钥对象
     * @throws Exception
     */
    private static void write(String path, Object key) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean creat = file.getParentFile().mkdirs();// 创建文件目录
            if (!creat) {
                System.out.println("创建文件目录异常！");
                return;
            }
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(key);
        } catch (Exception e) {
            LOGGER.error("密钥写入异常", e);
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    oos = null;
                }
            }
        }
    }

    /**
     * 验证签名 <br>
     * 
     * @param data c
     * @param sign 签名值
     * @param pubk 公钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean vertiy(byte[] data, byte[] sign, PublicKey pubk) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        signature.initVerify(pubk);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * 验证签名 <br>
     * 
     * @param data 原文
     * @param sign Base64编码后的签名值
     * @param pubk 公钥
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     */
    public static boolean vertiy(String data, String sign, PublicKey pubk) throws InvalidKeyException,
            NoSuchAlgorithmException, SignatureException {
        return vertiy(data.getBytes(), Base64.decodeBase64(sign), pubk);
    }

    /**
     * RSA签名:
     * 
     * @param data 请求原文
     * @param prik 私钥
     * @return 签名值
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] sign(byte[] data, PrivateKey prik) throws NoSuchAlgorithmException, InvalidKeyException,
            SignatureException {
        Signature signature = Signature.getInstance(SHA1_WITH_RSA);
        signature.initSign(prik);
        signature.update(data);
        return signature.sign();
    }

    /**
     * RSA签名
     * 
     * @param data 请求原文
     * @param prik 私钥
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     */
    public static String sign(String data, PrivateKey prik) throws InvalidKeyException, NoSuchAlgorithmException,
            SignatureException {
        return Base64.encodeBase64URLSafeString(sign(data.getBytes(), prik)).trim();
    }

    /**
     * 将通过Base64编码后的String类型的公钥字符串转换为PublicKey对象
     * 
     * @param strPubKey Base64编码后的String类型的公钥字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String strPubKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(strPubKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
        return pubKey;
    }

    /**
     * 将通过Base64编码后的String类型的私钥字符串转换为PrivateKey对象
     * 
     * @param strPriKey Base64编码后的String类型的私钥字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String strPriKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(strPriKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
        return priKey;
    }

    /**
     * 根据公钥文件存放位置解析为公钥对象
     * 
     * @param path 存放位置，例如：D:/pub/public.key
     * @return
     */
    public static PublicKey resolvePublicKey(String path) {
        PublicKey pubkey = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = FileUtils.openInputStream(new File(path));
            ois = new ObjectInputStream(fis);
            pubkey = (PublicKey) ois.readObject();
            return pubkey;
        } catch (FileNotFoundException e) {
            LOGGER.error("私钥文件找不到", e);
        } catch (IOException e) {
            LOGGER.error("文件输入错误", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("类文件找不到", e);
        } catch (Exception e) {
            LOGGER.error("解析异常", e);
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
        return null;
    }

    /**
     * 根据私钥文件存放位置解析为私钥对象
     * 
     * @param path 存放位置，例如：D:/pri/private.key
     * @return
     */
    public static PrivateKey resolvePrivateKey(String path) {
        PrivateKey prikey = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = FileUtils.openInputStream(new File(path));
            ois = new ObjectInputStream(fis);
            prikey = (PrivateKey) ois.readObject();
            return prikey;
        } catch (FileNotFoundException e) {
            LOGGER.error("私钥文件找不到", e);
        } catch (IOException e) {
            LOGGER.error("文件输入错误", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("类文件找不到", e);
        } catch (Exception e) {
            LOGGER.error("解析异常", e);
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
        return null;
    }

    /**
     * 将传入的公钥对象转换为经过Base64编码后的公钥字符串
     * 
     * @param pubKey
     * @return
     */
    public static String getBase64PublicKeyString(PublicKey pubKey) {
        return Base64.encodeBase64URLSafeString(pubKey.getEncoded()).trim();
    }

    /**
     * 将传入的私钥对象转换为经过Base64编码后的私钥字符串
     * 
     * @param priKey
     * @return
     */
    public static String getBase64PrivateKeyString(PrivateKey priKey) {
        return Base64.encodeBase64URLSafeString(priKey.getEncoded()).trim();
    }

    /**
     * 将传入的公钥存储路径读取公钥后转换为经过Base64编码后的公钥字符串
     * 
     * @param path 存放位置，例如：D:/pub/public.key
     * @return
     */
    public static String getBase64PublicKeyString(String path) {
        PublicKey pubKey = resolvePublicKey(path);
        return getBase64PublicKeyString(pubKey);
    }

    /**
     * 将传入的私钥存储路径读取私钥后转换为经过Base64编码后的私钥字符串
     * 
     * @param path 存放位置，例如：D:/pri/private.key
     * @return
     */
    public static String getBase64PrivateKeyString(String path) {
        PrivateKey priKey = resolvePrivateKey(path);
        return getBase64PrivateKeyString(priKey);
    }

    /**
     * RSA加密<br>
     * 注意：此方法支持公钥加密私钥解密或者私钥加密公钥解密
     * 
     * @param key 公钥或者私钥
     * @param message 准备加密的原文
     * @return 加密后的密文
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrypt(Key key, String message) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] miwen = cipher.doFinal(message.getBytes());
        return String.valueOf(new BigInteger(miwen));
    }

    /**
     * RSA解密<br>
     * 注意：此方法支持公钥加密私钥解密或者私钥加密公钥解密
     * 
     * @param key 私钥或者公钥
     * @param message 已加密的密文
     * @return 解密后的原文
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String decrypt(Key key, String message) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] miwen = new BigInteger(message).toByteArray();
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(miwen);
        return new String(result);
    }

}
