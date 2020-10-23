package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.model.entity.SurvivorNotificationEntity;
import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author rafaelnoleto
 */
public class SurvivorNotificationDao implements Dao<SurvivorNotificationEntity> {

    @Override
    public Integer create(SurvivorNotificationEntity survivorNotificationEntity) {
        String sql = "insert into survivor_notification (survivor_id, survivor_notifier_id) values(?, ?);";
        Integer id = null;
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            ps.setObject(1, survivorNotificationEntity.getSurvivorId());
            ps.setObject(2, survivorNotificationEntity.getSurvivorNotifierId());
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
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public SurvivorNotificationEntity readOne(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SurvivorNotificationEntity> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean update(Integer id, SurvivorNotificationEntity t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
