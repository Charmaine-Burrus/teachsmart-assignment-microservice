package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.TSAssignment;
import com.example.service.AssignmentService;

@CrossOrigin
@RestController
public class AssignmentController {
	
	@Autowired
	private AssignmentService service;
	
//	@RequestMapping(path="/addAssignmentData")
//	public void addAssignmentData() {
//		service.saveAssignmentData();
//	}
	
	//in real life
	@RequestMapping(value="/addAssignmentResults",
//			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.MULTIPART_FORM_DATA_VALUE,
			method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TSAssignment> addAssignmentResults(@RequestParam("file") MultipartFile file){
		//TODO this will actually be null.. so get rid of everything besides service.intializeTable(file);
		TSAssignment thisAssignment = service.intializeTable(file);
		if (thisAssignment != null) {
			return new ResponseEntity<>(thisAssignment, HttpStatus.OK);
		}
		return null;
	}


}
