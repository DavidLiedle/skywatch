package com.example.javafx;

import javafx.application.Application;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Duration;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.canvas.*;
import javafx.beans.property.*;
import javafx.scene.input.KeyCombination;

public class ModernDashboard extends Application {
    
    private static final Color COCKPIT_GREEN = Color.rgb(0, 255, 0);
    private static final Color COCKPIT_AMBER = Color.rgb(255, 191, 0);
    private static final Color COCKPIT_RED = Color.rgb(255, 0, 0);
    private static final Color COCKPIT_CYAN = Color.rgb(0, 255, 255);
    private static final Color COCKPIT_DARK = Color.rgb(10, 10, 15);
    private static final Color COCKPIT_PANEL = Color.rgb(30, 30, 35);
    
    private Timeline mainTimeline;
    private Random random = new Random();
    
    // Server metrics (simulated)
    private DoubleProperty cpuLoad = new SimpleDoubleProperty(45);
    private DoubleProperty memoryUsage = new SimpleDoubleProperty(62);
    private DoubleProperty diskIO = new SimpleDoubleProperty(30);
    private DoubleProperty networkIn = new SimpleDoubleProperty(250);
    private DoubleProperty networkOut = new SimpleDoubleProperty(180);
    private DoubleProperty serverTemp = new SimpleDoubleProperty(65);
    private IntegerProperty activeConnections = new SimpleIntegerProperty(1247);
    private DoubleProperty responseTime = new SimpleDoubleProperty(23);
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SKYWATCH - System Kontrol Yearning Wideband Analysis Terminal Control Hub");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0A0A0F;");
        
        // Top panel - Warning systems and status
        HBox topPanel = createTopPanel();
        root.setTop(topPanel);
        
        // Center - Main instrument cluster
        GridPane mainInstruments = createMainInstrumentPanel();
        root.setCenter(mainInstruments);
        
        // Bottom - System controls and readouts
        HBox bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);
        
        // Left - Vertical instrument stack
        VBox leftPanel = createLeftPanel();
        root.setLeft(leftPanel);
        
        // Right - Alert and diagnostic panel
        VBox rightPanel = createRightPanel();
        root.setRight(rightPanel);
        
        Scene scene = new Scene(root);
        scene.setFill(COCKPIT_DARK);
        
        // Add keyboard handler for exit
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                primaryStage.setFullScreen(false);
                primaryStage.close();
                System.exit(0);
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        startSimulation();
    }
    
    private HBox createTopPanel() {
        HBox panel = new HBox(20);
        panel.setPadding(new Insets(10));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1f, #0a0a0f); " +
                       "-fx-border-color: #00ff00; -fx-border-width: 0 0 1 0;");
        
        // Master Caution Light
        VBox cautionBox = createWarningLight("MASTER\nCAUTION", COCKPIT_AMBER, false);
        
        // System Status Indicators
        HBox systemLights = new HBox(10);
        systemLights.getChildren().addAll(
            createStatusLight("APU", true),
            createStatusLight("HYD", true),
            createStatusLight("ELEC", true),
            createStatusLight("FUEL", true),
            createStatusLight("ENV", true),
            createStatusLight("FMC", true)
        );
        
        // Digital Clock
        Label clockLabel = new Label();
        clockLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 24));
        clockLabel.setTextFill(COCKPIT_GREEN);
        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            clockLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }));
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
        
        // Server Status Display
        VBox serverStatus = new VBox(5);
        Label serverLabel = new Label("SERVER CLUSTER STATUS");
        serverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        serverLabel.setTextFill(COCKPIT_CYAN);
        Label statusText = new Label("OPERATIONAL");
        statusText.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        statusText.setTextFill(COCKPIT_GREEN);
        serverStatus.getChildren().addAll(serverLabel, statusText);
        serverStatus.setAlignment(Pos.CENTER);
        
        panel.getChildren().addAll(cautionBox, systemLights, clockLabel, serverStatus);
        return panel;
    }
    
    private GridPane createMainInstrumentPanel() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setAlignment(Pos.CENTER);
        
        // CPU Load Gauge (like Attitude Indicator)
        Node cpuGauge = createArtificialHorizon("CPU LOAD", cpuLoad);
        grid.add(cpuGauge, 0, 0);
        
        // Memory Gauge (like Altimeter)
        Node memGauge = createAltimeter("MEMORY", memoryUsage);
        grid.add(memGauge, 1, 0);
        
        // Network I/O (like Airspeed Indicator)
        Node netGauge = createAirspeedIndicator("NETWORK I/O", networkIn, networkOut);
        grid.add(netGauge, 2, 0);
        
        // Disk I/O Gauge (like Vertical Speed)
        Node diskGauge = createVerticalSpeed("DISK I/O", diskIO);
        grid.add(diskGauge, 0, 1);
        
        // Server Temperature (like Engine Temp)
        Node tempGauge = createEngineGauge("TEMP °C", serverTemp, 100);
        grid.add(tempGauge, 1, 1);
        
        // Response Time (like Heading Indicator)
        Node responseGauge = createHeadingIndicator("RESPONSE MS", responseTime);
        grid.add(responseGauge, 2, 1);
        
        return grid;
    }
    
    private Node createArtificialHorizon(String title, DoubleProperty value) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(COCKPIT_CYAN);
        
        StackPane gauge = new StackPane();
        gauge.setPrefSize(200, 200);
        
        // Background circle
        Circle bg = new Circle(100, COCKPIT_PANEL);
        bg.setStroke(COCKPIT_GREEN);
        bg.setStrokeWidth(2);
        
        // Horizon line
        Rectangle horizon = new Rectangle(180, 2, COCKPIT_GREEN);
        
        // Sky/Ground indicator
        Rectangle sky = new Rectangle(180, 90);
        sky.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(0, 50, 100)),
            new Stop(1, Color.rgb(0, 30, 60))));
        sky.setTranslateY(-45);
        
        Rectangle ground = new Rectangle(180, 90);
        ground.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.rgb(80, 50, 20)),
            new Stop(1, Color.rgb(60, 40, 15))));
        ground.setTranslateY(45);
        
        // Value display
        Label valueLabel = new Label();
        valueLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 24));
        valueLabel.setTextFill(COCKPIT_GREEN);
        valueLabel.textProperty().bind(value.asString("%.0f%%"));
        
        // Pitch ladder
        Group pitchLadder = new Group();
        for (int i = -40; i <= 40; i += 10) {
            if (i != 0) {
                Line line = new Line(-30, 0, 30, 0);
                line.setStroke(COCKPIT_GREEN);
                line.setTranslateY(i * 2);
                
                Text text = new Text(String.valueOf(Math.abs(i)));
                text.setFill(COCKPIT_GREEN);
                text.setFont(Font.font("Monospaced", 10));
                text.setTranslateX(35);
                text.setTranslateY(i * 2 + 3);
                
                pitchLadder.getChildren().addAll(line, text);
            }
        }
        
        // Animate based on CPU load
        RotateTransition rotate = new RotateTransition(Duration.seconds(4), pitchLadder);
        rotate.setByAngle(360);
        rotate.setCycleCount(Timeline.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.play();
        
        gauge.getChildren().addAll(bg, sky, ground, horizon, pitchLadder, valueLabel);
        
        container.getChildren().addAll(titleLabel, gauge);
        return container;
    }
    
    private Node createAltimeter(String title, DoubleProperty value) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(COCKPIT_CYAN);
        
        StackPane gauge = new StackPane();
        gauge.setPrefSize(200, 200);
        
        // Create circular gauge
        Circle bg = new Circle(100, COCKPIT_PANEL);
        bg.setStroke(COCKPIT_GREEN);
        bg.setStrokeWidth(2);
        
        // Add tick marks
        Group ticks = new Group();
        for (int i = 0; i < 10; i++) {
            Line tick = new Line(0, -95, 0, -85);
            tick.setStroke(COCKPIT_GREEN);
            tick.setStrokeWidth(2);
            tick.getTransforms().add(new Rotate(i * 36, 0, 0));
            
            Text number = new Text(String.valueOf(i * 10));
            number.setFill(COCKPIT_GREEN);
            number.setFont(Font.font("Monospaced", 14));
            double angle = Math.toRadians(i * 36 - 90);
            number.setTranslateX(Math.cos(angle) * 70);
            number.setTranslateY(Math.sin(angle) * 70 + 5);
            
            ticks.getChildren().addAll(tick, number);
        }
        
        // Needle
        Polygon needle = new Polygon(0, -80, -5, 0, 0, 10, 5, 0);
        needle.setFill(COCKPIT_AMBER);
        
        Rotate needleRotate = new Rotate(0, 0, 0);
        needle.getTransforms().add(needleRotate);
        
        // Bind needle to value
        value.addListener((obs, old, val) -> {
            needleRotate.setAngle(val.doubleValue() * 3.6);
        });
        
        // Digital display
        Label digitalDisplay = new Label();
        digitalDisplay.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        digitalDisplay.setTextFill(COCKPIT_GREEN);
        digitalDisplay.setTranslateY(40);
        digitalDisplay.textProperty().bind(value.asString("%.0f%%"));
        
        gauge.getChildren().addAll(bg, ticks, needle, digitalDisplay);
        
        container.getChildren().addAll(titleLabel, gauge);
        return container;
    }
    
    private Node createAirspeedIndicator(String title, DoubleProperty inValue, DoubleProperty outValue) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(COCKPIT_CYAN);
        
        StackPane gauge = new StackPane();
        gauge.setPrefSize(200, 200);
        
        Circle bg = new Circle(100, COCKPIT_PANEL);
        bg.setStroke(COCKPIT_GREEN);
        bg.setStrokeWidth(2);
        
        // Speed arc segments
        Arc greenArc = new Arc(0, 0, 95, 95, -90, -120);
        greenArc.setType(ArcType.OPEN);
        greenArc.setStroke(Color.LIGHTGREEN);
        greenArc.setStrokeWidth(15);
        greenArc.setFill(Color.TRANSPARENT);
        
        Arc yellowArc = new Arc(0, 0, 95, 95, -210, -60);
        yellowArc.setType(ArcType.OPEN);
        yellowArc.setStroke(COCKPIT_AMBER);
        yellowArc.setStrokeWidth(15);
        yellowArc.setFill(Color.TRANSPARENT);
        
        Arc redArc = new Arc(0, 0, 95, 95, -270, -30);
        redArc.setType(ArcType.OPEN);
        redArc.setStroke(COCKPIT_RED);
        redArc.setStrokeWidth(15);
        redArc.setFill(Color.TRANSPARENT);
        
        // IN/OUT indicators
        Label inLabel = new Label("IN");
        inLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 12));
        inLabel.setTextFill(COCKPIT_GREEN);
        inLabel.setTranslateY(-30);
        
        Label inValueLabel = new Label();
        inValueLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        inValueLabel.setTextFill(COCKPIT_GREEN);
        inValueLabel.setTranslateY(-10);
        inValueLabel.textProperty().bind(inValue.asString("%.0f Mb/s"));
        
        Label outLabel = new Label("OUT");
        outLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 12));
        outLabel.setTextFill(COCKPIT_AMBER);
        outLabel.setTranslateY(20);
        
        Label outValueLabel = new Label();
        outValueLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        outValueLabel.setTextFill(COCKPIT_AMBER);
        outValueLabel.setTranslateY(40);
        outValueLabel.textProperty().bind(outValue.asString("%.0f Mb/s"));
        
        gauge.getChildren().addAll(bg, greenArc, yellowArc, redArc, 
                                   inLabel, inValueLabel, outLabel, outValueLabel);
        
        container.getChildren().addAll(titleLabel, gauge);
        return container;
    }
    
    private Node createVerticalSpeed(String title, DoubleProperty value) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(COCKPIT_CYAN);
        
        StackPane gauge = new StackPane();
        gauge.setPrefSize(200, 200);
        
        Circle bg = new Circle(100, COCKPIT_PANEL);
        bg.setStroke(COCKPIT_GREEN);
        bg.setStrokeWidth(2);
        
        // Vertical scale
        Group scale = new Group();
        for (int i = -3; i <= 3; i++) {
            if (i != 0) {
                Line tick = new Line(-80, 0, -90, 0);
                tick.setStroke(COCKPIT_GREEN);
                tick.setStrokeWidth(2);
                tick.getTransforms().add(new Rotate(i * 30, 0, 0));
                
                Text number = new Text(String.valueOf(Math.abs(i)));
                number.setFill(COCKPIT_GREEN);
                number.setFont(Font.font("Monospaced", 12));
                double angle = Math.toRadians(i * 30 - 90);
                number.setTranslateX(Math.cos(angle) * 65);
                number.setTranslateY(Math.sin(angle) * 65 + 4);
                
                scale.getChildren().addAll(tick, number);
            }
        }
        
        // Zero line
        Line zeroLine = new Line(-95, 0, -85, 0);
        zeroLine.setStroke(COCKPIT_AMBER);
        zeroLine.setStrokeWidth(3);
        
        // Needle
        Polygon needle = new Polygon(0, -70, -4, 0, 0, 8, 4, 0);
        needle.setFill(COCKPIT_GREEN);
        
        Rotate needleRotate = new Rotate(0, 0, 0);
        needle.getTransforms().add(needleRotate);
        
        // Animate needle based on disk I/O
        Timeline needleAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> {
                double targetAngle = (value.get() - 50) * 1.8; // Map 0-100 to -90 to +90
                needleRotate.setAngle(targetAngle);
            })
        );
        needleAnimation.setCycleCount(Timeline.INDEFINITE);
        needleAnimation.play();
        
        // Rate display
        Label rateLabel = new Label();
        rateLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        rateLabel.setTextFill(COCKPIT_GREEN);
        rateLabel.setTranslateY(50);
        rateLabel.textProperty().bind(value.asString("%.0f MB/s"));
        
        gauge.getChildren().addAll(bg, scale, zeroLine, needle, rateLabel);
        
        container.getChildren().addAll(titleLabel, gauge);
        return container;
    }
    
    private Node createEngineGauge(String title, DoubleProperty value, double maxValue) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(COCKPIT_CYAN);
        
        StackPane gauge = new StackPane();
        gauge.setPrefSize(200, 200);
        
        Circle bg = new Circle(100, COCKPIT_PANEL);
        bg.setStroke(COCKPIT_GREEN);
        bg.setStrokeWidth(2);
        
        // Temperature zones
        Arc normalZone = new Arc(0, 0, 90, 90, 45, -180);
        normalZone.setType(ArcType.OPEN);
        normalZone.setStroke(COCKPIT_GREEN);
        normalZone.setStrokeWidth(20);
        normalZone.setFill(Color.TRANSPARENT);
        
        Arc cautionZone = new Arc(0, 0, 90, 90, -135, -45);
        cautionZone.setType(ArcType.OPEN);
        cautionZone.setStroke(COCKPIT_AMBER);
        cautionZone.setStrokeWidth(20);
        cautionZone.setFill(Color.TRANSPARENT);
        
        Arc dangerZone = new Arc(0, 0, 90, 90, -180, -45);
        dangerZone.setType(ArcType.OPEN);
        dangerZone.setStroke(COCKPIT_RED);
        dangerZone.setStrokeWidth(20);
        dangerZone.setFill(Color.TRANSPARENT);
        
        // Needle
        Line needle = new Line(0, 0, 0, -75);
        needle.setStroke(Color.WHITE);
        needle.setStrokeWidth(3);
        
        Rotate needleRotate = new Rotate(-135, 0, 0);
        needle.getTransforms().add(needleRotate);
        
        // Digital display
        Label tempDisplay = new Label();
        tempDisplay.setFont(Font.font("Monospaced", FontWeight.BOLD, 24));
        tempDisplay.setTranslateY(40);
        
        // Update color based on temperature
        value.addListener((obs, old, val) -> {
            double angle = -135 + (val.doubleValue() / maxValue) * 270;
            needleRotate.setAngle(angle);
            
            if (val.doubleValue() < 70) {
                tempDisplay.setTextFill(COCKPIT_GREEN);
            } else if (val.doubleValue() < 85) {
                tempDisplay.setTextFill(COCKPIT_AMBER);
            } else {
                tempDisplay.setTextFill(COCKPIT_RED);
            }
        });
        
        tempDisplay.textProperty().bind(value.asString("%.0f°C"));
        
        gauge.getChildren().addAll(bg, normalZone, cautionZone, dangerZone, needle, tempDisplay);
        
        container.getChildren().addAll(titleLabel, gauge);
        return container;
    }
    
    private Node createHeadingIndicator(String title, DoubleProperty value) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        titleLabel.setTextFill(COCKPIT_CYAN);
        
        StackPane gauge = new StackPane();
        gauge.setPrefSize(200, 200);
        
        Circle bg = new Circle(100, COCKPIT_PANEL);
        bg.setStroke(COCKPIT_GREEN);
        bg.setStrokeWidth(2);
        
        // Compass rose
        Group compassRose = new Group();
        String[] directions = {"N", "030", "060", "E", "120", "150", 
                              "S", "210", "240", "W", "300", "330"};
        
        for (int i = 0; i < 12; i++) {
            Text dir = new Text(directions[i]);
            dir.setFill(COCKPIT_GREEN);
            dir.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
            double angle = Math.toRadians(i * 30 - 90);
            dir.setTranslateX(Math.cos(angle) * 75);
            dir.setTranslateY(Math.sin(angle) * 75 + 5);
            
            Line tick = new Line(0, -95, 0, -85);
            tick.setStroke(COCKPIT_GREEN);
            tick.setStrokeWidth(2);
            tick.getTransforms().add(new Rotate(i * 30, 0, 0));
            
            compassRose.getChildren().addAll(dir, tick);
        }
        
        // Rotating compass
        Rotate compassRotate = new Rotate(0, 0, 0);
        compassRose.getTransforms().add(compassRotate);
        
        // Fixed aircraft symbol
        Polygon aircraft = new Polygon(0, -20, -10, 10, 0, 5, 10, 10);
        aircraft.setFill(COCKPIT_AMBER);
        
        // Response time display
        Label responseDisplay = new Label();
        responseDisplay.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        responseDisplay.setTextFill(COCKPIT_GREEN);
        responseDisplay.setTranslateY(50);
        responseDisplay.textProperty().bind(value.asString("%.0f ms"));
        
        // Animate compass rotation
        Timeline compassAnimation = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> {
                compassRotate.setAngle(compassRotate.getAngle() + 1);
            })
        );
        compassAnimation.setCycleCount(Timeline.INDEFINITE);
        compassAnimation.play();
        
        gauge.getChildren().addAll(bg, compassRose, aircraft, responseDisplay);
        
        container.getChildren().addAll(titleLabel, gauge);
        return container;
    }
    
    private VBox createLeftPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: " + toRGBCode(COCKPIT_PANEL) + "; " +
                      "-fx-border-color: #00ff00; -fx-border-width: 0 1 0 0;");
        panel.setPrefWidth(250);
        
        // Engine/Server Parameters
        Label engineTitle = new Label("SERVER PARAMETERS");
        engineTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        engineTitle.setTextFill(COCKPIT_CYAN);
        
        // N1/N2 style gauges for processes
        VBox processGauges = new VBox(10);
        processGauges.getChildren().addAll(
            createLinearGauge("PROCESSES", new SimpleDoubleProperty(activeConnections.get() / 2000.0), COCKPIT_GREEN),
            createLinearGauge("THREADS", new SimpleDoubleProperty(0.75), COCKPIT_GREEN),
            createLinearGauge("QUEUE DEPTH", new SimpleDoubleProperty(0.3), COCKPIT_AMBER),
            createLinearGauge("CACHE HIT", new SimpleDoubleProperty(0.92), COCKPIT_GREEN)
        );
        
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #00ff00;");
        
        // Fuel/Resource gauges
        Label resourceTitle = new Label("RESOURCE UTILIZATION");
        resourceTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        resourceTitle.setTextFill(COCKPIT_CYAN);
        
        VBox resourceGauges = new VBox(10);
        resourceGauges.getChildren().addAll(
            createLinearGauge("SWAP", new SimpleDoubleProperty(0.15), COCKPIT_GREEN),
            createLinearGauge("INODE", new SimpleDoubleProperty(0.42), COCKPIT_GREEN),
            createLinearGauge("FD USAGE", new SimpleDoubleProperty(0.28), COCKPIT_GREEN)
        );
        
        panel.getChildren().addAll(engineTitle, processGauges, sep, resourceTitle, resourceGauges);
        return panel;
    }
    
    private VBox createRightPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: " + toRGBCode(COCKPIT_PANEL) + "; " +
                      "-fx-border-color: #00ff00; -fx-border-width: 0 0 0 1;");
        panel.setPrefWidth(300);
        
        // ECAM style warning display
        Label ecamTitle = new Label("SYSTEM ALERTS");
        ecamTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ecamTitle.setTextFill(COCKPIT_CYAN);
        
        ScrollPane alertScroll = new ScrollPane();
        alertScroll.setPrefHeight(200);
        alertScroll.setStyle("-fx-background: #1a1a1f; -fx-background-color: #1a1a1f;");
        
        VBox alerts = new VBox(5);
        alerts.setStyle("-fx-background-color: #1a1a1f;");
        
        // Simulated alerts
        Timeline alertTimeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> {
                alerts.getChildren().clear();
                
                if (cpuLoad.get() > 80) {
                    alerts.getChildren().add(createAlert("CPU OVERLOAD", COCKPIT_RED));
                }
                if (memoryUsage.get() > 85) {
                    alerts.getChildren().add(createAlert("MEMORY CRITICAL", COCKPIT_AMBER));
                }
                if (serverTemp.get() > 75) {
                    alerts.getChildren().add(createAlert("TEMP WARNING", COCKPIT_AMBER));
                }
                if (diskIO.get() > 90) {
                    alerts.getChildren().add(createAlert("DISK SATURATED", COCKPIT_AMBER));
                }
                
                if (alerts.getChildren().isEmpty()) {
                    alerts.getChildren().add(createAlert("ALL SYSTEMS NOMINAL", COCKPIT_GREEN));
                }
            })
        );
        alertTimeline.setCycleCount(Timeline.INDEFINITE);
        alertTimeline.play();
        
        alertScroll.setContent(alerts);
        
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #00ff00;");
        
        // System Log
        Label logTitle = new Label("SYSTEM LOG");
        logTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        logTitle.setTextFill(COCKPIT_CYAN);
        
        TextArea logArea = new TextArea();
        logArea.setPrefHeight(250);
        logArea.setEditable(false);
        logArea.setStyle("-fx-control-inner-background: #1a1a1f; " +
                        "-fx-text-fill: #00ff00; " +
                        "-fx-font-family: 'Monospaced'; " +
                        "-fx-font-size: 10;");
        
        // Simulate log entries
        Timeline logTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2), e -> {
                String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String[] messages = {
                    "Connection established from 192.168.1." + random.nextInt(255),
                    "Database query executed: " + random.nextInt(100) + "ms",
                    "Cache invalidated for key: user_" + random.nextInt(10000),
                    "Background job completed: cleanup_task",
                    "Health check passed: all services operational",
                    "Load balancer: routing to server_" + (random.nextInt(4) + 1)
                };
                String log = "[" + timestamp + "] " + messages[random.nextInt(messages.length)] + "\n";
                logArea.appendText(log);
                logArea.setScrollTop(Double.MAX_VALUE);
            })
        );
        logTimeline.setCycleCount(Timeline.INDEFINITE);
        logTimeline.play();
        
        panel.getChildren().addAll(ecamTitle, alertScroll, sep, logTitle, logArea);
        return panel;
    }
    
    private HBox createBottomPanel() {
        HBox panel = new HBox(30);
        panel.setPadding(new Insets(15));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: linear-gradient(to top, #1a1a1f, #0a0a0f); " +
                      "-fx-border-color: #00ff00; -fx-border-width: 1 0 0 0;");
        
        // Throttle quadrant style controls
        VBox throttles = new VBox(10);
        throttles.setAlignment(Pos.CENTER);
        Label throttleLabel = new Label("PERFORMANCE THROTTLES");
        throttleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        throttleLabel.setTextFill(COCKPIT_CYAN);
        
        HBox sliders = new HBox(15);
        sliders.getChildren().addAll(
            createThrottle("CPU", cpuLoad),
            createThrottle("MEM", memoryUsage),
            createThrottle("NET", networkIn),
            createThrottle("DISK", diskIO)
        );
        
        throttles.getChildren().addAll(throttleLabel, sliders);
        
        // Autopilot panel
        GridPane autopilot = new GridPane();
        autopilot.setHgap(10);
        autopilot.setVgap(10);
        autopilot.setAlignment(Pos.CENTER);
        
        Button apButton = createCockpitButton("A/P", true);
        Button atButton = createCockpitButton("A/T", true);
        Button fdButton = createCockpitButton("F/D", false);
        Button locButton = createCockpitButton("LOC", false);
        Button appButton = createCockpitButton("APP", false);
        Button vnavButton = createCockpitButton("VNAV", true);
        Button lnavButton = createCockpitButton("LNAV", true);
        Button cmdButton = createCockpitButton("CMD A", true);
        
        autopilot.add(apButton, 0, 0);
        autopilot.add(atButton, 1, 0);
        autopilot.add(fdButton, 2, 0);
        autopilot.add(locButton, 3, 0);
        autopilot.add(appButton, 0, 1);
        autopilot.add(vnavButton, 1, 1);
        autopilot.add(lnavButton, 2, 1);
        autopilot.add(cmdButton, 3, 1);
        
        // Radio stack
        VBox radioStack = new VBox(5);
        radioStack.setAlignment(Pos.CENTER);
        Label radioLabel = new Label("CLUSTER COMM");
        radioLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        radioLabel.setTextFill(COCKPIT_CYAN);
        
        HBox frequencies = new HBox(10);
        frequencies.getChildren().addAll(
            createFrequencyDisplay("NODE1", "127.001"),
            createFrequencyDisplay("NODE2", "127.002"),
            createFrequencyDisplay("NODE3", "127.003"),
            createFrequencyDisplay("MSTR", "127.000")
        );
        
        radioStack.getChildren().addAll(radioLabel, frequencies);
        
        panel.getChildren().addAll(throttles, autopilot, radioStack);
        return panel;
    }
    
    private Node createLinearGauge(String label, DoubleProperty value, Color color) {
        VBox container = new VBox(3);
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Monospaced", 10));
        nameLabel.setTextFill(COCKPIT_CYAN);
        
        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(value);
        bar.setPrefWidth(200);
        bar.setStyle("-fx-accent: " + toRGBCode(color) + ";");
        
        Label valueLabel = new Label();
        valueLabel.setFont(Font.font("Monospaced", 10));
        valueLabel.setTextFill(color);
        valueLabel.textProperty().bind(value.multiply(100).asString("%.0f%%"));
        
        container.getChildren().addAll(nameLabel, bar, valueLabel);
        return container;
    }
    
    private Node createThrottle(String label, DoubleProperty value) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        
        Slider slider = new Slider(0, 100, value.get());
        slider.setOrientation(Orientation.VERTICAL);
        slider.setPrefHeight(100);
        slider.valueProperty().bindBidirectional(value);
        slider.setStyle("-fx-base: #1a1a1f;");
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Monospaced", 10));
        nameLabel.setTextFill(COCKPIT_GREEN);
        
        container.getChildren().addAll(slider, nameLabel);
        return container;
    }
    
    private Button createCockpitButton(String text, boolean active) {
        Button button = new Button(text);
        button.setPrefSize(60, 30);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        
        if (active) {
            button.setStyle("-fx-background-color: #003300; " +
                          "-fx-text-fill: #00ff00; " +
                          "-fx-border-color: #00ff00; " +
                          "-fx-border-width: 1;");
        } else {
            button.setStyle("-fx-background-color: #1a1a1f; " +
                          "-fx-text-fill: #666666; " +
                          "-fx-border-color: #666666; " +
                          "-fx-border-width: 1;");
        }
        
        button.setOnAction(e -> {
            boolean isActive = button.getStyle().contains("#00ff00");
            if (!isActive) {
                button.setStyle("-fx-background-color: #003300; " +
                              "-fx-text-fill: #00ff00; " +
                              "-fx-border-color: #00ff00; " +
                              "-fx-border-width: 1;");
            } else {
                button.setStyle("-fx-background-color: #1a1a1f; " +
                              "-fx-text-fill: #666666; " +
                              "-fx-border-color: #666666; " +
                              "-fx-border-width: 1;");
            }
        });
        
        return button;
    }
    
    private Node createFrequencyDisplay(String label, String freq) {
        VBox container = new VBox(2);
        container.setAlignment(Pos.CENTER);
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Monospaced", 9));
        nameLabel.setTextFill(COCKPIT_CYAN);
        
        Label freqLabel = new Label(freq);
        freqLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 12));
        freqLabel.setTextFill(COCKPIT_GREEN);
        freqLabel.setStyle("-fx-background-color: #0a0a0f; " +
                          "-fx-padding: 2 5 2 5; " +
                          "-fx-border-color: #00ff00; " +
                          "-fx-border-width: 1;");
        
        container.getChildren().addAll(nameLabel, freqLabel);
        return container;
    }
    
    private Label createAlert(String message, Color color) {
        Label alert = new Label("• " + message);
        alert.setFont(Font.font("Monospaced", FontWeight.BOLD, 12));
        alert.setTextFill(color);
        
        if (color == COCKPIT_RED) {
            FadeTransition blink = new FadeTransition(Duration.seconds(0.5), alert);
            blink.setFromValue(1.0);
            blink.setToValue(0.3);
            blink.setCycleCount(Timeline.INDEFINITE);
            blink.setAutoReverse(true);
            blink.play();
        }
        
        return alert;
    }
    
    private VBox createWarningLight(String text, Color color, boolean active) {
        VBox container = new VBox(2);
        container.setAlignment(Pos.CENTER);
        
        Rectangle light = new Rectangle(80, 40);
        light.setFill(active ? color : Color.rgb(40, 40, 40));
        light.setStroke(color);
        light.setStrokeWidth(2);
        
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        label.setTextFill(active ? Color.BLACK : color);
        label.setTranslateY(-30);
        
        StackPane stack = new StackPane(light, label);
        container.getChildren().add(stack);
        
        if (active) {
            FadeTransition flash = new FadeTransition(Duration.seconds(1), light);
            flash.setFromValue(1.0);
            flash.setToValue(0.5);
            flash.setCycleCount(Timeline.INDEFINITE);
            flash.setAutoReverse(true);
            flash.play();
        }
        
        return container;
    }
    
    private VBox createStatusLight(String label, boolean on) {
        VBox container = new VBox(2);
        container.setAlignment(Pos.CENTER);
        
        Circle light = new Circle(8);
        light.setFill(on ? COCKPIT_GREEN : Color.rgb(40, 40, 40));
        light.setStroke(COCKPIT_GREEN);
        light.setStrokeWidth(1);
        
        if (on) {
            light.setEffect(new Glow(0.8));
        }
        
        Label text = new Label(label);
        text.setFont(Font.font("Monospaced", 9));
        text.setTextFill(COCKPIT_GREEN);
        
        container.getChildren().addAll(light, text);
        return container;
    }
    
    private void startSimulation() {
        // Main simulation timeline
        mainTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> {
                // Simulate server metrics with some randomness
                cpuLoad.set(Math.max(0, Math.min(100, cpuLoad.get() + (random.nextDouble() - 0.5) * 10)));
                memoryUsage.set(Math.max(0, Math.min(100, memoryUsage.get() + (random.nextDouble() - 0.5) * 5)));
                diskIO.set(Math.max(0, Math.min(100, diskIO.get() + (random.nextDouble() - 0.5) * 15)));
                networkIn.set(Math.max(0, Math.min(500, networkIn.get() + (random.nextDouble() - 0.5) * 50)));
                networkOut.set(Math.max(0, Math.min(500, networkOut.get() + (random.nextDouble() - 0.5) * 40)));
                serverTemp.set(Math.max(40, Math.min(95, serverTemp.get() + (random.nextDouble() - 0.5) * 3)));
                activeConnections.set(Math.max(0, Math.min(5000, activeConnections.get() + random.nextInt(100) - 50)));
                responseTime.set(Math.max(1, Math.min(200, responseTime.get() + (random.nextDouble() - 0.5) * 10)));
            })
        );
        mainTimeline.setCycleCount(Timeline.INDEFINITE);
        mainTimeline.play();
    }
    
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    @Override
    public void stop() {
        if (mainTimeline != null) {
            mainTimeline.stop();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}