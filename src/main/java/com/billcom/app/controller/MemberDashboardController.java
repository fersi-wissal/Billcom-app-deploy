package com.billcom.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billcom.app.dto.count.CountDto;
import com.billcom.app.dto.count.DetailKPI;
import com.billcom.app.service.MemberDashboardService;

@RestController
@RequestMapping(value="memberDashboard")

public class MemberDashboardController {
	private MemberDashboardService memberDashboardService;

	
	public MemberDashboardController( MemberDashboardService memberDashboardService) {
		this.memberDashboardService = memberDashboardService;
	}
	@GetMapping("/kpiTeamofTeamMember")
	public List<DetailKPI> kpiTeamofTeamMember(){
		return memberDashboardService.kpiTeamofTeamMember();
}
	@GetMapping("/delayRateTeamofTeamMember")
	public List<CountDto> delayRateKpiTeamMember(){
		return memberDashboardService.delayRateTeamMember();
}
}
