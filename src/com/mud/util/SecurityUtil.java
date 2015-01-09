/**
 * 
 */
package com.mud.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtil {
	public static final String DIGEST_SHA1 = "SHA-1";
	public static final String DIGEST_MD5 = "MD5";
	public static final String ALGORITHM_DES = "DES";
	public static final String ALGORITHM_DESEDE = "DESede";
	public static final String ALGORITHM_BLOWFISH = "Blowfish";
	public static final String ALGORITHM_TRIPLEDES = "TripleDES";

	/**
	 * 生成摘要
	 * 
	 */
	public static byte[] digest(byte[] srcBytes, String digestName) {
		MessageDigest md = null;
		byte[] result = null;
		try {
			md = MessageDigest.getInstance(digestName);
			md.update(srcBytes);
			result = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] digest(byte[] srcBytes) {
		return digest(srcBytes, DIGEST_MD5);
	}

	/**
	 * 生成摘要,并转换为16进度字符串
	 * 
	 */
	public static String digest(String src, String encode, String spitChar, String digestName) {
		String result = null;
		try {
			byte[] srcBytes = src.getBytes(encode);
			byte[] digestBytes = digest(srcBytes, digestName);
			if (digestBytes != null) {
				result = StringUtil.toHexString(digestBytes, spitChar);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String digest(String src, String spitChar, String digestName) {
		return digest(src, "UTF-8", spitChar, digestName);
	}

	public static String digest(String src, String spitChar) {
		return digest(src, spitChar, DIGEST_MD5);
	}

	public static String digest(String src) {
		return digest(src, "", DIGEST_MD5);
	}

	public static String SHA1(String src) {
		return digest(src, "", DIGEST_SHA1);
	}

	/**
	 * 加密,DES算法为8位密钥，DESede算法为24字节密钥,源数据字节数必须为8的倍数，
	 * 可使用PKCS5Padding或SSL3Padding自动补充,默认为PKCS5Padding,不需补充可使用NOPadding
	 * 
	 */
	public static byte[] encript(byte[] src, byte[] keybyte, String algorithm) throws Exception {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keybyte, algorithm);
		// 生成密码
		Cipher cipher = Cipher.getInstance(algorithm);
		// 初始化密码
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		// 加密
		byte[] result = cipher.doFinal(src);
		return result;
	}

	public static byte[] encript(byte[] src, byte[] keybyte) throws Exception {
		return encript(src, keybyte, ALGORITHM_DES);
	}

	/**
	 * 解密,DES算法为8位密钥，DESede算法为24字节密钥
	 * 
	 */
	public static byte[] decript(byte[] src, byte[] keybyte, String algorithm) throws Exception {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keybyte, algorithm);
		// 生成密码
		Cipher cipher = Cipher.getInstance(algorithm);
		// 初始化密码
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		// 解密
		byte[] result = cipher.doFinal(src);
		return result;
	}

	public static byte[] decript(byte[] src, byte[] keybyte) throws Exception {
		return decript(src, keybyte, ALGORITHM_DES);
	}

	public static void main(String[] args) {
		System.out.println(digest(digest("gt1023")));
	}
}
