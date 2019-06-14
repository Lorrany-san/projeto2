package projetocrisloh;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import projetocrisloh.IFCadastro;
import projetocrisloh.ModeloGrade;
import projetocrisloh.Produto;

public class IFProduto extends IFCadastro {

    private JTextField tfPreco;

    public IFProduto() {
        super("Cadastro de produtos", 300, 2);
        tfPreco = new JTextField();
        pnCampos.add(tfPreco);
    }

    @Override
    protected void atualizarGrade() {
        try {
            ResultSet rs = new ProdutoDAO().carregarGrade();
            tbDados.setModel(new ModeloGrade(rs,
                    new String[]{"Codigo", "Descricao", "Preco", "idCategoria"}));
            tbDados.getColumnModel().getColumn(0).setMaxWidth(50);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void incluir() {
        Produto p = new Produto();
        p.setDescricao(tfDesc.getText());

        p.setPreco(tfPreco.getText());
        try {
            new ProdutoDAO().incluir(p);
            atualizarGrade();
            tpAbas.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void alterar() {
        Produto p = new Produto();
        p.setCodigo(tfCodigo.getText());
        p.setDescricao(tfDesc.getText());
        p.setPreco(tfPreco.getText());

        try {
            new ProdutoDAO().alterar(p);
            atualizarGrade();
            tpAbas.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void excluir() {
        try {
            int codigo = Integer.parseInt(tfCodigo.getText());
            new ProdutoDAO().excluir(codigo);
            ModeloGrade dtm = (ModeloGrade) tbDados.getModel();
            dtm.removeRow(tbDados.getSelectedRow());
            tpAbas.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void carregarRegistro(String codigo) throws Exception {
        Produto p = new ProdutoDAO().pesquisar(codigo);
        tfCodigo.setText(String.valueOf(p.getCodigo()));
        tfDesc.setText(p.getDescricao());
        tfPreco.setText(p.getPrecoFmt());
    }

}
