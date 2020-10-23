package br.dev.rafaelnoleto.survivors.model.service;

import br.dev.rafaelnoleto.survivors.model.dao.ItemDao;
import br.dev.rafaelnoleto.survivors.model.entity.ItemEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author rafaelnoleto
 */
public class ItemService implements Service {

    @Inject
    private ItemDao itemDao;

    @Override
    public List validate(LinkedHashMap data) {
        List<String> errors = new ArrayList<>();

        if (data.get("description") == null || ((String) data.get("description")).length() > 100) {
            errors.add("O campo 'description' deve ser informado e deve ter no máximo 100 caracteres.");
        }

        Integer points = Utils.parseInt(data.get("points"));
        if ((points != null && (points > 10 || points < 0)) || points == null && data.get("points") != null) {
            errors.add("O campo 'points' deve ter no mínimo 0 e no máximo 10 e deve ser um valor inteiro.");
        }

        return errors;
    }

    @Override
    public ItemEntity parseRequestData(LinkedHashMap<String, Object> data) {
        Integer points = Utils.parseInt(data.get("points"));
        data.put("points", (points != null) ? points : 0);
        return new ItemEntity(data);
    }

    @Override
    public LinkedHashMap<String, Object> parseResponseData(Object object) {
        ItemEntity item = (ItemEntity) object;
        
        LinkedHashMap<String, Object> dataObject = new LinkedHashMap<>();
        dataObject.put("id", item.getId());
        dataObject.put("description", item.getDescription());
        dataObject.put("points", item.getPoints());
        
        return dataObject;
    }

    public Integer create(LinkedHashMap<String, Object> data) {
        ItemEntity itemEntity = this.parseRequestData(data);
        return this.itemDao.create(itemEntity);
    }
    
    public List<LinkedHashMap<String, Object>> readAll() {
        List<LinkedHashMap<String, Object>> items = new ArrayList<>();
        
        this.itemDao.readAll().forEach(item -> {
            items.add(this.parseResponseData(item));
        });
        
        return items;
    }
    
    public LinkedHashMap<String, Object> readOne(Integer id) {
        ItemEntity itemEntity = this.itemDao.readOne(id);
        LinkedHashMap<String, Object> item = null;

        if (itemEntity != null) {
            item = this.parseResponseData(itemEntity);
        }

        return item;
    }

}
