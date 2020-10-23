package br.dev.rafaelnoleto.survivors.model.dao;

import br.dev.rafaelnoleto.survivors.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author rafaelnoleto
 */
public class ReportDao {

    public LinkedHashMap<String, Object> readInfectedsReport() {
        String sql = "with infecteds as ( "
                    + " select s.*, (count(sn.survivor_id) >= 3) as infected "
                    + " from survivor s "
                    + " left join survivor_notification sn on sn.survivor_id = s.id "
                    + " group by s.id "
                    + ") "
                    + "select "
                    + " case "
                    + "     when ((select count(*) from infecteds) > 0) then (select (count(infected) * 100 / (select count(*) from infecteds)) from infecteds i where i.infected is false) "
                    + "     else 50 "
                    + "	end as percent_not_infecteds, "
                    + " case "
                    + "     when ((select count(*) from infecteds) > 0) then (select (count(infected) * 100 / (select count(*) from infecteds)) from infecteds i where i.infected is true) "
                    + "     else 50 "
                    + " end as percent_infecteds, "
                    + " (select count(*) from infecteds) as total_survivors, "
                    + " (select count(*) from infecteds i where i.infected is false) as total_not_infecteds, "
                    + " (select count(*) from infecteds i where i.infected is true) as total_infecteds, "
                    + " (select sum(i2.points * si.quantidade) from infecteds i inner join survivor_item si on si.survivor_id = i.id inner join item i2 on i2.id = si.item_id where i.infected is true) as total_points_lost;";

        LinkedHashMap<String, Object> infecteds = new LinkedHashMap<>();

        try (
                Connection con = Utils.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                infecteds.put("percent_not_infecteds", rs.getObject(1));
                infecteds.put("percent_infecteds", rs.getObject(2));
                infecteds.put("total_survivors", rs.getObject(3));
                infecteds.put("total_not_infecteds", rs.getObject(4));
                infecteds.put("total_infecteds", rs.getObject(5));
                infecteds.put("total_points_lost", rs.getObject(6));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return infecteds;
    }
    
    public List<LinkedHashMap<String, Object>> readAverageRemainingItemsReport() {
        String sql = "select i.id, i.description, avg(quantidade) as avgItems from ( "
                    + "     select s.*, (count(sn.survivor_id) >= 3) as infected "
                    + "     from survivor s "
                    + "     left join survivor_notification sn on sn.survivor_id = s.id "
                    + "     group by s.id "
                    + ") as survivors "
                    + "inner join survivor_item si on si.survivor_id = survivors.id "
                    + "inner join item i on i.id = si.item_id "
                    + "where infected is false "
                    + "group by i.id, i.description "
                    + "order by i.id;";
        
        List<LinkedHashMap<String, Object>> averageRemainingItems = new ArrayList<>();
        
        try (
            Connection con = Utils.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            
            LinkedHashMap<String, Object> item;
            while (rs.next()) {
                item = new LinkedHashMap<>();
                item.put("id", rs.getObject(1));
                item.put("description", rs.getObject(2));
                item.put("average", rs.getObject(3));
                averageRemainingItems.add(item);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return averageRemainingItems;
    }
}
