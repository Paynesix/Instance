/** 
*@Copyright (c) 宝库技术团队 www.baoku.com
*@Package com.baoku.conver
*@Project：conver
*@authur：zhucj zhuchengjie@baoku.com
*@date：2019年11月5日 下午1:59:56   
*@version 1.0
*/
package com.spring.security.tools;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @ClassName：ZipUtil
 * @authur：wxy
 * @date：2020年08月19日 下午1:59:56
 * @version 1.0
 */
public class ZIPUtil {
	/**
	 * 压缩
	 *
	 * @param source 源文件,可以是目录或单个文件
	 * @param target    压缩到的目录+文件名
	 * @param password  密码 不是必填
	 * @throws ZipException 异常
	 */
	public static void zip(String source, String target, String password) throws ZipException {
		File srcfile = new File(source);
		// 创建目标文件
		createPath(target);
		ZipParameters par = new ZipParameters();
		par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		if (StringUtils.isNotBlank(password)) {
			par.setEncryptFiles(true);
			par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			par.setPassword(password.toCharArray());
		}
		ZipFile zipfile = new ZipFile(target);
		if (srcfile.isDirectory()) {
			zipfile.addFolder(srcfile, par);
		} else {
			zipfile.addFile(srcfile, par);
		}
	}

	/**
	 * @param target 
	 * void 
	 * @throws
	 * @author zhucj
	 * @date 2019年11月5日 下午2:32:42
	 * 创建文件夹
	 */
	private static void createPath(String target) {
		File destDir = new File(target.substring(0,target.lastIndexOf(File.separator)));
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}
}
