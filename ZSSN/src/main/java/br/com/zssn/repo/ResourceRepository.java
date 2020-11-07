package br.com.zssn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zssn.entity.Resource;

/**
 * @author Wesley Araujo
 *
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
