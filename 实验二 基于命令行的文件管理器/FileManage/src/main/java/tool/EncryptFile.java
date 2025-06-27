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

//加密文件
public class EncryptFile {
    // 文件的加密方式
    private static final String ALGORITHM = "AES";

    /**
     * 文件加密
     * @param secretKey  文件加密密钥
     * @param sourceFilePath  需要加密文件地址
     * @param destinationFilePath  加密后文件地址
     */
    private static long totalBytes = 0; // 源文件的总字节数
    private static long encryptedBytes = 0; // 已加密字节数
    private static long startTime; // 加密开始时间

    public static void encryptFile(String secretKey, String sourceFilePath, String destinationFilePath, int back)
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

        // 使用密钥字符串生成秘密密钥
        SecretKey secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        // 获取 AES 加密算法的实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 使用秘密密钥初始化密码 cipher，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // 获取源文件路径
        Path sourcePath = Paths.get(sourceFilePath);
        // 获取目标加密文件路径
        Path destinationPath = Paths.get(destinationFilePath);

        // 记录总字节数和开始时间
        totalBytes = Files.size(sourcePath); // 获取源文件大小
        encryptedBytes = 0; // 重置已加密字节数
        startTime = System.currentTimeMillis(); // 记录开始时间

        // 创建输入流，读取源文件
        try (InputStream inputStream = Files.newInputStream(sourcePath);
             // 创建输出流，写入加密文件
             OutputStream outputStream = Files.newOutputStream(destinationPath);
             // 创建密码输出流，连接到输出流，并使用密码 cipher 进行加密
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            // 缓冲区大小
            byte[] buffer = new byte[4096];
            int bytesRead;
            // 读取源文件内容到缓冲区
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // 将加密后的数据写入加密文件
                cipherOutputStream.write(buffer, 0, bytesRead);
                encryptedBytes += bytesRead; // 更新已加密字节数

                // 当 back 为 0 时打印进度
                if (back == 0) {
                    printProgress(); // 打印进度
                }
            }
        }
        if (back == 0) System.out.println(); // 完成后换行
    }

    // 打印加密进度与已花费时间
    private static void printProgress() {
        int progressPercentage = (int) ((encryptedBytes * 100) / totalBytes);
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = elapsedTime / 1000;
        long milliseconds = elapsedTime % 1000;

        // 使用 '\r' 替换 '\n'，以覆盖当前行
        System.out.printf("\r加密进度: %d%%, 已加密字节: %d 字节, 已花费时间: %d.%03d 秒",
                progressPercentage, encryptedBytes, seconds,milliseconds);
    }

}
