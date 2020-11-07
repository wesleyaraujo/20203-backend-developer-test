package br.com.zssn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import br.com.zssn.entity.Position;
import br.com.zssn.entity.Resource;
import br.com.zssn.entity.ResourcesSurvivor;
import br.com.zssn.entity.Survivor;
import br.com.zssn.repo.ResourceRepository;
import br.com.zssn.repo.ResourcesSurvivorRepository;
import br.com.zssn.repo.SurvivorRepository;
import br.com.zssn.util.ResultOperation;
import br.com.zssn.util.Trade;
import br.com.zssn.util.TradeItem;

/**
 * 
 * @author wesleyaraujo
 *
 */
@Service
@Transactional
public class SurvivorService {

	private final String MESSAGE_SUCESS = "Operação realizada com sucesso.";
	private final String MESSAGE_ERROR = "Operação falhou.";

	@Autowired
	private SurvivorRepository survivorRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	@Autowired
	private ResourcesSurvivorRepository resourcesSurvivorRepository;

	public ResultOperation createSurvivor(Survivor survivor) {
		try {
			Survivor survivorDB = survivorRepository.save(survivor);
			survivor.getResources().stream().forEach(r -> {
				r.setSurvivor(survivorDB);
				resourcesSurvivorRepository.save(r);
			});
			ResultOperation result = new ResultOperation();
			result.setStatus(HttpStatus.OK);
			result.setMessage(MESSAGE_SUCESS);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			ResultOperation result = new ResultOperation();
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			result.setMessage(MESSAGE_ERROR);
			return result;
		}

	}

	public Survivor updateSurvivor(Survivor survivor) {
		Survivor survivorDB = survivorRepository.save(survivor);
		survivor.getResources().stream().forEach(r -> {
			r.setSurvivor(survivorDB);
			resourcesSurvivorRepository.save(r);
		});

		return survivor;
	}

	public ResultOperation updateLocation(Long survivorId, Position newPosition) {
		try {
			if (Objects.nonNull(survivorId)) {
				Optional<Survivor> survivorDB = survivorRepository.findById(survivorId);
				Survivor survivor = survivorDB.map(u -> u).orElse(null);
				if (Objects.nonNull(survivor)) {
					survivor.setLastPosition(newPosition);
					survivorRepository.save(survivor);
				}
			}

			ResultOperation result = new ResultOperation();
			result.setStatus(HttpStatus.OK);
			result.setMessage(MESSAGE_SUCESS);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			ResultOperation result = new ResultOperation();
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			result.setMessage(MESSAGE_ERROR);
			return result;
		}
	}

	public ResultOperation informInfected(Long survivorInfectedId) {
		try {
			if (Objects.nonNull(survivorInfectedId)) {
				Optional<Survivor> survivorDB = survivorRepository.findById(survivorInfectedId);
				Survivor survivor = survivorDB.map(u -> u).orElse(null);
				if (Objects.nonNull(survivor)) {
					if (Objects.nonNull(survivor.getCountInfected())) {
						survivor.setCountInfected(survivor.getCountInfected() + 1);
					} else {
						survivor.setCountInfected(1);
					}

					if (survivor.getCountInfected() >= 3) {
						survivor.setInfected(true);
					}

					survivorRepository.save(survivor);
				}
			}
			ResultOperation result = new ResultOperation();
			result.setStatus(HttpStatus.OK);
			result.setMessage(MESSAGE_SUCESS);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			ResultOperation result = new ResultOperation();
			result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			result.setMessage(MESSAGE_ERROR);
			return result;
		}
	}

