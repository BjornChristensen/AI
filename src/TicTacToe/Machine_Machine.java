package TicTacToe;
// TicTacToe
// Bjørn Christensen, 1/3 2014

import java.util.Scanner;

public class Machine_Machine {

  public static void main(String[] args) {
    int depthX=1;               // Search depth in gametree for player 'X'
    int depthO=8;               // Search depth in gametree for player 'O'
   
    Scanner keyboard=new Scanner(System.in);
    GameState currentState=new GameState();
    System.out.println(currentState);
    
    while(!currentState.gameIsOver()) {
      if (currentState.playerToMove=='X')
        currentState.AB(-(GameState.inf+10), GameState.inf+10, depthX);
      else
        currentState.AB(-(GameState.inf+10), GameState.inf+10, depthO);
      
      Move nextMove=currentState.bestMove;
      currentState=currentState.makeMove(nextMove);
      System.out.println(currentState);
    }
    
    if (currentState.threeInARow()){
      if (currentState.playerToMove=='X')  // last piece was set by 'O' so 'O' won the game
        System.out.println("Game won by O");
      else
        System.out.println("Game won by X");
    }
    if (currentState.boardIsFull())
      System.out.println("Game is draw");
  }
}