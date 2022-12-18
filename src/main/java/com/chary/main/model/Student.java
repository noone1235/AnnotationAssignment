package com.chary.main.model;

import com.chary.main.annotaions.ChangeName;

public class Student {
	private int rollNo;
	@ChangeName(value="nickName")
	private String name;
	private int classNo;
	private String marks;
	public int getRollNo() {
		return rollNo;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getClassNo() {
		return classNo;
	}
	public void setClassNo(int classNo) {
		this.classNo = classNo;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public Student(int rollNo, String name, int classNo, String marks) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.classNo = classNo;
		this.marks = marks;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
