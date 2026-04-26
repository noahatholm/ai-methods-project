package com.aim.project.obr.hyperheuristics;

import java.util.Arrays;
import java.util.Random;

public class ChoiceFunction {
    private double[][] order_performance; //Tracks how well a heuristic performs based on the previous one, should allow the HH to learn exploration -> exploitation
    private double[] heuristic_performance;
    private int[] starvation_counter;

    //Choice function weightings
    private double order_weight;
    private double performance_weight;
    private double starvation_weight;

    private Random rng;


    private int numberOfheuristics;

    private double learning_rate;
    public ChoiceFunction(int numberOfheuristics, Random rng){
        //Set initial learning rate;
        learning_rate = 0.1;
        this.order_weight = 1;
        this.performance_weight = 1;
        this.starvation_weight = 0.2;

        this.rng = rng;
        this.numberOfheuristics = numberOfheuristics;

        //The order tracking component
        heuristic_performance = new double[numberOfheuristics];
        order_performance = new double[numberOfheuristics][numberOfheuristics];
        starvation_counter = new int[numberOfheuristics];
        //Formula will involve multiplication so can't allow it to be 0
        for (int i = 0; i<numberOfheuristics; i++){
            Arrays.fill(order_performance[i], 0.1);
        }
        Arrays.fill(heuristic_performance, 0.1);
    }

    public void update_heuristic_performance(int heuristic, double difference, double cpu_time){
        difference = difference * -1;
        if (difference > 0) { //if it improved (Reward)
            heuristic_performance[heuristic] = learning_rate * (difference/cpu_time) * heuristic_performance[heuristic];
        }
        else{ //If it didn't improve (Punish)
            heuristic_performance[heuristic] = (1-learning_rate) * heuristic_performance[heuristic];
        }
    }

    public void update_order_performance(int heuristic, int prev_heuristic, double difference, double cpu_time){
        difference = difference * -1;
        if (difference > 0){ //if it improved (Reward)
            order_performance[prev_heuristic][heuristic] = learning_rate * (difference/cpu_time) * order_performance[prev_heuristic][heuristic];
        }
        else{ //If it didn't improve (Punish)
            order_performance[prev_heuristic][heuristic] = (1-learning_rate) * order_performance[prev_heuristic][heuristic];
        }
    }

    public void update_starvation_counter(int heuristic){ //sets heuristic last used to 0 and increments everyone else
        for (int i = 0; i < starvation_counter.length; i++){
            if (i == heuristic) {
                starvation_counter[i] = 0;
                continue;
            };
            starvation_counter[i]++;
        }
    }

    private double normalise(double n,double n_min,double n_max){
        if (n_max == n_min) return 0.5; //prevent divide by 0
        return (n - n_min) / (n_max - n_min);
    }

    public int heuristicChoiceFunction(int prev_heuristic){
        int best_choice = -1;
        double best_choice_value = Double.MIN_VALUE;
        int starvation_max = max(starvation_counter);
        double order_max = max(order_performance[prev_heuristic]);
        double order_min = min(order_performance[prev_heuristic]);
        double performance_min = min(heuristic_performance);
        double performance_max = max(heuristic_performance);

        double [] selection_scores =  new double[numberOfheuristics];
        double sum = 0;
        for (int i = 0; i < numberOfheuristics; i++){
            double starvation_normal = normalise(starvation_counter[i], 0, starvation_max);
            double order_normal = normalise(order_performance[prev_heuristic][i], order_min, order_max);
            double performance_normal = normalise(heuristic_performance[i], performance_min, performance_max);

            double selection_score = performance_weight * performance_normal + order_weight * order_normal + starvation_weight * starvation_normal;
            selection_scores[i] = selection_score;
            sum+=selection_score;
        }
        return rouletteWheelSelection(selection_scores,sum);
    }

    private int rouletteWheelSelection(double[] selection_scores, double sum){
        double random = rng.nextDouble();
        double target = sum * random;
        double new_sum = 0;
        for (int i = 0; i < selection_scores.length; i++){
            new_sum+= selection_scores[i];
            if (target <= new_sum) return i;
        }
        return -1; //should never happen
    }

    private int max(int[] array){
        int max = Integer.MIN_VALUE;
        for (int j : array) {
            if (j > max) max = j;
        }
        return max;
    }

    private double max(double[] array){
        double max = Double.MIN_VALUE;
        for (double j : array) {
            if (j > max) max = j;
        }
        return max;
    }


    private double min(double [] array){
        double min = Double.MAX_VALUE;
        for (double j : array){
            if (j < min) min = j;
        }
        return min;
    }

}
