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

package com.fossvisualizer.graph.Edges;

import com.fossvisualizer.graph.Cell;
import com.fossvisualizer.graph.Edge;
import com.fossvisualizer.info.FossInfo;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class DirectoryEdge extends Edge {

    public DirectoryEdge(Cell source, Cell target, FossInfo fossInfo){
        super(source, target);

        //line is the part of edge that descents straight down
        Line line = new Line();
        //the part of the edge that goes rightward from bottom of line and points at the component
        Arrow arrow = new Arrow();

        line.startXProperty().bind( source.layoutXProperty().add(source.getLineTarget().layoutXProperty().add(12)));
        line.startYProperty().bind( source.layoutYProperty().add(source.getLineTarget().layoutYProperty()));

        line.endXProperty().bind(source.layoutXProperty().add(source.getLineTarget().layoutXProperty()).add(12));
        line.endYProperty().bind(target.layoutYProperty().add(target.getLineTarget().layoutYProperty()));

        arrow.startXProperty().bind(target.layoutXProperty());
        arrow.startYProperty().bind(target.layoutYProperty().add(target.getLineTarget().layoutYProperty()));

        arrow.endXProperty().bind(line.endXProperty());
        arrow.endYProperty().bind(line.endYProperty());

        if(fossInfo.getLinkingType().name().equals("DYNAMIC")){
            line.getStrokeDashArray().addAll(List.of(6.0, 11.0));
            arrow.setStrokeDashArray(List.of(6.0, 11.0));
        }
        line.setStroke(Color.rgb(80, 109, 126));

        getChildren().addAll(line, arrow);
    }

}
