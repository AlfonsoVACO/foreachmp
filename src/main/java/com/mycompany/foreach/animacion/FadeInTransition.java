/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.animacion;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author 6_Delta
 */
public class FadeInTransition extends CachedTimelineTransition {

    /**
     * @param node The node to affect
     */
    public FadeInTransition(final Node node) {
        super(
                node,
                TimelineBuilder.create()
                        .keyFrames(
                                new KeyFrame(Duration.millis(0),
                                        new KeyValue(node.opacityProperty(), 0, WEB_EASE)),
                                new KeyFrame(Duration.millis(1000),
                                        new KeyValue(node.opacityProperty(), 1, WEB_EASE))
                        )
                        .build()
        );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));

    }
}
