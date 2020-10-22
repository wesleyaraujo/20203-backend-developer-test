package br.dev.rafaelnoleto.survivors.model.entity;

import java.util.HashMap;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorNotificationEntity extends Entity {

    public SurvivorNotificationEntity(HashMap<String, Object> data) {
        super((int) data.get("id"));
        this.survivorId = (int) data.get("survivorId");
        this.survivorNotifierId = (int) data.get("survivorNotifierId");
    }

    private Integer survivorId;
    private Integer survivorNotifierId;

    public Integer getSurvivorId() {
        return survivorId;
    }

    public void setSurvivorId(Integer survivorId) {
        this.survivorId = survivorId;
    }

    public Integer getSurvivorNotifierId() {
        return survivorNotifierId;
    }

    public void setSurvivorNotifierId(Integer survivorNotifierId) {
        this.survivorNotifierId = survivorNotifierId;
    }

}
