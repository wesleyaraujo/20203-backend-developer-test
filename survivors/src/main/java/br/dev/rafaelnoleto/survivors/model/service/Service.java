package br.dev.rafaelnoleto.survivors.model.service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Interface para LinkedHashMapear funcionalidades em comum entre todos os services.
 * @author rafaelnoleto
 */
public interface Service {
    public List<String> validate(LinkedHashMap<String, Object> data);
    public Object parseRequestData(LinkedHashMap<String, Object> data);
    public LinkedHashMap<String, Object> parseResponseData(Object object);
}
