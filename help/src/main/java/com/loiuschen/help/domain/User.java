package com.loiuschen.help.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Entity
@Table(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = -563756687305585492L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "pid")
	private String pid;
	@Column(name = "id")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "cellphone")
	private String cellphone;
	@Column(name = "securityCode")
	private String securityCode;
	@Column(name = "email")
	private String email;
	@Column(name = "author")
	private Integer author = 1;
	@Column(name = "password")
	private String password;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAuthor() {
		return author;
	}
	public void setAuthor(Integer author) {
		this.author = author;
	}
	
	@Override
	public String toString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("id", id);
		map.put("name", name);
		map.put("cellphone", cellphone);
		map.put("securityCode", securityCode);
		map.put("email", email);
		map.put("author", author);
		JsonConfig sc = new JsonConfig();
		return JSONObject.fromObject(map, sc).toString();
	}
}
