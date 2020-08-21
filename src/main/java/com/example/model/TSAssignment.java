package com.example.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
public class TSAssignment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="assignment_name")
	private String assignmentName;
	
	//could make these URLs if there was any point to it
	@Column(name="sheet_url", unique = true)
	private String responseUrl;
	
	@Column(name="picture_url")
	private String formPictureUrl;
	
	@Column(name="total_points")
	private int totalPoints;
	
	@OneToMany(mappedBy = "assignment")
	private List<ResponseGroup> responseGroups = new ArrayList<>();
	
	@Transient
	private String accessToken;
	@Transient
	private boolean hasResponse;
	@Transient
	private File csv;
	
	public File getCsv() {
		return csv;
	}
	public void setCsv(File csv) {
		this.csv = csv;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	public boolean isHasResponse() {
		return hasResponse;
	}
	public void setHasResponse(boolean hasResponse) {
		this.hasResponse = hasResponse;
	}
	public String getResponseUrl() {
		return responseUrl;
	}
	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
	public String getFormPictureUrl() {
		return formPictureUrl;
	}
	public void setFormPictureUrl(String formPictureUrl) {
		this.formPictureUrl = formPictureUrl;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	public List<ResponseGroup> getResponseGroups() {
		return responseGroups;
	}
	public void setResponseGroups(List<ResponseGroup> responseGroups) {
		this.responseGroups = responseGroups;
	}
	@Override
	public String toString() {
		return "TSAssignment [id=" + id + ", assignmentName=" + assignmentName + ", responseUrl=" + responseUrl
				+ ", formPictureUrl=" + formPictureUrl + ", totalPoints=" + totalPoints + ", responseGroups="
				+ responseGroups + ", accessToken=" + accessToken + ", hasResponse=" + hasResponse + ", csv=" + csv
				+ "]";
	}
	
}
