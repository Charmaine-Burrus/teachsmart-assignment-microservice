package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class ResponseGroup {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@OneToMany(mappedBy="responseGroup")
	private List<Response> responses = new ArrayList<Response>();
	
	private String header;
	
	@ManyToOne
	private TSAssignment assignment;

	public TSAssignment getAssignment() {
		return assignment;
	}

	public void setAssignment(TSAssignment assignment) {
		this.assignment = assignment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "ResponseGroup [id=" + id + ", responses=" + responses + ", header=" + header + "]";
	}

}
