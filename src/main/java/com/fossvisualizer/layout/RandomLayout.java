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
