import java.util.*;

public class Population {

	float mutationRate = 0;
	int totalPopulation = 0;
	ArrayList<Chromosome> population = new ArrayList<>();
	String target = "";
	float maxFitness = (float)0;
	ArrayList<Chromosome> newPopulation = new ArrayList<>();
	int generations = 0;
	float perfectScore = (float)1;
	boolean finished = false;
	Random rand = new Random();

	public Population(String target, int totalPopulation, float mutationRate) {
		this.totalPopulation = totalPopulation;
		this.target = target;
		this.mutationRate = mutationRate;
		for (int i = 0; i < totalPopulation; i++) {
			population.add(new Chromosome(target.length()));
		}
	}

	// rejection sampling
	private Chromosome acceptReject() {
		int counter = 0;
		while (true) {
			int index = rand.nextInt(population.size());
			float random = rand.nextFloat() * maxFitness;
			Chromosome partner = population.get(index);
			if (random < partner.fitness) {
				return partner;
			}
			counter++;
			if (counter > 10000) {
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void generate() {
		// natural selection
		for (int i = 0; i < population.size(); i++) {
			population.get(i).calculateFitness(target);
		}

		for (int i = 0; i < population.size(); i ++) {
			if (population.get(i).fitness > maxFitness) {
				maxFitness = population.get(i).fitness;
			}
		}
		newPopulation.clear();
		for (int i = 0; i < population.size(); i++) {
			Chromosome partnerA = acceptReject();
			Chromosome partnerB = acceptReject();
			while (partnerB.equals(partnerA)) {
				partnerB = acceptReject();
			}
			Chromosome child = partnerA.crossover(partnerB);
			child.mutate(mutationRate);
			newPopulation.add(child);
		}
		population = (ArrayList<Chromosome>)newPopulation.clone();
		generations++;
	}

	public String getBest() {
		for (int i = 0; i < population.size(); i++) {
			population.get(i).calculateFitness(target);
		}
		float best = (float)0;
		int index = 0;
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).fitness > best) {
				index = i;
				best = population.get(i).fitness;
			}
		}
		if (best == perfectScore) {
			finished = true;
		}
		return population.get(index).getPhrase();
	}

	public boolean isFinished() {
		return finished;
	}

	public int getGenerations() {
		return generations;
	}

	public float averageFitness() {
		float total = 0;
		for (int i = 0; i < population.size(); i++) {
			total += population.get(i).fitness;
		}
		return  total / (float) population.size();
	}

	public String allPhrases() {
		StringBuilder sb = new StringBuilder();
		int limit  = 50;
		for (int i = 0; i < limit; i++) {
			sb.append(population.get(i).getPhrase());
			sb.append("\n");
		}
		return sb.toString();
	}


}
