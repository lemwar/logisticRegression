package methods;

import java.util.Random;

import utils.Claster;
import utils.Utils;

public class Tyrsin
{
	static int M;
	static double [] x0;
	static double [] x1;
	static double [] x2;
	
	static double [] d;
	static double [] s;
	
	static double [] b0;
	static double [] b;
	
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
			double arg = (arg2[0][0]*X1.getY());
			
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
			double arg = (arg2[0][0]*X2.getY());
			
			Q += Math.log(1+Math.exp(arg));
			//System.out.println(Math.log(1+Math.exp(arg)));
		}
		
		return Q;
	}
	
	static double[] getVectorB0()
	{
		double[] b0 = new double [M];
		double sumSquareS = 0;
		for (int i = 0; i < M; i++)
		{
			sumSquareS += Math.pow(s[i], 2);
		}
		double lengthS = Math.sqrt(sumSquareS);
		
		for (int i = 0; i < M; i++)
		{
			b0[i] = s[i]/lengthS;
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
	
	static double[] getVectorS()
	{
		double [] s = new double [M];
		
		for (int i = 0; i < M; i++)
		{
			s[0] += d[i] * x0[i];
		}
		
		for (int i = 1; i < M; i++)
		{
			s[i] = d[i];
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

	public static void startMethod(Claster X1, Claster X2, double error) 
	{
		M = X1.getNumberOfIndications();
		
		x0 = findMiddlePoint(X1, X2);
		x1 = X1.getMiddleOfClaster();
		x2 = X2.getMiddleOfClaster();
		
		//find vector d
		d = getVectorD();
		
		//find vector S
		s = getVectorS();
		
		//Find b0 - unit vector S
		b0 = getVectorB0();
		System.out.println("\nCoefficient b0:");
		for (int i = 0; i < M; i++)
		{
			System.out.println(b0[i]);
		}
		
		//Find vector b
		double [] p = new double [M];
		double [] bLast = b0;
		b = b0;
		double Q = findQ(X1, X2, b);
		System.out.println("\nQ = " + Q);
		for(int k = 0; k < 1 /*|| (Math.abs(findQ(X1, X2, b) - findQ(X1, X2, bLast)) > error && k > 0)*/; k++)
		{
			for(int l = 0; l < 10; l++)
			{
				Q = findQ(X1, X2, b);
				double[] bNew = new double [M];
				Random rnd = new Random();
				for (int i = 0; i < M; i++)
				{
					bNew[i] = b[i] + rnd.nextGaussian();
				}
				if(Q > findQ(X1, X2, bNew))
				{
					b = bNew;

					Q = findQ(X1, X2, b);
					System.out.println("\nQ = " + Q);
				}

			}
		}

		System.out.println("\nCoefficient b:");
		for (int i = 0; i < M; i++)
		{
			System.out.println(b[i]);
		}
	}
}
