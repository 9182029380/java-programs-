import java.io.*;
import java.util.*;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode node) {
        return this.frequency - node.frequency;
    }
}

public class HuffmanCoding {
    private static final int BYTE_SIZE = 8;

    public static void main(String[] args) {
        String inputFile = "input.txt";
        String compressedFile = "compressed.bin";
        String decompressedFile = "decompressed.txt";

        // Compress the file
        compress(inputFile, compressedFile);

        // Decompress the file
        decompress(compressedFile, decompressedFile);
    }

    public static void compress(String inputFile, String compressedFile) {
        try {
            // Read input text file and calculate frequencies
            String text = readFile(inputFile);
            Map<Character, Integer> frequencyMap = calculateFrequencies(text);

            // Build the Huffman tree
            HuffmanNode root = buildHuffmanTree(frequencyMap);

            // Generate Huffman codes
            Map<Character, String> huffmanCodes = generateHuffmanCodes(root, "");

            // Encode the text using Huffman codes
            String encodedText = encodeText(text, huffmanCodes);

            // Write the Huffman codes and encoded text to the compressed file
            writeCompressedFile(compressedFile, frequencyMap, encodedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decompress(String compressedFile, String decompressedFile) {
        try {
            // Read compressed file and reconstruct Huffman tree
            Map<Character, Integer> frequencyMap = readFrequencyMap(compressedFile);
            HuffmanNode root = buildHuffmanTree(frequencyMap);

            // Read encoded text and decode using Huffman tree
            String encodedText = readEncodedText(compressedFile);
            String decodedText = decodeText(encodedText, root);

            // Write the decoded text to the decompressed file
            writeToFile(decompressedFile, decodedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int c;
            while ((c = reader.read()) != -1) {
                content.append((char) c);
            }
        }
        return content.toString();
    }

    private static Map<Character, Integer> calculateFrequencies(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }
        return frequencyMap;
    }

    private static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode internalNode = new HuffmanNode('\0', left.frequency + right.frequency);
            internalNode.left = left;
            internalNode.right = right;

            priorityQueue.add(internalNode);
        }

        return priorityQueue.poll();
    }

    private static Map<Character, String> generateHuffmanCodes(HuffmanNode root, String code) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodes(root, code, huffmanCodes);
        return huffmanCodes;
    }

    private static void generateCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.data != '\0') {
            huffmanCodes.put(node.data, code);
        }

        generateCodes(node.left, code + "0", huffmanCodes);
        generateCodes(node.right, code + "1", huffmanCodes);
    }

    private static String encodeText(String text, Map<Character, String> huffmanCodes) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(huffmanCodes.get(c));
        }
        return encodedText.toString();
    }

    private static void writeCompressedFile(String compressedFile, Map<Character, Integer> frequencyMap, String encodedText)
            throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(compressedFile))) {
            // Write frequency map
            outputStream.writeObject(frequencyMap);

            // Write encoded text
            int padding = BYTE_SIZE - encodedText.length() % BYTE_SIZE;
            outputStream.writeInt(padding);
            outputStream.writeUTF(encodedText);

            // Add padding bits to ensure the last byte is complete
            for (int i = 0; i < padding; i++) {
                outputStream.writeBoolean(false);
            }
        }
    }

    private static Map<Character, Integer> readFrequencyMap(String compressedFile) throws IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(compressedFile))) {
            // Read frequency map
            return (Map<Character, Integer>) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error reading frequency map from compressed file", e);
        }
    }

    private static String readEncodedText(String compressedFile) throws IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(compressedFile))) {
            // Skip frequency map
            inputStream.readObject();

            // Read encoded text
            int padding = inputStream.readInt();
            StringBuilder encodedText = new StringBuilder(inputStream.readUTF());

            // Remove padding bits
            encodedText.setLength(encodedText.length() - padding);

            return encodedText.toString();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error reading encoded text from compressed file", e);
        }
    }

    private static String decodeText(String encodedText, HuffmanNode root) {
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : encodedText.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            if (current.left == null && current.right == null) {
                decodedText.append(current.data);
                current = root;
            }
        }

        return decodedText.toString();
    }

    private static void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}

