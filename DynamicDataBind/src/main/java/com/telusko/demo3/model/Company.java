package com.telusko.demo3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Company {

	@Id
	@Column(name="companyId", columnDefinition = "VARCHAR(64)")
	private String companyId;
	private String CompanyName;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", CompanyName=" + CompanyName + "]";
	}

}
