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

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class Arrow extends Group {

    private final Line line;

    public Arrow() {
        this(new Line(), new Line(), new Line());
    }

    private static final double arrowLength = 15.0;
    private static final double arrowWidth = 3;

    private Arrow(Line line, Line arrow1, Line arrow2) {
        super(line, arrow1, arrow2);
        this.line = line;
        arrow1.setStroke(Color.rgb(80, 109, 126));
        arrow2.setStroke(Color.rgb(80, 109, 126));
        line.setStroke(Color.rgb(80, 109, 126));
        InvalidationListener updater = o -> {
            double ex = getStartX();
            double ey = getStartY();
            double sx = getEndX();
            double sy = getEndY();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double hypot = Math.hypot(sx - ex, sy - ey);
                double factor = arrowLength / hypot;
                double factorO = arrowWidth / hypot;

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part orthogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    // start/end properties

    public void setStartX(double value) {
        line.setStartX(value);
    }

    public double getStartX() {
        return line.getStartX();
    }

    public DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public void setStartY(double value) {
        line.setStartY(value);
    }

    public double getStartY() {
        return line.getStartY();
    }

    public DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public void setEndX(double value) {
        line.setEndX(value);
    }

    public double getEndX() {
        return line.getEndX();
    }

    public DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public void setEndY(double value) {
        line.setEndY(value);
    }

    public double getEndY() {
        return line.getEndY();
    }

    public DoubleProperty endYProperty() {
        return line.endYProperty();
    }

    public void setStrokeDashArray(List<Double> dashArray){
        line.getStrokeDashArray().addAll(dashArray);
    }

}
