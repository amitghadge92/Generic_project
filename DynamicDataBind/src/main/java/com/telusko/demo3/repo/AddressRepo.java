package com.telusko.demo3.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.telusko.demo3.model.Address;

@Repository
public interface AddressRepo extends CrudRepository<Address, String>{

}
