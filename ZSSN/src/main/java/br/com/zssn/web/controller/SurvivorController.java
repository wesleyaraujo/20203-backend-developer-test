package br.com.zssn.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.zssn.util.RequestTrade;
import br.com.zssn.util.ResultOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Wesley Araujo
 *
 */
@RestController
@RequestMapping("/api/survivors")
@Slf4j
public class SurvivorController {

	private final SurvivorService survivorService;

	@Autowired
	public SurvivorController(SurvivorService survivorService) {
		this.survivorService = survivorService;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResultOperation> createSurvivor(@RequestBody Survivor survivor) {
		log.info("process=create-survivor, name={}", survivor.getName());
		ResultOperation result = survivorService.createSurvivor(survivor);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResultOperation> updateLocation(@PathVariable Long id, @RequestBody Position newPosition) {
		log.info("process=update-location");
		ResultOperation result = survivorService.updateLocation(id, newPosition);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/infected/{survivorInfectedId}")
	public ResponseEntity<ResultOperation> informInfected(@PathVariable Long survivorInfectedId) {
		log.info("process=inform-infected");
		ResultOperation result = survivorService.informInfected(survivorInfectedId);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/trade")
	public ResponseEntity<ResultOperation> tradeResources(@RequestBody RequestTrade requestTrade) {
		log.info("process=trade-resources");
		ResultOperation result = survivorService.tradeResources(requestTrade.getTradeFrom(), requestTrade.getTradeTo());
		return ResponseEntity.ok(result);
	}

}
