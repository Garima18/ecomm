package com.programcreek.ecomm.bean;

public class itemBean {
private Integer item_id;
private String item_name;
private Integer stock_available;
private Integer status;
private String msg;
private Integer item_price;
private Integer quantity_left;

private Integer quantity;


public Integer getQuantity() {
	return quantity;
}
public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}
public Integer getQuantity_left() {
	return quantity_left;
}
public void setQuantity_left(Integer quantity_left) {
	this.quantity_left = quantity_left;
}
public Integer getItem_id() {
	return item_id;
}
public void setItem_id(Integer item_id) {
	this.item_id = item_id;
}
public Integer getItem_price() {
	return item_price;
}
public void setItem_price(Integer item_price) {
	this.item_price = item_price;
}
public String getItem_name() {
	return item_name;
}
public void setItem_name(String item_name) {
	this.item_name = item_name;
}


public Integer getStock_available() {
	return stock_available;
}
public void setStock_available(Integer stock_available) {
	this.stock_available = stock_available;
}
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


}
