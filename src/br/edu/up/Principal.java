package br.edu.up;

import java.sql.*;
import java.util.Scanner;

public class Principal 
{
	/*
	public static void main(String[] args) throws SQLException 
	{

		String URL = "jdbc:sqlite:C:\\Users\\Aluno\\Desktop\\banco.db";
		
		Connection con = DriverManager.getConnection(URL);
		
		Statement stmtOnibus = con.createStatement();
		
		
		String querySelect = "SELECT * FROM onibus;";
		
		ResultSet resultado = stmtOnibus.executeQuery(querySelect);
		
		int contX = 1;
		while(resultado.next())
		{
			int onibusId = resultado.getInt(1);
			String onibusNome = resultado.getString(2);
			
			System.out.println("onibusId " + onibusId);
			
			String queryHorarios = "SELECT * FROM Horarios where onibus_id = " + onibusId + ";";
			
			Statement stmtOnibus = con.createStatement();
			
			ResultSet resultadoHorarios = stmtOnibus.executeQuery(queryHorarios);
			
			
		while (resultadoHorarios.next()) {
				int horario = resultadoHorarios.getInt(2);
				String dia = resultadoHorarios.getString(4);
				
				System.out.println(horario);
				
			}
			
			Table.table(horarios);
			
			
		}
	}*/
		
	
	
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		String URL = "jdbc:sqlite:C:\\Users\\Aluno\\Desktop\\banco.db";
		
		int chave;
		String nomeOnibus;
		String onibusHorario = null;
		
		String ff;
		int quebra;
		
		try
		(
				Connection con = DriverManager.getConnection(URL);
				
				Statement stmt = con.createStatement();	
				
				Scanner leia = new Scanner(System.in);
				)
		{
			do
			{
				System.out.format("1. Cadastrar onibus;\n2. Ver horário dos onibus;\n3. Sair. ");
				chave = leia.nextInt();
				
				if(chave == 3) break;
				
				if(chave == 1)
				{
					leia.nextLine();
					System.out.println("Qual o nome do onibus:");
					nomeOnibus = leia.nextLine();
					
					String querySelect = "SELECT * FROM onibus WHERE nome = '" + nomeOnibus + "';";
					
					ResultSet resultado = stmt.executeQuery(querySelect);
					
					if (resultado.next()) {
						System.out.println("Já existe um onibus com este nome!");
						break;
					}
					
					String queryAdd = "INSERT INTO onibus (nome) VALUES (\"" + nomeOnibus + "\");";
					
					Statement stmtAdd = con.createStatement();
					stmtAdd.execute(queryAdd);
					
					
					String queryId = "SELECT * FROM onibus WHERE nome = '" + nomeOnibus + "';";
					
					ResultSet resultadoId = stmt.executeQuery(querySelect);
					int onibusId = -1;
					
					while (resultadoId.next()) {
						onibusId = resultadoId.getInt(1);
					}
					
					if (onibusId == -1) {
						System.out.println("deu errado aqui");
						break;
					}
					
					
					int cont = 1;
					do 
					{
						System.out.format("-Digite '*' para finalizar a adição de horários\n\nDigite o %d° horário", cont++);
						onibusHorario = leia.nextLine();
						
						if(onibusHorario.charAt(0) == '*') break;
						
						String queryInsert = "INSERT INTO Horarios (horario, onibus_id, dia) VALUES (" + onibusHorario + ", " + onibusId + ", 'UTIL')";
						
						Statement stmtInsert = con.createStatement();
						stmtInsert.execute(queryInsert);
						
					}while(onibusHorario != "x");
					cont = 0;
					
				}
				else if(chave == 2)
				{
					String querySelect = "SELECT * FROM onibus;";
					
					ResultSet resultado = stmt.executeQuery(querySelect);
					
					int contX = 1;
					while(resultado.next())
					{
						Statement stmtHorarios = con.createStatement();
						int onibusId = resultado.getInt(1);
						String onibusNome = resultado.getString(2);
						
						System.out.println("\nHorários do ônibus " + onibusNome);
						
						String queryHorarios = "SELECT * FROM Horarios where onibus_id = " + onibusId + ";";
						
						ResultSet resultadoHorarios = stmtHorarios.executeQuery(queryHorarios);
						
						String horarios = "";
						
					while (resultadoHorarios.next()) {
							int horario = resultadoHorarios.getInt(2);
							String dia = resultadoHorarios.getString(4);
							
							System.out.println(horario);
							
						}
						
						/*Table.table(horarios);
						*/
						
					}
				}
			}while(chave != 3);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}