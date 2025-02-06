package com.fossvisualizer.ui;

import com.fossvisualizer.graph.Cell;
import com.fossvisualizer.graph.Edge;
import com.fossvisualizer.graph.Edges.DirectoryEdge;
import com.fossvisualizer.graph.Graph;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ToolbarBuilder {

    Scene titleScene;
    Stage primaryStage;
    Graph graph;
    BorderPane root;
    Node legend;

    public ToolbarBuilder(Scene titleScene , Stage primaryStage, Graph graph, BorderPane root, Node legend){
        this.titleScene = titleScene;
        this.primaryStage = primaryStage;
        this.graph = graph;
        this.root = root;
        this.legend = legend;
    }

    /**
     * Creates a new Toolbar with full Ui functionality allows filtering, searching, simulation and toggling of the legend
     * @return Hbox containing the whole of the toolbar
     */
    public HBox buildToolbar(){
        HBox hBox = new HBox();
        Button reset_button = getResetButton();
        Button return_to_title = getTitleButton(titleScene, primaryStage, graph);

        Button legend = getLegendToggleButton();

        hBox.getChildren().addAll(reset_button,legend, return_to_title);
        return hBox;
    }

    /**
     * Creates a Button that when pressed resets all cells and edges,
     * All hidden cells are revealed,
     * Cells color is set back to default,
     * The Opacity of Cells is set back to 1.0,
     * Removes all Filters,
     * Incompatibility edges are removed as well
     * @return new Button with above specification
     */
    private Button getResetButton() {
        Button reset_button = new Button("Reset");
        reset_button.setOnAction(
                actionEvent -> {
                    for (Cell cell : graph.getModel().getAllCells()) {
                        cell.setVisible(true);
                        cell.getRectangle().setFill(Color.rgb(204,204,204));
                    }
                    for(Edge edge: graph.getModel().getAllEdges()) {
                        edge.setVisible(true);
                    }
                }
        );
        return reset_button;
    }

    /**
     * Creates a new button that when pressed hides or shows the legend
     * @return Button with above specification
     */
    private Button getLegendToggleButton(){
        Button toggle_legend = new Button("Hide Legend");
        toggle_legend.setOnAction(
                actionEvent -> {
                    if(toggle_legend.getText().equals("Hide Legend")){
                        toggle_legend.setText("Show Legend");
                        root.setRight(null);
                    } else if (toggle_legend.getText().equals("Show Legend")) {
                        toggle_legend.setText("Hide Legend");
                        root.setRight(legend);
                    }
                }
        );
        return toggle_legend;
    }

    /**
     * Creates a new button that switched the displayed scene to the provided Scene when pressed,
     * Also has the same effect as the reset button: removing filters, resetting opacity, colors and visibility
     * @param titleScene the scene that it to be switched to
     * @param primaryStage the stage to display the scene
     * @param graph the graph object containing the visual elements to be reset
     * @return Button with above specification
     */
    private Button getTitleButton(Scene titleScene, Stage primaryStage, Graph graph) {
        Button return_to_title = new Button("Return to Title");
        return_to_title.setOnAction(
                actionEvent -> {
                    primaryStage.setScene(titleScene);
                    for (Cell cell : graph.getModel().getAllCells()) {
                        cell.setVisible(true);
                        cell.getRectangle().setFill(Color.rgb(204,204,204));
                        cell.setOpacity(1.0);
                    }
                    for(Edge edge: graph.getModel().getAllEdges()) {
                        edge.setVisible(true);
                    }
                    //remove leftover directory edges
                    graph.beginUpdate();
                    graph.getModel().getRemovedEdges().addAll(graph.getModel().getAllEdges().stream().filter(edge -> edge instanceof DirectoryEdge).toList());
                    graph.endUpdate();
                }
        );
        return return_to_title;
    }

}