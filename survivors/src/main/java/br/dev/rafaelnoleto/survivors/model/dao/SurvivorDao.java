package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.SurvivorEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    public SurvivorEntity readOne(Integer id) {
        String sql = "select id, name, age, gender, latitude, longitude from survivor where id = ?;";
        SurvivorEntity survivor = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                survivor = new SurvivorEntity();
                survivor.setId(rs.getInt(1));
                survivor.setName(rs.getString(2));
                survivor.setAge(rs.getInt(3));
                survivor.setGender(rs.getInt(4));
                survivor.setLatitude(rs.getDouble(5));
                survivor.setLongitude(rs.getDouble(6));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return survivor;
    }

    @Override
    public List<SurvivorEntity> readAll() {
        String sql = "select id, name, age, gender, latitude, longitude from survivor;";
        List<SurvivorEntity> survivors = new ArrayList<>();
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            
            SurvivorEntity survivorEntity;
            while (rs.next()) {
                survivorEntity = new SurvivorEntity();
                survivorEntity.setId(rs.getInt(1));
                survivorEntity.setName(rs.getString(2));
                survivorEntity.setAge(rs.getInt(3));
                survivorEntity.setGender(rs.getInt(4));
                survivorEntity.setLatitude(rs.getDouble(5));
                survivorEntity.setLongitude(rs.getDouble(6));
                survivors.add(survivorEntity);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return survivors;
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
