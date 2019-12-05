package window.managerWindows.tableWindows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import window.managerWindows.tableWindows.tables.FlightsTable;

public class Chart {
    @FXML
    private PieChart pie;
    @FXML
    public void initialize(){



        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Понедельник", FlightsTable.day1),
                        new PieChart.Data("Вторник", FlightsTable.day2),
                        new PieChart.Data("Среда", FlightsTable.day3),
                        new PieChart.Data("Четверг", FlightsTable.day4),
                        new PieChart.Data("Пятница", FlightsTable.day5),
                        new PieChart.Data("Суббота", FlightsTable.day6),
                        new PieChart.Data("Восресенье", FlightsTable.day7));
        pie.setData(pieChartData);
    }
}
