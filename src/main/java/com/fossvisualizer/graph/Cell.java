/*
 * FOSS Visualizer
 * Copyright (C) 2025 Bitsea GmbH
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
