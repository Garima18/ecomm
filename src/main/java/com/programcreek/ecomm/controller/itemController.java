package com.programcreek.ecomm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.programcreek.ecomm.bean.itemBean;
import com.programcreek.ecomm.dao.AdminDao;

@Controller
@EnableWebMvc
@RestController
public class itemController {

	@Autowired
	AdminDao adminDao;

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public void addItem(HttpServletRequest request, HttpServletResponse response, @RequestBody itemBean ibean) throws IOException{
	
		itemBean bean = new itemBean();
		bean.setItem_name(ibean.getItem_name());
		bean.setItem_price(ibean.getItem_price());
		bean.setQuantity_left(ibean.getQuantity_left());
		itemBean result  = adminDao.addItem(bean);
		Gson gson = new Gson();
		response.setContentType("application/json;charset=UTF-8;");
		PrintWriter pw = response.getWriter();

		if (result.getStatus()== 400) {
			itemBean fStatus = adminDao.failureMessage();
			String data1 = gson.toJson(fStatus);
			pw.print(data1);

		} else if(result.getStatus()==200){
			String data1 = gson.toJson(result);
			pw.print(data1);
		}else {
			itemBean abean = new itemBean();
			abean.setMsg("This item already exists");
			abean.setStatus(200);	
			String data = gson.toJson(abean);
			pw.print(data);
		}
	
	pw.flush();
	pw.close();
		
	}
	
	@RequestMapping(value = {"/getItem"},method = RequestMethod.GET)
	public void getItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int i = 0;
		try {
			int item_id = Integer.parseInt(request.getParameter("item_id"));
			i =item_id;
		}catch(Exception e){
			i = 0;
		}
		List<itemBean> result  =adminDao.getItem(i);
		Gson gson = new Gson();
		PrintWriter pw =response.getWriter();
 		response.setContentType("application/json;charset=UTF-8");

		if(result.get(0).getStatus()==400) {
			//failure
			itemBean fStatus = adminDao.failureMessage();
			String data1 = gson.toJson(fStatus);
			pw.print(data1);
		}else {
			if(result.size()==0) {
				//item not available
				itemBean bean = new itemBean();
				bean.setMsg("Item not available");
				bean.setStatus(200);
				String data = gson.toJson(bean);
				pw.print(data);
			}else {
				//show the list
				String data = gson.toJson(result);
				JSONArray jsonarray = new JSONArray(data);
				pw.print(jsonarray);
			}
		}
		
	}
}
