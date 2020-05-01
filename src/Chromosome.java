import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chromosome implements Comparable {
    public static ArrayList<int[]> givens;
    public ArrayList<int[]> genes;
    private int fitness;
    private int aging;

    Chromosome(Random random){
        genes = new ArrayList<>();
        int row [] = new int[9];
        int cnt = 0;
        for (int i=0; i<81; i++){
            row[i%9] = givens.get(cnt)[i%9];

            if ((i+1)%9==0){
                Integer [] set = {1,2,3,4,5,6,7,8,9};
                ArrayList list = new ArrayList(Arrays.asList(set));
                for (int j=0; j<row.length; j++)
                    if (row[j]!=0){
                        int t = row[j]-1;
                        list.set(t, -1);
                    }
                list.removeIf(o -> o.equals(-1));
                for (int j=0; j<row.length; j++){
                    if (row[j] == 0){
                        int index = random.nextInt(list.size());
                        row[j] = (int) list.get(index);
                        list.remove(index);
                    }
                }
                genes.add(row);
                row = new int[9];
                ++cnt;
            }
        }
    }

    public void setFitness(int fitness){
        this.fitness = fitness;
    }

    public int getFitness(){
        return this.fitness;
    }

    @Override
    public int compareTo(Object o) {
        int compareFitness =((Chromosome)o).getFitness();
        return this.fitness-compareFitness;
    }

    public void incrementAging(){
        ++this.aging;
    }

    public int getAging(){
        return this.aging;
    }

    public void setAging(int aging){
        this.aging = aging;
    }

}
