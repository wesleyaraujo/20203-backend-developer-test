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
        this.quantidade = Utils.parseInt(data.get("quantidade"));
    }

    private Integer survivorId;
    private Integer itemId;
    private Integer quantidade;

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
