package com.dac.dto;

import jakarta.validation.constraints.NotNull;

public class TaskCreateDTO {

    @NotNull
    private String title;

    private String description;
    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	 public TaskCreateDTO() {}
	
	public TaskCreateDTO(@NotNull String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

}
