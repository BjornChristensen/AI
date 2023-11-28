package Recursion;

public class Power {
    public static void main(String[] args) {
        System.out.println("2^3="+ power(2,3));
    }

    static int power(int x, int n) {
        if (n==1)
            return x;
        else
            return x*power(x, n-1);
    }
}
