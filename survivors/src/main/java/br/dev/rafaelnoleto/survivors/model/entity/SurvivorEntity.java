package br.dev.rafaelnoleto.survivors.model.entity;

import java.util.HashMap;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorEntity extends Entity {

    public SurvivorEntity(HashMap<String, Object> data) {
        super((int) data.get("id"));
        this.name = (String) data.get("name");
        this.age = (int) data.get("age");
        this.gender = (int) data.get("gender");
        this.latitude = (Double) data.get("latitude");
        this.longitude = (Double) data.get("logitude");
    }

    private String name;
    private Integer age;
    private Integer gender;
    private Double latitude;
    private Double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
