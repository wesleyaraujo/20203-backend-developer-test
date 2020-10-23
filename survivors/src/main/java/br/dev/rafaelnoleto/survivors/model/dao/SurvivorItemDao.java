package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.SurvivorItemEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorItemDao implements Dao<SurvivorItemEntity> {

    @Override
    public Integer create(SurvivorItemEntity SurvivorItemEntity) {
        String sql = "insert into survivor_item (survivor_id, item_id, quantidade) values(?, ?, ?);";
        Integer id = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            ps.setObject(1, SurvivorItemEntity.getSurvivorId());
            ps.setObject(2, SurvivorItemEntity.getItemId());
            ps.setObject(3, SurvivorItemEntity.getQuantidade());
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
    public SurvivorItemEntity readOne() {
        return null;
    }

    @Override
    public List<SurvivorItemEntity> readAll() {
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
