package Search;
// Bjørn Christensen 27/11-2023

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class IterativeDepthFirst {
    public static void main(String[] args) {
        Maze maze=new Maze("src/Search/Maze.txt");
        maze.print();
        Field start=maze.find(Maze.Start);          // Find the start field
        LinkedList<Field> solution=iterativeDepthFirst(maze, start);
        for (Field f: solution) System.out.println(f);
        maze.print();
    }

    static LinkedList<Field> iterativeDepthFirst(Maze maze, Field start_field){
        Stack<LinkedList<Field>> stack=new Stack<>();   // The stack contains paths from Start to current Field
        LinkedList<Field> root=new LinkedList<>();      // Initial path containing the start field
        root.addLast(start_field);
        stack.push(root);                               // push initial path
                                                        // Note: The Java Stack class will push to the bottom
                                                        // and pop from the bottom !!!

        while (true){
            if (stack.isEmpty()) return null;               // stack is empty=no solution
            LinkedList<Field> path=stack.pop();             // pop
            Field f=path.getLast();
            if (maze.get(f)==Maze.Goal) return path;        // Test current path
            if (maze.get(f)!=Maze.Start) maze.set(f, '.');  // Mark the field

            // Try to go up
            if (maze.isLegal(f.up())){
                LinkedList<Field> newPath=(LinkedList<Field>)path.clone();
                newPath.addLast(f.up());
                stack.push(newPath);
            }

            // Try to go down
            if (maze.isLegal(f.down())){
                LinkedList<Field> newPath=(LinkedList<Field>)path.clone();
                newPath.addLast(f.down());
                stack.push(newPath);
            }

            // Try to go left
            if (maze.isLegal(f.left())){
                LinkedList<Field> newPath=(LinkedList<Field>)path.clone();
                newPath.addLast(f.left());
                stack.push(newPath);
            }

            // Try to go right
            if (maze.isLegal(f.right())){
                LinkedList<Field> newPath=(LinkedList<Field>)path.clone();
                newPath.addLast(f.right());
                stack.push(newPath);
            }
        }
    }
}
