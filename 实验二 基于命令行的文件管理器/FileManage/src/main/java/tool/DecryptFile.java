package tool;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//解密文件
public class DecryptFile {
    private static final String ALGORITHM = "AES";
    private static long totalBytes = 0; // 源文件的总字节数
    private static long decryptedBytes = 0; // 已解密字节数
    private static long startTime; // 解密开始时间

    public static void decryptFile(String secretKey, String sourceFilePath, String destinationFilePath, int back)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {

        // 检查并调整密钥的长度
        byte[] keyBytes = secretKey.getBytes();
        if (keyBytes.length < 16) {
            byte[] paddedKey = new byte[16];
            System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
            keyBytes = paddedKey;
        } else if (keyBytes.length > 16) {
            byte[] truncatedKey = new byte[16];
            System.arraycopy(keyBytes, 0, truncatedKey, 0, 16);
            keyBytes = truncatedKey;
        }

        SecretKey secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM); // 使用密钥字符串生成秘密密钥
        Cipher cipher = Cipher.getInstance(ALGORITHM); // 获取 AES 加密算法的实例
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); // 初始化密码 cipher，设置为解密模式

        Path sourcePath = Paths.get(sourceFilePath); // 获取源加密文件路径
        Path destinationPath = Paths.get(destinationFilePath); // 获取目标解密文件路径

        // 记录总字节数和开始时间
        totalBytes = Files.size(sourcePath); // 获取源文件大小
        decryptedBytes = 0; // 重置已解密字节数
        startTime = System.currentTimeMillis(); // 记录开始时间

        try (InputStream inputStream = Files.newInputStream(sourcePath);
             OutputStream outputStream = Files.newOutputStream(destinationPath);
             CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {

            byte[] buffer = new byte[4096]; // 缓冲区大小
            int bytesRead;

            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead); // 将解密后的数据写入解密文件
                decryptedBytes += bytesRead; // 更新已解密字节数

                // 当 back 为 0 时打印进度
                if (back == 0) {
                    printProgress(); // 打印进度
                }
            }
            if (back == 0) {
                decryptedBytes = totalBytes;
                printProgress(); // 打印100%的进度
            }
        }
        if (back == 0) System.out.println(); // 完成后换行
    }

    // 打印解密进度与已花费时间
    private static void printProgress() {
        int progressPercentage = (int) ((decryptedBytes * 100) / totalBytes);
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = elapsedTime / 1000;
        long milliseconds = elapsedTime % 1000;

        // 使用 '\r' 替换 '\n'，以覆盖当前行
        System.out.printf("\r解密进度: %d%%, 已解密字节: %d 字节, 已花费时间: %d.%03d 秒",
                progressPercentage, decryptedBytes, seconds,milliseconds);
    }

}
