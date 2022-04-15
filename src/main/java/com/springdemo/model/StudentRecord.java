package com.springdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "students" )
public class StudentRecord {

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	@Id
	private int rollNo;
	private int contactNo;
	private String name;
	
	public StudentRecord(int rollNo, String name,int contactNo) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.contactNo=contactNo;
	}
	
	public StudentRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getContactNo() {
		return contactNo;
	}
	public void setContactNo(int contactNo) {
		this.contactNo = contactNo;
	}
	
	public int getRollNo() {
		return rollNo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
