import java.util.ArrayList;
import java.util.Arrays;

public class Chromosome implements Comparable {
    public static ArrayList<int[]> givens;
    public ArrayList<int[]> genes;
    private int fitness;

    Chromosome(){
        this.genes = new ArrayList<>();
    }

    public void randomizeGenes(){
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
                        int index = Meta.RANDOM.nextInt(list.size());
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

    @Override
    public String toString() {
        return this.fitness+"";
    }

    public ArrayList<Integer> getColumn(int col_index){
        int col_grid=getGrid(col_index);
        int offset = col_index%3;
        ArrayList<Integer> col = new ArrayList<>();

        for (int i=0; i<3; i++){
            int [] ch = this.genes.get(col_grid+3*i);
            for (int j=0; j<3; j++) {
                col.add(ch[offset+ j*3]);
            }
        }
        return col;
    }

    public ArrayList<Integer> getGivenColumn(int col_index){
        ArrayList<Integer> givens = new ArrayList<>();
        int col_grid=getGrid(col_index);
        int offset = col_index%3;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++) {
                givens.add(Chromosome.givens.get(col_grid+3*i)[offset+ j*3]);
            }
        }
        return givens;
    }

    public ArrayList<Integer> getRow(int row_index){
        ArrayList<Integer> row = new ArrayList<>();
        int row_grid = getGrid(row_index)*3;

        int offset = (row_index%3)*3;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                row.add(this.genes.get(row_grid+i)[offset+j]);
            }
        }
        return row;
    }

    public ArrayList<Integer> getGivenRow(int row_index){
        ArrayList<Integer> givens = new ArrayList<>();
        int row_grid = getGrid(row_index)*3;

        int offset = (row_index%3)*3;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                givens.add(Chromosome.givens.get(row_grid+i)[offset+j]);
            }
        }
        return givens;
    }

    public int getGrid(int index){
        if (index<3)
            index=0;
        else if (index<6)
            index=1;
        else
            index=2;
        return index;
    }

    public Chromosome clone(){
        Chromosome to_return = new Chromosome();
        for (int i=0; i<9;i++){
            int [] sub_grid = this.genes.get(i).clone();
            to_return.genes.add(i,sub_grid);
        }

        return to_return;
    }
}
