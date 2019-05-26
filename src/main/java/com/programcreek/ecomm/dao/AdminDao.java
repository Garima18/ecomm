package com.programcreek.ecomm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.programcreek.ecomm.bean.OrderBean;
import com.programcreek.ecomm.bean.itemBean;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDao {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	int check;
	public itemBean addItem(itemBean bean) {
		final itemBean resultBean  = new itemBean();
		String check_item = "Select count(*) as count from ecomm.item where lower(item_name) = '"+bean.getItem_name().toLowerCase()+"'";
		jdbcTemplate.query(check_item, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				check = rs.getInt("count");
				return check;
			}});
		if (check == 0) {
			String query = "insert into ecomm.item(item_name,item_price,quantity_left) values ('" + bean.getItem_name()
					+ "'," + bean.getItem_price() + "," + bean.getQuantity_left() + ") returning item_id";

			try {
				jdbcTemplate.query(query, new RowMapper<itemBean>() {
					public itemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
						resultBean.setItem_id(rs.getInt("item_id"));
						resultBean.setStatus(200);
						return resultBean;
					}
				});
			} catch (Exception e) {
				resultBean.setStatus(400);
				e.printStackTrace();

			}
		}else {
			resultBean.setStatus(401);
		}
		return resultBean;
	}
	public itemBean failureMessage() {
		final itemBean bean = new itemBean();
		bean.setStatus(400);
		bean.setMsg("Something went wrong");
		
		return bean;
	}
	public List<itemBean> getItem(int item_id){
		String item = "";
		if (item_id != 0) {
			item = "where item_id =" + item_id;
		}
		final List<itemBean> list = new ArrayList<>();
		try {
			String query = "Select * from ecomm.item " + item + " ";
			System.out.println(query);
			jdbcTemplate.query(query, new RowMapper<itemBean>() {
				public itemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
					itemBean bean = new itemBean();
					bean.setItem_id(rs.getInt("item_id"));
					bean.setItem_name(rs.getString("item_name"));
					bean.setItem_price(rs.getInt("item_price"));
					bean.setQuantity_left(rs.getInt("quantity_left"));
					bean.setStatus(200);
					list.add(bean);
					return bean;
				}
			});
		} catch (Exception e) {
			itemBean bean = new itemBean();
			bean.setStatus(400);
			list.add(bean);
			}
		return list;
	}
	public OrderBean makeOrder(OrderBean bean,JSONArray arr) {
		OrderBean resultBean = new OrderBean();
		try {
		String insert_order = "insert into ecomm.order(email_id,order_made_at) values ('"+bean.getEmail_ids()+"','"+Calendar.getInstance().getTime()+"') returning order_id";
		int order_id  = jdbcTemplate.queryForObject(insert_order, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				int id = rs.getInt("order_id");
				return id;
			}});
		
		
		for(int i =0;i<arr.length();i++) {
			String item_name = arr.getJSONObject(i).getString("item_name");
			String get_itemid = "Select * from ecomm.item where lower(item_name) = '"+item_name.toLowerCase()+"'";
			int item_id = jdbcTemplate.queryForObject(get_itemid, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					int id = rs.getInt("item_id");
					return id;
				}
			});
			
			String query1 = "insert into ecomm.order_product(order_id,item_id,quantity) values ("+order_id+","+item_id+","+arr.getJSONObject(i).getInt("quantity")+") ";
			jdbcTemplate.update(query1);
			
			String decrease_item_quantity = "update ecomm.item set quantity_left = quantity_left - "+arr.getJSONObject(i).getInt("quantity")+" where item_id = "+item_id+"";
			jdbcTemplate.update(decrease_item_quantity);
		}
		resultBean.setStatus(200);

		}catch(Exception e) {
			e.printStackTrace();
			resultBean.setStatus(400);
		}
		return resultBean;
	}
	public Boolean checkStock(JSONArray order) {
		Boolean stock_out = true;
		try {
		for(int i=0;i<order.length();i++) {
			String item_name = order.getJSONObject(i).getString("item_name");
			String query ="Select * from ecomm.item where lower(item_name)='"+item_name.toLowerCase()+"'";
			System.out.println(query);
			int quantity = jdbcTemplate.queryForObject(query, new RowMapper<Integer>() {
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					int num = rs.getInt("quantity_left");
					return num;
				}});
			if(quantity <= order.getJSONObject(i).getInt("quantity")) {
				stock_out = true;
				break;
			}else {
				stock_out = false;
			}
		}
		}catch(Exception e) {
			stock_out = true;

		}
		return stock_out;
	}
	public List<OrderBean> showOffer(int order_id){
		String order = "";
		if (order_id != 0) {
			order = "where order_id =" + order_id;
		}
		String query = "Select * from ecomm.order "+order+"";
		List<OrderBean> list  = new ArrayList<>();
		try {
		 list  = jdbcTemplate.query(query, new RowMapper<OrderBean>() {
			public OrderBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderBean bean = new OrderBean();
				bean.setEmail_ids(rs.getString("email_id"));
				bean.setOrder_id(rs.getInt("order_id"));
				List<itemBean> list = getProductByOrder(bean.getOrder_id());
				bean.setPer_order_list(list);
				bean.setStatus(200);
				return bean;
			}
			
		});
		}catch(Exception e) {
			
			OrderBean bean = new OrderBean();
			bean.setStatus(400);
			list.add(bean);
			
		}
		return list;
	}
	public List<itemBean> getProductByOrder(int order_id){
		String query = "Select * from ecomm.order_product where order_id = '"+order_id+"'";
		List<itemBean> list = jdbcTemplate.query(query, new RowMapper<itemBean>() {
			public itemBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				itemBean bean = new itemBean();
				bean.setItem_id(rs.getInt("item_id"));
				String get_name = "Select * from ecomm.item where item_id = '"+bean.getItem_id()+"'";
				String item_name = jdbcTemplate.queryForObject(get_name, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						String name = rs.getString("item_name");
						return name;
					}
					
				});
				bean.setItem_name(item_name);
				bean.setQuantity(rs.getInt("quantity"));
				return bean;
			}
			
		});
		return list;
	}
	
}

