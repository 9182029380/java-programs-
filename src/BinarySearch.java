import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input a sorted array
        System.out.print("Enter a sorted array (space-separated): ");
        String input = scanner.nextLine();
        int[] sortedArray = Arrays.stream(input.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Input the target value to search
        System.out.print("Enter the target value to search: ");
        int target = scanner.nextInt();

        // Perform binary search
        int result = binarySearch(sortedArray, target);

        // Display the result
        if (result != -1) {
            System.out.println("Target value " + target + " found at index " + result);
        } else {
            System.out.println("Target value " + target + " not found in the array.");
        }

        scanner.close();
    }

    // Function to perform binary search on a sorted array
    private static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return mid; // Target value found
            } else if (array[mid] < target) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        return -1; // Target value not found
    }
}

