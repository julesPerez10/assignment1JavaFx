package com.example.assignmentjava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Football Statistics");

        // Load custom icon
        primaryStage.getIcons().add(new Image("file:/C:/Users/USUARIO/Documents/assignmentJava/src/main/resources/com/example/assignmentjava/soccer-ball.png"));

        // Test database connection
        testDatabaseConnection();

        // Create initial scene with chart
        Scene chartScene = createChartScene();
        primaryStage.setScene(chartScene);
        primaryStage.show();
    }

    private void testDatabaseConnection() {
        ResultSet rs = DatabaseUtil.fetchData("SELECT 1");
        if (rs != null) {
            System.out.println("Database connection test was successful.");
        } else {
            System.out.println("Database connection test failed.");
        }
    }

    private Scene createChartScene() {
        BorderPane root = new BorderPane();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Goals and Assists by Player and Season");

        XYChart.Series<String, Number> goalsSeries = new XYChart.Series<>();
        goalsSeries.setName("Goals");

        XYChart.Series<String, Number> assistsSeries = new XYChart.Series<>();
        assistsSeries.setName("Assists");

        // Fetch data from the database
        ResultSet rs = DatabaseUtil.fetchData("SELECT * FROM football_statistics");
        try {
            while (rs.next()) {
                String playerSeason = rs.getString("player") + " (" + rs.getInt("season") + ")";
                goalsSeries.getData().add(new XYChart.Data<>(playerSeason, rs.getInt("goals")));
                assistsSeries.getData().add(new XYChart.Data<>(playerSeason, rs.getInt("assists")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().addAll(goalsSeries, assistsSeries);
        root.setCenter(barChart);

        // Navigation buttons
        HBox bottomButtons = new HBox(10);
        Button switchToTableButton = new Button("Show Table");
        switchToTableButton.setOnAction(e -> primaryStage.setScene(createTableScene()));
        bottomButtons.getChildren().add(switchToTableButton);
        Button switchToPieChartButton = new Button("Show Pie Chart");
        switchToPieChartButton.setOnAction(e -> primaryStage.setScene(createPieChartScene()));
        bottomButtons.getChildren().add(switchToPieChartButton);
        root.setBottom(bottomButtons);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("file:/C:/Users/USUARIO/Documents/assignmentJava/src/main/resources/com/example/assignmentjava/styles.css");
        return scene;
    }

    private Scene createTableScene() {
        BorderPane root = new BorderPane();

        TableView<FootballStatistics> tableView = new TableView<>();
        TableColumn<FootballStatistics, String> playerColumn = new TableColumn<>("Player");
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("player"));

        TableColumn<FootballStatistics, Integer> seasonColumn = new TableColumn<>("Season");
        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("season"));

        TableColumn<FootballStatistics, Integer> goalsColumn = new TableColumn<>("Goals");
        goalsColumn.setCellValueFactory(new PropertyValueFactory<>("goals"));

        TableColumn<FootballStatistics, Integer> assistsColumn = new TableColumn<>("Assists");
        assistsColumn.setCellValueFactory(new PropertyValueFactory<>("assists"));

        tableView.getColumns().addAll(playerColumn, seasonColumn, goalsColumn, assistsColumn);

        // Fetch data from the database and populate the table
        ResultSet rs = DatabaseUtil.fetchData("SELECT * FROM football_statistics");
        try {
            while (rs.next()) {
                tableView.getItems().add(new FootballStatistics(rs.getString("player"), rs.getInt("season"), rs.getInt("goals"), rs.getInt("assists")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Navigation buttons
        HBox bottomButtons = new HBox(10);
        Button switchToChartButton = new Button("Show Chart");
        switchToChartButton.setOnAction(e -> primaryStage.setScene(createChartScene()));
        bottomButtons.getChildren().add(switchToChartButton);
        Button switchToPieChartButton = new Button("Show Pie Chart");
        switchToPieChartButton.setOnAction(e -> primaryStage.setScene(createPieChartScene()));
        bottomButtons.getChildren().add(switchToPieChartButton);
        root.setBottom(bottomButtons);

        root.setCenter(tableView);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("file:/C:/Users/USUARIO/Documents/assignmentJava/src/main/resources/com/example/assignmentjava/styles.css");
        return scene;
    }

    private Scene createPieChartScene() {
        BorderPane root = new BorderPane();

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Goals and Assists by Player and Season");

        // Fetch data from the database
        ResultSet rs = DatabaseUtil.fetchData("SELECT * FROM football_statistics");
        try {
            while (rs.next()) {
                String playerSeason = rs.getString("player") + " (" + rs.getInt("season") + ")";
                int goals = rs.getInt("goals");
                int assists = rs.getInt("assists");

                pieChart.getData().add(new PieChart.Data(playerSeason + " - Goals: " + goals, goals));
                pieChart.getData().add(new PieChart.Data(playerSeason + " - Assists: " + assists, assists));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        root.setCenter(pieChart);

        // Navigation buttons
        HBox bottomButtons = new HBox(10);
        Button switchToChartButton = new Button("Show Chart");
        switchToChartButton.setOnAction(e -> primaryStage.setScene(createChartScene()));
        bottomButtons.getChildren().add(switchToChartButton);
        Button switchToTableButton = new Button("Show Table");
        switchToTableButton.setOnAction(e -> primaryStage.setScene(createTableScene()));
        bottomButtons.getChildren().add(switchToTableButton);
        root.setBottom(bottomButtons);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("file:/C:/Users/USUARIO/Documents/assignmentJava/src/main/resources/com/example/assignmentjava/styles.css");
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

