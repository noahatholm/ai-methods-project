package com.aim.project.obr.hyperheuristics;

public class SimulatedAnnealing {
    private final double initial_factor;
    private double current_temperature;
    private final double final_temp ;
    private final double end_time;
    //A slightly modified version of Geometric Cooling SA that spreads it out over the time it runs.
    public SimulatedAnnealing(double initial_solution_fitness,long runTime) {
        this.initial_factor = initial_solution_fitness * 0.1;
        this.current_temperature = initial_factor;
        this.end_time = runTime * 0.9; //Finish a bit before actual time ends
        this.final_temp = 1.0;

    }

    public double getCurrent_temperature() {
        return current_temperature;
    }

    public void advanceTemperature(long current_time) {
        double timeRatio = current_time / end_time;
        this.current_temperature = initial_factor * Math.pow((final_temp / initial_factor), timeRatio);
    }

    public double get_acceptance_probability(double delta) {
        double temp = Math.max(current_temperature, 0.00001);
        return Math.exp(-delta / temp);
    }

    public String toString(){
        return "Geometric Cooling T_0: " + this.initial_factor + "Current Temperature:" + this.current_temperature;
    }
}
