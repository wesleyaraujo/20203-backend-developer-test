package br.dev.rafaelnoleto.survivors.model.dao;

import java.util.List;

/**
 *
 * @author rafaelnoleto
 */
public interface Dao<T> {

    public Integer create(T t);

    public T readOne(Integer id);

    public List<T> readAll();

    public Boolean update(Integer id, T t);

    public Boolean delete(Integer id);
}
