// Infinite monkey theorem genetic algorithm
// Based on Daniel Shiffman's code
// https://github.com/nature-of-code/noc-examples-processing/tree/master/chp09_ga/NOC_9_01_GA_Shakespeare_simplified


import java.io.IOException;

public class MonkeyAlgorithm {

	static String target = "All the world's a stage";
	static int populationMax = 10000;
	static float mutationRate = (float)0.01;
	static Population population = new Population(target, populationMax, mutationRate);

	public static void main(String[] args) {
		while (!population.isFinished()) {
			clearConsole();
			System.out.println("Generation: " + population.getGenerations());
			System.out.println("Average Fitness: " + population.averageFitness());
			System.out.println("Mutation rate: " + mutationRate);
			System.out.println("Population: " + populationMax);
			population.generate();
			System.out.println("Best Phrase: " + population.getBest());
		}
	}

	public static void clearConsole() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
		catch (IOException | InterruptedException ex) {}
	}

}
