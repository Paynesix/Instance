/** 
*@Copyright (c) 宝库技术团队 www.baoku.com
*@Package com.baoku.pay.biz.utils
*@Project：baoku-pay-biz
*@authur：zhucj zhuchengjie@baoku.com
*@date：2019年11月6日 上午11:27:00   
*@version 1.0
*/
package com.spring.security.tools;

import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Properties;

/** 
*@ClassName：SFTPUtil
*@authur：wxy
*@date：2020年08月19日 上午11:27:00
*@version 1.0
*SFTP工具类
*/
public class SFTPUtil {
    private ChannelSftp sftp;  
    private Session session;  
    /** FTP 登录用户名*/    
    private String username;  
    /** FTP 登录密码*/    
    private String password;  
    /** 私钥 */    
    private String privateKey;  
    /** FTP 服务器地址IP地址*/    
    private String host;  
    /** FTP 端口*/  
    private int port;  
    /**  
     * 构造基于密码认证的sftp对象  
     * @param username
     * @param password  
     * @param host  
     * @param port  
     */    
    public SFTPUtil(String username, String password, String host, int port) {  
        this.username = username;  
        this.password = password;  
        this.host = host;  
        this.port = port;  
    }  
    
    /**  
     * 构造基于秘钥认证的sftp对象 
     * @param username
     * @param host 
     * @param port 
     * @param privateKey 
     */  
    public SFTPUtil(String username, String host, int port, String privateKey) {  
        this.username = username;  
        this.host = host;  
        this.port = port;  
        this.privateKey = privateKey;  
    }  
    
    public SFTPUtil(){}  
    
    /**
     * void 
     * @throws JSchException 
     * @throws
     * @author zhucj
     * @date 2019年11月6日 上午11:33:46
     * 连接
     */
    public void login() throws JSchException{  
    	JSch jsch = new JSch();  
        if (privateKey != null) {  
            jsch.addIdentity(privateKey);// 设置私钥  
        }  
        session = jsch.getSession(username, host, port);  
        if (password != null) {  
            session.setPassword(password);    
        }  
        Properties config = new Properties();  
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config);  
        session.connect();  
        Channel channel = session.openChannel("sftp");  
        channel.connect();  
        sftp = (ChannelSftp) channel;  
    }    
    
    /** 
     * 关闭连接 server  
     */  
    public void logout(){  
        if (sftp != null && sftp.isConnected()) {  
            sftp.disconnect();  
        }  
        if (session != null && session.isConnected()) {  
            session.disconnect();  
        }  
    }  
    /**  
     * 将输入流的数据上传到sftp作为文件  
     *   
     * @param directory  
     *            上传到该目录  
     * @param sftpFileName  
     *            sftp端文件名  
     * @param input
     *            输入流  
     * @throws SftpException   
     * @throws Exception  
     */    
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException{ 
    	if(StringUtils.isNotBlank(directory)) {
    		try {    
    			sftp.cd(directory);  
    		} catch (SftpException e) {  
    			sftp.mkdir(directory);  
    			sftp.cd(directory);  
    		}  
    	}
        sftp.put(input, sftpFileName);  
    }  
}  

