package window.managerWindows.tableWindows;

import essences.Route;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import webConnection.Connector;
import window.Message;
import window.managerWindows.tableWindows.tables.RoutesTable;

import java.io.IOException;

public class EditRoutesWindow {

    @FXML
    private BorderPane borderPane;

    private TableView<RoutesTable> tableView;
    private RoutesTable routesTable;
    private TableView.TableViewSelectionModel<RoutesTable> selectionModel;


    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;
    @FXML
    private TextField tf4;

    @FXML
    private Label l1;
    @FXML
    private Label l2;
    @FXML
    private Label l3;
    @FXML
    private Label l4;

    @FXML
    public void initialize() {
        try {
            borderPane.setTop(FXMLLoader.load(getClass().getResource("RoutesTableView.fxml")));
            borderPane.setMaxHeight(600);
            borderPane.setPrefHeight(500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BorderPane b = (BorderPane) borderPane.getTop();
        b.setBottom(null);

        tf3.textProperty().addListener((o, oldV, newV) -> {
            if (newV.length() == 2) tf3.setText(newV + " ч, ");
            if (newV.length() == 8) tf3.setText(newV + " мин");
            if (newV.length() > 12) tf3.setText(oldV);
            if (newV.length() < 6 && newV.length() > 2) tf3.setText(oldV);
            if (newV.length() < 12 && newV.length() > 8) tf3.setText(oldV);
        });

        tf4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (arg2.length() == 1) {
                            tf4.setText(arg2 + " км");
                        }
                        if (arg2.length() == 4) tf4.positionCaret(1);
                    }
                });
            }
        });
        borderPane.addEventHandler(MouseEvent.ANY, event -> {
            if (tableView == null) {
                BorderPane bp = (BorderPane) borderPane.getTop();
                tableView = (TableView<RoutesTable>) bp.getCenter();
                selectionModel = tableView.getSelectionModel();
                tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event2 -> {
                    routesTable = selectionModel.getSelectedItem();
                    if (routesTable != null) {
                        tf1.setText(routesTable.getFrom().split(" ")[1]);
                        tf2.setText(routesTable.getTo().split(" ")[1]);
                        tf3.setText(routesTable.getFlightTime());
                        tf4.setText(routesTable.getDistance());

                    }
                });
            }
        });

    }

    @FXML
    public void add() {
        try {
            if (!checkTextFields()) return;
        } catch (Exception e) {
            return;
        }
        Object o = sendRequest("Add");
        if (o instanceof String && !((String) o).equals("ok"))
            new Message("Не удалось добавить пользователя в базу данных!", Alert.AlertType.ERROR).show();
        else {
            new Message("Маршрут был добавлен", Alert.AlertType.INFORMATION).show();
            updateWindow();
        }
    }

    public Object sendRequest(String requestType) {
        Route r = new Route();
        r.setDataBaseCommand(requestType);
        r.setFrom(tf1.getText());
        r.setTo(tf2.getText());
        if(requestType.equals("Update"))r.setId(routesTable.getId());
        r.setFlightTime(tf3.getText().split(" ")[0] + ":" + tf3.getText().split(" ")[2]);
        if (!tf4.getText().isEmpty()) r.setDistance(Integer.parseInt(tf4.getText().split(" ")[0]));
        Connector.getInstance().send(r);
        Object o = Connector.getInstance().get();
        return o;
    }

    @FXML
    public void edit() {
        try {
            if (!checkTextFields()) return;
        } catch (Exception e) {
            return;
        }
        Object o = sendRequest("Update");
        if (o instanceof String && !((String) o).equals("ok"))
            new Message("Не удалось изменить данные о пользователе!", Alert.AlertType.ERROR).show();
        else {
            new Message("Маршрут был изменён", Alert.AlertType.INFORMATION).show();
            updateWindow();
        }
    }

    @FXML
    public void back() {
        BorderPane borderPane = (BorderPane) this.borderPane.getParent();
        try {
            borderPane.setBottom(FXMLLoader.load(getClass().getResource("RoutesTableView.fxml")));
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("FlightsTableView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkTextFields() throws Exception {
        if (tf1.getText().isEmpty()) {
            l1.setVisible(true);
            return false;
        } else l1.setVisible(false);
        if (tf2.getText().isEmpty()) {
            l2.setVisible(true);
            return false;
        } else l2.setVisible(false);
        if (tf3.getText().isEmpty()) {
            l3.setVisible(true);
            return false;
        } else l3.setVisible(false);
        try {
            Integer.parseInt(tf3.getText().split(" ")[0]);
            Integer.parseInt(tf3.getText().split(" ")[2]);
            l3.setVisible(false);
        } catch (NumberFormatException e) {
            l3.setVisible(true);
            return false;
        }
        if (Integer.parseInt(tf3.getText().split(" ")[0]) > 23 || Integer.parseInt(tf3.getText().split(" ")[2]) > 59) {
            l3.setVisible(true);
            return false;
        } else {
            l3.setVisible(false);
        }

        if (!tf4.getText().isEmpty()) {
            try {
                Integer.parseInt(tf4.getText().split(" ")[0]);
                l4.setVisible(false);
            } catch (NumberFormatException e) {
                l4.setVisible(true);
                return false;
            }
        }
        if (!tf4.getText().isEmpty() && (tf4.getText().split(" ").length > 2 || !tf4.getText().split(" ")[1].equals("км"))) {
            l4.setVisible(true);
            return false;
        } else l4.setVisible(false);
        return true;
    }


    public void updateWindow() {
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        try {
            borderPane.setTop(null);
            borderPane.setTop(FXMLLoader.load(getClass().getResource("RoutesTableView.fxml")));
            BorderPane borderPane = (BorderPane) this.borderPane.getTop();
            borderPane.setBottom(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BorderPane bp = (BorderPane) borderPane.getTop();
        tableView = (TableView<RoutesTable>) bp.getCenter();
        selectionModel = tableView.getSelectionModel();
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event2 -> {
            routesTable = selectionModel.getSelectedItem();
            if (routesTable != null) {
                tf1.setText(routesTable.getFrom().split(" ")[1]);
                tf2.setText(routesTable.getTo().split(" ")[1]);
                tf3.setText(routesTable.getFlightTime());
                tf4.setText(routesTable.getDistance());

            }
        });
    }
}
