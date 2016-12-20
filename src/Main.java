

public class Main
{

	public static void main(String[] args)
	{
		int N = 20;
		double error = 0.01;
		double[][] initialX;
		int numberOfIteration = 0;
		
		initialX = new double[][] { { 1, -0.49, 11.244 }, { 1, 1.3, 10.11 }, { 1, 1.67, 10.526 }, { 1, 1.68, 10.424 },
				{ 1, 1.94, 12.576 }, { 1, 2.25, 14.413 }, { 1, 2.4, 12.722 }, { 1, 2.41, 11.23 }, { 1, 3.05, 11.349 },
				{ 1, 3.4, 12.799 }, { 1, 3.5, 7.026 }, { 1, 3.54, 6.068 }, { 1, 4.36, 5.469 }, { 1, 4.92, 7.758 },
				{ 1, 4.94, 6.93 }, { 1, 5.02, 5.808 }, { 1, 5.33, 5.998 }, { 1, 5.38, 7.245 }, { 1, 5.77, 6.008 },
				{ 1, 6.57, 5.642 } };
		
		initialX = Utils.getNormalSelection(N);
		initialX = Utils.getSelection(N);
		
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				String sNumb = String.format("%.2f", initialX[i][j] );
				sNumb.replace('.', '.');
				System.out.print(sNumb + ";");
			} 
			System.out.println(" ");
		}
		
		NewVoron.startMethod(N, error, initialX, numberOfIteration);
		
		Voron.startMethod(N, error, initialX, numberOfIteration);
		

	}

}
