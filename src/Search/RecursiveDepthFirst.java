package Search;
// Bjørn Christensen 27/11-2023

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class RecursiveDepthFirst {
    public static void main(String[] args) throws IOException {
        Maze maze=new Maze("src/Search/Maze.txt");
        maze.print();
        Field start=maze.find(Maze.Start);          // Find the start field
        LinkedList<Field> path=new LinkedList<>();
        recursiveDepthFirst(maze, start, path);
        for (Field f: path) System.out.println(f);
        maze.print();
    }

    static boolean recursiveDepthFirst(Maze maze, Field f, LinkedList<Field> path){
        // If f is the goal, we are done
        if (maze.get(f)==Maze.Goal){
            path.addFirst(f);
            return true;
        }

        // Mark this field as visited
        if (maze.get(f)!=Maze.Start) maze.set(f, '.');

        // Try to go up
        if (maze.isLegal(f.up())){
            if (recursiveDepthFirst(maze, f.up(), path)) {
                path.addFirst(f);
                return true;
            }
        }
        // Try to go down
        if (maze.isLegal(f.down())){
            if (recursiveDepthFirst(maze, f.down(), path)) {
                path.addFirst(f);
                return true;
            }
        }

        // Try to go left
        if (maze.isLegal(f.left())){
            if (recursiveDepthFirst(maze, f.left(), path)) {
                path.addFirst(f);
                return true;
            }
        }

        // Try to go right
        if (maze.isLegal(f.right())){
            if (recursiveDepthFirst(maze, f.right(), path)) {
                path.addFirst(f);
                return true;
            }
        }
        return false; // a dead end
    }
}
