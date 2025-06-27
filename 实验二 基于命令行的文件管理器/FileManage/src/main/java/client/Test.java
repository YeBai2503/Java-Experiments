package client;

import command.CreateFileCommand;
import command.DeleteFileCommand;
import command.ReadFileCommand;
import model.AbstractFile;
import model.CommandDetail;
import model.DirectoryModel;
import model.FileModel;
import tool.*;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

//测试类 测试代码是否有效
public class Test {
    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        //压缩，解压
//        ZipFile.zipPath("G:\\文件管理器TEST\\测试文件夹\\1345","G:\\文件管理器TEST\\111你电脑"+".zip",true);
//        UnzipFile.unzip("G:\\文件管理器TEST\\111你电脑"+".zip","G:\\文件管理器TEST");


        //加密解密
//        EncryptFile.encryptFile("6699","G:\\文件管理器TEST\\测试文件夹\\1345\\你是\\FBAj.xlsx","G:\\文件管理器TEST\\测试文件夹\\1345\\你是\\加密.xlsxEncrypt");
//        DecryptFile.decryptFile("6699","G:\\文件管理器TEST\\测试文件夹\\1345\\你是\\加密.xlsxEncrypt","G:\\文件管理器TEST\\测试文件夹\\1345\\你是\\解密.xlsx");
        //拷贝
//       CopyFile.copy("G:\\文件管理器TEST\\测试文件夹\\1345","G:\\文件管理器TEST\\拷贝");

        //删除，创建
//        CommandDetail commandDetail=new CommandDetail();
//        commandDetail.setPath("G:\\文件管理器TEST\\创建.docx");
//        CreateFileCommand command=new CreateFileCommand();
//        DeleteFileCommand command=new DeleteFileCommand();
//        command.execute(commandDetail);



    }

}