package br.edu.up.model;

public class ex_crud {
  
  
// 1. Salvar (Create)
boolean inseriu = cmd.execute("INSERT INTO onibus (id,nome,horario) VALUES ('1', 'Fazendinha','8.10')");
// 2. Buscar (Retreive)
ResultSet resultado = cmd.executeQuery("SELECT id,nome,horario FROM onibus");
// 3. Atualizar (Update)
int qtde = cmd.executeUpdate("UPDATE onibus SET nome = 'Campo Comprido' WHERE id = 1");
// 4. Excluir (Delete)
boolean excluiu = cmd.execute ("DELETE FROM onibus WHERE id='1'");
  
}
