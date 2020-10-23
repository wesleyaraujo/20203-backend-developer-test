package br.dev.rafaelnoleto.survivors.model.entity;

import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.LinkedHashMap;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorItemEntity extends Entity {

    public SurvivorItemEntity() {
        super();
    }

    public SurvivorItemEntity(LinkedHashMap<String, Object> data) {
        super(Utils.parseInt(data.get("id")));
        this.survivorId = Utils.parseInt(data.get("survivorId"));
        this.itemId = Utils.parseInt(data.get("itemId"));
    }

    private Integer survivorId;
    private Integer itemId;

    public Integer getSurvivorId() {
        return survivorId;
    }

    public void setSurvivorId(Integer survivorId) {
        this.survivorId = survivorId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
