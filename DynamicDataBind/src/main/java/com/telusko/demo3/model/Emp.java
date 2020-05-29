package com.telusko.demo3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Emp {

	@Id
	@Column(name = "empId", columnDefinition = "VARCHAR(64)")
	private String empId;
	private String empName;

	public Emp() {

	}

	public Emp(String empId, String empName) {
		this.empId = empId;
		this.empName = empName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "Emp [empId=" + empId + ", empName=" + empName + "]";
	}

	
}
