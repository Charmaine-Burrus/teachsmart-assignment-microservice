package com.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Response;
import com.example.model.ResponseGroup;
import com.example.model.TSAssignment;
import com.example.repository.AssignmentRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class AssignmentService {
	
	@Autowired
	private AssignmentRepository assignmentRepo;
	
	String line = "";
	
	//TODO: this will actually be void
	private TSAssignment parseCSV(TSAssignment thisAssignment, MultipartFile file) {
		CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
		try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream())).withCSVParser(parser).build()) {
			List<String[]> allData = csvReader.readAll();
			
			//go through the header... set a ResponseGroup with Header for each
			for (String cell : allData.get(0)) {
				ResponseGroup thisGroup = new ResponseGroup();
				thisGroup.setHeader(cell);
				//there's nothing in this group yet though.. so we'll have to get it by it's index
				thisAssignment.getResponseGroups().add(thisGroup);
			}
			
			//go through the rows besides header
			for (int i=1; i<allData.size(); i++) {
				String[] thisRow = allData.get(i);
				for (int j=1; j<thisRow.length; j++) {
					//make a response for the cell
					String thisCell = thisRow[j];
					Response thisResponse = new Response();
					thisResponse.setAnswer(thisCell);
					//add it to the correct ResponseGroup
					thisAssignment.getResponseGroups().get(j).getResponses().add(thisResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		for (ResponseGroup group : thisAssignment.getResponseGroups()) {
//			System.out.println(group);
//		}
		return thisAssignment;
	}
	

	public TSAssignment intializeTable(MultipartFile file) {
		TSAssignment assignment1 = new TSAssignment();
		//won't need to actually do this (will be taking in a TSAssignment... will need to get file from it
		assignment1.setAssignmentName("Unit 0 Quiz - Testing");
		assignment1.setResponseUrl("https://docs.google.com/spreadsheets/d/1mFri2MssuA_sCCP93nz5zUtdiF_v1QdNtSmH9nrEmlA/edit#gid=0");
		assignment1.setTotalPoints(17);
		assignmentRepo.save(assignment1);
		
		TSAssignment savedAssignment = assignmentRepo.save(assignment1);
		 if(savedAssignment != null){
			 //TODO: undo this as it should actulaly be null
			 TSAssignment thisAssignment = parseCSV(savedAssignment, file);
			 return thisAssignment;
		 }
		return null;
	}
	
	//get percentage of students with each grade
	//will need to get score: fraction -> percentage
	//switch within a certain range, add to that letter grade
	//return array of grades and matching array of percentages
	

}
