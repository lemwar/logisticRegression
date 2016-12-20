public class Voron
{

	public static void startMethod(int N, double error, double[][] initialX, int numberOfIteration)
	{
		double[][] X = new double[N][3];
		double[][] XT = new double[3][N];
		double[][] Y = new double[N][1];
		double[][] initialY = new double[N][1];
		double[][] b = new double[1][3];
		double[][] z = new double[N][1];
		double[][] delta = new double[N][1];
		double[][] diagW = new double[N][N];
		double sumDelta = 0;
		double sumDelta2 = 0;
		
		for (int i = 0; i < N / 2; i++)
		{
			initialY[i][0] = -1;
			initialY[i + N / 2][0] = 1;
			Y[i][0] = -1;
			Y[i + N / 2][0] = 1;
		}

		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				X[i][j] = initialX[i][j];
			}
		}

		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				X[i][j] = initialX[i][j];
			}
		}

		for (int i = 0; i < N; i++)
		{
			XT[0][i] = X[i][0];
			XT[1][i] = X[i][1];
			XT[2][i] = X[i][2];
			// System.out.printf("%.2f %.3f %s", XT[1][i], XT[2][i], "\n");
		}

		b = Utils.matrixMultiply(Utils.matrixInverse(Utils.matrixMultiply(XT, X)), Utils.matrixMultiply(XT, Y));
		//Print b0
		System.out.println("Coefficients b0 from Voronzov");
		for (int i = 0; i < 3; i++)
		{
			String sNumb = String.format("%.2f", b[i][0]);
			sNumb.replace('.', '.');
			System.out.println(sNumb);
		}
		System.out.println("");
		
		z = Utils.matrixMultiply(initialX, b);

		sumDelta = 0;
		for (int i = 0; i < N; i++)
		{
			delta[i][0] = 1 / (1 + Math.exp(z[i][0]));
			sumDelta += delta[i][0];
		}

		int k = 0;
		do
		{
			if (k != 0)
				sumDelta = sumDelta2;

			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					if (i == j)
						diagW[i][j] = initialY[i][0] * Math.sqrt((1 - delta[i][0]) * delta[i][0]);
					else
						diagW[i][j] = 0;

			// Находим новые X, ХТ и Y
			X = Utils.matrixMultiply(diagW, initialX);
			for (int i = 0; i < N; i++)
			{
				XT[0][i] = X[i][0];
				XT[1][i] = X[i][1];
				XT[2][i] = X[i][2];
			}
			for (int i = 0; i < N; i++)
			{
				Y[i][0] = Math.sqrt((1 - delta[i][0]) / delta[i][0]);
			}

			// Находим новые b
			b = Utils.matrixMultiply(Utils.matrixInverse(Utils.matrixMultiply(XT, X)), Utils.matrixMultiply(XT, Y));
			z = Utils.matrixMultiply(initialX, b);

			sumDelta2 = 0;
			for (int i = 0; i < N; i++)
			{
				delta[i][0] = 1 / (1 + Math.exp(z[i][0]));
				sumDelta2 += delta[i][0];
			}
			k++;
		}

		while (Math.sqrt(Math.pow((sumDelta2 - sumDelta), 2)) > error && k < numberOfIteration);

		// Печать некоторых результатов

		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < 3; j++)
				//System.out.print(initialX[i][j] + " ");
			System.out.print("");
		}

		System.out.println("Coefficients b  of Voronzov method");
		for (int i = 0; i < 3; i++)
		{
			String sNumb = String.format("%.2f", b[i][0]);
			sNumb.replace('.', '.');
			System.out.println(sNumb);
		}
		System.out.println("");
	}

}
