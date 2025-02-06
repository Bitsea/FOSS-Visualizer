package com.fossvisualizer.ui;

import com.fossvisualizer.application.ExcelReader;
import com.fossvisualizer.graph.*;
import com.fossvisualizer.graph.Edges.EdgeType;
import com.fossvisualizer.graph.Edges.StraightEdge;
import com.fossvisualizer.info.FossInfo;
import com.fossvisualizer.layout.DirectoryLayout;
import com.fossvisualizer.layout.ForceLayout;
import com.fossvisualizer.layout.RandomLayout;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TitlePageBuilder {
    Stage primaryStage;
    Scene scene;
    Graph graph;
    List<FossInfo> fossInfos;

    List<Edge> backupEdges;

    public TitlePageBuilder(Stage primaryStage, Scene scene, Graph graph){
        this.primaryStage = primaryStage;
        this.scene = scene;
        this.graph = graph;
        fossInfos = new ArrayList<>();
        backupEdges = new ArrayList<>();
    }

    /**
     * Builds a new title page with a selection box that selects the wanted layout algorithm
     * and includes a button that opens a FileChooser to select an .xlsx file with the data
          * @return StackPane containing the full title page
     */
    public StackPane builtTitlePage(){
        StackPane titlePage = new StackPane();
        titlePage.setStyle("-fx-background-color:rgba(59,59,59);");

        ChoiceBox<String> layoutChoiceBox= new ChoiceBox<>();
        layoutChoiceBox.setValue("Random Layout");
        layoutChoiceBox.getItems().addAll("Random Layout", "Directory Layout", "Force Layout 250",
                "Force Layout 500", "Force Layout 1000");
        Button switchButton = getSwitchButton(layoutChoiceBox);

        Text dataStatus = new Text("No File Selected");
        dataStatus.setFill(Color.YELLOW);
        Button fileSelector = getButton(dataStatus);

        GridPane gridPane = new GridPane();
        gridPane.addRow(0, dataStatus, fileSelector);
        gridPane.addRow(1, switchButton, layoutChoiceBox);
        gridPane.setAlignment(Pos.CENTER);
        titlePage.getChildren().addAll(gridPane);
        return titlePage;
    }

    /**
     * Creates a new Button that when clicked opens a new dialog window via a FileChooser.
     * The types of selectable files are restricted to .xlsx files.
     * Closing the dialoge window without selecting a file empties the current graph.
     * The file itself is left unchanged.
     * @param dataStatus text that displays the status of the currently selected file
     * @return new Button with above specification
     */
    private Button getButton(Text dataStatus) {
        Button fileSelector = new Button("Select File");
        fileSelector.setOnAction(
                actionEvent -> {
                    dataStatus.setText("No File Selected");
                    dataStatus.setFill(Color.YELLOW);
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Data File");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Excel Sheet", "*.xlsx")
                    );
                    File selectedFile = fileChooser.showOpenDialog(primaryStage);
                    if(selectedFile != null) {
                        dataStatus.setText("File Selected: " + selectedFile.getName());
                        dataStatus.setFill(Color.GREEN);
                        fossInfos.clear();
                        try {
                            Map<Integer, List<String>> data = ExcelReader.getDataFromExcel(selectedFile);
                            for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
                                fossInfos.add(new FossInfo(entry.getValue()));
                            }
                        } catch (Exception e){
                            dataStatus.setText("Error reading .xlsx file");
                            dataStatus.setFill(Color.RED);
                            addGraphComponents(new ArrayList<>());
                        }
                        addGraphComponents(fossInfos);
                    }else{
                        dataStatus.setText("No File Selected");
                        dataStatus.setFill(Color.YELLOW);
                        addGraphComponents(new ArrayList<>());
                    }

                }
        );
        return fileSelector;
    }

    /**
     * Creates a new button that when clicked executes the selected layout algorithm and switches current stage to the scene containing the visualisation
     * @param layoutChoiceBox ChoiceBox containing the information of the selected layout as a string
     * @return new Button with above specification
     */
    private Button getSwitchButton(ChoiceBox<String> layoutChoiceBox) {
        Button switchButton = new Button("Confirm Selection");
        switchButton.setOnAction(
                actionEvent -> {
                    if (!backupEdges.isEmpty()){
                        graph.beginUpdate();
                        graph.getModel().getAddedEdges().addAll(backupEdges);
                        graph.endUpdate();
                        backupEdges.clear();
                    }
                    switch(layoutChoiceBox.getValue()){
                        case "Random Layout":
                            new RandomLayout(graph,2000,2000).execute();
                            primaryStage.setScene(scene);
                            graph.fitContent(true);
                            break;
                        case "Directory Layout":
                            new DirectoryLayout(graph, fossInfos).execute();
                            graph.beginUpdate();
                            graph.getModel().getRemovedEdges().addAll(graph.getModel().getAllEdges().stream().filter(edge -> edge instanceof StraightEdge).toList());
                            backupEdges.addAll(graph.getModel().getAllEdges().stream().filter(edge -> edge instanceof StraightEdge).toList());
                            graph.endUpdate();
                            primaryStage.setScene(scene);
                            graph.fitContent(true);
                            break;
                        case "Force Layout 250":
                            new ForceLayout(graph, 250, 2000, 2000).execute();
                            primaryStage.setScene(scene);
                            graph.fitContent(true);
                            break;
                        case "Force Layout 500":
                            new ForceLayout(graph, 500, 2000, 2000).execute();
                            primaryStage.setScene(scene);
                            graph.fitContent(true);
                            break;
                        case "Force Layout 1000":
                            new ForceLayout(graph, 1000, 2000, 2000).execute();
                            primaryStage.setScene(scene);
                            graph.fitContent(true);
                            break;
                        default:
                            break;
                    }
                    //remove the cells to layer them above the edges
                    graph.beginUpdate();
                    List<Cell> backup = new ArrayList<>(graph.getModel().getAllCells());
                    graph.getModel().getRemovedCells().addAll(backup);
                    graph.endUpdate();
                    //add the cells back in, so they are layered above the edges
                    graph.beginUpdate();
                    graph.getModel().getAddedCells().addAll(backup);
                    graph.endUpdate();
                }
        );
        return switchButton;
    }

    /**
     * Takes a list of fossInfos and adds edges and cells to the graph.
     * Removes all cells and edges currently contained within the graph.
     * Leaves the list of fossInfos unchanged
     * @param fossInfo list of FossInfos based on which edges and cells are generated
     */
    private void addGraphComponents( List<FossInfo> fossInfo) {

        backupEdges.clear();
        Model model = graph.getModel();

        //remove old cells and edges
        graph.beginUpdate();
        model.getRemovedEdges().addAll(model.getAllEdges());
        model.getRemovedCells().addAll(model.getAllCells());
        graph.endUpdate();
        model.clear();

        graph.beginUpdate();

        for(FossInfo info: fossInfo){
            model.addCell(info.getName(), CellType.FOSS, info);
            if(!info.getParentName().isEmpty()) {
                model.addEdge(info.getName(), info.getParentName(), info, EdgeType.STRAIGHT);
            }
        }

        graph.endUpdate();
    }
}