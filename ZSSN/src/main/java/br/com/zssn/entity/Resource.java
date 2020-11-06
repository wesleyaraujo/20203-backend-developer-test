package br.com.zssn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "resources")
public class Resource {

	@Id
	@SequenceGenerator(name = "resouces_id_generator", sequenceName = "resouces_id_seq")
	@GeneratedValue(generator = "resouces_id_generator")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Double point;
}
