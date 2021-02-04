package com.spring.security.tools.svn;

import com.google.common.collect.Sets;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;

import java.io.File;
import java.util.Set;

/** 
* @项目名称：baoku-svn-tool 
* @类名称：BranchTool 
* @类描述：svn切换工具，需要注意pom中svnkit版本要与eclipse、idea或外置的svn客户端版本一致，否则会报兼容错误。
* 		 切换完成后，eclipse可以执行team->Refresh/Cleanup，来刷新编译器缓存
* @创建人：LiJiaxing
* @作者单位：北京宝库在线网络技术有限公司 
* @联系方式：lijiaxing@baoku.com 
* @创建时间：2020年5月20日 下午3:20:19 
* @version 1.0.0
*/
public class BranchTool {
	/**
	 * svn根路径
	 */
	private static String svnRootUrl = "http://svn.baoku.com/svn/baoku";
	/**
	 * svn用户名
	 */
	private static String username = "";
	/**
	 * svn密码
	 */
	private static String password = "";
	/**
	 * 本地工作空间目录
	 */
	private static String workspaceUrl = "E:\\baoku\\";
	/**
	 * 要切换的版本号
	 */
	private static String version = "200723";
	/**
	 * 是否切换tag分支
	 */
	private static boolean isTag = false;
	
	
	public static void main(String[] args) throws SVNException {
		SVNClientManager clientManager = SvnTool.authSvn(svnRootUrl, username, password);
		File folder = new File(workspaceUrl);
		//判断目录是否为宝库项目，是的话切换单个项目
		Long result = switchBranch(clientManager, folder);
		if(result!=null) {
			System.out.println("切换完成");
			return;
		}
		//目录为工作空间的，遍历所有子目录，命名为宝库项目风格的执行切换
		for(File file : folder.listFiles()) {
			switchBranch(clientManager, file);
		}
		System.out.println("切换完成");
		
	}

	/**
	 * 切换分支
	 * @Title: switchBranch
	 * @param clientManager
	 * @param file
	 * @return
	 * @throws SVNException 
	 * @author LiJiaxing
	 * @date 2020年5月20日 下午5:56:51
	 */
	private static Long switchBranch(SVNClientManager clientManager, File file) throws SVNException {
		String projectName = getProjectName(file.getName());
		String projectType = projectType(projectName);
		if(projectType!=null) {
			String url = svnRootUrl+"/code/"+projectType+"/"+projectName+"/"+projectName+"-"+version+"/";
			if(isTag) {
				url = svnRootUrl+"/tag/"+version+"/"+projectName+"-"+version+"/";
			}
			SVNURL svnUrl= SVNURL.parseURIEncoded(url);
			Long version = SvnTool.switchBranch(clientManager, file, svnUrl);
			System.out.println("switch project:"+projectName+" at revision "+version);
			return version;
		}
		return null;
	}
	
	private static String projectType(String name) {
		if(name.startsWith("baoku")) {
			if(name.contains(PROJECT_SERVER)) {
				return PROJECT_SERVER;
			}else if(name.contains(PROJECT_WEB)) {
				return PROJECT_WEB;
			}
			if(commonProject.contains(name)) {
				return PROJECT_COMMON;
			}
		}
		return null;
	}
	
	private static String getProjectName(String name) {
		return name.replaceAll("\\-(\\d){6}", "");
	}
	
	private static Set<String> commonProject = Sets.newHashSet("baoku-common-lib","baoku-parent","baoku-parent-manage");
	private static String PROJECT_SERVER="server";
	private static String PROJECT_WEB="web";
	private static String PROJECT_COMMON="common";
}
