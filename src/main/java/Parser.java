import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Parser {

    public static Document getPage() throws IOException {
        String url = "https://www.cbr.ru/currency_base/daily/";
        return Jsoup.parse(new URL(url), 3000);
    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        HashMap<String, Float> currencies = new HashMap<>();
        //date
        Element dateCur = page.select("button[class=datepicker-filter_button]").first();
        assert dateCur != null;
        String date = dateCur.text();
        //Currency
        Elements tableCur = page.select("table[class=data]").select("tr");
        for (int i = 1; i<tableCur.size();i++) {
            String token = tableCur.get(i).child(1).text();
            Float realCur = Float.parseFloat(tableCur.get(i).child(4).text().replace(",", "."))/Float.parseFloat(tableCur.get(i).child(2).text());
            currencies.put(token, realCur);
        }
        System.out.println("Курс валют по ЦБ на "+ date);
        System.out.println(currencies);
    }
}
