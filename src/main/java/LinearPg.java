import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinearPg {

    public static void main(String[] args){
        LinearPg lp = new LinearPg();
        lp.exec();
    }

    public void exec(){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Stream<String> str = in.lines();
        str.map((v) -> convertToMatrix(v))
                .map((v) -> calc(v))
                .forEach((v) -> output(v));
    }

    public void output(List matrix){
        matrix.forEach((col) -> {
            ((List)col).forEach((v) -> {
                System.out.print(v + ",");
            });
            System.out.println("");
        });
    }

    private List convertToMatrix(String v){
        List<String> rows = Arrays.asList(v.split("|"));

        List result = rows.stream().map((s) -> {
            String[] cols = s.split(",");
            return Arrays.asList(cols);
        }).collect(Collectors.toList());

        return result;
    }

    private List calc(List matrix){
        int min;
        int col_num = ((List)matrix.get(0)).size();
        int row_num = matrix.size();
        int row = 0, column = 0;
        while(true){

            //column selection
            min = Integer.MAX_VALUE;
            for(int k = 0; k < col_num - 1; k++){
                if(getCell(matrix, row_num - 1, k) < min){
                    min = getCell(matrix, row_num - 1, k);
                    column = k;
                }
            }
            if(min >= 0) break;

            //row selection
            min = Integer.MAX_VALUE;
            for(int k = 0; k < row_num - 1; k++){
                int div = getCell(matrix, k, col_num - 1) / getCell(matrix, k, column);
                if( getCell(matrix, k, column) > 0 && div < min){
                    min = div;
                    row = k;
                }
            }

            int pivot = getCell(matrix, row, column);
            for(int k = 0; k < col_num; k++){
                setCell(matrix, row, k, getCell(matrix, row, k) / pivot);
            }

            for(int k = 0; k < row_num; k++){
                if (k != row){
                    int x = getCell(matrix, k, column);
                    for(int j = 0; j < col_num; j++){
                        setCell(matrix, k, j, getCell(matrix, k, j) - x * getCell(matrix, row, j));
                    }
                }
            }
        }

        return matrix;
    }

    private int getCell(List matrix, int row, int col){
        return (int)(((List)matrix.get(row)).get(col);
    }

    private void setCell(List matrix, int row, int col, int val){
        ((List)matrix.get(row)).set(col, val);
    }
}
