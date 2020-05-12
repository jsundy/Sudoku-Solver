import java.util.*;

public class FitnessFunction {

    public void evaluateFitness(Chromosome chromosome){
        int fitness=0;

        //calculate the number of missing digits in each row set and column set
        for (int i=0; i<9; i++){
            fitness += getColFitness(i, chromosome);
            fitness += getRowFitness(i, chromosome);
        }

        chromosome.setFitness(fitness);
    }

    private int getRowFitness(int row_index, Chromosome chromosome) {
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> row = chromosome.getRow(row_index);
        ArrayList<Integer> givens = chromosome.getGivenRow(row_index);

        set.removeAll(row);
        return set.size()+sameDigitAsGiven(row,givens);
    }

    private int getColFitness(int col_index, Chromosome chromosome) {
        ArrayList<Integer> set = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> col = chromosome.getColumn(col_index);
        ArrayList<Integer> givens = chromosome.getGivenColumn(col_index);

        set.removeAll(col);
        return set.size()+sameDigitAsGiven(col,givens);
    }

    private int sameDigitAsGiven(ArrayList<Integer> set, ArrayList<Integer> givens){
        int penalty = 0;
        givens.removeIf(o -> o.equals(0));
        for (int i=0; i<givens.size();i++) {
            int t = Collections.frequency(set, givens.get(i));
            if (t > 1)
                penalty+=2;
        }
        return penalty;
    }

}
