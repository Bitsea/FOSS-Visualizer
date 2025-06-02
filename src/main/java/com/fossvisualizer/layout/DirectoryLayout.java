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

package com.fossvisualizer.layout;

import com.fossvisualizer.graph.Cell;
import com.fossvisualizer.graph.Edges.EdgeType;
import com.fossvisualizer.graph.Graph;
import com.fossvisualizer.info.FossInfo;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DirectoryLayout extends Layout{

    Graph graph;
    List<FossInfo> fossInfos;
    public DirectoryLayout(Graph graph, List<FossInfo> fossInfos){
        this.graph = graph;
        this.fossInfos = fossInfos;
    }
    @Override
    public void execute() {
        List<Cell> cellList = graph.getModel().getAllCells();
        if(!cellList.isEmpty()) {
            String parentCellId = fossInfos.stream().findFirst().filter(fossInfo -> fossInfo.getParentName().isEmpty()).get().getName();
            Cell parentCell = cellList.stream().findFirst().filter(cell -> cell.getCellId().equals(parentCellId)).get();
            parentCell.relocate(0, 0);
            layoutSubTree(parentCell);
            for (Cell cell : cellList) {
                for (Cell cellParent : cell.getCellParents()) {
                    FossInfo info = fossInfos.stream().filter(fossInfo -> fossInfo.getName().equals(cellParent.getCellId())).toList().get(0);
                    graph.getModel().addEdge(cell.getCellId(), cellParent.getCellId(), info, EdgeType.DIRECTORY);
                }
            }
        }
    }

    private void layoutSubTree(Cell cell){
        List<Cell> cellParents = cell.getCellParents();
        int counter = 1;
        for(Cell parent: cellParents){
            parent.relocate(cell.getLayoutX() + 150,  cell.getLayoutY() + 80 * counter);
            layoutSubTree(parent);
            //calculate the size of the subtree above this one
            //add the value to counter to get proper height
            counter += (getMaxWidth(parent) + (getHeight(parent)-1));
            if(numberSubtreesWithChildren(parent)!=0) counter+= (numberSubtreesWithChildren(parent) -1);
        }
    }

    private int getMaxWidth(Cell root) {
        if (root == null) {
            return 0;
        }

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(root);
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of nodes at this level
            maxWidth = Math.max(maxWidth, levelSize);

            for (int i = 0; i < levelSize; i++) {
                Cell node = queue.poll();
                for (Cell child : node.getCellParents()) {
                    if (child != null) {
                        queue.offer(child);
                    }
                }
            }
        }
        return maxWidth;
    }

    private int getHeight(Cell root) {
        if (root == null) {
            return 0;
        }

        int height = 0;
        for (Cell parent : root.getCellParents()) {
            height = Math.max(height, getHeight(parent));
        }
        return height + 1;
    }

    private int numberSubtreesWithChildren(Cell cell){
        int childsOFCurrentLevel = 0;
        for (Cell parent: cell.getCellParents()){
            if (!parent.getCellParents().isEmpty()){
                childsOFCurrentLevel += (parent.getCellParents().size() + numberSubtreesWithChildren(parent));
            }
        }
        return childsOFCurrentLevel;
    }

}

