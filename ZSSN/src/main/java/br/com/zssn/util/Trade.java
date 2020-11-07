package br.com.zssn.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Wesley Araujo
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {

	private Long survivorId;
	private List<TradeItem> items;
}
