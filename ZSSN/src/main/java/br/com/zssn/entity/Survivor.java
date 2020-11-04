package br.com.zssn.entity;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zssn.enuns.Gender;
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
@Table(name = "survivors")
public class Survivor {

	@Id
	@SequenceGenerator(name = "survivor_id_generator", sequenceName = "survivor_id_seq")
	@GeneratedValue(generator = "survivor_id_generator")
	private Long id;

	@Column(nullable = false)
	private String name;

	private LocalDateTime birthdate;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "latitude", column = @Column(name = "last_latitude")),
			@AttributeOverride(name = "longitude", column = @Column(name = "last_longitude")) })
	private Position lastPosition;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	void preSave() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
	}

	@PreUpdate
	void preUpdate() {
		if (updatedAt == null) {
			updatedAt = LocalDateTime.now();
		}
	}

}
