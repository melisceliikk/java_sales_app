
package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import props.ExchangeRates;

public class ExchangeRatesModel {
    
    List<ExchangeRates> erls = new ArrayList<>();

    public void exchangeRatesAllResult() {
        erls.clear();
        try {
            String url = "https://www.tcmb.gov.tr/kurlar/today.xml";
            String data = Jsoup.connect(url).timeout(30000).ignoreContentType(true).get().toString();
            Document doc = Jsoup.parse(data, "", Parser.xmlParser());
            Elements elements = doc.getElementsByTag("Currency");
            for (Element item : elements) {
                ExchangeRates er = new ExchangeRates();
                String name = item.getElementsByTag("Isim").text();
                String ForexBuying = item.getElementsByTag("ForexBuying").text();
                String ForexSelling = item.getElementsByTag("ForexSelling").text();
                er.setTitle(name);
                er.setForexBuying(ForexBuying);
                er.setForexSelling(ForexSelling);
                erls.add(er);
            }
        } catch (Exception e) {
            System.err.println("ExchangeRatesError :" + e);
        }

    }

    public DefaultTableModel exchangeRatesTable() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Birim");
        dtm.addColumn("Alış");
        dtm.addColumn("Satış");

        for (ExchangeRates item : erls) {
            Object[] row = {item.getTitle(), item.getForexBuying(), item.getForexSelling()};
            dtm.addRow(row);
        }
        return dtm;
    }

    
}
