package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public  class DomParser {

   private DateTimeFormatter formatter;

    private  DomParser(){
         formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    private static  DomParser instance = new DomParser();

    public static  DomParser getInstance(){
        return instance;
    }

    private ArrayList<Country> countryList;

    public ArrayList<Country> getData(String stringxml)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(stringxml);

            Element kokElement = document.getDocumentElement();

            NodeList kayitListesi = kokElement.getElementsByTagName("record");

            countryList =  addToList(kayitListesi);

        } catch (
                ParserConfigurationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return countryList;

    }
    private ArrayList<Country> addToList(NodeList kayitListesi) throws ParseException {
        ArrayList<Country> copyCountryList2 = new ArrayList<>();

        for (int  i = 0 ; i < kayitListesi.getLength();i++) {

            Node record = kayitListesi.item(i);
            Element kayitElement = (Element) record;

            if(kayitElement.getElementsByTagName("popData2018").item(0).getTextContent().equals("")){
                continue;
            }
            String countriesAndTerritories = kayitElement.getElementsByTagName("countriesAndTerritories").item(0).getTextContent();
            String geoId = kayitElement.getElementsByTagName("geoId").item(0).getTextContent();
            String countryterritoryCode = kayitElement.getElementsByTagName("countryterritoryCode").item(0).getTextContent();
            int popData2018 = Integer.parseInt(kayitElement.getElementsByTagName("popData2018").item(0).getTextContent());
            String kita = kayitElement.getElementsByTagName("continentExp").item(0).getTextContent();

            Country country = new Country();
            country.setCountriesAndTerritories(countriesAndTerritories);
            country.setGeoID(geoId);
            country.setContinentExp(kita);
            country.setCountryterritoryCode(countryterritoryCode);
            country.setPopData2018(popData2018);

            for (int j = i; j< kayitListesi.getLength(); j++) {
                    Node record2 = kayitListesi.item(j);
                    Element kayitElement2 = (Element) record2;

                    if (geoId.equals(kayitElement2.getElementsByTagName("geoId").item(0).getTextContent())) {

                        String stringDate = kayitElement2.getElementsByTagName("dateRep").item(0).getTextContent();
                        LocalDate date = LocalDate.parse(stringDate,formatter);
                        country.setDates(date);

                        String stringDailyCases = kayitElement2.getElementsByTagName("cases").item(0).getTextContent();
                        int dailyCases = Integer.parseInt(stringDailyCases);
                        country.setCasesByDate(dailyCases);
                        country.setTotalCases(country.getTotalCases() + dailyCases);

                        String stringDailyDeaths = kayitElement2.getElementsByTagName("deaths").item(0).getTextContent();
                        int dailyDeaths = Integer.parseInt(stringDailyDeaths);
                        country.setDeathsByDate(dailyDeaths);
                        country.setTotalDeaths(country.getTotalDeaths() + dailyDeaths);
                        i++;
                        continue;
                    }
                    else{
                        i--;
                        break;
                    }

                }
            calculateTotalDeathTotalCase(country);
            double mortality =  calculateMortality(country.getTotalDeaths(),(double)(country.getTotalCases()));
            double attackRate = calculateAttackRate(country.getTotalCases(),(double)(country.getPopData2018()));
            country.setMortality(mortality);
            country.setAttackRate(attackRate);
            copyCountryList2.add(country);
            }

        return copyCountryList2;
    }

    private void calculateTotalDeathTotalCase (Country country){
        country.setTotalCasesByDate(country.getTotalCases());
        country.setTotalDeathsByDate(country.getTotalDeaths());
        for(int i = 0; i<country.getCaseByDate().size() && i<country.getDeathsByDate().size(); i++ ){
            int dailyTotalCases = country.getTotalCasesByDate().get(i)-country.getCaseByDate().get(i);
            int dailyTotalDeaths = country.getTotalDeathsByDate().get(i)-country.getDeathsByDate().get(i);
            country.setTotalCasesByDate(dailyTotalCases);
            country.setTotalDeathsByDate(dailyTotalDeaths);
        }
    }
    private double calculateMortality(int totalDeaths, double totalCases){
        return totalDeaths/totalCases;
    }
    private double calculateAttackRate(int totalCases, double popData){
        return totalCases/popData;
    }
}


