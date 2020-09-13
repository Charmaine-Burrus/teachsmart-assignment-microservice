package com.example.model;

import java.util.Arrays;

public class AssignmentAnalysis {
	
	private double totalPoints;
	private double averageScore;
	private double highestScore;
	private double lowestScore;
	private String[] highestScoringStudents;
	private String[] highestScoringClasses;
	
	private double[] scorePercentages;
	
	private String[] classes;
	private double[] scorePercentagesByClass;
	
	private double[] portionOfScoresByPercentages;

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

	public String[] getHighestScoringStudents() {
		return highestScoringStudents;
	}

	public void setHighestScoringStudents(String[] highestScoringStudents) {
		this.highestScoringStudents = highestScoringStudents;
	}

	public String[] getHighestScoringClasses() {
		return highestScoringClasses;
	}

	public void setHighestScoringClasses(String[] highestScoringClasses) {
		this.highestScoringClasses = highestScoringClasses;
	}

	public double[] getScorePercentages() {
		return scorePercentages;
	}

	public void setScorePercentages(double[] scorePercentages) {
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

	public double[] getPortionOfScoresByPercentages() {
		return portionOfScoresByPercentages;
	}

	public void setPortionOfScoresByPercentages(double[] portionOfScoresByPercentages) {
		this.portionOfScoresByPercentages = portionOfScoresByPercentages;
	}

	@Override
	public String toString() {
		return "AssignmentAnalysis [totalPoints=" + totalPoints + ", averageScore=" + averageScore + ", highestScore="
				+ highestScore + ", lowestScore=" + lowestScore + ", highestScoringStudents="
				+ Arrays.toString(highestScoringStudents) + ", highestScoringClasses="
				+ Arrays.toString(highestScoringClasses) + ", scorePercentages=" + Arrays.toString(scorePercentages)
				+ ", classes=" + Arrays.toString(classes) + ", scorePercentagesByClass="
				+ Arrays.toString(scorePercentagesByClass) + ", portionOfScoresByPercentages="
				+ Arrays.toString(portionOfScoresByPercentages) + "]";
	}

}
