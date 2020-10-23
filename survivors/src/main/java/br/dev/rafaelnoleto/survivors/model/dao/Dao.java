package br.dev.rafaelnoleto.survivors.model.dao;

import java.util.List;

/**
 *
 * @author rafaelnoleto
 */
public interface Dao<T> {

    public Integer create(T t);

    public T readOne();

    public List<T> readAll();

    public Boolean update(Integer id);

    public Boolean delete(Integer id);
}
