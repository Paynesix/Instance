package com.spring.security.tools;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.*;

/**
 * linux工具类
 * 依赖jar
 * 		ganymed-ssh2-build210.jar
 * 
 * @author HuYaHui
 *
 */
public class PackageAndUpload {

    public static String host = "192.168.11.37";
    public static String username = "spring";
    public static String password = "5d387tTeWjSA";
    // service project target path
    public static String serverPath="/home/spring/service/jsszxybillbatchcheck-center/";
    public static String jarName="hc-service-jsszxybillbatchcheck.jar";

    public static String cmd="mvn package -Dmaven.test.skip=true";

    public static void main(String[] args) throws Exception {
        File f = new File(PackageAndUpload.class.getResource("/").getPath());
        String projectPath = new File(f.getParent()).getParent();
//      String classFilePath = PackageAndUpload.class.getResource("/").getPath();
        if(!mvnPackage("cmd /k  cd "+projectPath+" && "+cmd)) {
            System.out.println("mvn 打包失败！");
            System.exit(0);
        }
        
        //上传服务器
        upload(f.getParent());
    }


    private static void upload(String jarPath) throws Exception {
        //停止服务
        runSSH("sh "+serverPath+"cloud.sh stop");
        //jar改名
        runSSH("mv "+serverPath+jarName+" "+serverPath+jarName+".bak");
        //上传jar
        uploadFile(jarPath+"\\"+jarName, serverPath);
        //启动服务
        runSSH("sh "+serverPath+"cloud.sh start");
    }
    /**
     * 下载文件
     * 2013-9-12 下午2:59:35 by HuYaHui
     * @param remoteFile
     *          远程文件地址
     * @param localDir
     *          本地已经存在的目录
     * @throws IOException
     */
    public static void downLoad(String remoteFile, String localDir) throws IOException {
        Connection conn = getOpenedConnection(host, username, password);
        SCPClient client = new SCPClient(conn);
        System.out.println("下载开始...");
        client.get(remoteFile, localDir);
        conn.close();
        System.out.println("下载完成!");
    }

    /**
     * 上传文件 2013-9-12 下午2:53:38 by HuYaHui
     * 
     * @param localFile
     *            本地文件
     * @param remoteDir
     *            服务器目录
     * @throws IOException
     */
    public static void uploadFile(String localFile, String remoteDir)
            throws IOException {
        Connection conn = getOpenedConnection(host, username, password);
        SCPClient client = new SCPClient(conn);
        System.out.println("上传开始...");
        client.put(localFile, remoteDir);
        conn.close();
        System.out.println("上传完成!");
    }

    /**
     * 执行ssh命令 2013-9-12 下午2:32:12 by HuYaHui
     * 
     * @param cmd
     *            多个命令之间用 \n 分开
     * @return
     */
    public static int runSSH(String cmd) {
        Connection con = null;
        Session session = null;
        try {
            StringBuffer sb = new StringBuffer();
            con = getOpenedConnection(host, username, password);
            session = con.openSession();
            sb.append("执行的命令:").append(cmd).append("\n");
            session.execCommand(cmd);
            InputStream stdout = new StreamGobbler(session.getStdout());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(stdout));
            String str = br.readLine();
            sb.append("-----------------------返回结果内容S-----------------------\n");
            while (str != null) {
                sb.append(str).append("\n");
                str = br.readLine();
            }
            br.close();
            sb.append("-----------------------返回结果内容E-----------------------");
            System.out.println(sb.toString());
            Thread.sleep(200);
            return session.getExitStatus().intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
            con.close();
        }
        return -1;
    }
    // 获得连接
    private static Connection getOpenedConnection(String host, String username,
            String password) throws IOException {
        Connection conn = new Connection(host);
        conn.connect(); // make sure the connection is opened
        System.out.println("连接成功，开始登录...");
        boolean isAuthenticated = conn.authenticateWithPassword(username,
                password);
        System.out.println("登录成功！");
        if (isAuthenticated == false) {
            throw new IOException("Authentication failed.");
        }
        return conn;
    }

    /**
     * 执行CMD命令,并返回String字符串
     */
    public static boolean mvnPackage(String strCmd) throws Exception {
        boolean f=false;
        Process p = Runtime.getRuntime().exec(strCmd);
        BufferedReader bufferedReader=
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while(!(line=bufferedReader.readLine()).equals("")) {
            System.out.println(line);
            if(line.indexOf("[INFO] BUILD SUCCESS")!=-1) {
                f=true;
            }
        }
        p.destroy();
        return f;
    }
}
