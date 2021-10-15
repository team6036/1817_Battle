package frc.robot.math;

public class Matrix {
    private Double[][] matrix;

    public Matrix(Double[]...matrix) {
        this.matrix = matrix;

        int length = matrix[0].length;
        if (matrix.length == 0 || length == 0)
            // assert (matrix.length == 0 || length == 0), "";

        // row size needs to be consistent
        for (Double[] m : matrix) {
            // assert m.length == length, "Row size not consistent";
        }
    }

    public int[] getSize() {
        // return rows * columns;
        return new int[] {matrix.length, matrix[0].length};
    }
    
}
