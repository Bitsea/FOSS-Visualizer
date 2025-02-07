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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fossvisualizer.Cells.FossCell;
import com.fossvisualizer.graph.Edges.DirectoryEdge;
import com.fossvisualizer.graph.Edges.EdgeType;
import com.fossvisualizer.graph.Edges.StraightEdge;
import com.fossvisualizer.info.FossInfo;

public class Model {

    Cell graphParent;

    List<Cell> allCells;
    List<Cell> addedCells;
    List<Cell> removedCells;

    List<Edge> allEdges;
    List<Edge> addedEdges;
    List<Edge> removedEdges;


    Map<String,Cell> cellMap; // <id,cell>

    public Model() {

        graphParent = new Cell( "_ROOT_");

        // clear model, create lists
        clear();
    }

    public void clear() {

        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();

        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();

        cellMap = new HashMap<>(); // <id,cell>

    }

    public void clearAddedLists() {
        addedCells.clear();
        addedEdges.clear();
    }

    public List<Cell> getAddedCells() {
        return addedCells;
    }

    public List<Cell> getRemovedCells() {
        return removedCells;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Edge> getRemovedEdges() {
        return removedEdges;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }

    public void addCell(String id, CellType type, FossInfo fossInfo) {

        switch (type) {
                case FOSS:
                    FossCell fossCell = new FossCell(id, fossInfo);
                    addCell(fossCell);
                    break;

                default:
                    throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    private void addCell( Cell cell) {

        if(!cellMap.containsKey(cell.cellId)) {

            addedCells.add(cell);

            cellMap.put(cell.getCellId(), cell);
        }

    }

    public void addEdge(String sourceId, String targetId, FossInfo fossInfo, EdgeType edgeType) {

        Cell sourceCell = cellMap.get( sourceId);
        Cell targetCell = cellMap.get( targetId);

        Edge edge = null;

        switch (edgeType){

            case STRAIGHT:
                edge = new StraightEdge( sourceCell, targetCell, fossInfo);
                sourceCell.addEdge((StraightEdge) edge);
                targetCell.addEdge((StraightEdge) edge);
                break;
            case DIRECTORY:
                edge = new DirectoryEdge(sourceCell, targetCell, fossInfo);
                break;
            default:
                break;
        }

        addedEdges.add( edge);

    }

    /**
     * Attach all cells which don't have a parent to graphParent
     * @param cellList
     */
    public void attachOrphansToGraphParent( List<Cell> cellList) {

        for( Cell cell: cellList) {
            if(cell.getCellParents().isEmpty()) {
                graphParent.addCellChild( cell);
            }
        }

    }

    /**
     * Remove the graphParent reference if it is set
     * @param cellList
     */
    public void disconnectFromGraphParent( List<Cell> cellList) {

        for( Cell cell: cellList) {
            graphParent.removeCellChild( cell);
        }
    }

    public void merge() {

        // cells
        allCells.addAll( addedCells);
        allCells.removeAll( removedCells);

        addedCells.clear();
        removedCells.clear();

        // edges
        allEdges.addAll( addedEdges);
        allEdges.removeAll( removedEdges);

        addedEdges.clear();
        removedEdges.clear();
    }
}
