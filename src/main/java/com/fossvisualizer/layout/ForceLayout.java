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
import com.fossvisualizer.graph.Edge;
import com.fossvisualizer.graph.Graph;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class ForceLayout extends Layout{
    private static final double SCALING_FACTOR = 1000;
    Graph graph;
    int numberIterations;
    int initialWidth;
    int initialHeight;
    double acceleration;

    private final double repulsiveForce;
    private final double attractionForce;
    private final double attractionScale;

    /**
     * Uses a forceDirected graph layout to achieve an even distribution of components within the plane
     * @param graph tha graph upon wich the simulation is to be carried out
     * @param numberIterations number of iterations until system is considered stable
     * @param initialWidth width of the initial pane in which the components are randomly distributed
     * @param initialHeight height of the initial pane in which the components are randomly distributed
     */
    public ForceLayout(Graph graph, int numberIterations, int initialWidth, int initialHeight){
        this.graph = graph;
        this.numberIterations = numberIterations;
        this.repulsiveForce = 80;
        this.attractionForce = 3;
        this.attractionScale = 10;
        this.acceleration = 0.8;
        this.initialHeight = initialHeight;
        this.initialWidth = initialWidth;
    }
    @Override
    public void execute() {

        new RandomLayout(graph, initialWidth, initialHeight).execute();

        List<Cell> cells = graph.getModel().getAllCells();
        List<Edge> edges = graph.getModel().getAllEdges();
        List<ForceCell> forceCells = new ArrayList<>();

        for(Cell cell: cells){
            forceCells.add( new ForceCell(cell, new Point2D(0,0)));
            //put the parent component in the centre to get better looking results
            if(cell.getEdges().stream().noneMatch(edge -> edge.getSource().equals(cell))){
                cell.relocate((double) initialHeight /2, (double) initialHeight /2);
            }
        }

        for(int i = 0; i < numberIterations; i++){

            //compute forces
            for(ForceCell cell1: forceCells){
                for (ForceCell cell2: forceCells){
                    if(cell1!=cell2){
                        boolean connected = edges.stream().anyMatch(edge -> edge.getSource().equals(cell1.cell) && edge.getTarget().equals(cell2.cell));
                        cell1.force = cell1.force.add(computeForceBetween(cell1.cell, cell2.cell, connected, i));
                    }
                }
            }

            //move cells
            for(ForceCell cell: forceCells){
                Point2D totalForce = cell.force;
                cell.cell.relocate(cell.cell.getLayoutX() + totalForce.getX(), cell.cell.getLayoutY() + totalForce.getY());
                cell.force = new Point2D(0,0);
            }

        }

    }

    private Point2D computeForceBetween(Cell cell1, Cell cell2, boolean connected, int iteration){
        Point2D positionC1 = new Point2D(cell1.getLayoutX(), cell1.getLayoutY());
        Point2D positionC2 = new Point2D(cell2.getLayoutX(), cell2.getLayoutY());
        double distance = positionC1.distance(positionC2);
        Point2D forceDirection = positionC2.subtract(positionC1).normalize();

        // attractive force
        Point2D attraction;
        if(connected) {
            double attraction_factor = attractionForce * Math.log(distance / attractionScale);
            attraction = forceDirection.multiply(attraction_factor);
            if (iteration < 150){ // increase attraction at the beginning to avoid clumping
                attraction.multiply(60);
            }
        } else {
            attraction = new Point2D(0,0);
        }

        // repelling force
        double repulsive_factor = repulsiveForce * SCALING_FACTOR / (distance * distance);
        Point2D repulsion = forceDirection.multiply(-repulsive_factor);

        // combine forces
        Point2D totalForce = new Point2D(attraction.getX() + repulsion.getX(),
                attraction.getY() + repulsion.getY());

        return totalForce.multiply(acceleration);
    }

    private class ForceCell{
        Cell cell;
        Point2D force;

        public ForceCell(Cell cell, Point2D force){
            this.cell = cell;
            this.force = force;
        }
    }
}