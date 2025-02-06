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

import javafx.scene.Group;

public abstract class Edge extends Group {

    private final Cell source;
    private final Cell target;

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

    public Edge (Cell source, Cell target){
        this.source = source;
        this.target = target;
    }
}
