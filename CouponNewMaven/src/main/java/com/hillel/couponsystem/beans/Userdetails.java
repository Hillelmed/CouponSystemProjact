package com.hillel.couponsystem.beans;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.hillel.couponsystem.enums.ClientType;

@XmlRootElement
@Component
public class Userdetails {

	private long id;
	private String email,password;
	private ClientType clienttype;
	private long comeback;
	

	public Userdetails() {
		this.id = -1;
	}
	
	public Userdetails(String email,String password,ClientType clienttype) {
		this.setEmail(email);
		this.setPassword(password);
		this.setClienttype(clienttype);
		this.id = -1;
	}
	
	public Userdetails(long id, String email, String password, ClientType clienttype, long comeback) {
		this.setId(id);
		this.setEmail(email);
		this.setPassword(password);
		this.setClienttype(clienttype);
		this.setComeback(comeback);;
	}

	public long getComeback() {
		return comeback;
	}
	
	public void setComeback(long comeback) {
		this.comeback = comeback;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ClientType getClienttype() {
		return clienttype;
	}
	public void setClienttype(ClientType clienttype) {
		this.clienttype = clienttype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Userdetails [getComeback()=" + getComeback() + ", getPassword()=" + getPassword() + ", getClienttype()="
				+ getClienttype() + ", getEmail()=" + getEmail() + ", getId()=" + getId() + "]";
	}
	
}
