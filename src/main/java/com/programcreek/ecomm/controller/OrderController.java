package com.programcreek.ecomm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.programcreek.ecomm.bean.OrderBean;
import com.programcreek.ecomm.bean.itemBean;
import com.programcreek.ecomm.dao.AdminDao;

@RestController
@EnableWebMvc

public class OrderController {
	@Autowired
	AdminDao adminDao;

	@RequestMapping(value = "/makeOrder")
	public void makeOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderBean obean) throws IOException {
		OrderBean bean  = new OrderBean();
		bean.setEmail_ids(obean.getEmail_ids());
		bean.setOrder(obean.getOrder());
		JSONArray arr = new JSONArray(bean.getOrder());
	
		Boolean check_stock = adminDao.checkStock(arr);
		Gson gson = new Gson();
		response.setContentType("application/json;charset=UTF-8;");
		PrintWriter pw = response.getWriter();
		if (check_stock.equals(false)) {
			OrderBean result = adminDao.makeOrder(obean,arr);
			if (result.getStatus() == 400) {
				itemBean fStatus = adminDao.failureMessage();
				String data1 = gson.toJson(fStatus);
				pw.print(data1);

			} else {
				String data1 = gson.toJson(result);
				pw.print(data1);
			}
		}else {
			OrderBean abean = new OrderBean();
			abean.setMsg("Items out of stock");
			abean.setStatus(200);
			String data = gson.toJson(abean);
			pw.print(data);
		}
	
	pw.flush();
	pw.close();
	}
	@RequestMapping(value="/showOrder")
	public void showOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int i = 0;
		try {
			int order_id = Integer.parseInt(request.getParameter("order_id"));
			i =order_id;
		}catch(Exception e){
			i = 0;
		}
		List<OrderBean> result  =adminDao.showOffer(i);
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
				bean.setMsg("Orders not available");
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
