package br.com.zssn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zssn.entity.Survivor;

/**
 * 
 * @author wesleyaraujo
 *
 */

@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long> {

}
