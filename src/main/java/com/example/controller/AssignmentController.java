package com.example.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.dto.AssignmentDTO;
import com.example.model.TSAssignment;
import com.example.service.AssignmentService;

@CrossOrigin
@RestController
public class AssignmentController {
	
	@Autowired
	private AssignmentService service;
	
	@RequestMapping(value="/addAssignmentResults",
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

	//just for testing
	@RequestMapping(value="/getAssignmentAnalysis",
			method=RequestMethod.GET)
	public void getAssignmentAnalysis(){
		Optional<TSAssignment> thisAssignment = service.findById((long)1699);
		if (thisAssignment.isPresent()) {
			TSAssignment realAssignment = thisAssignment.get();
			service.representingController(realAssignment);
		}
		else {
			System.out.println("Assignment not found");
		};
	}
	
	//in TS, use something similar to this to get all the assignments for ONE USER
	@RequestMapping(value="/findAllAssignments",
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.GET)
	@ResponseBody
	private ResponseEntity<List<AssignmentDTO>>findAllAssignments() {
		return new ResponseEntity<>(service.getAssignmentDTOList(), HttpStatus.OK);
	}

}
