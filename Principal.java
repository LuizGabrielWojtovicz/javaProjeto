package br.com.up.javaProjeto;

import java.sql.*;
import java.util.*;

public class Principal 
{
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		String URL = "jdbc:sqlite:C:\\Users\\Aluno\\Desktop\\dataBase\\banco_Projeto.db";
		
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
					
					String queryAdd = "ALTER TABLE horarios\r\n"
							+ "ADD '" + nomeOnibus + "' TEXT;";
					
					Statement stmtAdd = con.createStatement();
					stmtAdd.execute(queryAdd);
					
					int cont = 1;
					do 
					{
						System.out.format("-Digite '*' para finalizar a adição de horários\n\nDigite o %d° horário", cont++);
						onibusHorario = leia.nextLine();
						
						if(onibusHorario.charAt(0) == '*') break;
						
						String queryInsert = "INSERT INTO horarios ('" + nomeOnibus + "') \r\n"
								+ "VALUES ('" + onibusHorario + "');";
						
						Statement stmtInsert = con.createStatement();
						stmtInsert.execute(queryInsert);
						
					}while(onibusHorario != "x");
					cont = 0;
					
				}
				else if(chave == 2)
				{
					String querySelect = "SELECT * FROM horarios;";
					
					ResultSet resultado = stmt.executeQuery(querySelect);
					
					int contX = 1;
					while(resultado.next())
					{
						ff = resultado.getString(contX++);
						System.out.format( ff + "\n");
						
						
					}
				}
			}while(chave != 3);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
