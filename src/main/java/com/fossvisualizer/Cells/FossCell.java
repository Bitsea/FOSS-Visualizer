package com.fossvisualizer.Cells;

import com.fossvisualizer.graph.Cell;
import com.fossvisualizer.info.FossInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class FossCell extends Cell {

    FossInfo fossInfo;

    public FossCell(String id, FossInfo fossInfo) {
        super(id);
        this.fossInfo = fossInfo;

        //create label and basic shape
        StackPane shapeStack = new StackPane();
        ContextMenu contextMenu = new ContextMenu();
        AnchorPane subShapePane = new AnchorPane();
        BorderPane shapeAndConnectorPane = new BorderPane();
        AnchorPane iconAndShapePane = new AnchorPane();

        //build context menu
        MenuItem item1 = new MenuItem("Details");
        //button for pop-up menu
        item1.setOnAction(
                e->{
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    Scene dialogScene = new Scene(buildPopupText(fossInfo), 500, 600);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
        );

        MenuItem item2 = new MenuItem("Hide");
        //button for hiding the shape
        item2.setOnAction(
                e->{
                    this.setVisible(false);
                    super.getEdges().forEach(edge -> edge.setVisible(false));
                }
        );
        contextMenu.getItems().addAll(item2,item1);

        //show context menu on right click
        shapeAndConnectorPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(shapeAndConnectorPane, e.getScreenX(), e.getScreenY());
            }
        });

        Polygon shape = getRectangle(fossInfo);


        subShapePane.getChildren().add(shape);

        //add any number of necessary subShapes
        addSubShapes(subShapePane, fossInfo);
        shapeStack.getChildren().add(subShapePane);

        //setModificationIcon
        addIconsWithinShape(fossInfo, shapeStack);

        //build text and connectors
        Text text = new Text(id);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        text.setWrappingWidth(90.0);
        Circle rightConnector = new Circle(3, Color.rgb(204,204,204));

        //add connectors and text
        Insets insets = new Insets(0.0,0.0,2.0,0.0);
        BorderPane.setMargin(text, insets);
        BorderPane.setAlignment(text, Pos.TOP_LEFT);
        shapeAndConnectorPane.setTop(text);
        BorderPane.setAlignment(rightConnector, Pos.CENTER);
        shapeAndConnectorPane.setCenter(shapeStack);
        shapeAndConnectorPane.setRight(rightConnector);

        //add all necessary Icons e.g. for Vulnerability or Export Restriction and set Offset for text
        addIcons(fossInfo, iconAndShapePane, text);
        iconAndShapePane.getChildren().addAll(shapeAndConnectorPane);
        AnchorPane.setLeftAnchor(shapeAndConnectorPane, 12.0);

        setView(iconAndShapePane);
        setLineTarget(rightConnector);
    }

    private void addIconsWithinShape(FossInfo fossInfo, StackPane shapeStack) {
        HBox iconBox = new HBox();

        if(fossInfo.getModification().equalsIgnoreCase("yes")){
            Image backgroundImage = new Image("pencil.png");
            ImageView imageView = new ImageView(backgroundImage);
            imageView.setFitHeight(18);
            imageView.setFitWidth(16);

            iconBox.getChildren().add(imageView);
        }

        if(fossInfo.getPatent()){
            Image patentImage = new Image("gavel.png");
            ImageView patentView = new ImageView(patentImage);
            patentView.setFitWidth(18);
            patentView.setFitHeight(18);

            iconBox.getChildren().add(patentView);
        }

        //adds an icon when operational risk is present. currently this means if the version of the component is outdated
        if(!fossInfo.getMostActualVersion().isEmpty() && !fossInfo.getName().contains(fossInfo.getMostActualVersion())){
            Image orImage = new Image("star.png");
            ImageView orView = new ImageView(orImage);
            orView.setFitWidth(18);
            orView.setFitHeight(18);

            iconBox.getChildren().add(orView);
        }

        iconBox.setAlignment(Pos.CENTER);
        iconBox.setTranslateY(-4.0);
        shapeStack.getChildren().add(iconBox);
    }

    private void addIcons(FossInfo fossInfo, AnchorPane iconPane, Text name) {
        //to add further icons extend method below

        VBox vBox = new VBox(2);
        if(fossInfo.getExportRestriction()){
            Image warning = new Image("yellowTriangle.png");
            ImageView warningView = new ImageView(warning);
            warningView.setFitHeight(16);
            warningView.setFitWidth(14);
            int number = fossInfo.getNumberOfExportRestriction();
            Text text = new Text();
            if (number >= 10) {
                text.setFont(Font.font("Arial", FontWeight.NORMAL, 7));
                text.setText("9+");
            }else{
                text.setFont(Font.font("Arial", FontWeight.NORMAL, 9));
                text.setText("" + number);
            }
            StackPane pane = new StackPane();
            pane.getChildren().addAll(warningView, text);
            vBox.getChildren().add(pane);
        }
        if(fossInfo.getVulnerabilities().toLowerCase().contains("yes")){
            Image stop = new Image("redPolygon.png");
            ImageView stopView = new ImageView(stop);
            stopView.setFitHeight(16);
            stopView.setFitWidth(14);
            int number = fossInfo.getNumberOfVulnerabilities();
            String stopNumber = "" + number;
            if (number >= 10) stopNumber = "9+";
            Text text = new Text(stopNumber);
            text.setFont(Font.font("Arial", FontWeight.NORMAL, 9));
            StackPane pane = new StackPane();
            pane.getChildren().addAll(stopView, text);
            vBox.getChildren().add(pane);
        }
        if(fossInfo.getApprovedByPolicy().toLowerCase().contains("no")){
            Image question = new Image("blueCircle.png");
            ImageView questionView = new ImageView(question);
            questionView.setFitHeight(16);
            questionView.setFitWidth(14);
            StackPane pane = new StackPane();
            pane.getChildren().add(questionView);
            vBox.getChildren().add(pane);
        }

        AnchorPane.setTopAnchor(vBox, name.getBoundsInLocal().getHeight() - 5);
        iconPane.getChildren().addAll(vBox);
    }

    private void addSubShapes(AnchorPane shapeAndTextPane, FossInfo info) {
        //to add further subShapes extend the method below
        HBox subShapes = new HBox();
        if(info.getKi()){
            Rectangle rectangle = new Rectangle(80, 4);
            rectangle.setFill(Color.rgb(113,113,162));
            subShapes.getChildren().add(rectangle);
        }
        AnchorPane.setRightAnchor(subShapes, 8.0);
        AnchorPane.setTopAnchor(subShapes, 3.0);
        shapeAndTextPane.getChildren().add(subShapes);
    }

    private Polygon getRectangle(FossInfo fossInfo) {
        Polygon view = new Polygon();

        //if component is snippet
        if(fossInfo.getSnippet()) {
            view.getPoints().addAll(
                    0.0, 0.0,
                    0.0, 38.0,
                    5.0, 32.0,
                    10.0, 38.0,
                    15.0, 32.0,
                    20.0, 38.0,
                    25.0, 32.0,
                    30.0, 38.0,
                    35.0, 32.0,
                    40.0, 38.0,
                    45.0, 32.0,
                    50.0, 38.0,
                    55.0, 32.0,
                    60.0, 38.0,
                    65.0, 32.0,
                    70.0, 38.0,
                    75.0, 32.0,
                    80.0, 38.0,
                    85.0, 32.0,
                    90.0, 38.0,
                    90.0, 0.0
            );
        }
        //normal component gets a rectangular rectangle
        else{
            view.getPoints().addAll(
                    0.0, 0.0,
                    0.0, 38.0,
                    90.0, 38.0,
                    90.0, 0.0
            );
        }
        view.setStrokeWidth(4);

        //for each type of license change border colour
        switch (fossInfo.getStrictness()){
            case COPYLEFT -> view.setStroke(Color.rgb(155,78,78));
            case OTHER -> view.setStroke(Color.rgb(184, 149, 55));
            case PERMISSIVE -> view.setStroke(Color.rgb(123, 137, 81));
        }
        view.setFill(Color.rgb(204,204,204));
        this.setRectangle(view);
        return view;
    }

    private ScrollPane buildPopupText(FossInfo fossInfo){
        Accordion accordion = new Accordion();

        List<TitledPane> panes = new ArrayList<>();
        panes.add(new TitledPane("# Issue", new VBox(new Text(fossInfo.getIssue()))));
        panes.add(new TitledPane("SW Area", new VBox(new Text(fossInfo.getSwArea()))));
        panes.add(new TitledPane("Name", new VBox(new Text(fossInfo.getName()))));
        panes.add(new TitledPane("Parent Name", new VBox(new Text(fossInfo.getParentName()))));
        panes.add(new TitledPane("Component Indicator", new VBox(new Text(fossInfo.getComponentIndicator()))));
        panes.add(new TitledPane("Priority", new VBox(new Text("" + fossInfo.getPriority()))));
        panes.add(new TitledPane("License Type", new VBox(new Text(fossInfo.getLicenseType()))));
        panes.add(new TitledPane("License Text", new VBox(new Text(fossInfo.getLicenseText()))));
        panes.add(new TitledPane("Description", new VBox(new Text(fossInfo.getDescription()))));
        panes.add(new TitledPane("Package/File Version", new VBox(new Text(fossInfo.getPackageFileVersion()))));
        panes.add(new TitledPane("URL", new VBox(new Text(fossInfo.getUrl()))));
        panes.add(new TitledPane("External Notes", new VBox(new Text(fossInfo.getExternalNotes()))));
        panes.add(new TitledPane("Vulnerabilities", new VBox(new Text(fossInfo.getVulnerabilities()))));
        panes.add(new TitledPane("Vulnerability List (CVSS Severity Score, Severity Impact, CVE Dictionary Entry, URL)",
                new VBox(new Text(fossInfo.getVulnerabilityList()))));
        panes.add(new TitledPane("# File ", new VBox(new Text("" + fossInfo.getFileNumber()))));
        panes.add(new TitledPane("Files", new VBox(new Text(fossInfo.getFiles()))));
        panes.add(new TitledPane("Copyright", new VBox(new Text(fossInfo.getCopyright()))));
        panes.add(new TitledPane("Modification", new VBox(new Text(fossInfo.getModification()))));
        panes.add(new TitledPane("Linking", new VBox(new Text(fossInfo.getLinkingType().name()))));
        panes.add(new TitledPane("Encryption", new VBox(new Text(fossInfo.getEncryption()))));
        panes.add(new TitledPane("Activity", new VBox(new Text("" + fossInfo.getActivity()))));
        panes.add(new TitledPane("Size", new VBox(new Text("" + fossInfo.getSize()))));
        panes.add(new TitledPane("Community Diversity", new VBox(new Text("" + fossInfo.getCommunityDiversity()))));
        panes.add(new TitledPane("Most Actual Version", new VBox(new Text(fossInfo.getMostActualVersion()))));
        panes.add(new TitledPane("Approved by Policy", new VBox(new Text(fossInfo.getApprovedByPolicy()))));
        panes.forEach(titledPane -> titledPane.setPrefWidth(500));
        accordion.getPanes().addAll(panes);
        ScrollPane scrollPane = new ScrollPane(new VBox(accordion));
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

}