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

import java.util.List;

public class StraightEdge extends Edge {

    Arrow arrow;

    public StraightEdge(Cell source, Cell target, FossInfo fossInfo) {

        super(source, target);

        source.addCellChild(target);
        target.addCellParent(source);

        arrow = new Arrow();

        if(fossInfo.getLinkingType().name().equals("DYNAMIC")){
            arrow.setStrokeDashArray(List.of(6.0, 11.0));
        }

        arrow.startXProperty().bind(source.layoutXProperty().add(source.getLineTarget().layoutXProperty().add(12)));
        arrow.startYProperty().bind( source.layoutYProperty().add(source.getLineTarget().layoutYProperty()));

        arrow.endXProperty().bind(target.layoutXProperty().add(target.getLineTarget().layoutXProperty().add(12)));
        arrow.endYProperty().bind(target.layoutYProperty().add(target.getLineTarget().layoutYProperty()));


        getChildren().addAll(arrow);


    }

}
