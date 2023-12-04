import java.util.Scanner;

public class FactorialCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read input from the user
        System.out.print("Enter a non-negative integer: ");
        int n = scanner.nextInt();

        // Check if the input is non-negative
        if (n < 0) {
            System.out.println("Please enter a non-negative integer.");
        } else {
            // Calculate and display the factorial
            long factorial = calculateFactorial(n);
            System.out.println("The factorial of " + n + " is: " + factorial);
        }

        scanner.close();
    }

    // Function to calculate the factorial of a non-negative integer
    private static long calculateFactorial(int n) {
        if (n == 0 || n == 1) {
            return 1; // Factorial of 0 and 1 is 1
        }

        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }

        return result;
    }
}
