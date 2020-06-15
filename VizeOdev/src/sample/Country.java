package sample;

import javafx.scene.chart.PieChart;

import java.time.LocalDate;
import java.util.*;

public class Country {

    private ArrayList<LocalDate> dates;
    private ArrayList<Integer> casesByDate;
    private ArrayList<Integer> deathsByDate;
    private ArrayList<Integer> totalCasesByDate;
    private ArrayList<Integer> totalDeathsByDate;

    private int totalCases;
    private int totalDeaths;
    private int popData2018;

    private double mortality;
    private double attackRate;

    private String geoID;
    private String countryterritoryCode;
    private String continentExp;
    private String countriesAndTerritories;


    public Country() {
          casesByDate = new ArrayList<>();
          deathsByDate = new ArrayList<>();
          dates  = new ArrayList<>();
          totalCasesByDate = new ArrayList<>();
         totalDeathsByDate=new ArrayList<>();
    }

    public ArrayList<Integer> getTotalCasesByDate() {
        return totalCasesByDate;
    }

    public void setTotalDeathsByDate(int dailyTotalDeaths) {
        this.totalDeathsByDate.add(dailyTotalDeaths);
    }

    public ArrayList<Integer> getTotalDeathsByDate() {
        return totalDeathsByDate;
    }

    public void setTotalCasesByDate(int dailyTotalCases) {
        this.totalCasesByDate.add(dailyTotalCases);
    }

    public ArrayList<LocalDate> getDates() {
        return dates;
    }

    public void setDates(LocalDate date) {
        dates.add(date);
    }

    public ArrayList<Integer> getCaseByDate() {
        return casesByDate;
    }

    public void setCasesByDate(Integer dailyCases) {
        this.casesByDate.add(dailyCases);
    }

    public ArrayList<Integer> getDeathsByDate() {
        return deathsByDate;
    }

    public void setDeathsByDate(Integer dailyDeath) {
        this.deathsByDate.add(dailyDeath);
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getPopData2018() {
        return popData2018;
    }

    public void setPopData2018(int popData2018) {
        this.popData2018 = popData2018;
    }

    public String getCountriesAndTerritories() {
        return countriesAndTerritories;
    }

    public void setCountriesAndTerritories(String countriesAndTerritories) {
        this.countriesAndTerritories = countriesAndTerritories;
    }

    public String getGeoID() {
        return geoID;
    }

    public void setGeoID(String geoID) {
        this.geoID = geoID;
    }

    public String getCountryterritoryCode() {
        return countryterritoryCode;
    }

    public void setCountryterritoryCode(String countryterritoryCode) {
        this.countryterritoryCode = countryterritoryCode;
    }

    public String getContinentExp() {
        return continentExp;
    }

    public void setContinentExp(String continentExp) {
        this.continentExp = continentExp;
    }

    public double getMortality() {
        return mortality;
    }

    public void setMortality(double mortality) {
        this.mortality = mortality;
    }

    public double getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(double attackRate) {
        this.attackRate = attackRate;
    }

    @Override
    public String toString() {
        return countriesAndTerritories;
    }

}







