package lab06;
//package com.uofu.main;

/**
 * This class represents a simple row or column vector of numbers.
 * In a row vector, the numbers are written horizontally (i.e., along the columns).
 * In a column vector, the numbers are written vertically (i.e., along the rows).
 * 
 * @author Erin Parker & ?? (STUDENT: Replace the ?? with your first and last name.)
 * @version January 13, 2022
 */
public class MathVector {

	// 2D array to hold the numbers of the vector, either along the columns or rows
	private double[][] data;      
	// set to true for a row vector and false for a column vector
	private boolean isRowVector;
	// count of elements in the vector
	private int vectorSize;
	
	/**
	 * Creates a new row or column vector.
	 * For a row vector, the input array is expected to have 1 row and a positive number of columns,
	 * and this number of columns represents the vector's length.
	 * For a column vector, the input array is expected to have 1 column and a positive number of rows,
	 * and this number of rows represents the vector's length.
	 * 
	 * @param data - a 2D array to hold the numbers of the vector
	 * @throws IllegalArgumentException if the numbers of rows and columns in the input 2D array is not 
	 *         compatible with a row or column vector
	 */
	public MathVector(double[][] data) {
		if(data.length == 0)
			throw new IllegalArgumentException("Number of rows must be positive.");
		if(data[0].length == 0)
			throw new IllegalArgumentException("Number of columns must be positive.");
		
		if(data.length == 1) {
			// This is a row vector with length = number of columns.
			this.isRowVector = true;
			this.vectorSize = data[0].length;
		}
		else if(data[0].length == 1) {
			// This is a column vector with length = number of rows.
			this.isRowVector = false;
			this.vectorSize = data.length;
		}
		else
			throw new IllegalArgumentException("Either the number of rows or the number of columns must be 1.");
		
		// Create the array and copy data over.
		if(this.isRowVector)
			this.data = new double[1][vectorSize];
		else
			this.data = new double[vectorSize][1];
		for(int i=0; i < this.data.length; i++) { 
			for(int j=0; j < this.data[0].length; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	/**
	 * Determines whether this vector is "equal to" another vector, where equality is
	 * defined as both vectors being row (or both being column), having the same 
	 * vector length, and containing the same numbers in the same positions.
	 * 
	 * @param other - another vector to compare
	 */
	public boolean equals(Object other) {
		if(!(other instanceof MathVector))
			return false;
		
		MathVector otherVec = (MathVector) other;
		
		if (this.isRowVector && !otherVec.isRowVector)
			return false;
		if (!this.isRowVector && otherVec.isRowVector)
			return false;
		if (this.vectorSize != otherVec.vectorSize)
			return false;
		
		if (this.isRowVector) {
			for (int i = 0; i < this.vectorSize; i++) {
				if (this.data[0][i] != otherVec.data[0][i]) 
					return false;
			}
		} else {
			for (int i = 0; i < this.vectorSize; i++) {
				if (this.data[i][0] != otherVec.data[i][0]) 
					return false;
			}
		}

		return true;
	}
	
	/**
	 * Generates a returns a new vector that is the transposed version of this vector.
	 */
	public MathVector transpose() {
		double[][] transposedData;
		if (isRowVector) {
			transposedData = new double[vectorSize][1];
			for (int i = 0; i < vectorSize; i++) {
				transposedData[i][0] = data[0][i];
			}
		} else {
			transposedData = new double[1][vectorSize];
			for (int i = 0; i < vectorSize; i++) {
				transposedData[0][i] = data[i][0];
			}
		}
		
		MathVector result = new MathVector(transposedData);
		return result;
	}
	
	/**
	 * Generates and returns a new vector representing the sum of this vector and another vector.
	 * 
	 * @param other - another vector to be added to this vector
	 * @throws IllegalArgumentException if the other vector and this vector are not both row vectors of
	 *         the same length or column vectors of the same length
	 */
	public MathVector add(MathVector other) {
		if (this.vectorSize != other.vectorSize)
			throw new IllegalArgumentException("Both vectors must be equal in length!");
		
		double[][] data = null;
		if (this.isRowVector && other.isRowVector) {
			data = new double[1][vectorSize];
			for (int i = 0; i < vectorSize; i++) {
				data[0][i] = this.data[0][i] + other.data[0][i];
			}
		} else if (!this.isRowVector && !other.isRowVector) {
			data = new double[vectorSize][1];
			for (int i = 0; i < vectorSize; i++) {
				data[i][0] = this.data[i][0] + other.data[i][0];
			}
		} else {
			throw new IllegalArgumentException("Both vectors must be either row vectors or column vectors!");
		}
		MathVector result = new MathVector(data);
		return result;
	}
	
	/**
	 * Computes and returns the dot product of this vector and another vector.
	 * 
	 * @param other - another vector to be combined with this vector to produce the dot product
	 * @throws IllegalArgumentException if the other vector and this vector are not both row vectors of
	 *         the same length or column vectors of the same length
	 */	
	public double dotProduct(MathVector other) {
		double product = 1; 
		if (this.isRowVector && other.isRowVector) {
			for (int i = 0; i < vectorSize; i++) {
				product *= this.data[0][i] * other.data[0][i];
			}
		} else if (!this.isRowVector && !other.isRowVector) {
			for (int i = 0; i < vectorSize; i++) {
				product *= this.data[i][0] * other.data[i][0];
			}
		} else {
			throw new IllegalArgumentException("Both vectors must either be row or column vectors!");
		}
		return product;
	}
	
	/**
	 * Computes and returns this vector's magnitude (also known as a vector's length) .
	 */
	public double magnitude() {
		double sum = 0;
		if (isRowVector) {
			for (double d : data[0]) {
				sum += Math.pow(d, 2);
			}
		} else {
			for (int i = 0; i < vectorSize; i++) {
				sum += Math.pow(data[i][0], 2);
			}
		}
		return Math.sqrt(sum);
	}
	
	/** 
	 * Generates and returns a normalized version of this vector.
	 */
	public MathVector normalize() {
		double[][] normalizedData = null;
		if (isRowVector) {
			normalizedData = new double[1][vectorSize];
			for (int i = 0; i < vectorSize; i++) {
				normalizedData[0][i] = this.data[0][i] / this.magnitude();
			}
		} else {
			normalizedData = new double[vectorSize][1];
			for (int i = 0; i < vectorSize; i++) {
				normalizedData[i][0] = this.data[i][0] / this.magnitude();
			}
		}
		MathVector result = new MathVector(normalizedData);
		return result;
	}
	
	/**
	 * Generates and returns a textual representation of this vector.
	 * For example, "1.0 2.0 3.0 4.0 5.0" for a sample row vector of length 5 and 
	 * "1.0
	 *  2.0
	 *  3.0
	 *  4.0
	 *  5.0" for a sample column vector of length 5. 
	 *  In both cases, notice the lack of a newline or space after the last number.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		if (isRowVector) {
			for (double val : data[0]) {
				if (first) {
					sb.append(val);
					first = false;
				}
				else sb.append(" " + val);
			}
		} else {
			for (int i = 0; i < vectorSize; i++) {
				if (first) {
					sb.append(data[i][0]);
					first = false;
				}
				else sb.append("\n" + data[i][0]);
			}
		}
		return sb.toString();
	}
}
