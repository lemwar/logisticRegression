package main;

import java.io.ObjectInputStream.GetField;

import utils.Claster;
import utils.Utils;
import methods.NewVoron;
import methods.Tyrsin;

public class Main
{

	public static void main(String[] args)
	{
		int N = 4;
		double error = 0.01;
		double[][] initialX;
		int numberOfIteration = 0;
		int M = 3;
		
		/*initialX = new double[][] { { 1, -0.49, 11.244 }, { 1, 1.3, 10.11 }, { 1, 1.67, 10.526 }, { 1, 1.68, 10.424 },
				{ 1, 1.94, 12.576 }, { 1, 2.25, 14.413 }, { 1, 2.4, 12.722 }, { 1, 2.41, 11.23 }, { 1, 3.05, 11.349 },
				{ 1, 3.4, 12.799 }, { 1, 3.5, 7.026 }, { 1, 3.54, 6.068 }, { 1, 4.36, 5.469 }, { 1, 4.92, 7.758 },
				{ 1, 4.94, 6.93 }, { 1, 5.02, 5.808 }, { 1, 5.33, 5.998 }, { 1, 5.38, 7.245 }, { 1, 5.77, 6.008 },
				{ 1, 6.57, 5.642 } };
		*/
		
		//initialX = Utils.getNormalSelection(N);
		//initialX = Utils.getSelection(N);
		
		//initialX = new double[][] {{1, 4, 1},{1, 3, 1},{1, 1, 3},{1, 1, 4}};
		
		//Claster X1 = new Claster(new double[][] {{1, 4, 1},{1, 3, 1}}, 1, M);
		//Claster X2 = new Claster(new double[][] {{1, 1, 4},{1, 1, 3}}, -1, M);
		
		Claster X1 = new Claster(Utils.getNormalSelection(N/2, 5, 2), 1, M);
		Claster X2 = new Claster(Utils.getNormalSelection(N/2, 2, 5), -1, M);
		initialX = new double [X1.getX().length + X2.getX().length][M];
		for(int i = 0; i < X1.getX().length; i++)
		{
			initialX[i] = X1.getXByIndex(i);
		}
		for(int i = 0; i < X2.getX().length; i++)
		{
			initialX[X1.getClasterSize() + i] = X2.getXByIndex(i);
		}
		//System.arraycopy(X2.getX(), 0, initialX, initialX.length, X2.getX().length);
		
		for (int i = 0; i < initialX.length; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				String sNumb = String.format("%.2f", initialX[i][j] );
				sNumb = sNumb.replace(',', '.');
				System.out.print(sNumb + ",");
			} 
			System.out.println("");
		}
		
		Tyrsin.startMethod(X1, X2, error);
		
		//NewVoron.startMethod(initialX.length, error, initialX, numberOfIteration);
		
		//Voron.startMethod(initialX.length, error, initialX, numberOfIteration);
		

	}

}
