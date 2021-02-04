package com.spring.security.tools.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @项目名称：baoku-svn-tool
 * @类名称：AutoCreateBatchTool
 * @描述: Git自动创建分支
 * @创建人: 赵瑾强
 * @联系方式: zhaojinqiang@baoku.com
 * @创建时间: 2021-01-19 19:46:05
 * @version: 1.0-SNAPSHOT
 */
public class AutoCreateBatchTool {

    //本地Git仓库目录 E:\git-branch-dir\
    final static String LOCALPATH = "";
    //远程Git仓库目录
    final static String REMOTE_PATH_HTTP = "https://git.baoku.com/";
    //final static String REMOTE_PATH = "git@git.baoku.com:baoku-air/baoku-air-server.git";
    //要拉取的远程分支名 master
    final static String SERVER_BRANCH = "";
    //要创建的分支名 develop-210325
    final static String TO_BRANCH = "";
    //Git远程仓库用户名
    final static String USERNAME = "";
    //Git远程仓库密码
    final static String PASSWORD = "";

    public static void main(String[] args) {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(USERNAME,PASSWORD);

        createLocalBatch(provider);
    }

    /**
     * @description: 创建本地分支
     * @author: 赵瑾强
     * @date: 2021/1/22 17:12
     * @param: [provider]
     * @return: void
     */
    private static void createLocalBatch(UsernamePasswordCredentialsProvider provider){
        //遍历要执行的项目
        for(ProjectEnum project : ProjectEnum.values()){
            String name = project.getName();
            String groupName = project.getGroupName();

            //要初始化的本地项目路径
            String localProjcetPath = LOCALPATH + name;
            //远程仓库url
            String remoteUrl = REMOTE_PATH_HTTP + groupName + "/" + name + ".git";
            try {
                //初始化目录 如果存在则删除
                gitInit(localProjcetPath);
                //克隆远程仓库 https://git.baoku.com/baoku-air/baoku-air-server.git
                gitClone(remoteUrl, localProjcetPath, SERVER_BRANCH, provider);

                /** 如果不需要修改maven版本 需要注释掉此代码*/
                //看是否需要修改maven版本
                /*File folder = new File(localProjcetPath);
                UpdateMavenVersionTool.updateVersion(folder);*/

                //提交本地修改到本地仓库
                //gitCommit(localProjcetPath, "切换maven版本");

                //获取本地仓库
                Git git = getRepositoryFromDir(localProjcetPath);
                //创建本地分支 bash命令：git branch release-210204

                //先检查新建的分支是否已经存在，如果存在则将已存在的分支强制删除并新建一个分支
                checkLocalBranch(git);
                //创建新本地分支
                Ref ref = git.branchCreate().setName(TO_BRANCH).call();

                //push本地分支到远程进行创建
                push(git, provider);
            }catch (Exception e){
                System.out.println("分支："+name+"创建失败：原因："+e.getMessage());
                continue;
            }
            System.out.println("分支："+name+"创建成功");
        }
    }

    /**
     * 仓库初始化
     */
    public static void gitInit(String localPath) throws Exception {
        File file = new File(localPath);
        //git文件不存在进行初始化
        if(file.exists()){
            System.out.println("git file already exists");
            //如果目录存在则删除
            deleteDir(file);
        }
        System.out.println("git init success");
    }

    /**
     * @description: 递归删除目录下的所有文件及子目录下所有文件
     * @author: 赵瑾强
     * @date: 2021/1/20 10:53
     * @param: [dir]
     * @return: boolean
     */
    public static boolean deleteDir(File dir) throws Exception{
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if(dir.delete()) {
            return true;
        } else {
            System.out.println("目录删除失败！");
            return false;
        }
    }

    /**
     * 克隆远程仓库
     * @param remotePath:"url"
     * @param branch：master
     * @throws GitAPIException
     */
    public static void gitClone(String remotePath,String localProjcetPath, String branch, UsernamePasswordCredentialsProvider provider) throws GitAPIException {
        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();
        Git git= cloneCommand.setURI(remotePath) //设置远程URI
                .setBranch(branch) //设置clone下来的分支
                .setTimeout(1800)
                .setDirectory(new File(localProjcetPath)) //设置下载存放路径
                .setCredentialsProvider(provider) //设置权限验证
                .call();
        System.err.println("git clone success");
    }

    /**
     * @description: 根据路径获取本地Git仓库
     * @author: 赵瑾强
     * @date: 2021/1/20 10:11
     * @param: [dir]
     * @return: org.eclipse.jgit.api.Git
     */
    public static Git getRepositoryFromDir(String dir) throws IOException {
        Repository repository =  new FileRepositoryBuilder().setGitDir(Paths.get(dir, ".git").toFile()).build();
        return new Git(repository);
    }

    /**
     * @description: 检查新建的分支是否已经存在，如果存在则将已存在的分支强制删除并新建一个分支
     * @author: 赵瑾强
     * @date: 2021/1/20 10:57
     * @param: [git]
     * @return: void
     */
    public static void checkLocalBranch(Git git) throws Exception{
        //检查新建的分支是否已经存在，如果存在则将已存在的分支强制删除并新建一个分支
        List<Ref> refs = git.branchList().setListMode(null).call();
        for (Ref ref : refs) {
            if (ref.getName().equals("refs/heads/"+TO_BRANCH)) {
                System.out.println("Removing branch before");
                git.branchDelete().setBranchNames(TO_BRANCH).setForce(true)
                        .call();
                break;
            }
        }
    }

    /**
     * 本地代码提交
     * @param msg:提交信息
     * @throws IOException
     * @throws GitAPIException
     */
    public static void gitCommit(String localPath, String msg) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + "/.git"));
        //全部提交
        git.commit().setAll(true).setMessage(msg).call();
        System.err.println("git commit success");
    }

    /**
     * @description: 向Git服务器推送分支
     * @author: 赵瑾强
     * @date: 2021/1/18 20:36
     * @param: [git, provider]
     * @return: void
     */
    public static void push(Git git, CredentialsProvider provider) throws GitAPIException {
        Iterable<PushResult> origin = git.push().setCredentialsProvider(provider).setRefSpecs(new RefSpec(TO_BRANCH)).call();
    }

}