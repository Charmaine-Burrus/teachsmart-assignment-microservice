package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Response;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Long> {

}
