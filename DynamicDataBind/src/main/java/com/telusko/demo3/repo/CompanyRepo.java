package com.telusko.demo3.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo3.model.Company;

@Repository
public interface CompanyRepo extends CrudRepository<Company, String>{

}
