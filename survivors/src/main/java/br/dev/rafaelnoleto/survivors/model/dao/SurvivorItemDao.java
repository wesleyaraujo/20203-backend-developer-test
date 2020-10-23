package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.SurvivorItemEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorItemDao implements Dao<SurvivorItemEntity> {

    @Override
    public Integer create(SurvivorItemEntity survivorItemEntity) {
        String sql = "insert into survivor_item (survivor_id, item_id, quantidade) values(?, ?, ?);";
        Integer id = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            ps.setObject(1, survivorItemEntity.getSurvivorId());
            ps.setObject(2, survivorItemEntity.getItemId());
            ps.setObject(3, survivorItemEntity.getQuantidade());
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
    public SurvivorItemEntity readOne(Integer id) {
        return null;
    }

    @Override
    public List<SurvivorItemEntity> readAll() {
        return null;
    }
    
    public List<SurvivorItemEntity> readAllBySurvivor(Integer survivorId) {
        String sql = "select i.id, si.quantidade from item i " +
                        "inner join survivor_item si on si.item_id = i.id and si.survivor_id = ?";
        List<SurvivorItemEntity> items = new ArrayList<>();
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, survivorId);
            ResultSet rs = ps.executeQuery();
            
            SurvivorItemEntity survivorItemEntity;
            while (rs.next()) {
                survivorItemEntity = new SurvivorItemEntity();
                survivorItemEntity.setId(rs.getInt(1));
                survivorItemEntity.setQuantidade(rs.getInt(2));
                items.add(survivorItemEntity);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return items;
    }

    @Override
    public Boolean update(Integer id, SurvivorItemEntity survivorItemEntity) {
        String sql = "update survivor_item set quantidade = ? where id = ?;";
        Boolean success = false;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, survivorItemEntity.getQuantidade());
            ps.setObject(2, id);
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
    
    public SurvivorItemEntity readByIdSurvivorAndIdItem(Integer survivorId, Integer itemId) {
        String sql = "select id, quantidade from survivor_item "
                    + "where survivor_id = ? and item_id = ?;";
        SurvivorItemEntity survivorItem = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, survivorId);
            ps.setObject(2, itemId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                survivorItem = new SurvivorItemEntity();
                survivorItem.setId(rs.getInt(1));
                survivorItem.setQuantidade(rs.getInt(2));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return survivorItem;
    }
    
    public Map<String, Object> existsByIdSurvivorAndIdItemAndQuantidade(Integer survivorId, Integer itemId, Integer quantidade) {
        Integer points = 0;
        Boolean exists = false;
        String sql = "select i.points from survivor_item si "
                + "inner join item i on i.id = si.item_id "
                + "where si.survivor_id = ? "
                + "and si.item_id = ? "
                + "and si.quantidade >= ?;";

        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setObject(1, survivorId);
            ps.setObject(2, itemId);
            ps.setObject(3, quantidade);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();
            if (exists) {
                points = rs.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        Map<String, Object> map = new HashMap<>();
        map.put("points", points);
        map.put("exists", exists);
        return map;
    }

}
