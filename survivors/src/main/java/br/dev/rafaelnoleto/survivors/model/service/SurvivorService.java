package br.dev.rafaelnoleto.survivors.model.service;

import br.dev.rafaelnoleto.survivors.model.dao.ItemDao;
import br.dev.rafaelnoleto.survivors.model.dao.SurvivorDao;
import br.dev.rafaelnoleto.survivors.model.dao.SurvivorItemDao;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorEntity;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorItemEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorService implements Service {

    final SurvivorDao survivorDao = new SurvivorDao();
    final SurvivorItemDao survivorItemDao = new SurvivorItemDao();
    final ItemDao itemDao = new ItemDao();

    @Override
    public List validate(LinkedHashMap<String, Object> data) {
        List<String> errors = new ArrayList<>();

        if (data.get("name") == null || ((String) data.get("name")).length() > 100) {
            errors.add("O campo 'name' deve ser informado e deve ter no máximo 100 caracteres.");
        }

        Integer age = Utils.parseInt(data.get("age"));
        if ((age != null && (age > 200 || age < 0)) || age == null && data.get("age") != null) {
            errors.add("O campo 'age' deve ter no mínimo 0 e no máximo 200.");
        }

        Integer gender = Utils.parseInt(data.get("gender"));
        Integer[] possibleGenders = {SurvivorEntity.GENDER_ANOTHER, SurvivorEntity.GENDER_WOMAN, SurvivorEntity.GENDER_MAN};
        if (gender != null && !Stream.of(possibleGenders).anyMatch(x -> x.equals(gender))) {
            errors.add("O campo 'gender' deve ter um destes valores: 0, 1 ou 2.");
        }

        Double latitude = Utils.parseDouble(data.get("latitude"));
        if (latitude == null && data.get("latitude") != null) {
            errors.add("O campo 'latitude' deve ser um número real.");
        }

        Double longitude = Utils.parseDouble(data.get("longitude"));
        if (longitude == null && data.get("longitude") != null) {
            errors.add("O campo 'longitude' deve ser um número real.");
        }

        if (data.get("items") instanceof Collection) {
            List<Map> maps = (List<Map>) data.get("items");
            maps.forEach(item -> {
                if (Utils.parseInt(item.get("quantidade")) == null && item.get("quantidade") != null) {
                    errors.add("A quantidade de algum dos seus itens não foi informada como número inteiro.");
                }

                if (item.get("id") == null) {
                    errors.add("Id do item não informado.");
                } else if ((Utils.parseInt(item.get("id")) == null && item.get("id") != null)) {
                    errors.add("Id '" + item.get("id") + "' do item não é um inteiro válido.");
                } else if (!this.itemDao.existsById(Utils.parseInt(item.get("id")))) {
                    errors.add("Id '" + item.get("id") + "' do item não encontrado.");
                }
            });
        }

        return errors;
    }

    @Override
    public SurvivorEntity parseRequestData(LinkedHashMap<String, Object> data) {
        Integer gender = Utils.parseInt(data.get("gender"));
        data.put("gender", (gender != null) ? gender : 0);

        SurvivorEntity survivorEntity = new SurvivorEntity(data);

        if (data.get("items") instanceof Collection) {
            List<Map> maps = (List<Map>) data.get("items");
            maps.forEach(item -> {
                survivorEntity.getItems().add(new SurvivorItemEntity(new LinkedHashMap<String, Object>() {
                    {
                        put("itemId", item.get("id"));
                        put("quantidade", item.get("quantidade"));
                    }
                }));
            });
        }

        return survivorEntity;
    }

    @Override
    public LinkedHashMap<String, Object> parseResponseData(Object object) {
        SurvivorEntity survivorEntity = (SurvivorEntity) object;
        LinkedHashMap<String, Object> dataObject = new LinkedHashMap<>();
        dataObject.put("id", survivorEntity.getId());
        dataObject.put("name", survivorEntity.getName());
        dataObject.put("age", survivorEntity.getAge());
        dataObject.put("gender", survivorEntity.getGender());
        dataObject.put("latitude", survivorEntity.getLatitude());
        dataObject.put("longitude", survivorEntity.getLongitude());

        if (survivorEntity.getItems() != null) {
            dataObject.put("items", survivorEntity.getItems());
        }

        return dataObject;
    }

    public Integer create(LinkedHashMap<String, Object> data) {
        SurvivorEntity survivorEntity = this.parseRequestData(data);
        Integer id = this.survivorDao.create(survivorEntity);

        survivorEntity.getItems().forEach(survivorItemEntity -> {
            survivorItemEntity.setSurvivorId(id);
            this.survivorItemDao.create(survivorItemEntity);
        });

        return id;
    }

    public List<LinkedHashMap<String, Object>> readAll() {
        List<LinkedHashMap<String, Object>> survivors = new ArrayList<>();

        this.survivorDao.readAll().forEach(survivor -> {
            survivor.setItems(null);
            survivors.add(this.parseResponseData(survivor));
        });

        return survivors;
    }

    public LinkedHashMap<String, Object> readOne(Integer id) {
        SurvivorEntity survivorEntity = this.survivorDao.readOne(id);
        LinkedHashMap<String, Object> survivor = null;

        if (survivorEntity != null) {
            survivorEntity.setItems(this.survivorItemDao.readAllBySurvivor(id));
            survivor = this.parseResponseData(survivorEntity);
        }

        return survivor;
    }

}
