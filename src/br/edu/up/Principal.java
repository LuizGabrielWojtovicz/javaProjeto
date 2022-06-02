package br.edu.up;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.up.model.Horario;
import br.edu.up.model.Onibus;

public class Principal 
{
	public static Connection con;
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception 
	{
		String URL = "jdbc:sqlite:.\\banco.db";
		
		int chave;
		String nomeOnibus;
		String onibusHorario = null;
		
		String ff;
		int quebra;
		
		try
		{
			con = DriverManager.getConnection(URL);
			Statement stmt = con.createStatement();	
			Scanner leitor = new Scanner(System.in);
			do {
				
			Table.table("1. Cadastrar onibus");
			Table.table("2. Ver horário dos onibus");
			Table.table("3. Editar onibus");
			Table.table("4. Deletar Onibus");
			Table.table("5. Sair.");
			
				chave = leitor.nextInt();
				
				if(chave == 5) break;
				
				if(chave == 1)
				{
					leitor.nextLine();
					System.out.println("Qual o nome do onibus:");
					nomeOnibus = leitor.nextLine();
					
					List<Onibus> listaOnibus = listarOnibus();
					boolean jaExiste = false;
					
					if (getOnibus(nomeOnibus) != null) {
						System.out.println("Já existe um onibus com este nome!");
						break;
					}
					
					List<Horario> horarios = new ArrayList();
					
					Onibus onibusInserido = inserirOnibus(nomeOnibus);
					
					int count = 0;
					while (true)
					{
						System.out.format("-Digite '*' para finalizar a adição de horários\n\nDigite o %d° horário", ++count);
						onibusHorario = leitor.nextLine();
						
						if(onibusHorario.charAt(0) == '*') break;
						
						int horario = converterHorarioEmMinutos(onibusHorario);
						
						inserirHorario(horario, onibusInserido);
					}
					
				}
				else if(chave == 2)
				{
					List<Onibus> onibus = listarOnibus();
					
					for (Onibus oni: onibus) {
						Table.table(oni.nome);
						
						List<Horario> horarios = listarHorarios(oni);
						
						
						for (Horario hor : horarios) {
							System.out.println(converterMinutosEmHoras(hor.horario));
						}
					}
				} else if (chave == 3) {
					leitor.nextLine();
					
						System.out.println("Digite o nome do onibus para editar");
						String onibusNome = leitor.nextLine();
						
						Onibus onibus = getOnibus(onibusNome);
						if (onibus == null) {
							System.out.println("Este onibus não existe!");
							break;
						}
						
						System.out.println("Insira o novo nome:");
						
						String novoNome = leitor.nextLine();
						if (onibusNome == novoNome) {
							System.out.println("Insira um nome diferente");
							break;	
						}
						
						if (getOnibus(novoNome) != null) {
							System.out.println("Ja existe um onibus com esse nome");
							break;	
						}
						
						updateOnibus(onibus, novoNome);
						System.out.println("Onibus editado!");
					}  else if (chave == 4) {
					leitor.nextLine();
					
						System.out.println("Digite o nome do onibus para remover");
						String onibusNome = leitor.nextLine();
						
						Onibus onibus = getOnibus(onibusNome);
						if (onibus == null) {
							System.out.println("Este onibus não existe!");
							break;
						}
						
						
						
						deleteOnibus(onibus);
						System.out.println("Onibus deletado!");
					}
				
			}while(chave != 5);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	static List<Onibus> listarOnibus() throws Exception {
		 String querySelect = "SELECT * FROM onibus;";
		 Statement stmt = con.createStatement();
		 ResultSet resultado = stmt.executeQuery(querySelect);
		 
		 List<Onibus> onibus = new ArrayList();
		 while (resultado.next()) {
			Onibus oni = new Onibus();
			oni.id = resultado.getInt(1);
			oni.nome = resultado.getString(2);
			onibus.add(oni);
		}
		 
		 return onibus;
	 }
	
	static Onibus getOnibus(String nome) throws Exception {
		List<Onibus> onibus = listarOnibus();
		for (Onibus oni: onibus) {
			if (oni.nome.equals(nome)) {
				return oni;
			}
		}
		return null;
	}
	
	static List<Horario> listarHorarios(Onibus onibus) throws Exception {
		 String querySelect = "SELECT * FROM Horarios WHERE onibus_id = '" + onibus.id + "';";
		 Statement stmt = con.createStatement();
		 ResultSet resultado = stmt.executeQuery(querySelect);
		 
		 List<Horario> horarios = new ArrayList();
		 while (resultado.next()) {
			Horario hor = new Horario();
			hor.id = resultado.getInt(1);
			hor.horario = resultado.getInt(2);
			hor.onibus_id = resultado.getInt(3);
			horarios.add(hor);
		}
		 
		 return horarios;
	 }
	
	static Onibus inserirOnibus(String nome) throws Exception {
		String queryAdd = "INSERT INTO onibus (nome) VALUES (\"" + nome + "\");";
		Statement stmtAdd = con.createStatement();
		stmtAdd.execute(queryAdd);
		return getOnibus(nome);
	}
	
	static void inserirHorario(int horario, Onibus onibus) throws Exception {
		String queryAdd = "INSERT INTO Horarios (horario, onibus_id) VALUES ('" + horario + "', '" + onibus.id + "');";
		Statement stmtAdd = con.createStatement();
		stmtAdd.execute(queryAdd);
	}
	static String converterMinutosEmHoras(int minutos) {
		int horas = minutos / 60;
		int min = minutos - (horas * 60);
		if (min < 10) {
			return horas + ":0" + min;
		} else {
			return horas + ":" + min;
		}	
	}
	
	static int converterHorarioEmMinutos(String horario) {
		String[] split = horario.split(":");
		int horas = Integer.parseInt(split[0]);
		int minutos = Integer.parseInt(split[1]);
		return (horas * 60) + minutos;
	}
	
	static void updateOnibus (Onibus onibus, String novoNome) throws SQLException {
		String queryUpdate = "UPDATE onibus SET nome = '" + novoNome + "' WHERE id = '" + onibus.id + "';";
		con.createStatement().execute(queryUpdate);
	}
	
	static void deleteOnibus (Onibus onibus) throws SQLException {
		String queryDelete = "DELETE FROM onibus WHERE id = '" + onibus.id + "';";
		con.createStatement().execute(queryDelete);
	}
}