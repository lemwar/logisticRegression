package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import utils.Claster;
import utils.Utils;
import methods.Tyrsin;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main
{

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		int N = 20;
		double error = 0.01;
		double[][] initialX;
		//int numberOfIteration = 0;
		int M = 3;
		
		int X1_center = 1;
		int Y1_center = 3;
		int X2_center = 4;
		int Y2_center = 5;
	
		double[] b_real = {0.97332852678457522898687248128225, -0.16222142113076253816447874688037, -0.16222142113076253816447874688037};
		
		double[] b_general = new double [M];
		
		int N_exper = 100;
		
		File excel_file = new File ("data " + "(" + X1_center + "," + Y1_center + ") (" + X2_center + ", " + Y2_center +")" + ".xlsx");
		XSSFWorkbook excel = new XSSFWorkbook();
		Sheet sheet = excel.createSheet("(" + X1_center + "," + Y1_center + ") (" + X2_center + ", " + Y2_center +")");
		Row row;
		
		
		for(int k = 0; k < N_exper; k++)
		{
			Claster X1 = new Claster(Utils.getNormalSelection(N/2, X1_center, Y1_center), 1, M);
			Claster X2 = new Claster(Utils.getNormalSelection(N/2, X2_center, Y2_center), -1, M);
			initialX = new double [X1.getX().length + X2.getX().length][M];
			for(int i = 0; i < X1.getX().length; i++)
			{
				initialX[i] = X1.getXByIndex(i);
			}
			for(int i = 0; i < X2.getX().length; i++)
			{
				initialX[X1.getClasterSize() + i] = X2.getXByIndex(i);
			}
			
			double [][] temp_X = new double[initialX.length][M];
			// Sort array
			for (int i = 0; i < initialX.length; i++)
			{
				temp_X[i][1] = Math.pow(2, 64);
				for (int j = 0; j < initialX.length; j++)
				{
					if ((temp_X[i][1] > initialX[j][1] && i == 0) || (temp_X[i][1] > initialX[j][1] && i>0 && temp_X[i-1][1] < initialX[j][1]))
					{
						temp_X[i] = initialX[j];
					}
				}
			}
			
			// Print elements
			for (int i = 0; i < initialX.length; i++)
			{
				for (int j = 0; j < M-1; j++)
				{
					String sNumb = String.format("%.2f", initialX[i][j] );
					sNumb = sNumb.replace(',', '.');
					System.out.print(sNumb + ",");
				} 
				String sNumb = String.format("%.2f", initialX[i][M-1] );
				sNumb = sNumb.replace(',', '.');
				System.out.print(sNumb);
				System.out.println("");
			}

			for (int i = 0; i < N; i++)
			{
				if(k == 0)
					row = sheet.createRow(i);
				else
					row = sheet.getRow(i);
				
				for(int j = 0; j < M; j++)
				{
					Cell cell = row.createCell(k*4 + 1 + j);
					cell.setCellValue(temp_X[i][j]);
				}
			}

			double [] b = Tyrsin.startMethod(X1, X2, error);
			
			for(int i = 0; i < M; i++)
			{
				b_general[i] += b[i] / N_exper;
			}
			
			//find the length of b
			double l_b;
			double sum_sq = 0;
			for(int i = 0; i < M; i++)
			{
				sum_sq += Math.pow(b[i],2);
			}
			l_b = Math.sqrt(sum_sq);
			
			//find absolute error
			double sum_b_error_pos = 0;
			double sum_b_error_neg = 0;
			double sum_b_error;
			
			for(int i = 0; i < M; i++)
			{
				sum_b_error_pos += Math.pow((b_real[i] - b[i]/l_b),2);
				sum_b_error_neg += Math.pow((b_real[i] + b[i]/l_b),2);
				
			}
			if (sum_b_error_neg < sum_b_error_pos)
			{
				sum_b_error = sum_b_error_neg / M;
				for (int i = 0; i < M; i++)
				{
					b[i] = -b[i];
				}
			}
			else
				sum_b_error = sum_b_error_pos / M;
			
			//find theory error
			double sum_theory_error = 0;
			for(int i = 0; i < M; i++)
			{
				sum_theory_error += Math.abs(100 - (b[i]/l_b*100/b_real[i]));
			}
			sum_theory_error = sum_theory_error / M;
			
			// Print b
			if (k == 0)
			{
				row = sheet.createRow(N + 3);
				Cell cell = row.createCell(0);
				cell.setCellValue("Vector 'b' by Tyrsin");
			}
			row = sheet.getRow(N + 3);
			for(int i = 0; i < M; i++)
			{
				Cell cell = row.createCell(k*4 + 1 + i);
				cell.setCellValue(b[i]);
			}
			//Print length b
			Cell cell = row.createCell(k*4 + 1 + M);
			cell.setCellValue(l_b);
			//Print absolute error
			if(k == 0)
				row = sheet.createRow(N + 5);
			else
				row = sheet.getRow(N + 5);
			cell = row.createCell(k*4 + 1);
			cell.setCellValue(sum_b_error);
			
			//Print theory error
			if(k == 0)
				row = sheet.createRow(N + 6);
			else
				row = sheet.getRow(N + 6);
			cell = row.createCell(k*4 + 1);
			cell.setCellValue(sum_theory_error);
			
			
			//NewVoron.startMethod(initialX.length, error, initialX, numberOfIteration);
			
			//Voron.startMethod(initialX.length, error, initialX, numberOfIteration);
			
		}
		row = sheet.createRow(N + 4);
		Cell cell;
		for(int i = 0; i < M; i++)
		{
			cell = row.createCell(i + 1);
			cell.setCellValue(b_general[i]);
		}
				
				
		excel.write(new FileOutputStream(excel_file));
		excel.close();
	}

}
