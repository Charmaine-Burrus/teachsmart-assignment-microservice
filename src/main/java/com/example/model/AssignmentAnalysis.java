package com.example.model;

import java.util.ArrayList;
import java.util.Arrays;

public class AssignmentAnalysis {
	
	//x
	private double totalPoints;
	//x
	private double averageScore;
	//x
	private double highestScore;
	//x
	private double lowestScore;
	//x
	private ArrayList<String> highestScoringStudents;
	private ArrayList<String> highestScoringClasses;
	
	//x
	ArrayList<Double> scorePercentages;
	
	private String[] classes;
	private double[] scorePercentagesByClass;
	
	//x
	ArrayList<Integer> portionOfScoresByPercentages;

	public double getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public double getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(double highestScore) {
		this.highestScore = highestScore;
	}

	public double getLowestScore() {
		return lowestScore;
	}

	public void setLowestScore(double lowestScore) {
		this.lowestScore = lowestScore;
	}

	public ArrayList<String> getHighestScoringStudents() {
		return highestScoringStudents;
	}

	public void setHighestScoringStudents(ArrayList<String> highestScoringStudents) {
		this.highestScoringStudents = highestScoringStudents;
	}

	public ArrayList<String> getHighestScoringClasses() {
		return highestScoringClasses;
	}

	public void setHighestScoringClasses(ArrayList<String> highestScoringClasses) {
		this.highestScoringClasses = highestScoringClasses;
	}

	public ArrayList<Double> getScorePercentages() {
		return scorePercentages;
	}

	public void setScorePercentages(ArrayList<Double> scorePercentages) {
		this.scorePercentages = scorePercentages;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	public double[] getScorePercentagesByClass() {
		return scorePercentagesByClass;
	}

	public void setScorePercentagesByClass(double[] scorePercentagesByClass) {
		this.scorePercentagesByClass = scorePercentagesByClass;
	}

	public ArrayList<Integer> getPortionOfScoresByPercentages() {
		return portionOfScoresByPercentages;
	}

	public void setPortionOfScoresByPercentages(ArrayList<Integer> portionOfScoresByPercentages) {
		this.portionOfScoresByPercentages = portionOfScoresByPercentages;
	}

	@Override
	public String toString() {
		return "AssignmentAnalysis [totalPoints=" + totalPoints + ", averageScore=" + averageScore + ", highestScore="
				+ highestScore + ", lowestScore=" + lowestScore + ", highestScoringStudents=" + highestScoringStudents
				+ ", highestScoringClasses=" + highestScoringClasses + ", scorePercentages=" + scorePercentages
				+ ", classes=" + Arrays.toString(classes) + ", scorePercentagesByClass="
				+ Arrays.toString(scorePercentagesByClass) + ", portionOfScoresByPercentages="
				+ portionOfScoresByPercentages + "]";
	}

}
