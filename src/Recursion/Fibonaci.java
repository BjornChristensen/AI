package Recursion;

public class Fibonaci {
    public static void main(String[] args) {
        for (int i=1; i<=10; i++)
            System.out.println(fibonaci(i));
    }

    static int fibonaci(int n){
        if (n<=2)
            return 1;
        else
            return fibonaci(n-1)+fibonaci(n-2);
    }
}
