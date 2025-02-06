package com.fossvisualizer.graph;

import com.fossvisualizer.graph.Edges.StraightEdge;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {

    String cellId;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();

    List<StraightEdge> connectedEdges;

    Node view;

    //contains the connector that the end of any connected edges are attached to
    Node lineTarget;
    Polygon shape;

    public Cell(String cellId) {
        this.cellId = cellId;
        connectedEdges = new ArrayList<>();
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {

        this.view = view;
        getChildren().add(view);

    }

    public void setLineTarget(Node lineTarget) {
        this.lineTarget = lineTarget;
    }
    public Node getLineTarget() {
        return lineTarget;
    }

    public Node getView() {
        return this.view;
    }

    public String getCellId() {
        return cellId;
    }

    public void addEdge(StraightEdge edge){
        connectedEdges.add(edge);
    }

    public List<StraightEdge> getEdges(){
        return connectedEdges;
    }

    public Polygon getRectangle(){
        return shape;
    }

    public void setRectangle(Polygon shape){
        this.shape = shape;
    }


}
