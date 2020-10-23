package br.dev.rafaelnoleto.survivors.model.entity;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorNotificationEntity extends Entity {

    public SurvivorNotificationEntity() {
        super();
    }

    public SurvivorNotificationEntity(Integer survivorId, Integer survivorNotifierId) {
        super();
        this.survivorId = survivorId;
        this.survivorNotifierId = survivorNotifierId;
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
