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
