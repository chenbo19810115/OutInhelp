package com.loiuschen.help.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "userreg")
public class UserReg implements Serializable{

	private static final long serialVersionUID = -4326812455320996054L;
	@Column(name = "id")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "cellphone")
	private String cellphone;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "securityCode")
	private String securityCode;
	@Column(name = "email")
	private String email;
	@Column(name = "bsendmail")
	private String bsendmail = "0";
	public String getBsendmail() {
		return bsendmail;
	}
	public void setBsendmail(String bsendmail) {
		this.bsendmail = bsendmail;
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
	
}
