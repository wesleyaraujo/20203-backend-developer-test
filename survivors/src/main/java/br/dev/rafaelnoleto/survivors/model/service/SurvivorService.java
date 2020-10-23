package br.dev.rafaelnoleto.survivors.model.service;

import br.dev.rafaelnoleto.survivors.model.dao.ItemDao;
import br.dev.rafaelnoleto.survivors.model.dao.SurvivorDao;
import br.dev.rafaelnoleto.survivors.model.dao.SurvivorItemDao;
import br.dev.rafaelnoleto.survivors.model.dao.SurvivorNotificationDao;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorEntity;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorItemEntity;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorNotificationEntity;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorNotifiedEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
    final SurvivorNotificationDao survivorNotificationDao = new SurvivorNotificationDao();
    final ItemDao itemDao = new ItemDao();

    private void validateLocation(List<String> errors, LinkedHashMap<String, Object> data) {
        Double latitude = Utils.parseDouble(data.get("latitude"));
        if (latitude == null && data.get("latitude") != null) {
            errors.add("O campo 'latitude' deve ser um número real.");
        }

        Double longitude = Utils.parseDouble(data.get("longitude"));
        if (longitude == null && data.get("longitude") != null) {
            errors.add("O campo 'longitude' deve ser um número real.");
        }
    }

    private void validateItems(List<String> errors, LinkedHashMap<String, Object> data) {
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
    }

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

        this.validateLocation(errors, data);

        this.validateItems(errors, data);

        return errors;
    }

    public List validateUpdateLocation(Integer id, LinkedHashMap<String, Object> data) {
        List<String> errors = new ArrayList<>();

        this.validateLocation(errors, data);

        if (!this.survivorDao.existsById(Utils.parseInt(id))) {
            errors.add("Id '" + id + "' não encontrado.");
        }

        return errors;
    }

    public List validateCreateNotification(Integer survivorId, Integer survivorNotifierId) {
        List<String> errors = new ArrayList<>();

        if (survivorId.equals(survivorNotifierId)) {
            errors.add("Survivor (survivorId) e Notifier (survivorNotifierId) devem ser diferentes.");
        }

        if (!this.survivorDao.existsById(Utils.parseInt(survivorId))) {
            errors.add("Id '" + survivorId + "' não encontrado.");
        }

        if (!this.survivorDao.existsById(Utils.parseInt(survivorNotifierId))) {
            errors.add("Id '" + survivorNotifierId + "' do notificador não encontrado.");
        }

        if (this.survivorNotificationDao.existsBySurvivorIdAndSurvivorNotifierId(Utils.parseInt(survivorId), Utils.parseInt(survivorNotifierId))) {
            errors.add("Esta notificação já existe.");
        }

        return errors;
    }

    public List validateExchange(LinkedHashMap<String, Object> data) {
        List<String> errors = new ArrayList<>();

        if (data.get("survivor1") == null || data.get("survivor2") == null) {
            errors.add("O survivor1 e survivor2 devem ser informados.");
        } else {
            HashMap<String, Object> survivor1 = (HashMap<String, Object>) data.get("survivor1");
            HashMap<String, Object> survivor2 = (HashMap<String, Object>) data.get("survivor2");

            Integer points1 = this.validateExchangeSurvivor(errors, survivor1, "survivor1");
            Integer points2 = this.validateExchangeSurvivor(errors, survivor2, "survivor2");

            if (!points1.equals(points2)) {
                errors.add("Troca inviável, os pontos dos itens são diferentes - survivor1: " + points1 + ", suvivor2: " + points2);
            }
        }

        return errors;
    }

    private Integer validateExchangeSurvivor(List<String> errors, HashMap<String, Object> survivor, String survivorName) {
        Integer itemsPoints = 0;

        SurvivorNotifiedEntity survivorEntity = (SurvivorNotifiedEntity) this.survivorDao.readOne(Utils.parseInt(survivor.get("id")));
        if (survivorEntity == null) {
            errors.add("Id '" + survivor.get("id") + "' do " + survivorName + " não encontrado.");
        } else if (survivorEntity.getInfected()) {
            errors.add("O '" + survivorName + " está infectado.");
        }

        if ((survivor.get("items") instanceof Collection) && !((List<Map>) survivor.get("items")).isEmpty()) {
            List<Map> items = (List<Map>) survivor.get("items");
            for (Map item : items) {
                if (Utils.parseInt(item.get("quantidade")) == 0 || Utils.parseInt(item.get("quantidade")) == null) {
                    errors.add("A quantidade do item '" + item.get("id") + "' do " + survivorName + " deve ser informada e deve ser maior que 0.");
                }

                if (!this.itemDao.existsById(Utils.parseInt(item.get("id")))) {
                    errors.add("Id '" + item.get("id") + "' do item não encontrado.");
                } else {
                    Map<String, Object> existsPoints = this.survivorItemDao.existsByIdSurvivorAndIdItemAndQuantidade(Utils.parseInt(survivor.get("id")), Utils.parseInt(item.get("id")), Utils.parseInt(item.get("quantidade")));
                    if (!((Boolean) existsPoints.get("exists"))) {
                        errors.add("O item '" + item.get("id") + "' não está suscetível a troca, verifique se o " + survivorName + " possui este item e a quantidade que está tentando trocar.");
                    } else {
                        itemsPoints += ((Integer) existsPoints.get("points") * Utils.parseInt(item.get("quantidade")));
                    }
                }
            }
        } else {
            errors.add("Items do " + survivorName + " não foram informados ou são inválidos.");
        }

        return itemsPoints;
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
        SurvivorNotifiedEntity survivorEntity = (SurvivorNotifiedEntity) object;
        LinkedHashMap<String, Object> dataObject = new LinkedHashMap<>();
        dataObject.put("id", survivorEntity.getId());
        dataObject.put("name", survivorEntity.getName());
        dataObject.put("age", survivorEntity.getAge());
        dataObject.put("gender", survivorEntity.getGender());
        dataObject.put("latitude", survivorEntity.getLatitude());
        dataObject.put("longitude", survivorEntity.getLongitude());
        dataObject.put("infected", survivorEntity.getInfected());

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

    public Integer createNotification(Integer survivorId, Integer survivorNotifierId) {
        SurvivorNotificationEntity survivorNotificationEntity = new SurvivorNotificationEntity(survivorId, survivorNotifierId);
        Integer idNotification = this.survivorNotificationDao.create(survivorNotificationEntity);

        return idNotification;
    }

    public Boolean updateLocation(Integer id, LinkedHashMap<String, Object> data) {
        SurvivorEntity survivorEntity = this.parseRequestData(data);
        return this.survivorDao.update(id, survivorEntity);
    }

    public void executeExchange(LinkedHashMap<String, Object> data) {
        HashMap<String, Object> survivor1 = (HashMap<String, Object>) data.get("survivor1");
        HashMap<String, Object> survivor2 = (HashMap<String, Object>) data.get("survivor2");
        this.executeExchangeSurvivor(Utils.parseInt(survivor2.get("id")), survivor1);
        this.executeExchangeSurvivor(Utils.parseInt(survivor1.get("id")), survivor2);
    }

    private void executeExchangeSurvivor(Integer survivorReceiverId, HashMap<String, Object> survivor) {
        List<Map> items = (List<Map>) survivor.get("items");

        items.forEach(item -> {
            SurvivorItemEntity survivorItemEntity = this.survivorItemDao.readByIdSurvivorAndIdItem(survivorReceiverId, Utils.parseInt(item.get("id")));

            if (survivorItemEntity != null) {
                survivorItemEntity.setQuantidade(survivorItemEntity.getQuantidade() + Utils.parseInt(item.get("quantidade")));
                this.survivorItemDao.update(survivorItemEntity.getId(), survivorItemEntity);
            } else {
                survivorItemEntity = new SurvivorItemEntity();
                survivorItemEntity.setSurvivorId(survivorReceiverId);
                survivorItemEntity.setItemId(Utils.parseInt(item.get("id")));
                survivorItemEntity.setQuantidade(Utils.parseInt(item.get("quantidade")));
            }

            survivorItemEntity = this.survivorItemDao.readByIdSurvivorAndIdItem(Utils.parseInt(survivor.get("id")), Utils.parseInt(item.get("id")));
            survivorItemEntity.setQuantidade(survivorItemEntity.getQuantidade() - Utils.parseInt(item.get("quantidade")));
            this.survivorItemDao.update(survivorItemEntity.getId(), survivorItemEntity);
        });
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
