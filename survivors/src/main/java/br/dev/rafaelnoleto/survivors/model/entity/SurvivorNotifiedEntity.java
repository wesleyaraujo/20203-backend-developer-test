/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dev.rafaelnoleto.survivors.model.entity;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorNotifiedEntity extends SurvivorEntity {

    public SurvivorNotifiedEntity() {
        super();
    }

    private Boolean infected;

    public Boolean getInfected() {
        return infected;
    }

    public void setInfected(Boolean infected) {
        this.infected = infected;
    }

}
