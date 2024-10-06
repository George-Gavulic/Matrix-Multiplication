import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Matrix_Multiplication {

    static int numRowsOne, numColsOne, numRowsTwo, numColsTwo;
    static int[][] matrixOne;
    static int[][] matrixTwo;
    static int[][] resultMatrix;

    private static void readMatricesFromFile(String fileName1, String fileName2) {
        try {
            matrixOne = readMatrixFromFile(fileName1);
            matrixTwo = readMatrixFromFile(fileName2);

            numRowsOne = matrixOne.length;
            numColsOne = matrixOne[0].length;
            numRowsTwo = matrixTwo.length;
            numColsTwo = matrixTwo[0].length;

            if (numColsOne != numRowsTwo) {
                System.out.println("Matrix multiplication is not possible: " +
                                   "columns of first matrix (" + numColsOne + ") != rows of second matrix (" + numRowsTwo + ")");
                return;
            }

            resultMatrix = multiplyMatrices(matrixOne, matrixTwo);
            System.out.println("Matrices read from files and multiplied successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    private static int[][] readMatrixFromFile(String fileName) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(fileName));
        int rows = 0, cols = 0;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (rows == 0) {
                cols = line.split(" ").length;
            }
            rows++;
        }

        int[][] matrix = new int[rows][cols];
        fileScanner.close();
        fileScanner = new Scanner(new File(fileName));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = fileScanner.nextInt();
            }
        }
        fileScanner.close();

        return matrix;
    }

    private static void generateRandomMatrices(String fileName1, String fileName2, int size) {
        Random random = new Random();
        matrixOne = new int[size][size];
        matrixTwo = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixOne[i][j] = random.nextInt(100);
                matrixTwo[i][j] = random.nextInt(100);
            }
        }

        System.out.println("Generated two random matrices of size " + size + "x" + size + ":");
        System.out.println("Matrix One:");
        printMatrix(matrixOne);
        System.out.println("Matrix Two:");
        printMatrix(matrixTwo);

        writeMatrixToFile(fileName1, matrixOne);
        writeMatrixToFile(fileName2, matrixTwo);

        resultMatrix = multiplyMatrices(matrixOne, matrixTwo);
    }

    private static void writeMatrixToFile(String fileName, int[][] matrix) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int[] row : matrix) {
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static int[][] multiplyMatrices(int[][] matrixOne, int[][] matrixTwo) {
        int[][] result = new int[numRowsOne][numColsTwo];

        for (int i = 0; i < numRowsOne; i++) {
            for (int j = 0; j < numColsTwo; j++) {
                for (int k = 0; k < numColsOne; k++) {
                    result[i][j] += matrixOne[i][k] * matrixTwo[k][j];
                }
            }
        }
        return result;
    }

    private static void writeResultToFile(String fileName, int[][] resultMatrix) {
        writeMatrixToFile(fileName, resultMatrix);
        System.out.println("Resultant matrix written to " + fileName);
    }

    private static void printMatrix(int[][] printMatrix) {
        for (int[] row : printMatrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String fileName1;
        String fileName2;

        if (args.length == 2) {
            fileName1 = args[0];
            fileName2 = args[1];
        } else {
            Scanner scr = new Scanner(System.in);

            System.out.print("\nEnter the first file name for the first matrix: ");
            fileName1 = scr.nextLine().trim();

            System.out.print("Enter the second file name for the second matrix: ");
            fileName2 = scr.nextLine().trim();
        }

        File file1 = new File(fileName1);
        File file2 = new File(fileName2);
        
        if (file1.exists() && file2.exists()) {
            System.out.println("Files exist. Reading matrices from files...");
            readMatricesFromFile(fileName1, fileName2);
        } else {
            int size;
            Scanner scr = new Scanner(System.in);
            System.out.print("One or both files do not exist. Enter the size for the square matrices: ");
            size = scr.nextInt();
            numRowsOne = size;
            numColsOne = size;
            numRowsTwo = size;
            numColsTwo = size;
            generateRandomMatrices(fileName1, fileName2, size);
        }

        writeResultToFile("matrix3.txt", resultMatrix);
    }
}
