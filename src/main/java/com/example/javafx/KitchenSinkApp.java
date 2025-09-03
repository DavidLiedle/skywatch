package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.web.WebView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.shape.*;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import java.time.LocalDate;

public class KitchenSinkApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Kitchen Sink - Widget Showcase");

        TabPane mainTabPane = new TabPane();
        
        mainTabPane.getTabs().addAll(
            createBasicControlsTab(),
            createAdvancedControlsTab(),
            createChartsTab(),
            createMediaTab(),
            createEffectsTab(),
            createLayoutsTab(),
            createAnimationTab()
        );

        Scene scene = new Scene(mainTabPane, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab createBasicControlsTab() {
        Tab tab = new Tab("Basic Controls");
        tab.setClosable(false);
        
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Basic JavaFX Controls");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        HBox buttonBox = new HBox(10);
        Button normalButton = new Button("Normal Button");
        ToggleButton toggleBtn = new ToggleButton("Toggle Me");
        RadioButton radio1 = new RadioButton("Option 1");
        RadioButton radio2 = new RadioButton("Option 2");
        ToggleGroup radioGroup = new ToggleGroup();
        radio1.setToggleGroup(radioGroup);
        radio2.setToggleGroup(radioGroup);
        radio1.setSelected(true);
        CheckBox checkBox = new CheckBox("Check me!");
        buttonBox.getChildren().addAll(normalButton, toggleBtn, radio1, radio2, checkBox);
        
        HBox textBox = new HBox(10);
        TextField textField = new TextField();
        textField.setPromptText("Enter text here...");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        textBox.getChildren().addAll(
            new Label("Text Field:"), textField,
            new Label("Password:"), passwordField
        );
        
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(4);
        textArea.setPromptText("Multi-line text area...");
        
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Option A", "Option B", "Option C", "Option D");
        comboBox.setPromptText("Select an option");
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Choice 1", "Choice 2", "Choice 3");
        choiceBox.setValue("Choice 1");
        
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(
            "List Item 1", "List Item 2", "List Item 3", "List Item 4", "List Item 5"
        );
        listView.setItems(items);
        listView.setPrefHeight(100);
        
        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);
        
        ProgressBar progressBar = new ProgressBar(0.6);
        ProgressIndicator progressIndicator = new ProgressIndicator(0.6);
        
        HBox progressBox = new HBox(20);
        progressBox.getChildren().addAll(
            new VBox(5, new Label("Progress Bar:"), progressBar),
            new VBox(5, new Label("Progress Indicator:"), progressIndicator)
        );
        
        Hyperlink hyperlink = new Hyperlink("Click me - I'm a hyperlink!");
        hyperlink.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hyperlink Clicked");
            alert.setHeaderText(null);
            alert.setContentText("You clicked the hyperlink!");
            alert.showAndWait();
        });
        
        root.getChildren().addAll(
            title,
            new Separator(),
            new Label("Buttons and Toggles:"),
            buttonBox,
            new Separator(),
            textBox,
            new Label("Text Area:"),
            textArea,
            new HBox(20, 
                new VBox(5, new Label("ComboBox:"), comboBox),
                new VBox(5, new Label("ChoiceBox:"), choiceBox)
            ),
            new Label("ListView:"),
            listView,
            new Label("Slider:"),
            slider,
            progressBox,
            hyperlink
        );
        
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }

    private Tab createAdvancedControlsTab() {
        Tab tab = new Tab("Advanced Controls");
        tab.setClosable(false);
        
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Advanced JavaFX Controls");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 25);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.CORNFLOWERBLUE);
        
        TableView<Person> tableView = new TableView<>();
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        
        tableView.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        tableView.getItems().addAll(
            new Person("John", "Doe", "john@example.com"),
            new Person("Jane", "Smith", "jane@example.com"),
            new Person("Bob", "Johnson", "bob@example.com")
        );
        tableView.setPrefHeight(150);
        
        TreeItem<String> rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);
        TreeItem<String> branch1 = new TreeItem<>("Branch 1");
        branch1.getChildren().addAll(
            new TreeItem<>("Leaf 1.1"),
            new TreeItem<>("Leaf 1.2")
        );
        TreeItem<String> branch2 = new TreeItem<>("Branch 2");
        branch2.getChildren().addAll(
            new TreeItem<>("Leaf 2.1"),
            new TreeItem<>("Leaf 2.2")
        );
        rootItem.getChildren().addAll(branch1, branch2);
        TreeView<String> treeView = new TreeView<>(rootItem);
        treeView.setPrefHeight(150);
        
        Accordion accordion = new Accordion();
        TitledPane pane1 = new TitledPane("Pane 1", new Label("Content of pane 1"));
        TitledPane pane2 = new TitledPane("Pane 2", new Label("Content of pane 2"));
        TitledPane pane3 = new TitledPane("Pane 3", new Label("Content of pane 3"));
        accordion.getPanes().addAll(pane1, pane2, pane3);
        
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(
            new Label("Left Panel"),
            new Label("Right Panel")
        );
        splitPane.setPrefHeight(100);
        
        Pagination pagination = new Pagination(5, 0);
        pagination.setPageFactory(pageIndex -> {
            VBox pageContent = new VBox();
            pageContent.getChildren().add(new Label("Page " + (pageIndex + 1)));
            return pageContent;
        });
        
        root.getChildren().addAll(
            title,
            new Separator(),
            new HBox(20,
                new VBox(5, new Label("Spinner:"), spinner),
                new VBox(5, new Label("Date Picker:"), datePicker),
                new VBox(5, new Label("Color Picker:"), colorPicker)
            ),
            new Separator(),
            new Label("TableView:"),
            tableView,
            new Label("TreeView:"),
            treeView,
            new Label("Accordion:"),
            accordion,
            new Label("SplitPane:"),
            splitPane,
            new Label("Pagination:"),
            pagination
        );
        
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }

    private Tab createChartsTab() {
        Tab tab = new Tab("Charts");
        tab.setClosable(false);
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);
        
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
            new PieChart.Data("Desktop", 60),
            new PieChart.Data("Mobile", 30),
            new PieChart.Data("Tablet", 10)
        );
        pieChart.setTitle("Device Usage");
        pieChart.setPrefSize(400, 300);
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sales Data");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("2023");
        series1.getData().addAll(
            new XYChart.Data<>("Q1", 25000),
            new XYChart.Data<>("Q2", 30000),
            new XYChart.Data<>("Q3", 28000),
            new XYChart.Data<>("Q4", 35000)
        );
        barChart.getData().add(series1);
        barChart.setPrefSize(400, 300);
        
        NumberAxis xAxis2 = new NumberAxis();
        NumberAxis yAxis2 = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis2, yAxis2);
        lineChart.setTitle("Stock Price");
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("AAPL");
        series2.getData().addAll(
            new XYChart.Data<>(1, 150),
            new XYChart.Data<>(2, 155),
            new XYChart.Data<>(3, 148),
            new XYChart.Data<>(4, 162),
            new XYChart.Data<>(5, 170)
        );
        lineChart.getData().add(series2);
        lineChart.setPrefSize(400, 300);
        
        NumberAxis xAxis3 = new NumberAxis();
        NumberAxis yAxis3 = new NumberAxis();
        AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis3, yAxis3);
        areaChart.setTitle("Temperature");
        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.setName("Avg Temp");
        series3.getData().addAll(
            new XYChart.Data<>(1, 10),
            new XYChart.Data<>(2, 15),
            new XYChart.Data<>(3, 20),
            new XYChart.Data<>(4, 18),
            new XYChart.Data<>(5, 25)
        );
        areaChart.getData().add(series3);
        areaChart.setPrefSize(400, 300);
        
        grid.add(pieChart, 0, 0);
        grid.add(barChart, 1, 0);
        grid.add(lineChart, 0, 1);
        grid.add(areaChart, 1, 1);
        
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }

    private Tab createMediaTab() {
        Tab tab = new Tab("Media & Web");
        tab.setClosable(false);
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        
        Text title = new Text("Media and Web Components");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        WebView webView = new WebView();
        webView.getEngine().loadContent(
            "<html><body style='font-family: Arial; padding: 20px;'>" +
            "<h2>WebView Component</h2>" +
            "<p>This is a WebView displaying HTML content.</p>" +
            "<ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul>" +
            "<button onclick='alert(\"Button clicked!\")'>Click Me</button>" +
            "</body></html>"
        );
        webView.setPrefHeight(300);
        
        Label imageLabel = new Label("Image Display:");
        Rectangle imagePlaceholder = new Rectangle(400, 200);
        imagePlaceholder.setFill(LinearGradient.valueOf(
            "linear-gradient(from 0% 0% to 100% 100%, #3f51b5 0%, #2196f3 100%)"
        ));
        imagePlaceholder.setArcWidth(10);
        imagePlaceholder.setArcHeight(10);
        VBox imageBox = new VBox(10, imageLabel, imagePlaceholder);
        
        Label mediaLabel = new Label("Media Player Placeholder:");
        Rectangle mediaPlaceholder = new Rectangle(600, 300);
        mediaPlaceholder.setFill(Color.DARKGRAY);
        mediaPlaceholder.setStroke(Color.BLACK);
        HBox mediaControls = new HBox(10);
        mediaControls.setAlignment(Pos.CENTER);
        mediaControls.getChildren().addAll(
            new Button("Play"),
            new Button("Pause"),
            new Button("Stop"),
            new Slider(0, 100, 0)
        );
        VBox mediaBox = new VBox(10, mediaLabel, mediaPlaceholder, mediaControls);
        
        root.getChildren().addAll(
            title,
            new Separator(),
            webView,
            new Separator(),
            imageBox,
            new Separator(),
            mediaBox
        );
        
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }

    private Tab createEffectsTab() {
        Tab tab = new Tab("Effects");
        tab.setClosable(false);
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(30);
        grid.setVgap(30);
        
        Button dropShadowBtn = new Button("Drop Shadow");
        dropShadowBtn.setEffect(new DropShadow(20, Color.BLACK));
        
        Button innerShadowBtn = new Button("Inner Shadow");
        innerShadowBtn.setEffect(new InnerShadow(10, Color.BLACK));
        
        Button glowBtn = new Button("Glow Effect");
        Glow glow = new Glow();
        glow.setLevel(0.8);
        glowBtn.setEffect(glow);
        
        Button reflectionBtn = new Button("Reflection");
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7);
        reflectionBtn.setEffect(reflection);
        
        Button blurBtn = new Button("Gaussian Blur");
        blurBtn.setEffect(new GaussianBlur(3));
        
        Button bloomBtn = new Button("Bloom Effect");
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.3);
        bloomBtn.setEffect(bloom);
        
        Rectangle gradientRect = new Rectangle(150, 100);
        gradientRect.setFill(LinearGradient.valueOf(
            "linear-gradient(from 0% 0% to 100% 100%, red 0%, yellow 50%, green 100%)"
        ));
        
        Circle radialGradientCircle = new Circle(50);
        radialGradientCircle.setFill(javafx.scene.paint.RadialGradient.valueOf(
            "radial-gradient(center 50% 50%, radius 50%, red 0%, yellow 50%, green 100%)"
        ));
        
        grid.add(new VBox(10, new Label("Drop Shadow:"), dropShadowBtn), 0, 0);
        grid.add(new VBox(10, new Label("Inner Shadow:"), innerShadowBtn), 1, 0);
        grid.add(new VBox(10, new Label("Glow:"), glowBtn), 2, 0);
        grid.add(new VBox(10, new Label("Reflection:"), reflectionBtn), 0, 1);
        grid.add(new VBox(10, new Label("Gaussian Blur:"), blurBtn), 1, 1);
        grid.add(new VBox(10, new Label("Bloom:"), bloomBtn), 2, 1);
        grid.add(new VBox(10, new Label("Linear Gradient:"), gradientRect), 0, 2);
        grid.add(new VBox(10, new Label("Radial Gradient:"), radialGradientCircle), 1, 2);
        
        tab.setContent(grid);
        return tab;
    }

    private Tab createLayoutsTab() {
        Tab tab = new Tab("Layouts");
        tab.setClosable(false);
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new Label("TOP"));
        borderPane.setBottom(new Label("BOTTOM"));
        borderPane.setLeft(new Label("LEFT"));
        borderPane.setRight(new Label("RIGHT"));
        borderPane.setCenter(new Label("CENTER"));
        borderPane.setPrefHeight(150);
        borderPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(
            new Button("HBox 1"),
            new Button("HBox 2"),
            new Button("HBox 3")
        );
        hbox.setStyle("-fx-border-color: blue; -fx-border-width: 1; -fx-padding: 10;");
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
            new Button("VBox 1"),
            new Button("VBox 2"),
            new Button("VBox 3")
        );
        vbox.setStyle("-fx-border-color: green; -fx-border-width: 1; -fx-padding: 10;");
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gridPane.add(new Button("Grid " + i + "," + j), j, i);
            }
        }
        gridPane.setStyle("-fx-border-color: red; -fx-border-width: 1;");
        
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(10));
        for (int i = 1; i <= 10; i++) {
            flowPane.getChildren().add(new Button("Flow " + i));
        }
        flowPane.setStyle("-fx-border-color: purple; -fx-border-width: 1;");
        flowPane.setPrefWrapLength(300);
        
        TilePane tilePane = new TilePane();
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPadding(new Insets(10));
        for (int i = 1; i <= 6; i++) {
            tilePane.getChildren().add(new Button("Tile " + i));
        }
        tilePane.setStyle("-fx-border-color: orange; -fx-border-width: 1;");
        
        root.getChildren().addAll(
            new Label("BorderPane Layout:"),
            borderPane,
            new Label("HBox Layout:"),
            hbox,
            new Label("VBox Layout:"),
            vbox,
            new Label("GridPane Layout:"),
            gridPane,
            new Label("FlowPane Layout:"),
            flowPane,
            new Label("TilePane Layout:"),
            tilePane
        );
        
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }

    private Tab createAnimationTab() {
        Tab tab = new Tab("Animations");
        tab.setClosable(false);
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        Rectangle rect = new Rectangle(100, 50, Color.BLUE);
        Button rotateBtn = new Button("Rotate Animation");
        rotateBtn.setOnAction(e -> {
            RotateTransition rotate = new RotateTransition(Duration.seconds(2), rect);
            rotate.setByAngle(360);
            rotate.setCycleCount(1);
            rotate.play();
        });
        
        Circle circle = new Circle(30, Color.RED);
        Button translateBtn = new Button("Translate Animation");
        translateBtn.setOnAction(e -> {
            TranslateTransition translate = new TranslateTransition(Duration.seconds(2), circle);
            translate.setFromX(0);
            translate.setToX(200);
            translate.setAutoReverse(true);
            translate.setCycleCount(2);
            translate.play();
        });
        
        Rectangle fadeRect = new Rectangle(100, 50, Color.GREEN);
        Button fadeBtn = new Button("Fade Animation");
        fadeBtn.setOnAction(e -> {
            FadeTransition fade = new FadeTransition(Duration.seconds(2), fadeRect);
            fade.setFromValue(1.0);
            fade.setToValue(0.1);
            fade.setAutoReverse(true);
            fade.setCycleCount(2);
            fade.play();
        });
        
        Rectangle scaleRect = new Rectangle(100, 50, Color.ORANGE);
        Button scaleBtn = new Button("Scale Animation");
        scaleBtn.setOnAction(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.seconds(2), scaleRect);
            scale.setToX(2);
            scale.setToY(2);
            scale.setAutoReverse(true);
            scale.setCycleCount(2);
            scale.play();
        });
        
        Rectangle parallelRect = new Rectangle(100, 50, Color.PURPLE);
        Button parallelBtn = new Button("Parallel Animation");
        parallelBtn.setOnAction(e -> {
            RotateTransition rotate = new RotateTransition(Duration.seconds(2), parallelRect);
            rotate.setByAngle(360);
            
            FadeTransition fade = new FadeTransition(Duration.seconds(2), parallelRect);
            fade.setFromValue(1.0);
            fade.setToValue(0.3);
            fade.setAutoReverse(true);
            
            ParallelTransition parallel = new ParallelTransition(rotate, fade);
            parallel.setCycleCount(2);
            parallel.play();
        });
        
        root.getChildren().addAll(
            new VBox(10, rect, rotateBtn),
            new VBox(10, circle, translateBtn),
            new VBox(10, fadeRect, fadeBtn),
            new VBox(10, scaleRect, scaleBtn),
            new VBox(10, parallelRect, parallelBtn)
        );
        
        tab.setContent(root);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static class Person {
        private final javafx.beans.property.SimpleStringProperty firstName;
        private final javafx.beans.property.SimpleStringProperty lastName;
        private final javafx.beans.property.SimpleStringProperty email;
        
        public Person(String firstName, String lastName, String email) {
            this.firstName = new javafx.beans.property.SimpleStringProperty(firstName);
            this.lastName = new javafx.beans.property.SimpleStringProperty(lastName);
            this.email = new javafx.beans.property.SimpleStringProperty(email);
        }
        
        public javafx.beans.property.StringProperty firstNameProperty() { return firstName; }
        public javafx.beans.property.StringProperty lastNameProperty() { return lastName; }
        public javafx.beans.property.StringProperty emailProperty() { return email; }
    }
}