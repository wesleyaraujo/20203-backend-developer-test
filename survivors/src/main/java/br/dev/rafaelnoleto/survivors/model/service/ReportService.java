package br.dev.rafaelnoleto.survivors.model.service;

import br.dev.rafaelnoleto.survivors.model.dao.ReportDao;
import java.util.LinkedHashMap;
import javax.inject.Inject;

/**
 *
 * @author rafaelnoleto
 */
public class ReportService {

    @Inject
    private ReportDao reportDao;

    public LinkedHashMap<String, Object> readGeneralReport() {
        LinkedHashMap<String, Object> infecteds = this.reportDao.readInfectedsReport();

        if (infecteds != null) {
            infecteds.put("averageRemainingItems", this.reportDao.readAverageRemainingItemsReport());
        }

        return infecteds;
    }

}
