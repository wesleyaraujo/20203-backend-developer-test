package br.com.zssn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zssn.entity.ResourcesSurvivor;

/**
 * @author Wesley Araujo
 *
 */

@Repository
public interface ResourcesSurvivorRepository extends JpaRepository<ResourcesSurvivor, Long> {

}
