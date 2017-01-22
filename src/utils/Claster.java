package utils;

public class Claster 
{
	final double [][] X;
	final int y;
	final int N;
	final int M;
	
	public Claster(double [][] X, int y, int M)
	{
		this.X = X;
		this.y = y;
		this.N = X.length;
		this.M = M;
	}
	
	public int getClasterSize()
	{
		return N;
	}
	
	public int getNumberOfIndications()
	{
		return M;
	}
	
	public int getY()
	{
		return y;
	}
	
	public double[][] getYasMatrix()
	{
		double[][] y = new double [1][1];
		y[0][0] = this.y;
		return y;
	}
	
	public double[][] getX()
	{
		return X;
	}
	
	public double[] getXByIndex(int i)
	{
		return X[i];
	}
	
	public double[] getVectorByIndex(int index)
	{
		double [] vector = new double [N];
		for (int i = 0; i < N; i++)
		{
			vector[i] = X[i][index];
		}
		return vector;
	}
	
	public double [] getMiddleOfClaster()
	{
		double[] x = new double [M];
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < this.getClasterSize(); j++)
			{
				x[i] += this.getVectorByIndex(i)[j];
			}
			x[i] = x[i]/this.getClasterSize();
		}
		return x;
	}

	public double getMaxAbsX() 
	{
		double max = X[0][0];
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < M; j++)
			{
				if (Math.abs(X[i][j]) > max)
				{
					max = Math.abs(X[i][j]); 
				}
			}
		}
		return max;
	}
}
