import java.util.*;

public class Chromosome {

	Random rand = new Random();
	float fitness = 0;
	ArrayList<Character> genes = new ArrayList<>();

	// adding random genes to chromosome
	public Chromosome(int length) {
		for (int i = 0; i < length; i++) {
			genes.add((char)(rand.nextInt(127-32) + 32));

		}
	}

	// calculate fitness for this chromosome
	public void calculateFitness(String target) {
		int score = 0;
		for (int i = 0; i < target.length(); i++) {
			if (genes.get(i).equals(target.charAt(i))) {
				score++;
			}
		}
		fitness = (float)score / (float)(target.length());
	}

	// uniform crossover
	public Chromosome crossover(Chromosome partner) {
		Chromosome child = new Chromosome(genes.size());
		for (int i = 0; i < genes.size(); i++) {
			float probability = rand.nextFloat();
			if (probability > 0.5) {
				child.genes.set(i, genes.get(i));
			} else {
				child.genes.set(i, partner.genes.get(i));
			}
		}
		return child;
	}

	// mutate genes with probability of mutationRate
	public void mutate(float mutationRate) {
		for (int i = 0; i < genes.size(); i++) {
			if (mutationRate > rand.nextFloat()) {
				genes.set(i, (char)(rand.nextInt(127-32) + 32));
			}
		}
	}

	public String getPhrase() {
		StringBuilder sb = new StringBuilder();
		for (Character c : genes) {
			sb.append(c);
		}
		return sb.toString();
	}
}
