import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static FitnessFunction ff = new FitnessFunction();

    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        Meta.RANDOM.setSeed(seed);
        Chromosome.givens = readFile("test files/1/s01c.txt");

        //initial Generation
        ArrayList<Chromosome> population = new ArrayList<>(Meta.POPULATION_SIZE);
        for (int i=0; i<Meta.POPULATION_SIZE; i++) {
            Chromosome chromosome = new Chromosome();
            chromosome.randomizeGenes();
            population.add(chromosome);
        }

        do {
            for (int i=0; i < Meta.POPULATION_SIZE; i++) {
                population.get(i).setFitness(ff.evaluateFitness(population.get(i)));
            }
            //order population in ascending order based on fitness - goal is to minimize fitness
            Collections.sort(population);   //Collections sort has O(n(log(n))) - uses a variant of merge sort

            GeneticOperator go = new GeneticOperator(population);
            ArrayList<Chromosome> matingPool = go.generateMatingPool(); //generate mating pool using tournament selection
            //apply Genetic Operators - Uniform Crossover and Mutations.
            int children_to_generate_from_crossover = (int) Math.floor((1-Meta.CROSSOVER_PROBABILITY)*(Meta.POPULATION_SIZE));
            for (int i=Meta.POPULATION_SIZE-1; i>=0; i--){
                //implement Crossover based on crossover probability
                if(i >= children_to_generate_from_crossover) {
                    Chromosome p1, p2;
                    //ensures that each parent is different
                    do {
                        p1 = matingPool.get(Meta.RANDOM.nextInt(matingPool.size() - 1));
                        p2 = matingPool.get(Meta.RANDOM.nextInt(matingPool.size() - 1));
                    } while (p1.equals(p2));

                    population.remove(i);
                    population.add(i, go.uniformCrossover(p1, p2));
                }
            }
        }while (population.get(0).getFitness()!=0);

    }

    public static ArrayList<int[]> readFile(String fn){
        int [] givens = new int[81];
        ArrayList<int[]> to_return = new ArrayList<>();
        int [] subgridIndex = {
                0,1,2,9,10,11,18,19,20,
                3,4,5,12,13,14,21,22,23,
                6,7,8,15,16,17,24,25,26,
                27,28,29,36,37,38,45,46,47,
                30,31,32,39,40,41,48,49,50,
                33,34,35,42,43,44,51,52,53,
                54,55,56,63,64,65,72,73,74,
                57,58,59,66,67,68,75,76,77,
                60,61,62,69,70,71,78,79,80
        };

        File file = new File(fn);
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        int index=0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            for (int i=0; i<line.length(); i+=2){
                Integer j = Integer.parseInt(String.valueOf(line.charAt(i)));
                int t = subgridIndex[index];
                givens[t] = j;
                ++index;
            }
        }

        int [] grid = new int[9];
        for (int i=0; i<81; i++){
            grid[i%9] = givens[i];
            if ((i+1)%9==0){
                to_return.add(grid);
                grid = new int[9];
            }
        }
        return to_return;
    }

    private static void printPopulation(ArrayList<Chromosome> population) {
        for (int i = 0; i < population.size(); i++) {
            String to_print = "Population " + i + "\nFitness:"+ population.get(i).getFitness() +"\n[\n";
            for (int j = 0; j < 9; j++)
                to_print += '\t' + Arrays.toString(population.get(i).genes.get(j)) + "\n";
            to_print += "]\n\n";
            System.out.print(to_print);
        }
    }

    private static String printAverageFitness(ArrayList<Chromosome> population){
        int avg = 0;
        for (int i=0; i<Meta.POPULATION_SIZE; i++)
            avg += population.get(i).getFitness();
        return ": Avg fitness: "+avg/Meta.POPULATION_SIZE;
    }
}
