package Search;
// Bjørn Christensen 27/11-2023

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {
    static final char Start='S';
    static final char Goal='G';
    char[][] data;

    public static void main(String[] args) throws IOException {
        Maze maze=new Maze("src/Search/Maze.txt");
        maze.print();
        System.out.println(maze.find(Start));
        System.out.println(maze.find(Goal));
    }

    Maze(String filename){
        try { load(filename); } catch (IOException e) { e.printStackTrace();}
    }

    void load(String filename) throws IOException {
        FileReader fil = new FileReader(filename);
        BufferedReader ind = new BufferedReader(fil);

        // Read data from file to temp ArrayList
        ArrayList<String> templist=new ArrayList<>();
        String line=ind.readLine();
        while (line!=null){
            templist.add(line);
            line=ind.readLine();
        }
        ind.close();

        // Copy from ArrayList to char[][]
        data=new char[templist.size()][];
        for (int i=0; i<templist.size(); i++){
            data[i]=templist.get(i).toCharArray();
        }
    }

    char get(Field f){ return data[f.r][f.c];  }
    void set(Field f, char value){ data[f.r][f.c]=value; }
    boolean isLegal(Field f) {return (data[f.r][f.c]==' ' || data[f.r][f.c]==Goal);}

    Field find(char item){
        for (int r=0; r<data.length; r++){
            for (int c=0; c<data[r].length; c++){
                if (data[r][c]==item)
                    return new Field(r,c);
            }
        }
        return null;
    }

    // Print the maze
    void print(){
        System.out.print(" ");
        for (int i=0; i<data[0].length; i++)
            System.out.print(i%10); // col numbers
        System.out.println();
        for (int r=0; r<data.length; r++){
            System.out.print(r%10); // row numbers
            for (int c=0; c<data[r].length; c++){
                System.out.print(data[r][c]);
            }
            System.out.println();
        }
    }
} // class Maze

// A Field is a position in the Maze
class Field {
    int r,c;
    Field(int r, int c) { this.r=r; this.c=c; }
    Field left() { return new Field(r,c-1); }
    Field right(){ return new Field(r,c+1); }
    Field up()   { return new Field(r-1, c); }
    Field down() { return new Field(r+1, c); }
    public String toString() { return "("+r+","+c+")"; }
} // class Field