package frc.robot.math;

public class Matrix {
    private Double[][] matrix;

    public Matrix(Double[]...matrix) {
        this.matrix = matrix;

        int length = matrix[0].length;
        if (matrix.length == 0 || length == 0)
            assert (matrix.length == 0 || length == 0) : "Matrix cannot be of size 0x0";

        // row size needs to be consistent
        for (Double[] m : matrix) {
            assert m.length == length : "Row length not consistent";
        }
    }

    public Matrix multiply(Matrix m) {
        assert getSize()[1] == m.getSize()[0] : "#Cols in Matrix 1 != #Rows in Matrix 2";

        Matrix n = new Matrix();

        //

        return n;
    }

    public Matrix multiply(Double scalar) {
        Double[][] n = matrix;
        for (int i = 0; i < n.length; i++) {
            for (int ii = 0; ii < n[i].length; ii++) {
                n[i][ii] *= scalar;
            }
        }
        return new Matrix(n);
    }

    public static Double multiply(Double[] row, Double[] col) {
        double sum = 0;
        for (int i = 0; i < row.length; i++) {
            sum += row[i]*col[i];
        }
        return sum;
    }

    public int[] getSize() {
        // return rows * columns;
        return new int[] {matrix.length, matrix[0].length};
    }
    
}
