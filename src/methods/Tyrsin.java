package methods;

import java.util.Random;

import utils.Claster;

public class Tyrsin
{
	static int M;
	static double [] x0;
	static double [] x1;
	static double [] x2;
	
	static double [] d;
	static double [] s;
	
	//static double [] b0;
	//static double [] b;
	
	static double findQ(Claster X1, Claster X2, double [] b)
	{
		double Q = 0;
		
		double [][] bT = new double [1][M];
		for(int i = 0; i < M; i++)
		{
			bT[0][i] = b[i];
		}
		for (int i = 0; i < X1.getClasterSize(); i++)
		{
			double [][] x = new double [M][1];

			for (int j = 0; j < M; j++)
			{
				x[j][0] = X1.getX()[i][j];
			}
			
			double [][] arg2 = utils.Utils.matrixMultiply(bT, x);
			double arg = (-arg2[0][0]*X1.getY());
			
			Q += Math.log(1+Math.exp(arg));
		}
		
		for (int i = 0; i < X2.getClasterSize(); i++)
		{
			double [][] x = new double [M][1];

			for (int j = 0; j < M; j++)
			{
				x[j][0] = X2.getX()[i][j];
			}
			
			double [][] arg2 = utils.Utils.matrixMultiply(bT, x);
			double arg = (-arg2[0][0]*X2.getY());
			
			Q += Math.log(1+Math.exp(arg));
			//System.out.println(Math.log(1+Math.exp(arg)));
		}
		
		return Q;
	}
	
	static double[] getVectorB0()
	{
		double[] b0 = new double [M];

		for(int i = 1; i < M; i++)
		{
			b0[0] -= x0[i]*d[i];
			b0[i] = d[i];
		}
		
		
		
		double sumSquareB0 = 0;
		for (int i = 0; i < M; i++)
		{
			sumSquareB0 += Math.pow(b0[i], 2);
		}
		double lengthB0 = Math.sqrt(sumSquareB0);
		
		for (int i = 0; i < M; i++)
		{
			b0[i] = b0[i]/lengthB0;
		}
		return b0;
		
		
	}
	
	static double [] getVectorD()
	{
		double[] d = new double[M];
		for (int i = 0; i < M; i++)
		{
			d[i] = x2[i] - x1[i];
		}
		return d;
	}
	
	@Deprecated
	static double[] getVectorS()
	{
		double [] s = new double [M];
		
		s[0] = 0;
		
		for(int i = 1; i < M; i++)
		{
			s[i] = d[M-i];
		}
		return s;
	}
	
	static double [] findMiddlePoint(Claster X1, Claster X2)
	{
		double [] x0 = new double [M];
		
		for (int i = 0; i < M; i++)
		{
			x0[i] = (X1.getMiddleOfClaster()[i] + X2.getMiddleOfClaster()[i])/2;
		}
		
		return x0;
	}
	
	static double getVectorLength(double [] v)
	{
		double sumSquareV = 0;
		for (int i = 0; i < M; i++)
		{
			sumSquareV += Math.pow(v[i], 2);
		}
		double lengthV = Math.sqrt(sumSquareV);
		return lengthV;
	}

	public static double[] startMethod(Claster X1, Claster X2, double error) 
	{
		M = X1.getNumberOfIndications();
		
		x0 = findMiddlePoint(X1, X2);
		x1 = X1.getMiddleOfClaster();
		x2 = X2.getMiddleOfClaster();
		
		//find vector d
		d = getVectorD();
		
		//find vector S
		//s = getVectorS();
		
		//Find b0 - unit vector S
		double [] b0 = getVectorB0();
		System.out.println("\nCoefficient b0:");
		for (int i = 0; i < M; i++)
		{
			System.out.println(b0[i]);
		}
		
		//Find vector b
		double [] b = b0;
		double lengthB = getVectorLength(b);
		
		//find h
		double h = 0;
		if (X1.getMaxAbsX() >= X2.getMaxAbsX())
			h = X1.getMaxAbsX();
		else
			h = X2.getMaxAbsX();
		System.out.println("h = " + h);
		
		double Q = findQ(X1, X2, b);
		System.out.println("\nQ = " + Q);
		
		for(double k = 1; k < h /*|| (Math.abs(findQ(X1, X2, b) - findQ(X1, X2, bLast)) > error && k > 0)*/; 
				k = k + h / (X1.getClasterSize() + X2.getClasterSize()))
		{
			for(int l = 0; l < 1000; l++)
			{
				double[] bNew = new double [M];
				Random rnd = new Random();
				for (int i = 0; i < M; i++)
				{
					bNew[i] = ((b[i] + h * rnd.nextGaussian())/lengthB)*k;
				}
				
				if(Q > findQ(X1, X2, bNew))
				{
					b = bNew;
					lengthB = getVectorLength(b);
					Q = findQ(X1, X2, b);
					System.out.println("\nQ = " + Q + " k = " + k);
				}

			}
		}

		System.out.println("\n\nCoefficient b:");
		System.out.println("\n\n");
		for (int i = 0; i < M-1; i++)
		{
			System.out.print(b[i] + ",");
		}
		System.out.print(b[M-1] + "\n");
		
		return b;
	}
	
	
}
