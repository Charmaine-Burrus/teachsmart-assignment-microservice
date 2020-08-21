package com.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.AssignmentDTO;
import com.example.model.Response;
import com.example.model.ResponseGroup;
import com.example.model.TSAssignment;
import com.example.repository.AssignmentRepository;
import com.example.repository.ResponseGroupRepository;
import com.example.repository.ResponseRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class AssignmentService {

	@Autowired
	private AssignmentRepository assignmentRepo;

	@Autowired
	private ResponseGroupRepository responseGroupRepo;

	@Autowired
	private ResponseRepository responseRepo;

	String line = "";

	// TODO: this will actually be void
	private TSAssignment parseCSV(TSAssignment thisAssignment, MultipartFile file) {
		CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
		try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
				.withCSVParser(parser).build()) {
			List<String[]> allData = csvReader.readAll();

			// go through the header... set a ResponseGroup with Header for each
			for (String cell : allData.get(0)) {
				ResponseGroup thisGroup = new ResponseGroup();
				thisGroup.setHeader(cell);
				thisGroup.setAssignment(thisAssignment);
				ResponseGroup savedGroup = responseGroupRepo.save(thisGroup);
				thisAssignment.getResponseGroups().add(savedGroup);
			}

			// go through the rows besides header
			for (int i = 1; i < allData.size(); i++) {
				String[] thisRow = allData.get(i);
				for (int j = 1; j < thisRow.length; j++) {
					// make a response for the cell
					String thisCell = thisRow[j];
					Response thisResponse = new Response();
					thisResponse.setAnswer(thisCell);
					thisResponse.setResponseGroup(thisAssignment.getResponseGroups().get(j));
					Response savedResponse = responseRepo.save(thisResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Response Groups: " + thisAssignment.getResponseGroups().size());
//		for (ResponseGroup group : thisAssignment.getResponseGroups()) {
//			System.out.println(group);
//		}
//		assignmentRepo.save(thisAssignment);
		System.out.println("Saved Assignment" + thisAssignment);
		return thisAssignment;
	}

	public TSAssignment intializeTable(MultipartFile file) {
		TSAssignment assignment1 = new TSAssignment();
		// won't need to actually do this (will be taking in a TSAssignment... will need
		// to get file from it
		assignment1.setAssignmentName("Unit 1 Quiz");
		assignment1.setResponseUrl(
				"https://docs.google.com/spreadsheets/d/1mFri2MssuA_sCCP93nz5zUtdiF_v1QdNtSmH9nrEmlA/edit#gid=0");
		assignment1.setTotalPoints(17);
		// assignmentRepo.save(assignment1);

		TSAssignment savedAssignment = assignmentRepo.save(assignment1);
		System.out.println("savedAssignment is: " + savedAssignment);
		if (savedAssignment != null) {
			// TODO: undo this as it should actulaly be null
			TSAssignment thisAssignment = parseCSV(savedAssignment, file);
			return assignmentRepo.findById(thisAssignment.getId()).get();
		}
		return null;
	}

	// get percentage of students with each grade
	// will need to get score: fraction -> percentage
	// switch within a certain range, add to that letter grade
	// return array of grades and matching array of percentages

	public void representingController(TSAssignment assignment) {
		HashMap<Double, ArrayList<String>> scorestoStudentsMap = getScoretoStudentsMap(assignment);
		Set<Double> numeratorsSet = scorestoStudentsMap.keySet();
		ArrayList<Double> numerators = new ArrayList<Double>();
		for (Double numerator : numeratorsSet) {
			numerators.add(numerator);
		}
		
		ArrayList<String> scoreStrings = getArrayListOfScoreString(assignment);
		ArrayList<Double> allScores = getArrayOfNumerators(scoreStrings);
		// REACT: Numerators will be values for Graph 3 --- //this one doesn't seems to be getting all the scoress.. this is because it's consolidating duplicates
		System.out.println("For Graph 3: " + allScores);
		
		double averageScore = getMeanAverage(allScores);
		double lowestScore = getLowestScore(numerators);
		double highestScore = getHighestScore(numerators);
		ArrayList<String> studentsWithHighestScore = scorestoStudentsMap.get(highestScore);
		// REACT: All of this is data for table
		System.out.println("averageScore: " + averageScore);
		System.out.println("lowestScore: " + lowestScore);
		System.out.println("highestScore: " + highestScore);
		System.out.println("studentsWithHighestScore: " + studentsWithHighestScore);

		ArrayList<Integer> percentByLetterGrade = getPercentByLetterGrade(assignment);
		// REACT: percentByLetterGrade is values for Graph 1 (labels are A,B,C,D,F)
		System.out.println("Graph 1 (labels are A,B,C,D,F): " + percentByLetterGrade);

		// get HashMap of average percent to Hr... will be graphed
		// use .getKeySet and find highest one... then get it's values... this is
		// winning class

	}

	public double getHighestScore(List<Double> numerators) {
		double highestScore = numerators.get(0);
		for (int i = 1; i < numerators.size(); i++) {
			double score = numerators.get(i);
			if (score > highestScore) {
				highestScore = score;
			}
		}
		return highestScore;
	}

	public double getLowestScore(List<Double> numerators) {
		double lowestScore = numerators.get(0);
		for (int i = 1; i < numerators.size(); i++) {
			double score = numerators.get(i);
			if (score < lowestScore) {
				lowestScore = score;
			}
		}
		return lowestScore;
	}
	
	public ArrayList<String> getArrayListOfScoreString(TSAssignment assignment) {
		ArrayList<String> scores = new ArrayList<String>();
		ResponseGroup scoresGroup = null;
		
		for (ResponseGroup group : assignment.getResponseGroups()) {
			if (group.getHeader().equalsIgnoreCase("Score")) {
				scoresGroup = group;
				break;
			}
		}
		for (int i=0; i<scoresGroup.getResponses().size(); i++) {
			scores.add(scoresGroup.getResponses().get(i).getAnswer());
		}
		return scores;
	}

	public HashMap<Double, ArrayList<String>> getScoretoStudentsMap(TSAssignment assignment) {
		//TODO: use getArrayListOfScoreString(assignment) here to help
		HashMap<Double, ArrayList<String>> scorestoStudentsMap = new HashMap<Double, ArrayList<String>>();
		ResponseGroup students = null;
		ResponseGroup scores = null;

		for (ResponseGroup group : assignment.getResponseGroups()) {
			if (group.getHeader().equalsIgnoreCase("First Name")) {
				students = group;
			} else if (group.getHeader().equalsIgnoreCase("Score")) {
				scores = group;
			}
			if (scores != null & students != null) {
				break;
			}
		}

		for (int i = 0; i < students.getResponses().size(); i++) {
			double numerator = getNumeratorForScore(scores.getResponses().get(i).getAnswer());
			ArrayList<String> currStudents = scorestoStudentsMap.get(numerator);
			if (currStudents == null) {
				currStudents = new ArrayList<String>();
			}
			if(students.getResponses() != null && students.getResponses().size()> 0 && students.getResponses().get(i).getAnswer() != null) {
				currStudents.add(students.getResponses().get(i).getAnswer());
			}
			scorestoStudentsMap.put(numerator, currStudents);
		}
		return scorestoStudentsMap;
	}

	public double getNumeratorForScore(String score) {
		String numerator = score.substring(0, score.indexOf('/'));
		return Double.parseDouble(numerator);
	}
	
	public ArrayList<Double> getArrayOfNumerators(ArrayList<String> scores) { 
		ArrayList<Double> numerators = new ArrayList<Double>(); 
		for (int i=0; i<scores.size(); i++) { 
			numerators.add(getNumeratorForScore(scores.get(i))); 	
		} 
		return numerators; 		
	}

	/*
	 * public HashMap<Long, String> getAverageToHourMap(TSAssignment assignment){
	 * //TO DO: I DID THIS WRONG... INSTEAD DO THIS: //need get hr to scores
	 * (similar to students to numerators) //then change to hr to percents (convert
	 * each score to percent) //then get average to hr (for each array of percents
	 * get an average (using method below) and map the hr to it)
	 * 
	 * 
	 * ArrayList<String> scores = new ArrayList<String>(); for (ResponseGroup group
	 * : assignment.getResponseGroups()) { if
	 * (group.getHeader().equalsIgnoreCase("Score")) { ResponseGroup scoresGroup =
	 * group; for (Response response : scoresGroup.getResponses()) {
	 * scores.add(response.getAnswer()); } } }
	 * 
	 * //now get hr to percent... then get average for hr HashMap<String,
	 * ArrayList<Long>> HourToScorePercentsMap = new HashMap<String,
	 * ArrayList<Long>>(); ArrayList<Long> percents = new ArrayList<Long>(); for
	 * (String score : scores) { percents.add(getPercentForScore(score)); }
	 * 
	 * }
	 */

	public ArrayList<Integer> getPercentByLetterGrade(TSAssignment assignment) {
		ArrayList<String> scores = new ArrayList<String>();
		for (ResponseGroup group : assignment.getResponseGroups()) {
			if (group.getHeader().equalsIgnoreCase("Score")) {
				ResponseGroup scoresGroup = group;
				for (Response response : scoresGroup.getResponses()) {
					scores.add(response.getAnswer());
				}
			}
		}

		// get number of students with each letter grade - HashMap
		String[] letterGrades = { "A", "B", "C", "D", "F" };
		HashMap<String, Integer> letterGradeToCountMap = new HashMap<String, Integer>();
		for (int i = 0; i < letterGrades.length; i++) {
			letterGradeToCountMap.put(letterGrades[i], 0);
		}

		int totalCount = 0;
		for (String score : scores) {
			long percent = getPercentForScore(score);
			if (percent > 100 || percent < 0) {
				continue;
			} else {
				totalCount++;
			}
			if (percent < 60) {
				int currCount = letterGradeToCountMap.get("F");
				currCount++;
				letterGradeToCountMap.put("F", currCount);
			} else if (percent < 70) {
				int currCount = letterGradeToCountMap.get("D");
				currCount++;
				letterGradeToCountMap.put("D", currCount);
			} else if (percent < 80) {
				int currCount = letterGradeToCountMap.get("C");
				currCount++;
				letterGradeToCountMap.put("C", currCount);
			} else if (percent < 90) {
				int currCount = letterGradeToCountMap.get("B");
				currCount++;
				letterGradeToCountMap.put("B", currCount);
			} else {
				int currCount = letterGradeToCountMap.get("A");
				currCount++;
				letterGradeToCountMap.put("A", currCount);
			}
		}

		// for each key/value of Hashmap, transfer to an array of Percentages
		ArrayList<Integer> percentages = new ArrayList<Integer>();
		for (int i = 0; i < letterGrades.length; i++) {
			int percentWithScore = (int) getPercentForScore(
					letterGradeToCountMap.get(letterGrades[i]) + "/" + totalCount);
			percentages.add(percentWithScore);
		}
		return percentages;
	}

	public long getPercentForScore(String score) {
		String numerator = score.substring(0, score.indexOf('/'));
		String denominator = score.substring(score.indexOf('/') + 1);
		double numeratorInt = Double.parseDouble(numerator);
		double denominatorInt = Double.parseDouble(denominator);
		long scorePercent = Math.round((numeratorInt / denominatorInt) * 100);
		return scorePercent;
	}
	
	public double getMeanAverage(ArrayList<Double> allScores) {
		double total = 0;
		int count = 0;
		for (double score : allScores) {
			total += score;
			count++;
		}
		return Math.round((total/count)*100.0) / 100.0;
	}

	public Optional<TSAssignment> findById(Long id) {
		return assignmentRepo.findById(id);
	}
	
	public List<TSAssignment> findAllAssignments() {
		return assignmentRepo.findAll();
	}

	public List<AssignmentDTO> getAssignmentDTOList(){
		return findAllAssignments()
				.stream()
				.map(assignment -> {
					AssignmentDTO dto = new AssignmentDTO();
					dto.setId(assignment.getId());
					dto.setName(assignment.getAssignmentName());
					return dto;
				})
				.collect(Collectors.toList());
	}
}
