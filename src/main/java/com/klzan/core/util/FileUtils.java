package com.klzan.core.util;

import java.io.*;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Des: 文件操作工具类
 * Created by suhao on 2015/12/14.
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 创建目录
     */
    public static File createDir(String dirPath) {
        File dir;
        try {
            dir = new File(dirPath);
            if (!dir.exists()) {
                org.apache.commons.io.FileUtils.forceMkdir(dir);
            }
        } catch (Exception e) {
            logger.error("创建目录出错！", e);
            throw new RuntimeException(e);
        }
        return dir;
    }

    /**
     * 创建文件
     */
    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                org.apache.commons.io.FileUtils.forceMkdir(parentDir);
            }
        } catch (Exception e) {
            logger.error("创建文件出错！", e);
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 复制目录（不会复制空目录）
     */
    public static void copyDir(String srcPath, String destPath) {
        try {
            File srcDir = new File(srcPath);
            File destDir = new File(destPath);
            if (srcDir.exists() && srcDir.isDirectory()) {
                org.apache.commons.io.FileUtils.copyDirectoryToDirectory(srcDir, destDir);
            }
        } catch (Exception e) {
            logger.error("复制目录出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制文件
     */
    public static void copyFile(String srcPath, String destPath) {
        try {
            File srcFile = new File(srcPath);
            File destDir = new File(destPath);
            if (srcFile.exists() && srcFile.isFile()) {
                org.apache.commons.io.FileUtils.copyFileToDirectory(srcFile, destDir);
            }
        } catch (Exception e) {
            logger.error("复制文件出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除目录
     */
    public static void deleteDir(String dirPath) {
        try {
            File dir = new File(dirPath);
            if (dir.exists() && dir.isDirectory()) {
                org.apache.commons.io.FileUtils.deleteDirectory(dir);
            }
        } catch (Exception e) {
            logger.error("删除目录出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                org.apache.commons.io.FileUtils.forceDelete(file);
            }
        } catch (Exception e) {
            logger.error("删除文件出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 重命名文件
     */
    public static void renameFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            File newFile = new File(destPath);
            boolean result = srcFile.renameTo(newFile);
            if (!result) {
                throw new RuntimeException("重命名文件出错！" + newFile);
            }
        }
    }

    /**
     * 将字符串写入文件
     */
    public static void writeFile(String filePath, String fileContent) {
        OutputStream os = null;
        Writer w = null;
        try {
            FileUtils.createFile(filePath);
            os = new BufferedOutputStream(new FileOutputStream(filePath));
            w = new OutputStreamWriter(os, "UTF-8");
            w.write(fileContent);
            w.flush();
        } catch (Exception e) {
            logger.error("写入文件出错！", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
                logger.error("释放资源出错！", e);
            }
        }
    }

    /**
     * 获取真实文件名（去掉文件路径）
     */
    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    /**
     * 判断文件是否存在
     */
    public static boolean checkFileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 把一个文件src拷贝给dst
     *
     * @param 文件src
     * @param 文件dst
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(File src, File dst) throws FileNotFoundException, IOException {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dst);
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }

    /**
     * 方便spring mvc上传组件直接获取inputstream的文件上传方法
     *
     * @param InputStream
     *            in
     * @param 文件dst
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copyStream(InputStream in, File dst) throws FileNotFoundException, IOException {
        FileOutputStream fos = null;

        try {
            // in = new FileInputStream(src);
            fos = new FileOutputStream(dst);
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }

    /**
     * 在服务器生成临时文件供下载
     *
     * @param filename
     * @param out
     * @throws IOException
     */
    public static void createFile(String filename, ByteArrayOutputStream out) throws IOException {
        File output = new File(filename);
        FileOutputStream fos;
        fos = new FileOutputStream(output);
        fos.write(out.toByteArray());
        fos.close();

    }

    /**
     * 下载文件, 并删除临时文件
     *
     * @param file
     * @param out
     * @throws IOException
     */
    public static void download(File file, OutputStream out) throws IOException {

        FileInputStream fis = null;
        byte[] b = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);

            int i = 0;
            while ((i = fis.read(b)) != -1) {
                out.write(b, 0, i);
            }
            fis.close();
            out.flush();
            out.close();
            file.delete();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {

            }
        }

    }

    public static File multipartToFile(MultipartFile multfile) throws IOException {
        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;
        //这个myfile是MultipartFile的
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        //手动创建临时文件
        if(file.length() < 2048){
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +
                    file.getName());
            multfile.transferTo(tmpFile);
            return tmpFile;
        }
        return file;
    }
}
