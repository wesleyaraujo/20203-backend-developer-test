package br.dev.rafaelnoleto.survivors.model.entity;

import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.util.LinkedHashMap;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorNotificationEntity extends Entity {

    public SurvivorNotificationEntity() {
        super();
    }

    public SurvivorNotificationEntity(LinkedHashMap<String, Object> data) {
        super(Utils.parseInt(data.get("id")));
        this.survivorId = Utils.parseInt(data.get("survivorId"));
        this.survivorNotifierId = Utils.parseInt(data.get("survivorNotifierId"));
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
