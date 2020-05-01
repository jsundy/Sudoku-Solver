import java.util.*;

public class FitnessFunction {
    public Vector<Chromosome> best;

    public int evaluateFitness(Chromosome chromosome){
        int fitness=0;

        //calculate the number of missing digits in each row set and column set
        for (int i=0; i<9; i++){
            fitness += getColFitness(i, chromosome);
            fitness += getRowFitness(i, chromosome);
        }
        return fitness;
    }

    private int getRowFitness(int row_index, Chromosome chromosome) {
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> row = new ArrayList<>();
        int row_grid = getGrid(row_index)*3;

        int offset = (row_index%3)*3;
        //optimization - don't need to loop. Improves O from O(n^2) to O(n)
        row.add(chromosome.genes.get(row_grid)[offset]);
        row.add(chromosome.genes.get(row_grid)[offset+1]);
        row.add(chromosome.genes.get(row_grid)[offset+2]);
        row.add(chromosome.genes.get(row_grid+1)[offset]);
        row.add(chromosome.genes.get(row_grid+1)[offset+1]);
        row.add(chromosome.genes.get(row_grid+1)[offset+2]);
        row.add(chromosome.genes.get(row_grid+2)[offset]);
        row.add(chromosome.genes.get(row_grid+2)[offset+1]);
        row.add(chromosome.genes.get(row_grid+2)[offset+2]);

        set.removeAll(row);
        return set.size();
    }

    private int getColFitness(int col_index, Chromosome chromosome) {
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> col = new ArrayList<>();
        int col_grid=getGrid(col_index);

        int offset = col_index%3;
        for (int i=0; i<3; i++){
            int [] ch = chromosome.genes.get(col_grid+3*i);
            //optimization
            col.add(ch[offset]);
            col.add(ch[offset+3]);
            col.add(ch[offset+6]);
        }

        set.removeAll(col);
        return set.size();
    }

    private int getGrid(int index){
        if (index<3)
            index=0;
        else if (index<6)
            index=1;
        else
            index=2;
        return index;
    }
}
