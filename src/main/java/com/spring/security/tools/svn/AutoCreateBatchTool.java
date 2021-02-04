package com.spring.security.tools.svn;

import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/** 
 * 
 * 注：
 * 此工具类是用来自动创建项目分支的
 * 执行前需要先检查 现版本尾缀 presentSuffix和新分支尾缀batchSuffix是否正确
 * 
 * @项目名称：baoku-svn-tool
 * @类名称：AutoCreateBatchTool 
 * @类描述： 自动创建svn分支工具类
 * @创建人：Zhaojinqiang
 * @作者单位：北京宝库在线网络技术有限公司 
 * @联系方式：zhaojinqiang@baoku.com 
 * @创建时间：2019年9月11日 上午09:42:18 
 * @version 1.0.0
 */
public class AutoCreateBatchTool {
	//现版本尾缀
	private static final String presentSuffix = "200616";
	//新分支尾缀
	private static final String batchSuffix = "200723";

	//需要分支的项目
	private static final String[] projectNames = {
		"/code/common/baoku-common-lib/baoku-common-lib",
		"/code/server/baoku-air-server/baoku-air-server",
		"/code/server/baoku-crm-server/baoku-crm-server",
		"/code/server/baoku-data-server/baoku-data-server",
		"/code/server/baoku-hotelbook-server/baoku-hotelbook-server",
		"/code/server/baoku-hotelinfo-server/baoku-hotelinfo-server",
		"/code/server/baoku-insurance-server/baoku-insurance-server",
		"/code/server/baoku-interair-server/baoku-interair-server",
		"/code/server/baoku-message-server/baoku-message-server",
		"/code/server/baoku-online-server/baoku-online-server",
		"/code/server/baoku-pay-server/baoku-pay-server",
		"/code/server/baoku-train-server/baoku-train-server",
		"/code/web/baoku-api-web/baoku-api-web",
		"/code/web/baoku-hotel-web/baoku-hotel-web",
		"/code/web/baoku-manage-web/baoku-manage-web",
		"/code/web/baoku-pay-web/baoku-pay-web",
		"/code/web/baoku-proxy-web/baoku-proxy-web",
		"/code/web/baoku-report-web/baoku-report-web",
		"/code/web/baoku-shopen-web/baoku-shopen-web",
		"/code/web/baoku-shop-web/baoku-shop-web",
		"/code/web/baoku-super-web/baoku-super-web",
		"/code/web/baoku-surface-web/baoku-surface-web",
		"/code/web/baoku-h5api-web/baoku-h5api-web",
		"/code/common/baoku-parent/baoku-parent"
	};

	//svn根目录
	private static final String svnUrl = "http://svn.baoku.com/svn/baoku";
	//svn用户名
	private static final String svnUserName = "zhaojinqiang_baoku";
	//svn密码
	private static final String svnPassword = "zhaojinqiang_baoku";

	public static void main(String[] orgs){
		//验证登录
		SVNClientManager svnClientManager = authSvn(svnUrl, svnUserName, svnPassword);

		//创建分支
		createBatch(svnClientManager);
	}

	private static void createBatch(SVNClientManager svnClientManager){
		for(int i=0; i<projectNames.length; i++){
			String projectName = projectNames[i] + "-" + presentSuffix;
			String batcheUrl = projectNames[i] + "-" + batchSuffix;
			String message = "自动创建分支";
			String revision = "-1";
			try {
				copyModel(projectName, batcheUrl, message, revision, svnUrl, svnClientManager);
			} catch (SVNException e) {
				System.out.println("分支："+batcheUrl+"创建失败：原因："+e.getMessage());
				continue;
			}
			System.out.println("分支："+batcheUrl+"创建成功");
		}
	}

	/**
	 * 验证登录svn
	 * @Param: svnRoot:svn的根路径
	 */
	public static SVNClientManager authSvn(String svnRoot, String username, String password) {
		// 初始化版本库
		setupLibrary();

		// 创建库连接
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnRoot));
		} catch (SVNException e) {
			e.printStackTrace();
			System.out.println("SVNClientManager fail");
			return null;
		}

		// 身份验证
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);

		// 创建身份验证管理器
		repository.setAuthenticationManager(authManager);

		DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
		SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
		return clientManager;
	}

	/**
	 * 通过不同的协议初始化版本库
	 */
	public static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
	}

	/**
	 * 复制 A目录（子文件）到 B 目录
	 * @throws SVNException 
	 */
	public static SVNCommitInfo copyModel(String buffUrl,String storeUrl,String message,String revision,String defaultSvnRoot, SVNClientManager ourClientManager) throws SVNException {
		SVNCopyClient copyClient = ourClientManager.getCopyClient();
		copyClient.setIgnoreExternals(false);
		// 根目录
		SVNURL root = SVNURL.parseURIEncoded(defaultSvnRoot);
		// 被复制目录
		SVNURL repositoryOptUrl = SVNURL.parseURIEncoded(defaultSvnRoot + buffUrl);
		// 目的目录
		SVNURL destUrl = SVNURL.parseURIEncoded(defaultSvnRoot + storeUrl);

		SVNRepository repository = ourClientManager.createRepository(root, true);

		// 检测 目的 url目录是否存在;  -1 代表当前最新版本
		SVNNodeKind svnNodeKind = repository.checkPath(storeUrl, -1);
		SVNCopySource[] copySources;
		// 如果目标目录B存在, 获取复制路径下A子目录到目标地址； 否则直接复制A 到B
		if (svnNodeKind == SVNNodeKind.DIR) {
			String basePath = defaultSvnRoot + buffUrl;
			repository.setLocation(repositoryOptUrl, true);
			List<?> urlPaths = listEntries(repository, "",Long.valueOf(revision));
			copySources = new SVNCopySource[urlPaths.size()];
			for (int i = 0; i < urlPaths.size(); i++) {
				copySources[i] = new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(basePath +urlPaths.get(i)));
			}
		} else {
			copySources = new SVNCopySource[1];
			if ("-1".equals(revision)) {
				copySources[0] = new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, repositoryOptUrl);
			} else {
				copySources[0] = new SVNCopySource(SVNRevision.create(Long.valueOf(revision)), SVNRevision.create(Long.valueOf(revision)), repositoryOptUrl);
			}
		}

		return copyClient.doCopy(copySources, destUrl, false, true, false, message, null);

	}

	/**
	 * 此函数递归的获取版本库中某一目录下的条目。
	 * @param repository
	 * @param path
	 * @throws SVNException
	 */
	public static List<String> listEntries(SVNRepository repository, String path, long revision)
			throws SVNException {
		List<String> urlPaths =new ArrayList<String>();
		//获取版本库的path目录下的所有条目。参数－1表示是最新版本。
		Collection<?> entries = repository.getDir(path, revision, null,(Collection<?>) null);
		Iterator<?> iterator = entries.iterator();
		while (iterator.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
			urlPaths.add("/"+entry.getName());
		}
		return urlPaths;
	}

}
