package tool;

import model.AbstractFile;
import model.DirectoryModel;

//根据文件名获取文件类型
public class GetFileType {
    public static <T extends AbstractFile> String getFileType(T file) {
        if(file instanceof DirectoryModel)
        {
            return "文件夹";
        }
        String fileName=file.getName();
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        switch (fileExtension) {
            case "txt":
                return "文本文档";
            case "zip":
                return "压缩包";
            case "md":
                return "md文档";
            case "jpg":
                return "JPG图片";
            case "bmp":
                return "BMP图像";
            case "jpeg":
                return "JPEG图片";
            case "png":
                return "PNG图片";
            case "gif":
                return "GIF图片";
            case "pdf":
                return "PDF文档";
            case "mp4":
                return "MP4视频";
            case "doc":
            case "docx":
                return "Word文档";
            case "xls":
            case "xlsx":
                return "Excel表格";
            case "ppt":
            case "pptx":
                return  "PPT演示文稿";

            // 其他文件类型可以添加更多的 case
            default:
                return "未知类型";
        }
    }
}
