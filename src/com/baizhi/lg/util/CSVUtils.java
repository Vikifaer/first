package com.baizhi.lg.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.baizhi.lg.entity.User;

/**
 * 文件操作
 */
public class CSVUtils {

	/**
	 * 生成为CVS文件
	 * 
	 * @param exportData
	 *            源数据List
	 * @param map
	 *            csv文件的列表头map
	 * @param outPutPath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File createCSVFile(List exportData, LinkedHashMap map,
			String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv",
					new File(outPutPath));
			System.out.println("csvFile：" + csvFile);
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), "UTF-8"), 1024);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
					.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
						.next();
				csvFileOutputStream
						.write((" " + (String) propertyEntry.getValue() != null ? (String) propertyEntry
								.getValue() : "" + ""));
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();
			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
						.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
							.next();
					csvFileOutputStream.write(BeanUtils.getProperty(
							row, (String) propertyEntry.getKey()));
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 下载文件
	 * @param response
	 * @param csvFilePath
	 * @param fileName
	 * @throws IOException
	 */
	public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
			throws IOException {
		// 文件类型
		response.setContentType("application/csv;charset=UTF-8");
		// 获取响应头
		response.setHeader("Content-Disposition", "attachment; filename="
				+ URLEncoder.encode(fileName, "UTF-8"));

		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
			int len = 0;
			byte[] buffer = new byte[2048];
			// 设置响应的编码
			response.setCharacterEncoding("UTF-8");
			// 获取响应流
			OutputStream out = response.getOutputStream();

			while ((len = in.read(buffer)) > 0) {
				out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
				out.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	
}