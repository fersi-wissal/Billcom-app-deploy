package com.billcom.app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.billcom.app.dto.TeamDto;
import com.billcom.app.dto.count.CountDto;
import com.billcom.app.dto.count.DetailKPI;
import com.billcom.app.entity.Task;
import com.billcom.app.entity.Team;
import com.billcom.app.entity.TeamMember;
import com.billcom.app.entity.UserApp;
import com.billcom.app.repository.TaskRepository;
import com.billcom.app.repository.TeamRepository;
import com.billcom.app.security.SecurityUtils;

@Service
public class MemberDashboardService {
	private TaskRepository taskRepository;
	private TeamRepository teamRepository;
	private SecurityUtils securityUtils;

	public MemberDashboardService(TaskRepository taskRepository, TeamRepository teamRepository,
			SecurityUtils securityUtils) {
		this.taskRepository = taskRepository;
		this.securityUtils = securityUtils;
		this.teamRepository = teamRepository;
	}

	public UserApp getCurrentUser() {
		return securityUtils.getLoggedUser();
	}

	/**
	 * 
	 * @return List of team that member is a part of them
	 */
	public List<TeamDto> teamOfTeamMember() {
		UserApp user = getCurrentUser();
		List<Team> listTeam = teamRepository.findAll();
		List<TeamDto> list = new ArrayList<>();
		for (Team t : listTeam) {
			for (TeamMember teamMember : t.getTeamMember()) {
				if (teamMember.getUser().getId() == user.getId()) {

					list.add(new TeamDto(t.getTeamName(), t.getLeader().getUser().getId(), teamMember,t.getId()));
				}
			}
		}
		return list;

	}

	/**
	 * count number of tasks done from team for everey team
	 * 
	 * @return number of task done for every team
	 */

	public List<DetailKPI> kpiTeamofTeamMember() {
		List<TeamDto> teamDtoList = teamOfTeamMember();
		List<DetailKPI> list = new ArrayList<>();
		for (TeamDto t : teamDtoList) {
			List<CountDto> test = new ArrayList<>();

			List<Task> taskList = taskRepository.findAllByTeamMember(t.getTeamMember());
			long nbTask = taskList.size();
			test.add(new CountDto("Nb task", nbTask));

			long nbdelayed = taskList.stream().filter(task -> (task.getDeleveryDate().isBefore(LocalDateTime.now())))
					.filter(task -> !(task.getStatus().getStatusDescritpion().equalsIgnoreCase("done"))).count();

			test.add(new CountDto("task delaed", nbdelayed));
			list.add(new DetailKPI(t.getTeamName(), test));

			long nbdoneAdvanced = taskList.stream()
					.filter(task -> (task.getDeleveryDate().isAfter(LocalDateTime.now())))
					.filter(task -> (task.getStatus().getStatusDescritpion().equalsIgnoreCase("done"))).count();

			test.add(new CountDto("task done advanced", nbdoneAdvanced));

		}
		return list;
	}

	
	public List<CountDto> delayRateTeamMember(){
		List<CountDto> listDelayRateTeamMember = new ArrayList<>();
		for (TeamDto t :teamOfTeamMember()  ) {
			
			float nb;
			float nbb;
			List<Task> taskTodo = taskRepository.findAllByTeamMember(t.getTeamMember()).stream()
					.filter(task -> (task.getDeleveryDate().isBefore(LocalDateTime.now()))).collect(Collectors.toList());

			nb = taskRepository.findAllByTeamMember(t.getTeamMember()).stream()
					.filter(task -> (task.getDeleveryDate().isBefore(LocalDateTime.now()))).count();

			nbb = taskTodo.stream().filter(task -> !(task.getStatus().getStatusDescritpion().equalsIgnoreCase("done")))
					.count();
            if(nbb == 0) {
			listDelayRateTeamMember.add( new CountDto(t.getTeamName(), 0));}
            else {
				listDelayRateTeamMember.add( new CountDto(t.getTeamName(), (nbb / nb) * 100));
			}
            
			
		}
		
		
		return listDelayRateTeamMember;
	}
	
	
	
}
