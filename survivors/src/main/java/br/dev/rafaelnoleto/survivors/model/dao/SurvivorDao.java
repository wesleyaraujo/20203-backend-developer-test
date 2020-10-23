package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.SurvivorEntity;
import br.dev.rafaelnoleto.survivors.model.entity.SurvivorNotifiedEntity;
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
        String sql = "select s.id, s.name, s.age, s.gender, s.latitude, s.longitude,(count(sn.survivor_id) >= 3) as infected "
                    + "from survivor s "
                    + "left join survivor_notification sn on sn.survivor_id = s.id "
                    + "where s.id = ? "
                    + "group by s.id;";
        SurvivorNotifiedEntity survivor = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                survivor = new SurvivorNotifiedEntity();
                survivor.setId(rs.getInt(1));
                survivor.setName(rs.getString(2));
                survivor.setAge(rs.getInt(3));
                survivor.setGender(rs.getInt(4));
                survivor.setLatitude(rs.getDouble(5));
                survivor.setLongitude(rs.getDouble(6));
                survivor.setInfected(rs.getBoolean(7));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return survivor;
    }

    @Override
    public List<SurvivorEntity> readAll() {
        String sql = "select s.id, s.name, s.age, s.gender, s.latitude, s.longitude,(count(sn.survivor_id) >= 3) as infected "
                    + "from survivor s "
                    + "left join survivor_notification sn on sn.survivor_id = s.id "
                    + "group by s.id;";
        List<SurvivorEntity> survivors = new ArrayList<>();
        
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            
            SurvivorNotifiedEntity survivorEntity;
            while (rs.next()) {
                survivorEntity = new SurvivorNotifiedEntity();
                survivorEntity.setId(rs.getInt(1));
                survivorEntity.setName(rs.getString(2));
                survivorEntity.setAge(rs.getInt(3));
                survivorEntity.setGender(rs.getInt(4));
                survivorEntity.setLatitude(rs.getDouble(5));
                survivorEntity.setLongitude(rs.getDouble(6));
                survivorEntity.setInfected(rs.getBoolean(7));
                survivors.add(survivorEntity);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return survivors;
    }

    @Override
    public Boolean update(Integer id, SurvivorEntity survivorEntity) {
        String sql = "update survivor set latitude = ?, longitude = ? where id = ?;";
        Boolean success = false;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, survivorEntity.getLatitude());
            ps.setObject(2, survivorEntity.getLongitude());
            ps.setObject(3, id);
            success = ps.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return success;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }
    
    public Boolean existsById(Integer id) {
        Boolean exists = false;
        String sql = "select id from survivor where id = ?;";

        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return exists;
    }

}
