/**
 * 
 */
package cn.sx.decentworld.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.sx.decentworld.common.Constants;
import android.os.Handler;
import android.os.Message;

/**
 * @ClassName: HttpDownload.java
 * @Description:
 * @author: dyq
 * @date: 2015年10月24日 下午4:05:56
 */
public class HttpDownloader {
	// private static InputStream inputStream = null;
	/**
	 * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容 1.创建一个URL对象
	 * 2.通过URL对象，创建一个HttpURLConnection对象 3.得到InputStream 4.从InputStream当中读取数据
	 *
	 * @param urlStr
	 *            要下载的文件地址
	 * @return
	 */
	public static String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		// BufferedReader有一个readLine（）方法，可以每次读取一行数据
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			URL url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 使用IO流读取数据，InputStreamReader将读进来的字节流转化成字符流
			// 但是字符流还不是很方便，所以再在外面套一层BufferedReader，
			// 用BufferedReader的readLine（）方法，一行一行读取数据
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 0代表传输失败，1代表传输成功，2代表传输中 该函数返回整型
	 *
	 * @param urlStr
	 *            下载文件的网络地址
	 * @param path
	 *            想要把下载过来的文件存放到哪一个SDCARD目录下
	 * @param fileName
	 *            下载的文件的文件名，可以跟原来的名字不同，所以这里加一个fileName
	 * @return
	 */
	public static int downFile(String urlStr, String fileName) {
		InputStream inputStream = null;
		int resultInt = 0;
		try {
			inputStream = getInputStreamFromURL(urlStr);
			File resultFile = FileUtils.writeToSDFromInput(fileName,
					inputStream);
			if (resultFile == null) {
				resultInt = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultInt = 1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultInt;
		// Message msg =new Message();
		// msg.what = resultInt;
		// msg.obj = fileName;
		// handler.sendMessage(msg);
	}

	/**
	 * 根据URL得到输入流
	 *
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	public static InputStream getInputStreamFromURL(String urlStr) {
		HttpURLConnection urlConn = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			urlConn = (HttpURLConnection) url.openConnection();
			inputStream = urlConn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}