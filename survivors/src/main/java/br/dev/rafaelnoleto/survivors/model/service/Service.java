package br.dev.rafaelnoleto.survivors.model.service;

import java.util.HashMap;
import java.util.List;

/**
 * Interface para mapear funcionalidades em comum entre todos os services.
 * @author rafaelnoleto
 * @param <T>
 */
public interface Service<T> {
    public List<String> validate(HashMap data);
    public Object parseRequestData(HashMap data);
    public HashMap parseResponseData(Object object);
}