	public ResultOperation tradeResources(Trade survivorFrom, Trade survivorTo) {
		ResultOperation result = new ResultOperation();
		Survivor survivorFromDB = survivorRepository.findById(survivorFrom.getSurvivorId()).map(s -> s).orElse(null);
		Survivor survivorToDB = survivorRepository.findById(survivorTo.getSurvivorId()).map(s -> s).orElse(null);
		if (validateSurvivorIsInfected(survivorFromDB) || validateSurvivorIsInfected(survivorToDB)) {
			result.setStatus(HttpStatus.BAD_REQUEST);
			result.setMessage("Alguem esta infectado e não pode fazer troca de recursos.");
			return result;
		}
		survivorFrom.getItems().stream().forEach(i -> {
			i.setResource(resourceRepository.findById(i.getResource().getId()).map(r -> r).orElse(null));
		});

		survivorTo.getItems().stream().forEach(i -> {
			i.setResource(resourceRepository.findById(i.getResource().getId()).map(r -> r).orElse(null));
		});

		if (validatePointsEqualsToTradeResources(survivorFrom.getItems(), survivorTo.getItems())) {
			trade(survivorFromDB, survivorFrom.getItems(), survivorToDB, survivorTo.getItems());
		}
		result.setStatus(HttpStatus.OK);
		result.setMessage(MESSAGE_SUCESS);
		return result;

	}

	private void trade(Survivor survivorFrom, List<TradeItem> itemsFrom, Survivor survivorTo, List<TradeItem> itemsTo) {
		trade(survivorFrom, itemsTo);
		survivorTo.getResources().stream().forEach(r -> {
			TradeItem tradeItemTo = itemsTo.stream()
					.filter(t -> t.getResource().getId().equals(r.getResource().getId())).findFirst().orElse(null);
			if (Objects.nonNull(tradeItemTo)) {
				r.setQuantity(r.getQuantity() - tradeItemTo.getQuantity());
			}
		});

		trade(survivorTo, itemsFrom);
		survivorFrom.getResources().stream().forEach(r -> {
			TradeItem tradeItemTo = itemsFrom.stream()
					.filter(t -> t.getResource().getId().equals(r.getResource().getId())).findFirst().orElse(null);
			if (Objects.nonNull(tradeItemTo)) {
				r.setQuantity(r.getQuantity() - tradeItemTo.getQuantity());
			}
		});
	}

	private void trade(Survivor survivor, List<TradeItem> items) {
		if (CollectionUtils.isEmpty(survivor.getResources())) {
			List<ResourcesSurvivor> itemsResources = new ArrayList<ResourcesSurvivor>();
			ResourcesSurvivor resourcesSurvivor = null;
			for (TradeItem i : items) {
				resourcesSurvivor = new ResourcesSurvivor();
				resourcesSurvivor.setResource(i.getResource());
				resourcesSurvivor.setQuantity(i.getQuantity());
				itemsResources.add(resourcesSurvivor);
			}
			survivor.setResources(itemsResources);
		} else {
			for (TradeItem tradeItem : items) {
				if (hasResource(survivor, tradeItem.getResource())) {
					ResourcesSurvivor resourceSurvivor = survivor.getResources().stream()
							.filter(r -> r.getResource().getId().equals(tradeItem.getResource().getId())).findFirst()
							.orElse(null);
					resourceSurvivor.setQuantity(resourceSurvivor.getQuantity() + tradeItem.getQuantity());
				} else {
					ResourcesSurvivor resourceSurvivor = new ResourcesSurvivor();
					resourceSurvivor.setResource(tradeItem.getResource());
					resourceSurvivor.setQuantity(tradeItem.getQuantity());
					survivor.getResources().add(resourceSurvivor);
				}
			}

		}
		this.updateSurvivor(survivor);
	}

	private boolean hasResource(Survivor survivor, Resource resource) {
		return survivor.getResources().stream().filter(r -> r.getResource().getId().equals(resource.getId()))
				.count() > 0;
	}

	private boolean validatePointsEqualsToTradeResources(List<TradeItem> from, List<TradeItem> to) {

		int totalFrom = 0;
		for (TradeItem item : from) {
			totalFrom += item.getResource().getPoint() * item.getQuantity();
		}

		int totalTo = 0;
		for (TradeItem item : to) {
			totalTo += item.getResource().getPoint() * item.getQuantity();
		}

		return totalFrom == totalTo;
	}

	private boolean validateSurvivorIsInfected(Survivor survivor) {
		return survivor.isInfected();
	}

}
