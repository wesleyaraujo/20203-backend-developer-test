package br.com.zssn.web.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zssn.entity.Position;
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

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Survivor createSurvivor(@RequestBody Survivor survivor) {
		log.info("process=create-survivor, name={}", survivor.getName());
		return survivorService.createSurvivor(survivor);
	}

	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Survivor> updateLocation(@PathVariable Long id, @RequestBody Position newPosition) {
		Survivor updateLocation = survivorService.updateLocation(id, newPosition);
		if (Objects.nonNull(updateLocation)) {
			return ResponseEntity.ok(updateLocation);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
