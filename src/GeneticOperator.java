import java.util.ArrayList;
import java.util.Collections;

public class GeneticOperator {
    private ArrayList<Chromosome> population;

    GeneticOperator(ArrayList<Chromosome> population){
        this.population = population;
    }

    public ArrayList<Chromosome> generateMatingPool(){
        ArrayList<Chromosome> matingPool = new ArrayList<>();
        //mating pool size is the size of the number of children about to be generated
        for (int i=Meta.MATING_POOL-1; i>=0; i--){
            int randomIndex = Meta.RANDOM.nextInt(Meta.POPULATION_SIZE-1);
            Chromosome p1 = this.population.get(randomIndex);
            Chromosome p2 = this.population.get(Meta.RANDOM.nextInt(Meta.POPULATION_SIZE-1));
            matingPool.add(tournamentSelection(p1,p2));
        }
        return matingPool;
    }

    private Chromosome tournamentSelection(Chromosome p1, Chromosome p2){
        double rand = Meta.RANDOM.nextInt();
        return p1.getFitness()<p2.getFitness()?p1:p2;
//        return rand<0?p1:p2;
    }


    public Chromosome uniformCrossover(Chromosome p1, Chromosome p2){
        Chromosome child=new Chromosome();
        int rand = Meta.RANDOM.nextInt((4096-512)+1)+512;
        String mask = Integer.toBinaryString(rand);
        for (int i=0; i<9; i++){
            int [] sub_grid = (mask.charAt(i)=='1'?p1.genes.get(i).clone():p2.genes.get(i).clone());
            child.genes.add(i,sub_grid);
        }

        return child;
    }

    public Chromosome mutation(Chromosome chromosome){
        int random_subgrid = Meta.RANDOM.nextInt(9);
        int sequence_of_mutations = Meta.RANDOM.nextInt((5-1)+1)+1;

        for(int i=0; i<sequence_of_mutations;i++){
            int position1, position2;
            do {
                position1 = Meta.RANDOM.nextInt(9);
                position2 = Meta.RANDOM.nextInt(9);
            }while (Chromosome.givens.get(random_subgrid)[position1] != 0 || Chromosome.givens.get(random_subgrid)[position2] != 0 || position1 == position2);

            if (isValidToSwap(chromosome, random_subgrid, position1, position2)) {
                int v1 = chromosome.genes.get(random_subgrid)[position1];
                int v2 = chromosome.genes.get(random_subgrid)[position2];

                chromosome.genes.get(random_subgrid)[position1] = v2;
                chromosome.genes.get(random_subgrid)[position2] = v1;
            }
        }
        return chromosome;
    }

    private boolean isValidToSwap(Chromosome chromosome, int subgrid_index, int p1, int p2){
            int v1 = chromosome.genes.get(subgrid_index)[p1];
            int v2 = chromosome.genes.get(subgrid_index)[p2];
            ArrayList<Integer> c1,c2,r1,r2;

            int row = chromosome.getGrid(subgrid_index)*3;
            int col = (subgrid_index%3)*3;

            c1 = chromosome.getColumn(col+p1%3);
            c2 = chromosome.getColumn(col+p2%3);

            r1 = chromosome.getRow(row+chromosome.getGrid(p1));
            r2 = chromosome.getRow(row+chromosome.getGrid(p2));

            int occurences = 0;
            occurences += Collections.frequency(c1,v2);
            occurences += Collections.frequency(c2,v1);
            occurences += Collections.frequency(r1,v2);
            occurences += Collections.frequency(r2,v1);

            if (occurences <= Meta.OCCURRENCES)
                return true;

        return false;
    }

}
