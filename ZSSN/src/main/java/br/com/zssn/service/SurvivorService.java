package br.com.zssn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zssn.entity.Survivor;
import br.com.zssn.repo.SurvivorRepository;

/**
 * 
 * @author wesleyaraujo
 *
 */
@Service
@Transactional
public class SurvivorService {

	private final SurvivorRepository survivorRepository;

	public SurvivorService(SurvivorRepository survivorRepository) {
		this.survivorRepository = survivorRepository;
	}

	public Optional<Survivor> getSurvivorById(Long id) {
		return survivorRepository.findById(id);
	}

	public List<Survivor> getAllSurvivors() {
		return survivorRepository.findAll();
	}

	public Survivor createSurvivor(Survivor survivor) {
		return survivorRepository.save(survivor);
	}

	public Survivor updateSurvivor(Survivor survivor) {
		return survivorRepository.save(survivor);
	}

	public void deleteSurvivor(Long survivorId) {
		survivorRepository.deleteById(survivorId);
	}

}
