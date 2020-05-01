import java.util.*;

public class FitnessFunction {
    private Chromosome previousBest = null;
    private Chromosome currentBest = null;

    public int evaluateFitness(Chromosome chromosome){
        int fitness=0;

        //calculate the number of missing digits in each row set and column set
        for (int i=0; i<9; i++){
            fitness += getColFitness(i, chromosome);
            fitness += getRowFitness(i, chromosome);
        }

        //aging factor
        if (currentBest != null) {
            if (fitness < currentBest.getFitness()+currentBest.getAging())
                currentBest = chromosome;
        }else
            currentBest = chromosome;

        return fitness+chromosome.getAging();
    }

    private int getRowFitness(int row_index, Chromosome chromosome) {
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> row = new ArrayList<>();
        ArrayList<Integer> givens = new ArrayList<>();
        int row_grid = getGrid(row_index)*3;

        int offset = (row_index%3)*3;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                row.add(chromosome.genes.get(row_grid+i)[offset+j]);
                int t = Chromosome.givens.get(row_grid+i)[offset+j];
                if (t!=0)
                    givens.add(t);
            }
        }
        set.removeAll(row);
        return set.size()+sameDigitAsGiven(row,givens);
    }

    private int getColFitness(int col_index, Chromosome chromosome) {
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> col = new ArrayList<>();
        ArrayList<Integer> givens = new ArrayList<>();
        int col_grid=getGrid(col_index);

        int offset = col_index%3;
        for (int i=0; i<3; i++){
            int [] ch = chromosome.genes.get(col_grid+3*i);

            for (int j=0; j<3; j++) {
                col.add(ch[offset+ j*3]);
                int t = Chromosome.givens.get(col_grid+3*i)[offset+ j*3];
                if (t != 0)
                    givens.add(t);
            }
        }

        set.removeAll(col);
        return set.size()+sameDigitAsGiven(col,givens);
    }

    //helper functions
    private int getGrid(int index){
        if (index<3)
            index=0;
        else if (index<6)
            index=1;
        else
            index=2;
        return index;
    }

    private int sameDigitAsGiven(ArrayList<Integer> set, ArrayList<Integer> givens){
        int penalty = 0;
        for (int i=0; i<givens.size();i++) {
            int t = Collections.frequency(set, givens.get(i));
            if (t > 1)
                penalty+=2;
        }
        return penalty;
    }

    public void setBest(Chromosome best){
        this.previousBest = best;
    }

    public void agingFactor(){
        if (previousBest!=null){
            if (previousBest==currentBest)
                previousBest.incrementAging();
//            else
//                previousBest.setAging(0);
        }
    }

}
