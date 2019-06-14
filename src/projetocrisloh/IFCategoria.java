package projetocrisloh;

import java.sql.ResultSet;
import javax.swing.JOptionPane;

import projetocrisloh.Categoria;
import projetocrisloh.CategoriaDAO;
import projetocrisloh.IFCadastro;
import projetocrisloh.ModeloGrade;

public class IFCategoria extends IFCadastro{
    
    public IFCategoria(){
        super("Cadastro de categorias", 300, 2);
    }

    @Override
    protected void atualizarGrade() {
        try{
            ResultSet rs = new CategoriaDAO().carregarGrade();
            tbDados.setModel(new ModeloGrade(rs,
            new String[]{"Codigo", "Descricao"}));
            tbDados.getColumnModel().getColumn(0).setMaxWidth(50);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void incluir() {
        Categoria c = new Categoria();
        c.setDescricao(tfDesc.getText());
        try{
            new CategoriaDAO().incluir(c);
            atualizarGrade();
            tpAbas.setSelectedIndex(0);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void alterar() {
       Categoria c = new Categoria();
       c.setCodigo(tfCodigo.getText());
       c.setDescricao(tfDesc.getText());
       
       try{
           new CategoriaDAO().alterar(c);
           atualizarGrade();
           tpAbas.setSelectedIndex(0);
       }catch(Exception e){
           JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
       }
    }

    @Override
    protected void excluir() {
       try{
           int codigo = Integer.parseInt(tfCodigo.getText());
           new CategoriaDAO().excluir(codigo);
           ModeloGrade dtm = (ModeloGrade)tbDados.getModel();
           dtm.removeRow(tbDados.getSelectedRow());
           tpAbas.setSelectedIndex(0);
       }catch(Exception e){
           JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
       }
    }

    @Override
    protected void carregarRegistro(String codigo) throws Exception {
       Categoria c = new CategoriaDAO().pesquisar(codigo);
       tfCodigo.setText(String.valueOf(c.getCodigo()));
       tfDesc.setText(c.getDescricao());
    }
}

