package projetocrisloh;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloGrade extends AbstractTableModel{
    private List<String> colunas;
    private List<List<Object>> linhas;
    
    public ModeloGrade(){
        colunas = new ArrayList<String>();
        linhas = new ArrayList<List<Object>>();
    }
    
    public ModeloGrade(String[] titulos){
        colunas = new ArrayList<String>();
        for(int i = 0; i < titulos.length; i++)
            colunas.add(titulos[i]);
        linhas = new ArrayList<List<Object>>();
    }
    
    public ModeloGrade(ResultSet rs, String[] titulos) throws SQLException{
        this();
        
        try{
            ResultSetMetaData rsmd = rs.getMetaData();
            
            if(titulos != null)
                for (int i = 0; i < titulos.length; i++)
                    colunas.add(titulos[i]);
            else
                for (int i = 1; i <= rsmd.getColumnCount(); i++)
                    colunas.add(rsmd.getColumnLabel(i));
            
            while(rs.next()){
                ArrayList<Object> linha = new ArrayList<Object>();
                for(int i = 1; i <= rsmd.getColumnCount(); i++)
                    linha.add(rs.getObject(i));
                linhas.add(linha);
            }
        }catch(SQLException e){
            
        }
    }
    
    public List<List<Object>> getLinhas(){
        return linhas;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        return linhas.get(linha).get(coluna);
    }
    
    @Override
    public String getColumnName(int indice){
     return colunas.get(indice);
    }
    
    @Override
    public boolean isCellEditable(int linha, int coluna){
       return false; 
    }
    
    @Override
    public Class getColumnClass(int coluna){
        return getValueAt(0, coluna).getClass();
    }
    
    public void removeRow(int linha){
        linhas.remove(linha);
    }
    
    public void insertRow(List<Object> linha){
        linhas.add(linha);
    }
    
    public void clearLines(){
        linhas.clear();
    }
}
