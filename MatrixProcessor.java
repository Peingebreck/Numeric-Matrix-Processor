package processor;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MatrixProcessor {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        while (true) {
            try {
                String choice = printMenu();
                if ("1".equals(choice)) {
                    printResult(addMatrices(getMatrix(" first"), getMatrix(" second")));
                } else if ("2".equals(choice)) {
                    printResult(multiplyMatrixByConstant(getMatrix(""), getConstant()));
                } else if ("3".equals(choice)) {
                    printResult(multiplyMatrices(getMatrix(" first"), getMatrix(" second")));
                } else if ("4".equals(choice)) {
                    transposeMatrix();
                } else if ("5".equals(choice)) {
                    printResult(calculateDeterminant(getMatrix("")));
                } else if ("6".equals(choice)) {
                    printResult(inverseMatrix(getMatrix("")));
                } else if ("0".equals(choice)) {
                    break;
                } else {
                    System.out.println("Error: unknown command\n");
                }
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("Error: please enter numbers.\n");
            } catch (IllegalArgumentException e) {
                System.out.println("The operation cannot be performed.\n");
            } catch (ArithmeticException e) {
                System.out.println("This matrix doesn't have an inverse.\n");
            }
        }
    }

    static String printMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit\n" +
                "Your choice: ");
        return sc.next();
    }

    static void printResult(double[][] matrix) {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("The result is:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%s ", matrix[i][j] == 0 ? "0" : df.format(Double.valueOf(matrix[i][j])));
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printResult(double result) {
        System.out.printf("The result is:\n%.2f\n\n", result);
    }

    static double[][] getMatrix(String order) throws NumberFormatException, NoSuchElementException {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter size of%s matrix: ", order);
        int[] size = new int[]{sc.nextInt(), sc.nextInt()};
        if (size[0] <= 0 || size[1] <= 0) {
            throw new NumberFormatException();
        }
        System.out.printf("Enter%s matrix:\n", order);
        double[][] matrix = new double[size[0]][size[1]];
        for (int i = 0; i < size[0]; i++) {
            for (int j = 0; j < size[1]; j++) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        return matrix;
    }

    static double getConstant() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter constant: ");
        return sc.nextDouble();
    }

    static double[][] addMatrices(double[][] matrix1, double[][] matrix2) {
        if (matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length) {
            double[][] result = new double[matrix1.length][matrix1[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[0].length; j++) {
                    result[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException();
        }
    }

    static double[][] multiplyMatrixByConstant(double[][] matrix, double constant) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] * constant;
            }
        }
       return result;
    }

    static double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        double[][] result = new double[matrix1.length][matrix2[0].length];
        double element;
        if (matrix1[0].length == matrix2.length) {
            for (int n = 0; n < matrix1.length; n++) {
                for (int k = 0; k < matrix2[0].length; k++) {
                    element = 0;
                    for (int m = 0; m < matrix2.length; m++) {
                        element += matrix1[n][m] * matrix2[m][k];
                    }
                    result[n][k] = element;
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException();
        }
    }

    static void transposeMatrix() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line\n" +
                "Your choice: ");
        String choice = sc.next();
        try {
            double[][] matrix = getMatrix("");
            if ("1".equals(choice)) {
                printResult(transposeMain(matrix));
            } else if ("2".equals(choice)) {
                printResult(transposeSide(matrix));
            } else if ("3".equals(choice)) {
                printResult(transposeVertical(matrix));
            } else if ("4".equals(choice)) {
                printResult(transposeHorizontal(matrix));
            } else {
                System.out.println("Error: unknown command\n");
            }
        } catch (NumberFormatException | NoSuchElementException e) {
            System.out.println("Error: please enter numbers.\n");
        }
    }

    static double[][] transposeMain(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    static double[][] transposeSide(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int j = matrix[0].length - 1, n = 0; j >= 0; j--, n++) {
            for (int i = matrix.length - 1, m = 0; i >= 0; i--, m++) {
                result[n][m] = matrix[i][j];
            }
        }
        return result;
    }

    static double[][] transposeVertical(double[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = matrix[0].length - 1, m = 0; j >= 0; j--, m++) {
                result[i][m] = matrix[i][j];
            }
        }
        return result;
    }

    static double[][] transposeHorizontal(double[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = matrix.length - 1, n = 0; i >= 0; i--, n++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[n][j] = matrix[i][j];
            }
        }
        return result;
    }

    static double calculateDeterminant(double[][] matrix) {
        double determinant = 0;
        if (matrix.length == matrix[0].length) {
            for (int i = 0; i < matrix.length; i++) {
                determinant += matrix[0][i] * getCofactor(matrix, 0, i);
            }
            return determinant;
        } else {
            throw new IllegalArgumentException();
        }
    }

    static double[][] inverseMatrix(double[][] matrix) {
        double determinant = calculateDeterminant(matrix);
        if (determinant != 0) {
            double[][] matrixOfCofactors = new double[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrixOfCofactors[i][j] = getCofactor(matrix, i, j);
                }
            }
            return multiplyMatrixByConstant(transposeMain(matrixOfCofactors), 1 / determinant);
        } else {
            throw new ArithmeticException();
        }
    }

    static double getCofactor(double[][] matrix, int x, int y) {
        double cofactor = 0;
        double[][] minor = new double[matrix.length - 1][matrix[0].length - 1];
        for (int i = 0, n = 0; i < matrix.length; i++) {
            if (i != x) {
                for (int j = 0, k = 0; j < matrix[0].length; j++) {
                    if (j != y) {
                        minor[n][k] = matrix[i][j];
                        k++;
                    }
                }
                n++;
            }
        }
        if (minor.length == 1) {
            cofactor = minor[0][0];
        } else if (minor.length == 2) {
            cofactor = minor[0][0] * minor[1][1] - minor[0][1] * minor[1][0];
        } else {
            for (int i = 0; i < minor.length; i++) {
                cofactor += minor[0][i] * getCofactor(minor, 0 , i);
            }
        }
        return Math.pow(-1, x + y) * cofactor;
    }
}
