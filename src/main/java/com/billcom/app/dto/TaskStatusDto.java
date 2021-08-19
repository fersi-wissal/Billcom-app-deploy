package com.billcom.app.dto;

import java.util.List;

import com.billcom.app.entity.Task;

public class TaskStatusDto {
	private long id;
	private String status;
	private List<Task> taskStatusDto;
	


	public List<Task> getTaskStatusDto() {
		return taskStatusDto;
	}

	public void setTaskStatusDto(List<Task> taskStatusDto) {
		this.taskStatusDto = taskStatusDto;
	}

	public TaskStatusDto(long id, String status,  List<Task> taskStatusDto) {
		this.id = id;
		this.status = status;
		this.taskStatusDto = taskStatusDto;
	}

	public TaskStatusDto(String status, List<Task> taskk) {
		
		this.status = status;
		this.taskStatusDto = taskk;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
}
