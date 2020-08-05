package com.example.model;

import javax.persistence.*;

@Entity
public class Response {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	private String answer;
	
	@ManyToOne
	private ResponseGroup responseGroup;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", answer=" + answer + "]";
	}

}
