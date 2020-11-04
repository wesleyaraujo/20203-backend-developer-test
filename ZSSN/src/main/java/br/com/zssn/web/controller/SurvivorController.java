package br.com.zssn.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zssn.entity.Survivor;
import br.com.zssn.service.SurvivorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/survivors")
@Slf4j
public class SurvivorController {

	private final SurvivorService survivorService;

	@Autowired
	public SurvivorController(SurvivorService survivorService) {
		this.survivorService = survivorService;
	}

	@GetMapping("")
	public List<Survivor> getUsers() {
		log.info("process=get-survivors");
		return survivorService.getAllSurvivors();
	}

}
