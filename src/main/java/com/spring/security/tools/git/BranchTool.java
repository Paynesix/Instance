package com.spring.security.tools.git;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.nio.file.Paths;

/**
 * @项目名称：baoku-online-api-doc
 * @类名称：BranchTool
 * @描述: git新建本地分支工具类 可支持批量操作
 * @创建人: 赵瑾强
 * @联系方式: zhaojinqiang@baoku.com
 * @创建时间: 2021-01-25 09:53:46
 * @version: 1.0-SNAPSHOT
 */
public class BranchTool {

    //本地Git仓库目录 E:\server\
    final static String LOCALPATH = "E:\\web\\";
    //远程Git仓库目录
    final static String REMOTE_PATH_HTTP = "https://git.baoku.com/";
    //要拉取的远程开发分支
    final static String BRANCH = "develop-210204";
    //Git远程仓库用户名
    final static String USERNAME = "";
    //Git远程仓库密码
    final static String PASSWORD = "";

    /**
     * 批量创建本地开发分支
     * @param args
     */
    public static void main(String[] args) {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(USERNAME,PASSWORD);

        //遍历要执行的项目
        for(ProjectEnum project : ProjectEnum.values()){
            String name = project.getName();
            //远程仓库url
            String remoteUrl = REMOTE_PATH_HTTP + project.getGroupName() + "/" + name + ".git";
            try {
                File file = new File(LOCALPATH + name);
                //git文件不存在进行初始化
                if(!file.exists()){
                    continue;
                }
                //获取本地仓库
                Repository repository =  new FileRepositoryBuilder().setGitDir(Paths.get(LOCALPATH + name, ".git").toFile()).build();
                Git git = new Git(repository);
                //创建本地分支
                Ref ref = git.checkout().
                        setCreateBranch(true).
                        setName(BRANCH).
                        setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
                        setStartPoint("origin/" + BRANCH).
                        call();
            }catch (Exception e){
                System.out.println(name+"分支："+BRANCH+"创建失败：原因："+e.getMessage());
                continue;
            }
            System.out.println(name+"分支："+BRANCH+",创建成功");
        }
    }
}
