package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Button btnGetData;

    @FXML
    private TextField textField;

    @FXML
    private TableView<Country> tableView;

    @FXML
    private TableColumn<Country, String> country;

    @FXML
    private TableColumn<Country, Integer> totalCases;

    @FXML
    private TableColumn<Country, Integer> newCases;

    @FXML
    private TableColumn<Country, Integer> totalDeaths;

    @FXML
    private TableColumn<Country, Integer> newDeaths;

    @FXML
    private TableColumn<Country, Integer> population;

    @FXML
    private TableColumn<Country, Double> mortality;

    @FXML
    private TableColumn<Country, Double> attackRate;

    @FXML
    private LineChart<String, Integer> casesLineChart;

    @FXML
    private LineChart<String, Integer> deathsLineChart;

    @FXML
    private StackedBarChart<String, Integer> casesBarChart;

    @FXML
    private StackedBarChart<String, Integer> deathsBarChart;

    @FXML
    private Button btnShowChart;

    @FXML
    private ListView<Country> listView;

    private XYChart.Series<String, Integer> seriesCases;
    private XYChart.Series<String, Integer> seriesDeaths;

   private final static String asia = "Asia";
   private final static String europe = "Europe";
   private final static String africa = "Africa";
   private final static String america = "America";
   private final static String oceania = "Oceania";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        btnShowChart.setDisable(true);

        country.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Country, String> countryStringCellDataFeatures) {
                return  new ReadOnlyObjectWrapper<>(countryStringCellDataFeatures.getValue().getCountriesAndTerritories());
            }
        });
        totalCases.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Country, Integer> countryIntegerCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryIntegerCellDataFeatures.getValue().getTotalCases());
            }
        });
        newCases.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Integer>, ObservableValue<Integer>>() {

            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Country, Integer> countryIntegerCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryIntegerCellDataFeatures.getValue().getCaseByDate().get(0));
            }
        });
        totalDeaths.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Country, Integer> countryIntegerCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryIntegerCellDataFeatures.getValue().getTotalDeaths());
            }
        });
        newDeaths.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Country, Integer> countryIntegerCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryIntegerCellDataFeatures.getValue().getDeathsByDate().get(0));
            }
        });
        population.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Country, Integer> countryIntegerCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryIntegerCellDataFeatures.getValue().getPopData2018());
            }
        });
        mortality.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Country, Double> countryDoubleCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryDoubleCellDataFeatures.getValue().getMortality());
            }
        });
        attackRate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Country, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Country, Double> countryDoubleCellDataFeatures) {
                return new ReadOnlyObjectWrapper<>(countryDoubleCellDataFeatures.getValue().getAttackRate());
            }
        });
    }

    @FXML
    public void buttonGetData(ActionEvent actionEvent) {

        ObservableList<Country> countryObservableList = FXCollections.observableArrayList(DomParser.getInstance().getData(textField.getText()));


        tableView.getSortOrder().add(country);
        tableView.getSortOrder().add(totalCases);
        tableView.getSortOrder().add(newCases);
        tableView.getSortOrder().add(totalDeaths);
        tableView.getSortOrder().add(newDeaths);
        tableView.getSortOrder().add(population);
        tableView.getSortOrder().add(mortality);
        tableView.getSortOrder().add(attackRate);

        tableView.setItems(countryObservableList);

        listView.setItems(countryObservableList);

        btnShowChart.setDisable(false);


    }

    @FXML
    public void buttonShowChart(ActionEvent actionEvent) throws ParseException {

        ObservableList<Country> selectedItems = listView.getSelectionModel().getSelectedItems();
        addToCasesLineChart(selectedItems);
        addToDeathsLineChart(selectedItems);

        ObservableList<Country> items = listView.getItems();
        addToCasesBarChart(items);
        addToDeathsBarChart(items);
    }
    private void addToCasesLineChart(ObservableList<Country> selectedItems){
        casesLineChart.getData().clear();
        for (Country country : selectedItems) {
            seriesCases = new XYChart.Series<String, Integer>();
            seriesCases.setName(country.getGeoID());
            for (int i = country.getCaseByDate().size() - 1; i >= 0; i--) {
                String date = country.getDates().get(i).toString();
                Integer totalCases = country.getTotalCasesByDate().get(i);
                seriesCases.getData().add(new XYChart.Data<>(date, totalCases));
            }
            casesLineChart.getData().add(seriesCases);
        }

    }
  private void addToDeathsLineChart(ObservableList<Country> selectedItems){
      deathsLineChart.getData().clear();
      for (Country country : selectedItems) {
          seriesDeaths = new XYChart.Series<String, Integer>();
          seriesDeaths.setName(country.getGeoID());
          for (int i = country.getDeathsByDate().size() - 1; i >= 0; i--) {
              String date = country.getDates().get(i).toString();
              Integer totalDeaths = country.getTotalDeathsByDate().get(i);
              seriesDeaths.getData().add(new XYChart.Data<>(date, totalDeaths));
          }
          deathsLineChart.getData().add(seriesDeaths);
      }


  }

    private void addToCasesBarChart(ObservableList<Country> items){

        XYChart.Series<String,Integer> casesSeriesAsia = new XYChart.Series();
        XYChart.Series<String,Integer> casesSeriesEurope = new XYChart.Series();
        XYChart.Series<String,Integer> casesSeriesAfrica = new XYChart.Series();
        XYChart.Series<String,Integer> casesSeriesAmerica = new XYChart.Series();
        XYChart.Series<String,Integer> casesSeriesOceania = new XYChart.Series();

        int totalAsiaCases = 0, totalEuropeCases = 0, totalAfricaCases = 0, totalAmericaCases = 0, totalOceaniaCases = 0;

        casesBarChart.getData().clear();
        for(int i = 100; i>0; i=i-5) {

            totalAsiaCases = 0; totalEuropeCases = 0; totalAfricaCases = 0; totalAmericaCases = 0; totalOceaniaCases = 0;
            String date = "";
            for (Country country : items) {
                if(country.getDates().size()-1>=i){
                    date=country.getDates().get(i).toString();
                    if (asia.equals(country.getContinentExp())) {
                        totalAsiaCases +=country.getTotalCasesByDate().get(i);
                    } else if (europe.equals(country.getContinentExp())) {
                        totalEuropeCases += country.getTotalCasesByDate().get(i);
                    } else if (africa.equals(country.getContinentExp())) {
                        totalAfricaCases += country.getTotalCasesByDate().get(i);
                    } else if (america.equals(country.getContinentExp())) {
                        totalAmericaCases += country.getTotalCasesByDate().get(i);
                    } else if (oceania.equals(country.getContinentExp())) {
                        totalOceaniaCases += country.getTotalCasesByDate().get(i);
                    }
                }
            }
            casesSeriesAsia.setName(asia);
            casesSeriesAsia.getData().add(new XYChart.Data<>(date, totalAsiaCases));

            casesSeriesEurope.setName(europe);
            casesSeriesEurope.getData().add(new XYChart.Data(date, totalEuropeCases));

            casesSeriesAfrica.setName(africa);
            casesSeriesAfrica.getData().add(new XYChart.Data(date, totalAfricaCases));

            casesSeriesAfrica.setName(america);
            casesSeriesAmerica.getData().add(new XYChart.Data(date, totalAmericaCases));

            casesSeriesOceania.setName(oceania);
            casesSeriesOceania.getData().add(new XYChart.Data(date, totalOceaniaCases));

        }
        casesBarChart.getData().addAll(casesSeriesAsia,casesSeriesEurope,casesSeriesAmerica,casesSeriesAfrica,casesSeriesOceania);
    }


    private void addToDeathsBarChart(ObservableList<Country> items){

        XYChart.Series<String,Integer> deathsSeriesAsia = new XYChart.Series();
        XYChart.Series<String,Integer> deathsSeriesEurope = new XYChart.Series();
        XYChart.Series<String,Integer> deathsSeriesAfrica = new XYChart.Series();
        XYChart.Series<String,Integer> deathsSeriesAmerica = new XYChart.Series();
        XYChart.Series<String,Integer> deathsSeriesOceania = new XYChart.Series();

        int totalAsiaDeaths = 0, totalEuropeDeaths = 0, totalAfricaDeaths = 0, totalAmericaDeaths = 0, totalOceaniaDeaths = 0;

        deathsBarChart.getData().clear();
        for(int i = 100; i>0; i=i-5) {

            totalAsiaDeaths = 0; totalEuropeDeaths = 0; totalAfricaDeaths = 0; totalAmericaDeaths = 0; totalOceaniaDeaths = 0;
            String date = "";
            for (Country country : items) {
                if(country.getDates().size()-1>=i){
                    date=country.getDates().get(i).toString();
                    if (asia.equals(country.getContinentExp())) {
                        totalAsiaDeaths +=country.getTotalDeathsByDate().get(i);
                    } else if (europe.equals(country.getContinentExp())) {
                        totalEuropeDeaths += country.getTotalDeathsByDate().get(i);
                    } else if (africa.equals(country.getContinentExp())) {
                        totalAfricaDeaths += country.getTotalDeathsByDate().get(i);
                    } else if (america.equals(country.getContinentExp())) {
                        totalAmericaDeaths += country.getTotalDeathsByDate().get(i);
                    } else if (oceania.equals(country.getContinentExp())) {
                        totalOceaniaDeaths += country.getTotalDeathsByDate().get(i);
                    }
                }
            }
            deathsSeriesAsia.setName(asia);
            deathsSeriesAfrica.getData().add(new XYChart.Data<>(date, totalAsiaDeaths));

            deathsSeriesEurope.setName(europe);
            deathsSeriesEurope.getData().add(new XYChart.Data(date, totalEuropeDeaths));

            deathsSeriesAfrica.setName(africa);
            deathsSeriesAfrica.getData().add(new XYChart.Data(date, totalAfricaDeaths));

            deathsSeriesAmerica.setName(america);
            deathsSeriesAmerica.getData().add(new XYChart.Data(date, totalAmericaDeaths));

            deathsSeriesOceania.setName(oceania);
            deathsSeriesOceania.getData().add(new XYChart.Data(date, totalOceaniaDeaths));

        }
        deathsBarChart.getData().addAll(deathsSeriesAsia,deathsSeriesEurope,deathsSeriesAmerica,deathsSeriesAfrica,deathsSeriesOceania);

    }
}
