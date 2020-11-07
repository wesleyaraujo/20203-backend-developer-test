package br.com.zssn.util;

import br.com.zssn.entity.Resource;
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
public class TradeItem {

	private Resource resource;
	private Integer quantity;

}
