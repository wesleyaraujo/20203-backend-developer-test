package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.SurvivorEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorDao implements Dao<SurvivorEntity> {

    @Override
    public Integer create(SurvivorEntity survivorEntity) {
        String sql = "insert into survivor (name, age, gender, latitude, longitude) values(?, ?, ?, ?, ?);";
        Integer id = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            ps.setObject(1, survivorEntity.getName());
            ps.setObject(2, survivorEntity.getAge());
            ps.setObject(3, survivorEntity.getGender());
            ps.setObject(4, survivorEntity.getLatitude());
            ps.setObject(5, survivorEntity.getLongitude());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return id;
    }

    @Override
    public SurvivorEntity readOne() {
        return null;
    }

    @Override
    public List<SurvivorEntity> readAll() {
        return null;
    }

    @Override
    public Boolean update(Integer id) {
        return null;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

}
