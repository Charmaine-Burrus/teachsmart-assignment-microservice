package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ResponseGroup;

@Repository
public interface ResponseGroupRepository extends CrudRepository<ResponseGroup, Long> {

}
