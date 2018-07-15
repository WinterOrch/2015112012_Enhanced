package com.tool.security.dh;

import com.tool.coding.HexConver;
import static com.tool.security.KeyPairUtility.*;

import javax.crypto.*;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Main Algorithm of Diffie-Hellman
 * @author Frankel.Y
 * Created in 0:11 2018/7/14
 */
public class DiffieHellman {

    /**
     * Generate Diffie-Hellman Source Key Pair
     * @return Key Pair As A Map
     * created in 14:41 2018/7/14
     */
    public static Map<String, Object> generateSourceKeyPair(String password, int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(keySize, new SecureRandom(password.getBytes()));

        // Generate Key Pair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();

        // Create Key Map
        Map<String,Object> key = new HashMap<>();
        key.put(DH_PUBLIC_KEY, dhPublicKey);
        key.put(DH_PRIVATE_KEY, dhPrivateKey);

        return key;
    }

    /**
     * Generate Diffie-Hellman Target Key Pair
     * @return Key Pair As A Map
     * created in 14:51 2018/7/14
     */
    public static Map<String,Object> generateTargetKeyPair(String password, byte[] sourcePublicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("DH");

        // Generate KeySpec According to Source Public Key And Generate Param of The Key
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(sourcePublicKey);
        DHPublicKey sourcePublic = (DHPublicKey) keyFactory.generatePublic(keySpec);
        DHParameterSpec dhPublicKeyParams = sourcePublic.getParams();

        // Initialize Key Pair Generator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(dhPublicKeyParams, new SecureRandom(password.getBytes()));

        // Generate Key Pair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();

        // Create Key Map
        Map<String,Object> key = new HashMap<>();
        key.put(DH_PUBLIC_KEY, dhPublicKey);
        key.put(DH_PRIVATE_KEY, dhPrivateKey);

        return key;
    }

    /**
     * Calculate The Session Key According to Opponent's Public Key And Self's Private Key
     * created in 15:05 2018/7/14
     */
    public static Map<String,Object> generateSessionKey(byte[] keyPublic, byte[] keyPrivate, String algorithm) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("DH");

        // Generate Public
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPublic);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // Generate Private
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyPrivate);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // KeyAgreement Perform Encryption
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH", "BC");    // Bouncy Castle As A Provider
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey,true);

        // Create Key Map
        Map<String,Object> key = new HashMap<>();
        key.put(algorithm + SESSION_KEY, keyAgreement.generateSecret(algorithm));

        return key;
    }

    public static void main(String[] args) throws Exception {

        byte[] source_public_key;
        byte[] source_private_key;
        byte[] source_session_key;

        byte[] target_public_key;
        byte[] target_private_key;
        byte[] target_session_key;

        Map<String, Object> sourceKey = generateSourceKeyPair("吔屎啦",DH_KEY_SIZE);
        source_public_key = getDHPublicKey(sourceKey);
        source_private_key = getDHPrivateKey(sourceKey);

        System.out.println("Source Public：" + HexConver.byte2HexStr(source_public_key, source_public_key.length));
        System.out.println("Source Private：" + HexConver.byte2HexStr(source_private_key, source_private_key.length));

        Map<String, Object> targetKey = generateTargetKeyPair("梁非凡",source_public_key);
        target_public_key = getDHPublicKey(targetKey);
        target_private_key = getDHPrivateKey(targetKey);

        System.out.println("Target Public：" + HexConver.byte2HexStr(target_public_key, target_public_key.length));
        System.out.println("Target Private：" + HexConver.byte2HexStr(target_private_key, target_private_key.length));

        source_session_key = getSessionKey(generateSessionKey(target_public_key, source_private_key, "AES"),"AES");
        target_session_key = getSessionKey(generateSessionKey(source_public_key, target_private_key, "AES"),"AES");

        System.out.println("Source Session：" + HexConver.byte2HexStr(source_session_key, source_session_key.length));
        System.out.println("Target Session：" + HexConver.byte2HexStr(target_session_key, target_session_key.length));

        System.out.println(source_session_key.length);
    }
}
