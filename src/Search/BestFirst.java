package Search;
// Bjørn Christensen 27/11-2023

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class BestFirst {
    public static void main(String[] args) {
        Maze maze=new Maze("src/Search/Maze.txt");
        maze.print();
        Field start=maze.find(Maze.Start);          // Find the start field
        Elm solution=bestFirst(maze, start);
        for (Field f: solution) System.out.println(f);
        maze.print();
    }

    // An element in the priority queue is a List of Fields
    static class Elm extends LinkedList<Field> implements Comparable<Elm> {
        public int compareTo(Elm o) {
            return size()-o.size();
        }
    }

    static Elm bestFirst(Maze maze, Field start_field){
        PriorityQueue<Elm> queue=new PriorityQueue<>();     // The queue contains paths from Start to current Field
        Elm root=new Elm();                                 // Initial path containing the start field
        root.addLast(start_field);
        queue.add(root);                                    // push initial path

        while (true){
            if (queue.isEmpty()) return null;               // queue is empty=no solution
            Elm path=queue.poll();                          // pop from the top: FIFO
            Field f=path.getLast();
            if (maze.get(f)==Maze.Goal) return path;        // Test current path
            if (maze.get(f)!=Maze.Start) maze.set(f, '+');  // Mark the field

            // Try to go up
            if (maze.isLegal(f.up())){
                Elm newPath=(Elm)path.clone();
                newPath.addLast(f.up());
                queue.add(newPath);
            }

            // Try to go down
            if (maze.isLegal(f.down())){
                Elm newPath=(Elm)path.clone();
                newPath.addLast(f.down());
                queue.add(newPath);
            }

            // Try to go left
            if (maze.isLegal(f.left())){
                Elm newPath=(Elm)path.clone();
                newPath.addLast(f.left());
                queue.add(newPath);
            }

            // Try to go right
            if (maze.isLegal(f.right())){
                Elm newPath=(Elm)path.clone();
                newPath.addLast(f.right());
                queue.add(newPath);
            }
        }
    }
}
