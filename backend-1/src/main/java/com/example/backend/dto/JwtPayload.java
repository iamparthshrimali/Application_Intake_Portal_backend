package com.example.backend.dto;

public class JwtPayload {
    private String sub;
    private String authorities;
    private long iat;
    private long exp;
	public JwtPayload() {
		super();
	}
	public JwtPayload(String sub, String authorities, long iat, long exp) {
		super();
		this.sub = sub;
		this.authorities = authorities;
		this.iat = iat;
		this.exp = exp;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public long getIat() {
		return iat;
	}
	public void setIat(long iat) {
		this.iat = iat;
	}
	public long getExp() {
		return exp;
	}
	public void setExp(long exp) {
		this.exp = exp;
	}
	@Override
	public String toString() {
		return "JwtPayload [sub=" + sub + ", authorities=" + authorities + ", iat=" + iat + ", exp=" + exp + "]";
	}

	
}
