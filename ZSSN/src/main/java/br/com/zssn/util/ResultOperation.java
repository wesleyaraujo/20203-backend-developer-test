package br.com.zssn.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wesley Araujo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultOperation {
	private String message;
	private HttpStatus status;
}
