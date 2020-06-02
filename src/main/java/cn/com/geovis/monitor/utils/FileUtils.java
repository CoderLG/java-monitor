package cn.com.geovis.monitor.utils;

import java.io.File;

/**
 * @author wangda
 * @Description 文件相关工具类
 * @date 20181031
 */
public class FileUtils<T> {

	/**
	 * 判断目录下是否存在符合规定文件结尾的
	 *
	 * @param dir      绝对路径
	 * @param format   osgb or b3dm or b3dm_title
	 *                 数据类型
	 * @param endsWith metadata.xml  tileset.json
	 *                 结尾文件名
	 * @return
	 */
	public static boolean fileIsExist(String dir, String format, String endsWith) {
		File file = new File(dir);
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				/**
				 * 大规模倾斜摄影备用方案
				 * new file 拼接dir + f
				 * 	for
				 */
				if (f.getAbsolutePath().endsWith(endsWith)) {
					return true;
				}
				f = null;  // for GC
			}
			file = null; // for GC
		}
		return false;
	}
	/**
	 *大规模倾斜摄影
	 * 下一级目录
	 */
	public static String getNextFilePath(String dir){
		File file = new File(dir);
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if(f.isDirectory()){
					return f.getAbsolutePath();
				}
			}
		}

		return  null;

	}


	/**
	 * @Author yijunxian
	 * @Description 判断文件是否存并且是文件夹
	 * @Date 10:25 2019/7/26
	 * @Param [dir]
	 * @return boolean
	 **/
	public static boolean fileIsDir(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

}
