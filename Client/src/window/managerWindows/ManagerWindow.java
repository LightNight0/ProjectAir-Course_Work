package window.managerWindows;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import window.MenuBarFunctions;
import window.managerWindows.tableWindows.tables.FlightsTable;
import window.managerWindows.tableWindows.tables.Tablo;

import java.io.IOException;

public class ManagerWindow {

    private MenuBarFunctions menuBarFunctions;

    @FXML
    private TableView table;
    private static TableView tablo;
    public static TableView getTablo(){return tablo;}

    private static BorderPane borderPane;
    public static BorderPane getBorderPane(){return borderPane;}

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        menuBarFunctions = new MenuBarFunctions();
        try {
            mainBorderPane.setCenter(FXMLLoader.load(getClass().getResource("tableWindows/FlightsTableView.fxml")));
            mainBorderPane.setBottom(FXMLLoader.load(getClass().getResource("tableWindows/RoutesTableView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane = mainBorderPane;
        tablo = table;
        table.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(list);
        table.getColumns().addAll(startTime,endTime,route,status,view);
        table.setEditable(true);
    }


    // Обработка функций menu bar

    @FXML
    public void closeProgramm() {
        menuBarFunctions.closeProgramm();
    }



    @FXML
    private MenuItem fullscreenMenuItem;
    @FXML
    public void setFullScreen() {
        menuBarFunctions.setFullScreen(fullscreenMenuItem);
    }


    @FXML
    public void about() {
        menuBarFunctions.about();
    }

    @FXML
    public void loginOut(){
        menuBarFunctions.loginOut();
    }


    private boolean routesWindow = true;
    @FXML
    private MenuItem routesWindowMenuItem;
    @FXML
    public void routesWindow() {
        if (routesWindow) {
            routesWindow = false;
            mainBorderPane.setBottom(null);
            routesWindowMenuItem.setText("окно маршрутов (выкл)");
        } else {
            routesWindow = true;
            try {
                mainBorderPane.setBottom(FXMLLoader.load(getClass().getResource("tableWindows/RoutesTableView.fxml")));
                routesWindowMenuItem.setText("окно маршрутов (вкл)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<Tablo> list;


    private static TableColumn<Tablo, String> startTime;
    private static TableColumn<Tablo, String> endTime;
    private static TableColumn<Tablo, String> route;
    private static TableColumn<Tablo, Status> status;
    private static TableColumn<Tablo, Boolean> view;

    public ManagerWindow(){
        startTime = new TableColumn("время вылета");
        endTime = new TableColumn("время прибытия");
        route = new TableColumn("маршрут");
        status = new TableColumn("статус");
        view = new TableColumn("показ");

        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        route.setCellValueFactory(new PropertyValueFactory<>("route"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        startTime.setCellFactory(TextFieldTableCell.<Tablo>forTableColumn());
        route.setCellFactory(TextFieldTableCell.<Tablo>forTableColumn());
        endTime.setCellFactory(TextFieldTableCell.<Tablo>forTableColumn());

        /*Calendar day = Calendar.getInstance();
        day.setTime(new Date());*/
        /*try {
            list = FlightsTable.getList(String.valueOf(Calendar.DAY_OF_WEEK));
        } catch (Exception e) {
            new Message(e.getMessage(), Alert.AlertType.INFORMATION).show();
        }*/
        list = FlightsTable.getTablos();

        ObservableList<Status> statuses = FXCollections.observableArrayList(Status.values());
        status.setCellFactory(ComboBoxTableCell.forTableColumn(statuses));
        status.setEditable(true);
        status.setStyle("-fx-text-fill: red;");

        status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tablo, Status>, ObservableValue<Status>>() {

            @Override
            public ObservableValue<Status> call(TableColumn.CellDataFeatures<Tablo, Status> param) {
                Tablo person = param.getValue();
                // F,M
                String statusCode = person.getStatus();
                Status status = Status.getByCode(statusCode);
                return new SimpleObjectProperty<Status>(status);
            }
        });

        status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tablo, Status>, ObservableValue<Status>>() {

            @Override
            public ObservableValue<Status> call(TableColumn.CellDataFeatures<Tablo, Status> param) {
                Tablo tablo = param.getValue();
                String statusCode = tablo.getStatus();
                Status status = Status.getByCode(statusCode);
                return new SimpleObjectProperty<Status>(status);
            }
        });


        view.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tablo, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tablo, Boolean> param) {
                Tablo tablo = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(tablo.isView());

                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        tablo.setView(newValue);
                    }
                });
                return booleanProp;
            }
        });

        view.setCellFactory(new Callback<TableColumn<Tablo, Boolean>, //
                TableCell<Tablo, Boolean>>() {
            @Override
            public TableCell<Tablo, Boolean> call(TableColumn<Tablo, Boolean> p) {
                CheckBoxTableCell<Tablo, Boolean> cell = new CheckBoxTableCell<Tablo, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

    }

    public enum Status{
        otl("Отл", "отложен"), z("З", "задерживается"), otm("Отм","Отменён");

        private String code;
        private String text;

        private Status(String code, String text) {
            this.code = code;
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public String getText() {
            return text;
        }

        public static Status getByCode(String statusCode) {
            for (Status g : Status.values()) {
                if (g.code.equals(statusCode)) {
                    return g;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }
}
