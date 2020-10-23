package br.dev.rafaelnoleto.survivors.model.entity;

import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.LinkedHashMap;

/**
 *
 * @author rafaelnoleto
 */
public class ItemEntity extends Entity {

    public ItemEntity() {
        super();
    }
    
    public ItemEntity(LinkedHashMap<String, Object> data) {
        super(Utils.parseInt(data.get("id")));
        this.description = (String) data.get("description");
        this.points = Utils.parseInt(data.get("points"));
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
