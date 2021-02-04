package com.spring.security.tools.svn;

import java.io.*;

/**
 * @项目名称：baoku-svn-tool
 * @类名称：UpdateMavenVersionTool @类描述：
 * @创建人：LiJiaxing
 * @作者单位：北京宝库在线网络技术有限公司
 * @联系方式：lijiaxing@baoku.com
 * @创建时间：年5月日 上午::
 * @version 1.0.0
 */
public class UpdateMavenVersionTool {
	/**
	 * 本地工作空间目录
	 */
	private static String workspaceUrl = "E:\\baoku-project\\";
	private static String oldVersion = "200616-SNAPSHOT";
	private static String newVersion = "200723.SNAPSHOT";

	public static void main(String[] args) {
		File folder = new File(workspaceUrl);
		for (File subFile : folder.listFiles()) {
			updateVersion(subFile);
		}
	}

	public static void updateVersion(File file) {
		if (isBaokuProject(file)) {
			for (File subFile : file.listFiles()) {
				if (subFile.getName().equals("pom.xml")) {
					String content = read(subFile);
					if(isBaokuParentProject(file)){
						String parentStr = content.substring(content.indexOf("<modelVersion>"), content.indexOf("</baoku.version>"));
						String newParent = parentStr.replace(oldVersion, newVersion);
						content = content.replace(parentStr, newParent);
						write(subFile, content);
						System.out.println(subFile+" 版本更新成功");
					}else{
						if(content.indexOf("<parent>")>-1) {
							//截取出来要替换的字符串，防止和其他项目版本一致，替换了多个版本号
							String parentStr = content.substring(content.indexOf("<parent>"), content.indexOf("</parent>"));
							String newParent = parentStr.replace(oldVersion, newVersion);
							content = content.replace(parentStr, newParent);
							write(subFile, content);
							System.out.println(subFile+" 版本更新成功");
						}else {
							System.out.println(subFile+" 未找到parent");
						}
					}
				}else {
					updateVersion(subFile);
				}
			}
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 */
	public static String read(File file) {
		BufferedReader br = null;
		String line = null;
		StringBuffer buf = new StringBuffer();
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(file));

			// 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
			while ((line = br.readLine()) != null) {
				buf.append(line);
				buf.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}

		return buf.toString();
	}

	/**
	 * 将内容回写到文件中
	 * 
	 * @param file
	 * @param content
	 */
	public static void write(File file, String content) {
		BufferedWriter bw = null;
		try {
			// 根据文件路径创建缓冲输出流
			bw = new BufferedWriter(new FileWriter(file));
			// 将内容写入文件中
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}
	
	public static boolean isBaokuProject(File file) {
		if(file.isFile()) {
			return false;
		}
		String name = file.getName().replaceAll("\\-(\\d){6}", "");
		if(name.startsWith("baoku-")) {
			return true;
		}
		return false;
	}

	public static boolean isBaokuParentProject(File file) {
		String name = file.getName().replaceAll("\\-(\\d){6}", "");
		if(name.startsWith("baoku-parent")) {
			return true;
		}
		return false;
	}
	

}
