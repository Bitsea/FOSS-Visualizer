package com.fossvisualizer.application;
import com.fossvisualizer.graph.*;
import com.fossvisualizer.ui.TitlePageBuilder;
import com.fossvisualizer.ui.ToolbarBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Graph graph = new Graph();

    @Override
    public void start(Stage primaryStage){

        BorderPane root = new BorderPane();

        graph = new Graph();

        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add("application.css");

        StackPane titlePage = new TitlePageBuilder(primaryStage, scene, graph).builtTitlePage();
        Scene titlePageScene = new Scene(titlePage, 1000, 650);
        titlePageScene.getStylesheets().add("application.css");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/legend.fxml"));
            Scene scene1 = new Scene(fxmlLoader.load(), 200, 650);
            root.setRight(scene1.getRoot());
            HBox toolbar = new ToolbarBuilder(titlePageScene, primaryStage, graph, root, root.getRight()).buildToolbar();
            root.setTop(toolbar);
        }
        catch (IOException e){
            System.out.println("There was an error loading fxml file");
        }

        primaryStage.setScene(titlePageScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}