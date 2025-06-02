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
import com.fossvisualizer.graph.Graph;

import java.util.List;
import java.util.Random;



public class RandomLayout extends Layout {

    Graph graph;

    Random rnd = new Random();
    int canvasSizeX;
    int canvasSizeY;

    /**
     * this layout is mainly used for testing and for the force layout
     * places all cells within the graph on random coordinates within the frame defined by the canvasSize parameters
     * @param graph
     * @param canvasSizeX
     * @param canvasSizeY
     */
    public RandomLayout(Graph graph, int canvasSizeX, int canvasSizeY) {

        this.graph = graph;
        this.canvasSizeX = canvasSizeX;
        this.canvasSizeY = canvasSizeY;
    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * canvasSizeX;
            double y = rnd.nextDouble() * canvasSizeY;
            cell.relocate(x, y);

        }

    }

}
