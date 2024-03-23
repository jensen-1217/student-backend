package com.example.backend.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String encryptPassword(String password) {
        try {
            // 使用SHA-256算法创建MessageDigest实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将密码转换为字节数组
            byte[] hash = digest.digest(password.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 处理算法不存在的异常
            e.printStackTrace();
            return null;
        }
    }
}