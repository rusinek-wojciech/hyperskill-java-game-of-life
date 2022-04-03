package org.ikinsure.life.controller;

import org.ikinsure.life.model.Universe;
import org.ikinsure.life.view.GameOfLife;

public class App {

    private final int generations;
    private Universe universe;
    private final GameOfLife game;
    private final int size;

    public App(int size, int generations) {
        this.generations = generations;
        this.size = size;
        game = new GameOfLife();
        prepare();
        simulateWorld();
    }

    public void prepare() {
        this.universe = new Universe(size);
        universe.shallowCopyCurrentToNext();
    }

    public void simulateWorld() {
        int generation;
        for (generation = 0; generation < generations; generation++) {
            game.getSimulationPane().setUniverse(universe);
            universe.deepCopyNextToCurrent();
            simulateGeneration();
            game.getGenerationLabel().setText("Generation #" + (generation + 1));
            game.getAliveLabel().setText("Alive: " + universe.getAliveCounter());
            game.getSimulationPane().repaint();
            try {
                Thread.sleep(game.getSlider().getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateGeneration() {
        for (int x = 0; x < universe.getSize(); x++) {
            for (int y = 0; y < universe.getSize(); y++) {
                switch (universe.getNeighbourCounter(x, y)) {
                    case 3:
                        universe.setNext(x, y, true); // reborn
                        break;
                    case 2:
                        universe.setNext(x, y, universe.getCurrent(x, y)); // survive
                        break;
                    default:
                        universe.setNext(x, y, false); // dead
                }
            }
        }
    }
}
