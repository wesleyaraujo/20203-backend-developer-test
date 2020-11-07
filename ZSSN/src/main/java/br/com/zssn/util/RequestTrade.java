package br.com.zssn.util;

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
public class RequestTrade {
	private Trade tradeFrom;
	private Trade tradeTo;
}
