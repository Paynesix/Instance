package com.spring.security.tools.git;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

import static com.spring.security.tools.git.AutoCreateBatchTool.*;

/**
 * @项目名称：baoku-code-tool
 * @类名称：CSGitBranchTool
 * @描述: 长沙项目组git切换新建分支专用，git新建本地分支工具类 可支持批量操作
 * @创建人: 伍贤壹
 * @联系方式: wuxianyi@baoku.com
 * @创建时间: 2021-02-04 09:53:46
 * @version: 2.0-SNAPSHOT
 */
public class CSGitBranchTool {

    //本地Git仓库目录 D:\workspace\git\
    final static String LOCALPATH = "D:\\workspace\\gittest0204\\";
    //远程Git仓库目录
    final static String REMOTE_PATH_HTTP = "https://git.baoku.com/";
    //要拉取的远程开发分支
    final static String BRANCH = "develop-210311";
    //Git远程仓库用户名
    final static String USERNAME = "wuxianyi";
    //Git远程仓库密码
    final static String PASSWORD = "KF2301410Ad7Fe48";
    //maven命令行
    public static String MAVEN_CMD = "mvn package -Dmaven.test.skip=true";
    //是否第一次进行git操作
    public static boolean GIT_FIRST_INIT = false;
    //是否需要暂存
    public static boolean GIT_NEED_STASH = false;
    //是否需要切换版本
    public static boolean GIT_NEED_CHECKOUT = true;
    //是否需要maven打包
    public static boolean MAVEN_NEED_CMD = false;

    // 批量切换分支
    public static void main(String[] args) {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD);
        if(GIT_FIRST_INIT) createLocalBatch(provider); // 第一次初始化需要创建，后续无需
        if(GIT_NEED_CHECKOUT) checkoutBranch(); //版本切换使用
        if(MAVEN_NEED_CMD) mvnPackage(); //自动打包
    }

    /**
     * @description: 创建本地分支
     * @author: 赵瑾强
     * @date: 2021/1/22 17:12
     * @param: [provider]
     * @return: void
     */
    private static void createLocalBatch(UsernamePasswordCredentialsProvider provider) {
        //遍历要执行的项目
        for (ProjectEnum project : ProjectEnum.values()) {
            String name = project.getName();
            String groupName = project.getGroupName();
            try {
                //要初始化的本地项目路径
                String localProjcetPath = LOCALPATH + name;
                //初始化目录 如果存在则删除
                gitInit(localProjcetPath);
                //远程仓库url 克隆远程仓库 https://git.baoku.com/baoku-air/baoku-air-server.git
                String remoteUrl = REMOTE_PATH_HTTP + groupName + "/" + name + ".git";
                gitClone(remoteUrl, localProjcetPath, remoteUrl, provider);
            } catch (Exception e) {
                System.out.println("分支：" + name + "创建失败：原因：" + e.getMessage());
                continue;
            }
            System.out.println("======>分支：" + name + "创建成功");
        }
    }

    /**
     * @throws
     * @Title: 切换本地分支
     * @Description: 切换本地分支
     * @author wxy
     * @Date 2021.2.3 15:12
     * @version 1.0.0
     **/
    private static void checkoutBranch() {
        //遍历要执行的项目
        for (ProjectEnum project : ProjectEnum.values()) {
            String name = project.getName();
            //远程仓库url
            String remoteUrl = REMOTE_PATH_HTTP + project.getGroupName() + "/" + name + ".git";
            try {
                File file = new File(LOCALPATH + name);
                //git文件不存在进行初始化
                if (!file.exists()) {
                    continue;
                }
                //获取本地仓库
                File gitDir = Paths.get(LOCALPATH + name, ".git").toFile();
                Repository repository = new FileRepositoryBuilder().setGitDir(gitDir).build();
                Git git = new Git(repository);
                // 本地暂存
                RevCommit call = null;
                if(GIT_NEED_STASH){
                    call = git.stashCreate().call();
                }
                //创建本地分支
                boolean flag = branchNameExist(git, BRANCH);
                Ref ref = git
                        .checkout()
                        .setCreateBranch(!flag)
                        .setName(BRANCH)
                        .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                        .setStartPoint("origin/" + BRANCH)
                        .call();
                if (call != null) {
                    // 应用本地暂存
                    git.stashApply().call();
                }
            } catch (Exception e) {
                System.out.println(name + "分支：" + BRANCH + "创建失败：原因：" + e.getMessage());
                continue;
            }
            System.out.println(name + "分支：" + BRANCH + ",创建成功");
        }
    }

    /**
     * @param git
     * @param branchName
     * @return
     * @throws GitAPIException
     * @Description:判断本地分支名是否存在
     * @author wxy
     * @date 2021年2月3日 下午2:49:46
     */
    public static boolean branchNameExist(Git git, String branchName) throws GitAPIException {
        List<Ref> refs = git.branchList().call();
        for (Ref ref : refs) {
            if (ref.getName().contains(branchName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Title: 执行CMD命令, 并返回String字符串
     * @Description: 执行CMD命令, 并返回String字符串
     * @author wxy
     * @Date 2021.2.4 16:03
     * @version 1.0.0
     **/
    private static void mvnPackage() {
        try {
            for (ProjectEnum project : ProjectEnum.values()) {
                String name = project.getName();
                File file = new File(LOCALPATH + name);
                if (file.exists()) {
                    String strCmd = "cmd /k  cd " + LOCALPATH + name + " && " + MAVEN_CMD;
                    mvnPackage(strCmd);
                }
            }
        } catch (Exception e) {
            System.out.println("Maven cmd is error: " + e);
        }
    }

    /**
     * 执行CMD命令,并返回String字符串
     */
    public static boolean mvnPackage(String strCmd) throws Exception {
        boolean flag = false;
        Process process = Runtime.getRuntime().exec(strCmd);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while (!(line = bufferedReader.readLine()).equals("")) {
            System.out.println(line);
            if (line.contains("[INFO] BUILD SUCCESS")) {
                flag = true;
            }
        }
        process.destroy();
        return flag;
    }
}
