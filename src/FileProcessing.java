import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessing {
    public static void main(String[] args) {
        // Input and output file paths
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try {
            // Read data from the input file
            String inputData = readFile(inputFile);

            // Perform processing (counting words in this case)
            int wordCount = countWords(inputData);

            // Write the result to the output file
            writeResult(outputFile, wordCount);

            System.out.println("Processing completed successfully. Result written to " + outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Function to read data from a file
    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Function to perform processing on the data (counting words)
    private static int countWords(String data) {
        String[] words = data.split("\\s+");
        return words.length;
    }

    // Function to write the result to a file
    private static void writeResult(String filePath, int result) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Word Count: " + result);
        }
    }
}
