package com.skidrunner.javafx.raytracer;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mark on 5/3/2016.
 */
public class RaytraceRenderer {
    private static final Logger logger = Logger.getLogger(RaytraceRenderer.class.getName());

    private Canvas canvas;
    private Parent parent;
    private GraphicsContext graphics;

    private Thread thread;
    private final AtomicBoolean running;

    public RaytraceRenderer(Canvas canvas, Parent group) {
        this.canvas = canvas;
        this.parent = parent;
        running = new AtomicBoolean(false);
    }

    public boolean isRunning() {
        boolean value;
        synchronized (running) {
            value = running.get();
        }
        return value;
    }

    private void setRunning(boolean value) {
        synchronized (running) {
            running.set(value);
        }
    }

    public void start() {
        if(isRunning()) {
            return;
        }

        thread = new Thread(new Runnable() {
            public void run() {
                render();
            }
        });
        thread.start();
    }

    protected void render() {

    }

    public void stop() {
        setRunning(false);

        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException exception) {
                logger.log(Level.WARNING, "Trouble stopping renderer: " + exception.getMessage());
            }
        }
    }
}
