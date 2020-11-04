package br.com.zssn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author wesleyaraujo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "resouces_survivor")
public class ResourcesSurvivor {

	@Id
	@SequenceGenerator(name = "resouces_survivor_id_generator", sequenceName = "resouces_survivor_id_seq")
	@GeneratedValue(generator = "resouces_survivor_id_generator")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "resource_id")
	private Resource resource;

	@Column(nullable = false)
	private Integer quantity;

}
