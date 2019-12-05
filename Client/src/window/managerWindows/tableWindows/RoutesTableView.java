package window.managerWindows.tableWindows;

import essences.Flight;
import essences.Route;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import webConnection.Connector;
import window.Message;
import window.managerWindows.tableWindows.tables.RoutesTable;

import java.io.IOException;

public class RoutesTableView {

    @FXML
    TableView<RoutesTable> routesTableView;

    private ObservableList<RoutesTable> list = null;

    private TableView.TableViewSelectionModel<RoutesTable> selectionModel;

    private static TableColumn from;
    private static TableColumn to;
    private static TableColumn flightTime;
    private static TableColumn distance;

    public RoutesTableView() {
        from = new TableColumn("откуда");
        to = new TableColumn("куда");
        flightTime = new TableColumn("время полёта");
        distance = new TableColumn("дистанция");

        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        to.setCellValueFactory(new PropertyValueFactory<>("to"));
        flightTime.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
        distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        from.setCellFactory(TextFieldTableCell.<RoutesTable>forTableColumn());
        to.setCellFactory(TextFieldTableCell.<RoutesTable>forTableColumn());
        flightTime.setCellFactory(TextFieldTableCell.<RoutesTable>forTableColumn());
        distance.setCellFactory(TextFieldTableCell.<RoutesTable>forTableColumn());


        try {
            list = RoutesTable.getList();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }


    @FXML
    public void initialize() {
        routesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        routesTableView.setItems(list);
        routesTableView.getColumns().addAll(from, to, flightTime, distance);
        selectionModel = routesTableView.getSelectionModel();
    }



    @FXML
    public void edit() {
        BorderPane borderPane = (BorderPane) routesTableView.getParent().getParent();
        try {
            borderPane.setCenter(FXMLLoader.load(getClass().getResource("EditRoutesWindow.fxml")));
            borderPane.setBottom(null);
            //borderPane.setCenter(FXMLLoader.load(getClass().getResource("RoutesTableView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void delete() {
        RoutesTable routesTable = selectionModel.getSelectedItem();
        if(selectionModel.getSelectedItem()==null){
            new Message("Вы не выбрали строку для удаления!",Alert.AlertType.ERROR).show();
            return;
        }
        Route r = new Route();
        Flight f = new Flight();
        f.setDataBaseCommand("Select");
        f.setRoute(routesTable.getId());
        Connector.getInstance().send(f);
        Object o = Connector.getInstance().get();
        if(o instanceof Flight[]){
            new Message("Данный маршрут используется в рейсе на "+((Flight[]) o)[0].getStartTime(),Alert.AlertType.ERROR).show();
        }
        else {
            r.setDataBaseCommand("Delete");
            r.setId(routesTable.getId());
            Connector.getInstance().send(r);
            String str = (String) Connector.getInstance().get();
            if (str.equals("ok")) {
                System.out.println("ok");
                list.remove(selectionModel.getSelectedItem());                      //вывод ошибок
            } else System.out.println(str);
        }
    }
}
