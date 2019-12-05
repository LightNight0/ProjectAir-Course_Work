package window.managerWindows.tableWindows;

import essences.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import webConnection.Connector;
import window.Main;
import window.Message;
import window.managerWindows.ManagerWindow;
import window.managerWindows.tableWindows.tables.FlightsTable;
import window.managerWindows.tableWindows.tables.Tablo;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class FlightsTableView {


    private static TableView<FlightsTable> table;

    public static TableView<FlightsTable> getTable() {
        return table;
    }

    private static HBox hb;

    public static HBox getHbox() {
        return hb;
    }

    private TableView.TableViewSelectionModel<FlightsTable> selectionModel;
    private static VBox vb;

    public static VBox getVbox() {
        return vb;
    }

    @FXML
    private VBox vBox;
    @FXML
    private HBox hBox;

    @FXML
    TableView<FlightsTable> flightTableView;

    private ObservableList<FlightsTable> list = null;

    private Calendar day;

    private static TableColumn startTime;
    private static TableColumn endTime;
    private static TableColumn route;
    private static TableColumn aircraft;
    private static TableColumn strip;
    private static TableColumn crew;
    private static TableColumn days;

    public FlightsTableView() {
        startTime = new TableColumn("время вылета");
        endTime = new TableColumn("время прибытия");
        route = new TableColumn("маршрут");
        aircraft = new TableColumn("ВС");
        strip = new TableColumn("полоса");
        crew = new TableColumn("экипаж");
        days = new TableColumn("дни недели");

        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        route.setCellValueFactory(new PropertyValueFactory<>("route"));
        aircraft.setCellValueFactory(new PropertyValueFactory<>("aircraft"));
        strip.setCellValueFactory(new PropertyValueFactory<>("strip"));
        crew.setCellValueFactory(new PropertyValueFactory<>("crew"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        days.setCellValueFactory(new PropertyValueFactory<>("days"));

        startTime.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());
        route.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());
        aircraft.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());
        strip.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());
        crew.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());
        endTime.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());
        days.setCellFactory(TextFieldTableCell.<FlightsTable>forTableColumn());


        try {
            list = FlightsTable.getList("1234567");
        } catch (Exception e) {
            new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
        }
    }

    @FXML
    public void initialize() {
        day = Calendar.getInstance();
        day.setTime(new Date());
        flightTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        flightTableView.setItems(list);
        flightTableView.getColumns().addAll(startTime, endTime, route, aircraft, strip, crew, days);
        selectionModel = flightTableView.getSelectionModel();
        table = flightTableView;
        hb = hBox;
        vb = vBox;
    }

    @FXML
    public void edit() {
        BorderPane borderPane = (BorderPane) vBox.getParent();
        borderPane.setBottom(null);
        try {
            borderPane.setBottom(FXMLLoader.load(getClass().getResource("EditFlightsWindow.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void delete() {
        if (flightTableView.getSelectionModel().getSelectedItem() == null) {
            new Message("Вы не выбрали рейс для удаления!", Alert.AlertType.ERROR).show();
            return;
        }
        Flight f = new Flight();
        f.setDataBaseCommand("Delete");
        f.setId(flightTableView.getSelectionModel().getSelectedItem().getId());
        Connector.getInstance().send(f);
        Object o = Connector.getInstance().get();
        if (o instanceof String && !((String) o).equals("ok"))
            new Message("Не удалось удалить рейс!", Alert.AlertType.ERROR).show();
        else {
            new Message("Рейс был удалён", Alert.AlertType.INFORMATION).show();
            update();
        }
    }


    public static boolean allFlights = true;

    //edit flights
    @FXML
    public void todayFlights() {
        allFlights = !allFlights;
        if (button.getText().equals("Показать на сегодня")) button.setText("Показать все");
        else button.setText("Показать на сегодня");
        update();
    }

    @FXML
    private Button button;

    private void update() {
        ObservableList<FlightsTable> list = FXCollections.observableArrayList();
        try {
            if (button.getText().equals("Показать на сегодня")) list = FlightsTable.getList("1234567");
            else list = FlightsTable.getList(String.valueOf(Calendar.DAY_OF_WEEK));
            FlightsTableView.getTable().setItems(list);
        } catch (Exception e) {
            new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
        }
    }


    private boolean infoTabloActive = false;

    @FXML
    public void infoTablo() {
        infoTabloActive = !infoTabloActive;
        if (infoTabloActive) {
            BorderPane borderPane = (BorderPane) vBox.getParent();
            borderPane.setBottom(null);
            ManagerWindow.getTablo().setVisible(true);
            ManagerWindow.getTablo().setMinHeight(400);
        }else {
            ManagerWindow.getTablo().setVisible(false);
            ManagerWindow.getTablo().setMinHeight(50);
            BorderPane borderPane = (BorderPane) vBox.getParent();
            try {
                borderPane.setBottom(FXMLLoader.load(getClass().getResource("RoutesTableView.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void updateTablo(){
        ObservableList<Tablo> list = FXCollections.observableArrayList();
        list = FlightsTable.getTablos();
                ManagerWindow.getTablo().setItems(list);
    }


    @FXML
    public void viewChart(){
        AnchorPane pane;
        Stage stage = new Stage();
        try {
            pane = FXMLLoader.load(getClass().getResource("Chart.fxml"));
            pane.setStyle("-fx-background-color: #ffffdd");
            stage.setScene(new Scene(pane, 600, 400));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                stage.close();
            });
            stage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                stage.close();
            });
            Main.getStage().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
