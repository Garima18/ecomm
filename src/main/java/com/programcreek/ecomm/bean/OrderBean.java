package com.programcreek.ecomm.bean;

import java.util.List;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class OrderBean {
private Integer order_id;
private Integer product_order_id;
private String order_made_at;
private Integer quantity;
private String email_ids;
private List<itemBean> per_order_list;
private String order;
private Integer status;
private String msg;



public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public String getOrder() {
	return order;
}
public void setOrder(String order) {
	this.order = order;
}
public Integer getOrder_id() {
	return order_id;
}
public void setOrder_id(Integer order_id) {
	this.order_id = order_id;
}
public Integer getProduct_order_id() {
	return product_order_id;
}
public void setProduct_order_id(Integer product_order_id) {
	this.product_order_id = product_order_id;
}
public String getOrder_made_at() {
	return order_made_at;
}
public void setOrder_made_at(String order_made_at) {
	this.order_made_at = order_made_at;
}
public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}
public String getEmail_ids() {
	return email_ids;
}
public void setEmail_ids(String email_ids) {
	this.email_ids = email_ids;
}
public List<itemBean> getPer_order_list() {
	return per_order_list;
}
public void setPer_order_list(List<itemBean> per_order_list) {
	this.per_order_list = per_order_list;
}



}
