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
