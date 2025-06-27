package com.ssoserver.tool;

//加密解密器
public class Encryptor {
    private static final int XOR_KEY = 75491; // XOR 加密的密钥
    private static final int SHIFT = 10; // 凯撒加密的移位数

    //数字运算加密
    private static int countEncrypt(int num) {
        return num * 97 - 3;
    }

    //数字运算解密
    private static int countDecrypt(int num) {
        return (num + 3) / 97;
    }

    // 数字加密方法
    public static String encrypt(int number) {
        // 先进行数字运算加密
        number = countEncrypt(number);
        // 再进行 XOR 加密
        int xorEncrypted = number ^ XOR_KEY;

        // 将 XOR 加密后的整数转换为字符串
        StringBuilder encrypted = new StringBuilder();
        String strXorEncrypted = String.valueOf(xorEncrypted);
        for (char ch : strXorEncrypted.toCharArray()) {
            // 对每个字符进行位移
            char encryptedChar = (char) (ch + SHIFT);
            encrypted.append(encryptedChar);
        }
        return encrypted.toString();
    }

    // 数字解密方法
    public static int decrypt(String encryptedString) {
        // 先进行凯撒解密
        StringBuilder decrypted = new StringBuilder();
        for (char ch : encryptedString.toCharArray()) {
            // 对每个字符进行反向位移
            char decryptedChar = (char) (ch - SHIFT);
            decrypted.append(decryptedChar);
        }

        // 将解密后的字符串转换为整数
        int xorDecrypted = Integer.parseInt(decrypted.toString());

        // 进行反向 XOR 解密
        int num = xorDecrypted ^ XOR_KEY;
        // 再进行数字运算解密
        return countDecrypt(num);
    }
//    public static void main(String[] args) {
//        int originalNumber = 123456; // 要加密的原始整数
//        System.out.println("Original Number: " + originalNumber);
//
//        // 加密
//        String encryptedString = encrypt(originalNumber);
//        System.out.println("Encrypted String: " + encryptedString);
//
//        // 解密
//        int decryptedNumber = decrypt(encryptedString);
//        System.out.println("Decrypted Number: " + decryptedNumber);
//    }
}