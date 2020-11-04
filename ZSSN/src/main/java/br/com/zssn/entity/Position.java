package br.com.zssn.entity;

import javax.persistence.Embeddable;

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
@Embeddable
public class Position {

	private String latitude;
	private String longitude;
}
