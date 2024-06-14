package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class SearchReq {

	private String name;

	@JsonProperty("start_date")
	private LocalDate startDate;

	@JsonProperty("end_date")
	private LocalDate endDate;

	public String getName() {
		return name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

}
