package projetocrisloh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoComercio {
    
    private Connection conexao;
    
    public ConexaoComercio(){
        try{
            this.conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cristiele", "postgres", "12345678");
            this.conexao.setAutoCommit(false);
        }catch(Exception ex){
            System.out.println("ERRO: " + ex.toString());
        }
    }
    
    public Connection getConexao(){
        return this.conexao;
    }
    
    public void fechar() throws SQLException{
        this.conexao.close();
    }
    
    public void confirmarTransacao() throws SQLException{
        conexao.commit();
    }
    
    public void cancelarTransacao() throws SQLException{
        conexao.rollback();
    }
}
