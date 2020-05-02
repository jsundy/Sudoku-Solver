import java.util.ArrayList;

public class GeneticOperator {
    private ArrayList<Chromosome> population;

    GeneticOperator(ArrayList<Chromosome> population){
        this.population = population;
    }

    public ArrayList<Chromosome> generateMatingPool(){
        ArrayList<Chromosome> matingPool = new ArrayList<>();
        //mating pool size is the size of the number of children about to be generated
        for (int i=Meta.MATING_POOL; i>=0; i--){
            int randomIndex = Meta.RANDOM.nextInt(Meta.POPULATION_SIZE-1);
            Chromosome p1 = this.population.get(randomIndex);
            Chromosome p2 = this.population.get(Meta.RANDOM.nextInt(Meta.POPULATION_SIZE-1));
            matingPool.add(tournamentSelection(p1,p2));
        }
        return matingPool;
    }

    private Chromosome tournamentSelection(Chromosome p1, Chromosome p2){
        double rand = Meta.RANDOM.nextInt();
//        return p1.getFitness()<p2.getFitness()?p1:p2;
        return rand<0?p1:p2;
    }


    public Chromosome uniformCrossover(Chromosome p1, Chromosome p2){
        Chromosome child=new Chromosome();
        int rand = Meta.RANDOM.nextInt()+512;
        String mask = Integer.toBinaryString(rand).substring(0,9);
        for (int i=0; i<mask.length(); i++){
            int [] sub_grid = (mask.charAt(i)=='1'?p1.genes.get(i).clone():p2.genes.get(i).clone());
            child.genes.add(i,sub_grid);
        }

        return child;
    }

}
