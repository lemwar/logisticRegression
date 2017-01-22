package utils;
import java.util.Random;

public class Utils
{
	public static double[][] getNormalSelection(int n)
	{
		Random rnd = new Random();
		double[][] X = new double[n][3];
		for (int i = 0; i < n / 2; i++)
		{
			// rndNumber = rnd.nextGaussian()+12;
			X[i][0] = 1;
			X[i][1] = rnd.nextGaussian() + 4;
			X[i][2] = rnd.nextGaussian() + 2;

			X[i + n / 2][0] = 1;
			X[i + n / 2][1] = X[i][2];
			X[i + n / 2][2] = X[i][1];

		}
		return X;
	}
	
	public static double[][] getNormalSelection(int n, double a, double b)
	{
		Random rnd = new Random();
		double[][] X = new double[n][3];
		for (int i = 0; i < n; i++)
		{
			// rndNumber = rnd.nextGaussian()+12;
			X[i][0] = 1;
			X[i][1] = rnd.nextGaussian() + a;
			X[i][2] = rnd.nextGaussian() + b;
		}
		return X;
	}
	
	public static double[][] getSelection(int n)
	{
		Random rnd = new Random();
		double[][] X = new double[n][3];
		for (int i = 0; i < n / 2; i++)
		{
			// rndNumber = rnd.nextGaussian()+12;
			X[i][0] = 1;
			X[i][1] = rnd.nextGaussian() + 5;
			X[i][2] = rnd.nextGaussian() + 2;

		}
		
		for (int i = n / 2; i < n; i++)
		{
			// rndNumber = rnd.nextGaussian()+12;
			X[i][0] = 1;
			X[i][1] = rnd.nextGaussian() + 2;
			X[i][2] = rnd.nextGaussian() + 5;

		}
		return X;
	}

	public static double[][] matrixMultiply(double[][] A, double[][] B)
	{

		int m = A.length;
		int n = B[0].length;
		int o = B.length;
		double[][] res = new double[m][n];

		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < n; j++)
			{
				for (int k = 0; k < o; k++)
				{
					double temp = A[i][k] * B[k][j];
					res[i][j] += temp;
				}
			}
		}
		return res;
	}

	public static double [][] matrixInverse(double [][] A)
	{
		int i, j, k;
		int size = A.length;
		double [][]E = new double [size][size];

		for (i = 0; i < size; i++)
		{
			for (j = 0; j < size; j++)
			{
				if (i == j)
					E[i][j] = 1;
				else
					E[i][j] = 0;
			}
		}

		for (k = 0; k < size; k++)
		{
			for (j = k + 1; j < size; j++)
			{
				A[k][j] = A[k][j] / A[k][k];
			}
			for (j = 0; j < size; j++)
			{
				E[k][j] = E[k][j] / A[k][k];
			}
			A[k][k] = A[k][k] / A[k][k];

			if (k > 0)
			{
				for (i = 0; i < k; i++)
				{
					for (j = 0; j < size; j++)
					{
						E[i][j] = E[i][j] - E[k][j] * A[i][k];
					}
					for (j = size - 1; j >= k; j--)
					{
						A[i][j] = A[i][j] - A[k][j] * A[i][k];
					}
				}
			}
			for (i = k + 1; i < size; i++)
			{ 
				for (j = 0; j < size; j++)
				{
					E[i][j] = E[i][j] - E[k][j] * A[i][k];
				}
				for (j = size - 1; j >= k; j--)
				{
					A[i][j] = A[i][j] - A[k][j] * A[i][k];
				}
			}
		}
		return E;
					
	}
}
