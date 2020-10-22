package br.dev.rafaelnoleto.survivors.model.entity;

/**
 *
 * @author rafaelnoleto
 */
public abstract class Entity {

    public Entity(Integer id) {
        this.id = id;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
