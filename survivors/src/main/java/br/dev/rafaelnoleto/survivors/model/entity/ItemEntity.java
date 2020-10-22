package br.dev.rafaelnoleto.survivors.model.entity;

import java.util.HashMap;

/**
 *
 * @author rafaelnoleto
 */
public class ItemEntity extends Entity {

    public ItemEntity(HashMap<String, Object> data) {
        super((int) data.get("id"));
        this.description = (String) data.get("description");
        this.points = (int) data.get("points");
    }

    private String description;
    private Integer points;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}
