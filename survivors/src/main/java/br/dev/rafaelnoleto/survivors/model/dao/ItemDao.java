package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.ItemEntity;
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
public class ItemDao implements Dao<ItemEntity> {

    @Override
    public Integer create(ItemEntity itemEntity) {
        String sql = "insert into item (description, points) values(?, ?);";
        Integer id = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, itemEntity.getDescription());
            ps.setObject(2, Utils.parseInt(itemEntity.getPoints()));
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
    public ItemEntity readOne() {
        return null;
    }

    @Override
    public List<ItemEntity> readAll() {
        String sql = "select id, description, points from item;";
        List<ItemEntity> items = new ArrayList<>();
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            
            ItemEntity itemEntity;
            while (rs.next()) {
                itemEntity = new ItemEntity();
                itemEntity.setId(rs.getInt(1));
                itemEntity.setDescription(rs.getString(2));
                itemEntity.setPoints(rs.getInt(3));
                items.add(itemEntity);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return items;
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
