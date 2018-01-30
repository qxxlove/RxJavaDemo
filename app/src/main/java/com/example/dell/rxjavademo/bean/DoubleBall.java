package com.example.dell.rxjavademo.bean;

import java.io.Serializable;

public class DoubleBall implements Serializable {
	private static final long serialVersionUID = 1L;
	private String redBall;
	private String blueBall;
	private int position;
	private boolean check;

	public DoubleBall() {
	}

	public DoubleBall(String redBall, String blueBall, int position, boolean check) {
		this.redBall = redBall;
		this.blueBall = blueBall;
		this.position = position;
		this.check = check;
	}

	public String getRedBall() {
		return redBall;
	}
	
	public void setRedBall(String redBall) {
		this.redBall = redBall;
	}

	public String getBlueBall() {
		return blueBall;
	}

	public void setBlueBall(String blueBall) {
		this.blueBall = blueBall;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
