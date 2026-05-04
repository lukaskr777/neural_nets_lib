import java.util.Arrays;

public class Matrix {

    double[][] row_matrix;
    double[][] col_matrix;
    int rows, cols;

    public Matrix(int rows, int cols){
        this.row_matrix = identity(rows, cols).row_matrix;
        this.col_matrix = identity(rows, cols).col_matrix;
        this.rows = rows;
        this.cols = cols;
    }
    public Matrix(double[][] row_matrix, double[][] col_matrix){
        this.row_matrix = row_matrix;
        this.col_matrix = col_matrix;
        this.rows = row_matrix.length;
        this.cols = row_matrix[0].length;
    }

    public double at(int row, int col){
        if(invalidRow(row)) throw new IllegalArgumentException("Invalid row");
        if(invalidCol(col)) throw new IllegalArgumentException("Invalid column");
        return row_matrix[row][col];
    }
    public void transpose(){
      double[][] row_m = row_matrix;
      double[][] col_m = col_matrix;

      this.rows = col_m.length;
      this.cols = row_m.length;

      this.row_matrix = col_m;
      this.col_matrix = row_m;
    }

 

    public Matrix scale(double scale){
        for(int i = 0; i != rows; ++i){
            for(int j = 0; j != cols; ++j){
                this.row_matrix[i][j] *= scale;
                this.col_matrix[j][i] *= scale;
            }
        }
        return this;
    }

    private boolean invalidRow(int row){
        return row < 0 || row >= rows;
    }
    private boolean invalidCol(int col){
        return col < 0 || col >= cols;
    }

    public double[] getRow(int row){
        if(invalidRow(row)) throw new IllegalArgumentException("Invalid row");
        return row_matrix[row].clone();
    }
    public double[] getCol(int col){
        if(invalidCol(col)) throw new IllegalArgumentException("Invalid column");
        return col_matrix[col].clone();
    }
    public void multiply(Matrix b){
        Matrix r = multiply(this, b);
        this.row_matrix = r.row_matrix;
        this.col_matrix = r.col_matrix;
        this.cols = r.cols;
        this.rows = r.rows;
    }

    public void set(int row, int col, double value){
        this.row_matrix[row][col] = value;
        this.col_matrix[col][row] = value;
    }

    @Override
    public String toString(){
        String s = "";
        for(int i = 0; i != rows; ++i){
            s += Arrays.toString(row_matrix[i]) + "\n";
        }
        return s;
    }

    @Override
    public Matrix clone(){
        Matrix c = identity(rows, cols);
        for(int i = 0; i != rows; ++i){
            for(int j = 0; j != cols; ++j){
                c.set(i, j, at(i, j));
            }
        }
        return c;
    }

    public static void fill(Matrix m, double value){
        for(int i = 0; i != m.rows; ++i){
            for(int j = 0; j != m.cols; ++j){
                m.set(i, j, value);
            }
        }
    }

    public static Matrix multiply(Matrix a, Matrix b){
        if(a.cols != b.rows) throw new IllegalArgumentException("Non multipliable matrices!");

        Matrix I = identity(a.rows, b.cols);
        double[][] r_matrix = I.row_matrix;
        double[][] c_matrix = I.col_matrix;

        for(int i = 0; i != r_matrix[0].length; ++ i){
            double[] col = b.getCol(i);
            for(int j = 0; j != r_matrix.length; ++j){
            
                double d = dot(a.getRow(j),col);
                r_matrix[j][i] = d;
                c_matrix[i][j] = d;
            }
        }
        return new Matrix(r_matrix,c_matrix);

    }
    public static double dot(double[] a, double[] b){
        if(a.length != b.length) throw new IllegalArgumentException("Vector lengths do not match");
        double s = 0;
        for(int i = 0; i != a.length; ++i){
            s += a[i]*b[i];
        }
        return s;

    }
    public static double[] columnVector(Matrix m, int col){
        double[] column = new double[m.rows];
        for(int i = 0; i != column.length; ++i){
            column[i] = m.at(i, col);
        }
        return column;
    }
    public static Matrix verticalConcat(Matrix a, Matrix b){
        if(a.rows != b.rows) throw new IllegalArgumentException(" matrices cannot be vertically concatenated (sizes do not match)");
        Matrix c = identity(a.rows, a.cols + b.cols);
        for(int i = 0; i != c.rows; ++i){
            int ix = 0;
            for(int j = 0; j != a.cols; ++j, ++ix){
                c.set(i,ix,a.at(i,j));
            } 
            for(int j = 0; j != b.cols; ++j, ++ix){
                c.set(i,ix,b.at(i,j));
            } 
        }
        return c;
    }

    public static Matrix identity(int rows, int cols){
        double[][] r_matrix = new double[rows][];
        double[][] c_matrix = new double[cols][];

        for(int i = 0; i != rows; ++i){
            double[] r = new double[cols];
            if(i < cols){
                r[i] = 1;
            }
            r_matrix[i] = r;
        }
        for(int i = 0; i != cols; ++i){
            double[] r = new double[rows];
            if(i < rows){
                r[i] = 1;
            }
            c_matrix[i] = r;
        }
        return new Matrix(r_matrix,c_matrix);
    }

    public static Matrix transpose(Matrix m){
        Matrix I = identity(m.cols, m.rows);
        double[][] r_t_matrix = I.row_matrix;
        double[][] c_t_matrix = I.col_matrix;

        for(int i = 0; i != m.cols; ++i){
            for(int j = 0; j != m.rows; ++j){
                r_t_matrix[i][j] = m.row_matrix[j][i];
                c_t_matrix[j][i] = m.row_matrix[j][i];

            }
        }
        return new Matrix(r_t_matrix,c_t_matrix);
    }
    
}
