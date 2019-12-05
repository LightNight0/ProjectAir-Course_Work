package window.managerWindows.tableWindows;

import essences.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import webConnection.Connector;
import window.Message;
import window.managerWindows.ManagerWindow;
import window.managerWindows.tableWindows.tables.FlightsTable;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class EditFlightsWindow {

    @FXML
    private TextField textField;
    @FXML
    private ComboBox<Route> choiceBoxRoute;
    @FXML
    private ComboBox<Aircraft> choiceBoxAircraft;
    @FXML
    private ComboBox<Runway> choiceBoxStrip;
    @FXML
    private ComboBox<Crew> choiceBoxCrew;
    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;
    @FXML
    private CheckBox checkBox5;
    @FXML
    private CheckBox checkBox6;
    @FXML
    private CheckBox checkBox7;

    @FXML
    private Button editButton;
    @FXML
    private Button addButton;


    private TableView.TableViewSelectionModel<FlightsTable> selectionModel;

    @FXML
    public void initialize() {
        textField.textProperty().addListener((o, oldV, newV) -> {
            if (newV.length() == 2) textField.setText(newV + " ч, ");
            if (newV.length() == 8) textField.setText(newV + " мин");
            if (newV.length() > 12) textField.setText(oldV);
            if (newV.length() < 6 && newV.length() > 2) textField.setText(oldV);
            if (newV.length() < 12 && newV.length() > 8) textField.setText(oldV);
        });
        FlightsTableView.getHbox().setVisible(false);
        selectionModel = FlightsTableView.getTable().getSelectionModel();

        loadChoiceBoxes();
    }


    @FXML
    public void back() {
        ManagerWindow.getBorderPane().setBottom(null);
        try {
            ManagerWindow.getBorderPane().setBottom(FXMLLoader.load(getClass().getResource("RoutesTableView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FlightsTableView.getHbox().setVisible(true);
    }

    @FXML
    public void add() {
        if (!checkBox1.isSelected() &&
                !checkBox2.isSelected() &&
                !checkBox3.isSelected() &&
                !checkBox4.isSelected() &&
                !checkBox5.isSelected() &&
                !checkBox6.isSelected() &&
                !checkBox7.isSelected()) {
            new Message("Вы не выбрали дни для рейса!", Alert.AlertType.ERROR).show();
            return;
        }
        if (textField.getText().isEmpty()) {
            new Message("Неверно введено поле \"Время отправления\"!", Alert.AlertType.ERROR).show();
            return;
        }
        try {
            Integer.parseInt(textField.getText().split(" ")[0]);
            Integer.parseInt(textField.getText().split(" ")[2]);
        } catch (NumberFormatException e) {
            new Message("Неверно введено поле \"Время отправления\"!", Alert.AlertType.ERROR).show();
            return;
        }
        if (Integer.parseInt(textField.getText().split(" ")[0]) > 23 || Integer.parseInt(textField.getText().split(" ")[2]) > 59) {
            new Message("Неверно введено поле \"Время отправления\"!", Alert.AlertType.ERROR).show();
            return;
        }

        if (choiceBoxCrew.getSelectionModel().getSelectedItem() == null) {
            new Message("Не выбрано поле \"Экипаж\"!", Alert.AlertType.ERROR).show();
            return;
        }
        if (choiceBoxAircraft.getSelectionModel().getSelectedItem() == null) {
            new Message("Не выбрано поле \"ВС\"!", Alert.AlertType.ERROR).show();
            return;
        }
        if (choiceBoxStrip.getSelectionModel().getSelectedItem() == null) {
            new Message("Не выбрано поле \"Полоса\"!", Alert.AlertType.ERROR).show();
            return;
        }
        if (choiceBoxRoute.getSelectionModel().getSelectedItem() == null) {
            new Message("Не выбрано поле \"Маршрут\"!", Alert.AlertType.ERROR).show();
            return;
        }

        Flight f = new Flight();
        if(addButton.getText().equals("Применить")){
            f.setDataBaseCommand("Update");
            f.setId(selectionModel.getSelectedItem().getId());
        }
        else f.setDataBaseCommand("Add");
        f.setStartTime(textField.getText().split(" ")[0] + ":" + textField.getText().split(" ")[2]);
        f.setRoute(choiceBoxRoute.getSelectionModel().getSelectedItem().getId());
        f.setAircraft(choiceBoxAircraft.getSelectionModel().getSelectedItem().getId());
        f.setStrip(choiceBoxStrip.getSelectionModel().getSelectedItem().getId());
        f.setCrew(choiceBoxCrew.getSelectionModel().getSelectedItem().getId());
        String days = "";
        if (checkBox1.isSelected()) days += "1";
        else days += "*";
        if (checkBox2.isSelected()) days += "2";
        else days += "*";
        if (checkBox3.isSelected()) days += "3";
        else days += "*";
        if (checkBox4.isSelected()) days += "4";
        else days += "*";
        if (checkBox5.isSelected()) days += "5";
        else days += "*";
        if (checkBox6.isSelected()) days += "6";
        else days += "*";
        if (checkBox7.isSelected()) days += "7";
        else days += "*";
        f.setDays(days);
        Connector.getInstance().send(f);
        Object o = Connector.getInstance().get();
        if (o instanceof String && !((String) o).equals("ok"))
            new Message("Не удалось добавить рейс!", Alert.AlertType.ERROR).show();
        else {
            if(addButton.getText().equals("Применить"))new Message("Рейс был изменён", Alert.AlertType.INFORMATION).show();
                else new Message("Рейс был добавлен", Alert.AlertType.INFORMATION).show();
            ObservableList<FlightsTable> list = FXCollections.observableArrayList();
            try {
                if(FlightsTableView.allFlights)list = FlightsTable.getList("1234567");
                else {
                    Calendar d = Calendar.getInstance();d.setTime(new Date());
                    list = FlightsTable.getList(String.valueOf(Calendar.DAY_OF_WEEK));
                }
                FlightsTableView.getTable().setItems(list);
            } catch (Exception e) {
                new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
            }
        }
        clearFields();
    }

    @FXML
    public void edit() {
        if (selectionModel.getSelectedItem()==null){
            new Message("Вы не выбрали рейс, который хотите изменить!", Alert.AlertType.ERROR).show();
            return;
        }
        FlightsTable flightsTable = selectionModel.getSelectedItem();
        Flight f = new Flight();
        f.setId(flightsTable.getId());
        f.setDataBaseCommand("Select");
        Connector.getInstance().send(f);
        f = ((Flight[]) Connector.getInstance().get())[0];
        choiceBoxCrew.getSelectionModel().selectFirst();
        choiceBoxStrip.getSelectionModel().selectFirst();
        choiceBoxAircraft.getSelectionModel().selectFirst();
        choiceBoxRoute.getSelectionModel().selectFirst();
        while (f.getRoute() != choiceBoxRoute.getSelectionModel().getSelectedItem().getId())
            choiceBoxRoute.getSelectionModel().selectNext();
        while (f.getAircraft() != choiceBoxAircraft.getSelectionModel().getSelectedItem().getId())
            choiceBoxAircraft.getSelectionModel().selectNext();
        while (f.getStrip() != choiceBoxStrip.getSelectionModel().getSelectedItem().getId())
            choiceBoxStrip.getSelectionModel().selectNext();
        while (f.getCrew() != choiceBoxCrew.getSelectionModel().getSelectedItem().getId())
            choiceBoxCrew.getSelectionModel().selectNext();
        textField.setText(flightsTable.getStartTime().split(":")[0] + " ч, " +
                flightsTable.getStartTime().split(":")[1] + " мин");
        if(flightsTable.getDays().indexOf("1")!=-1)checkBox1.setSelected(true);
        else checkBox1.setSelected(false);
        if(flightsTable.getDays().indexOf("2")!=-1)checkBox2.setSelected(true);
        else checkBox2.setSelected(false);
        if(flightsTable.getDays().indexOf("3")!=-1)checkBox3.setSelected(true);
        else checkBox3.setSelected(false);
        if(flightsTable.getDays().indexOf("4")!=-1)checkBox4.setSelected(true);
        else checkBox4.setSelected(false);
        if(flightsTable.getDays().indexOf("5")!=-1)checkBox5.setSelected(true);
        else checkBox5.setSelected(false);
        if(flightsTable.getDays().indexOf("6")!=-1)checkBox6.setSelected(true);
        else checkBox6.setSelected(false);
        if(flightsTable.getDays().indexOf("7")!=-1)checkBox7.setSelected(true);
        else checkBox7.setSelected(false);
        addButton.setText("Применить");
    }


    public void loadChoiceBoxes() {
        ObservableList<Route> listRoutes = FXCollections.observableArrayList();
        ObservableList<Aircraft> listAircrafts = FXCollections.observableArrayList();
        ObservableList<Runway> listStrips = FXCollections.observableArrayList();
        ObservableList<Crew> listCrews = FXCollections.observableArrayList();
        Route[] routes = null;
        Aircraft[] aircrafts = null;
        Runway[] strips = null;
        Crew[] crews = null;

        Route r = new Route();
        Aircraft a = new Aircraft();
        Runway s = new Runway();
        Crew c = new Crew();

        r.setDataBaseCommand("Select");
        a.setDataBaseCommand("Select");
        s.setDataBaseCommand("Select");
        c.setDataBaseCommand("Select");

        try {
            Connector.getInstance().send(r);
            routes = (Route[]) Connector.getInstance().get();
            Connector.getInstance().send(a);
            aircrafts = (Aircraft[]) Connector.getInstance().get();
            Connector.getInstance().send(s);
            strips = (Runway[]) Connector.getInstance().get();
            Connector.getInstance().send(c);
            crews = (Crew[]) Connector.getInstance().get();
        } catch (Exception e) {
            new Message("Не удаётся получить данные", Alert.AlertType.ERROR).show();
        }

        listRoutes.addAll(routes);
        choiceBoxRoute.setItems(listRoutes);
        listAircrafts.addAll(aircrafts);
        choiceBoxAircraft.setItems(listAircrafts);
        listStrips.addAll(strips);
        choiceBoxStrip.setItems(listStrips);
        listCrews.addAll(crews);
        choiceBoxCrew.setItems(listCrews);
    }




    @FXML
    public void clearFields(){
        BorderPane borderPane = (BorderPane) FlightsTableView.getVbox().getParent();
        borderPane.setBottom(null);
        try {
            borderPane.setBottom(FXMLLoader.load(getClass().getResource("EditFlightsWindow.fxml")));
            //selectionModel = FlightsTableView.getTable().getSelectionModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
