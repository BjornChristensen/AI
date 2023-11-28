package Recursion;

public class GCD {
    public static void main(String[] args) {
        System.out.println("GCD(12,8)="+gcd(12,8));
        System.out.println("GCD(8,18)="+gcd(8,18));
    }

    static int gcd(int a, int b){
        if (a==b) return a;
        if (a>b)
            return gcd(a-b,b);
        else
            return gcd(a,b-a);
    }
}
