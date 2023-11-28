package Search;
// Bjørn Christensen 27/11-2023

import java.util.LinkedList;
import java.util.PriorityQueue;

// An element in the priority queue is a List of Fields
class Elm extends LinkedList<Field> implements Comparable<Elm> {
    double priority=0;

    // In-between-method to compute priority when adding a new Field to this queue element
    void addField(Maze m, Field f){
        addLast(f);
        priority=size()+h1(m,f);
    }

    // Manhatten distance: Giver færrest felter at besøge, da dens værdi er højere end h2
    double h1(Maze maze, Field f){
        Field goal=maze.find(Maze.Goal);    // Where is the goal
        return Math.abs(goal.r-f.r)+Math.abs(goal.c-f.c);
    }

    // Birds distance
    double h2(Maze maze, Field f){
        Field goal=maze.find(Maze.Goal);    // Where is the goal
        return Math.sqrt((goal.r-f.r)*(goal.r-f.r)+(goal.c-f.c)*(goal.c-f.c));
    }

    // Used by the PriorityQueue to find best priority
    public int compareTo(Elm o) {
        return (int)Math.signum(priority-o.priority);
    }
}

public class A_Star {
    public static void main(String[] args) {
        Maze maze=new Maze("src/Search/Maze.txt");
        maze.print();
        Field start=maze.find(Maze.Start);          // Find the start field
        Elm solution=aStar(maze, start);
        for (Field f: solution) System.out.println(f);
        maze.print();
    }

    static Elm aStar(Maze maze, Field start_field){
        PriorityQueue<Elm> queue=new PriorityQueue<>();     // The queue contains paths from Start to current Field
        Elm root=new Elm();                                 // Initial path containing the start field
        root.addField(maze, start_field);
        queue.add(root);                                    // push initial path

        while (true){
            if (queue.isEmpty()) return null;               // queue is empty=no solution
            Elm path=queue.poll();                          // pop best path
            Field f=path.getLast();
            if (maze.get(f)==Maze.Goal) return path;        // Test current path
            if (maze.get(f)!=Maze.Start) maze.set(f, '+');  // Mark the field

            // Try to go up
            if (maze.isLegal(f.up())){
                Elm newPath=(Elm)path.clone();
                newPath.addField(maze, f.up());
                queue.add(newPath);
            }

            // Try to go down
            if (maze.isLegal(f.down())){
                Elm newPath=(Elm)path.clone();
                newPath.addField(maze, f.down());
                queue.add(newPath);
            }

            // Try to go left
            if (maze.isLegal(f.left())){
                Elm newPath=(Elm)path.clone();
                newPath.addField(maze, f.left());
                queue.add(newPath);
            }

            // Try to go right
            if (maze.isLegal(f.right())){
                Elm newPath=(Elm)path.clone();
                newPath.addField(maze, f.right());
                queue.add(newPath);
            }
        }
    }
}
