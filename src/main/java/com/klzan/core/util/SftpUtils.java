package com.klzan.core.util;

import com.jcraft.jsch.*;
import com.klzan.core.exception.SystemException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * sftp工具
 */
public class SftpUtils {
    private transient Logger log = LoggerFactory.getLogger(SftpUtils.class);

    private ChannelSftp sftp;

    private Session session;
    /**
     * FTP 登录用户名
     */
    private static String username;
    /**
     * FTP 登录密码
     */
    private static String password;
    /**
     * FTP 服务器地址IP地址
     */
    private static String host;
    /**
     * FTP 端口
     */
    private static int port;
    /**
     * 上传根目录
     */
    private static String uploadPath;

    static {
        username = PropertiesUtils.getString("sftp.user.name");
        password = PropertiesUtils.getString("sftp.user.password");
        host = PropertiesUtils.getString("sftp.host");
        port = PropertiesUtils.getInt("sftp.port");
        uploadPath = PropertiesUtils.getString("sftp.upload.dir");
    }

    /**
     * 连接sftp服务器
     *
     * @throws Exception
     */
    public SftpUtils connect() {
        try {
            JSch jsch = new JSch();
            log.info("sftp connect by host:{} username:{}", host, username);

            session = jsch.getSession(username, host, port);
            log.info("Session is build");
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            log.info("Session is connected");

            Channel channel = session.openChannel("sftp");
            channel.connect();
            log.info("channel is connected");

            sftp = (ChannelSftp) channel;
            log.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));

            return this;
        } catch (JSchException e) {
            log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});
            throw new SystemException(e.getMessage(), e);
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                log.info("sftp is closed already");
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     * @throws SftpException
     * @throws Exception
     */
    public SftpUtils upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        if (StringUtils.isNotBlank(directory)) {
            directory = uploadPath + "/" + directory;
        } else {
            directory = uploadPath;
        }
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.warn("directory is not exist");
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);
        log.info("file:{} is upload successful", sftpFileName);
        return this;
    }

    /**
     * 上传单个文件
     *
     * @param directory  上传到sftp目录
     * @param uploadFile 要上传的文件,包括路径
     * @throws FileNotFoundException
     * @throws SftpException
     * @throws Exception
     */
    public SftpUtils upload(String directory, String uploadFile) throws FileNotFoundException, SftpException {
        File file = new File(uploadFile);
        return upload(directory, file.getName(), new FileInputStream(file));
    }

    /**
     * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param byteArr      要上传的字节数组
     * @throws SftpException
     * @throws Exception
     */
    public SftpUtils upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException {
        return upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
    }

    /**
     * 将字符串按照指定的字符编码上传到sftp
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param dataStr      待上传的数据
     * @param charsetName  sftp上的文件，按该字符编码保存
     * @throws UnsupportedEncodingException
     * @throws SftpException
     * @throws Exception
     */
    public SftpUtils upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException {
        return upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @throws SftpException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public SftpUtils download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
        if (StringUtils.isNotBlank(directory)) {
            directory = uploadPath + "/" + directory;
        } else {
            directory = uploadPath;
        }
        sftp.cd(directory);
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
        log.info("file:{} is download successful", downloadFile);
        return this;
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     * @throws SftpException
     * @throws IOException
     * @throws Exception
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (StringUtils.isNotBlank(directory)) {
            directory = uploadPath + "/" + directory;
        } else {
            directory = uploadPath;
        }
        sftp.cd(directory);
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        log.info("file:{} is download successful", downloadFile);
        return fileData;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @throws SftpException
     * @throws Exception
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        if (StringUtils.isNotBlank(directory)) {
            directory = uploadPath + "/" + directory;
        } else {
            directory = uploadPath;
        }
        sftp.cd(directory);
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        if (StringUtils.isNotBlank(directory)) {
            directory = uploadPath + "/" + directory;
        } else {
            directory = uploadPath;
        }
        sftp.cd(directory);
        return sftp.ls(directory);
    }

}
