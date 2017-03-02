package com.baizhi.lg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baizhi.lg.entity.User;
import com.baizhi.lg.service.UserService;
import com.baizhi.lg.util.CSVUtils;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserAction {
	@Autowired
	private UserService userService;

	//调用createCSVFile方法生成CSV文件
	@RequestMapping("/all")
	@ResponseBody
	  public static void download(HttpServletRequest res,MultipartFile file,HttpServletResponse response,
			  							String filePath, String fileName, List<Stu> stu){
	  String name = "";
	  /*List exportData = new ArrayList();
	  LinkedHashMap datamMap = null;
	  for (Iterator iterator = stu.iterator(); iterator.hasNext();) {
	     HashMap map = (HashMap) iterator.next();
	     datamMap = new LinkedHashMap();
	     datamMap.put("1", map.get("Id"));
	    // datamMap.put("2",DateUtil.convertDateToString("yyyyMMdd", (Date) map.get("orderTime")));
	     BigDecimal amount = (BigDecimal) map.get("amount");
	     String amountString = amount.divide(new BigDecimal(10)).toPlainString();
	     datamMap.put("1", arg1)
	     datamMap.put("2", amountString);
	     datamMap.put("3", map.get("remark") != null ? map.get("remark") : "");
	     exportData.add(datamMap);
	  }*/
	  List<Stu> findSelectAll = peculiarService.findSelectAll();
	  
	  
	   List<Stu> exportData = findSelectAll;
	   LinkedHashMap map = new LinkedHashMap();
	   map.put("1", "id");
	   map.put("2", "name");
	   map.put("3", "age");
	   File file1 = CSVUtils.createCSVFile(exportData, map, filePath, name);//生成CSV文件
	   fileName = file1.getName();
	   System.out.println("文件的名字"+fileName);
	  // CSVUtils.createCSVFile(exportData, map, filePath, fileName);//下载生成的CSV文件
	   //CSVUtils.exportFile(response, filePath + fileName, fileName);//下载生成的CSV文件
	  }
	  

}
