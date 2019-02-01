/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.foreach.animacion;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author 6_Delta
 */
public class CachedTimelineTransition extends Transition {

    protected static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);
    protected final Node node;
    protected Timeline timeline;
    private boolean oldCache = false;
    private CacheHint oldCacheHint = CacheHint.DEFAULT;
    private final boolean useCache;

    /**
     * @param node The node that is being animated by the timeline
     * @param timeline The timeline for the animation, it should be from 0 to 1
     * seconds
     */
    public CachedTimelineTransition(final Node node, final Timeline timeline) {
        this(node, timeline, true);
    }

    /**
     * @param node The node that is being animated by the timeline
     * @param timeline The timeline for the animation, it should be from 0 to 1
     * seconds
     * @param useCache When true the node is cached as image during the
     * animation
     */
    public CachedTimelineTransition(final Node node, final Timeline timeline, final boolean useCache) {
        this.node = node;
        this.timeline = timeline;
        this.useCache = useCache;
        statusProperty().addListener((ObservableValue<? extends Animation.Status> ov, Animation.Status t, Animation.Status newStatus) -> {
            switch (newStatus) {
                case RUNNING:
                    starting();
                    break;
                default:
                    stopping();
                    break;
            }
        });
    }

    /**
     * Llamado cuando una animación inicia
     */
    protected void starting() {
        if (useCache) {
            oldCache = node.isCache();
            oldCacheHint = node.getCacheHint();
            node.setCache(true);
            node.setCacheHint(CacheHint.SPEED);
        }
    }

    /**
     * Llamado cuando una aimación termina
     */
    protected void stopping() {
        if (useCache) {
            node.setCache(oldCache);
            node.setCacheHint(oldCacheHint);
        }
    }

    @Override
    protected void interpolate(double d) {
        timeline.playFrom(Duration.seconds(d));
        timeline.stop();
    }

}
