package com.renan.models;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.renan.constants.TaskStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task {
	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String assignedTo;
	
	@NotBlank
	private TaskStatus status;
	
	private Date created;
	
	private Date updated;
	
	@JsonIgnore
	public String getStatusString() {
		return this.status != null ? this.status.toString() : null;
	}
}
