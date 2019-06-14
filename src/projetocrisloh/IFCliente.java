package projetocrisloh;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import projetocrisloh.Cliente;
import projetocrisloh.ClienteDAO;
import projetocrisloh.IFCadastro;
import projetocrisloh.ModeloGrade;

public class IFCliente extends IFCadastro {

    private JTextField tfNome, tfCpf, tfDataDeNascimento;

    public IFCliente() {
        super("Cadastro de Clientes", 300, 2);
        tfNome = new JTextField();
        tfCpf = new JTextField();
        tfDataDeNascimento = new JTextField();

        pnCampos.add(tfNome);
        pnCampos.add(tfCpf);
        pnCampos.add(tfDataDeNascimento);
    }

    @Override
    protected void atualizarGrade() {
        try {
            ResultSet rs = new ClienteDAO().carregarGrade();
            tbDados.setModel(new ModeloGrade(rs,
                    new String[]{"Codigo", "Nome", "CPF", "Data de nascimento"}));
            tbDados.getColumnModel().getColumn(0).setMaxWidth(50);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void incluir() {
        Cliente c = new Cliente();
        c.setNome(tfNome.getText());
        c.setCpf(tfCpf.getText());
        c.setNascimento(tfDataDeNascimento.getText());
        try {
            new ClienteDAO().incluir(c);
            atualizarGrade();
            tpAbas.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void alterar() {
        Cliente c = new Cliente();
        c.setCodigo(Integer.parseInt(tfCodigo.getText()));
        c.setNome(tfNome.getText());
        c.setCpf(tfCpf.getText());
        c.setNascimento(tfDataDeNascimento.getText());

        try {
            new ClienteDAO().alterar(c);
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
            new ClienteDAO().excluir(codigo);
            ModeloGrade dtm = (ModeloGrade) tbDados.getModel();
            dtm.removeRow(tbDados.getSelectedRow());
            tpAbas.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void carregarRegistro(String codigo) throws Exception {
        Cliente c = new ClienteDAO().pesquisar(codigo);
        tfCodigo.setText(String.valueOf(c.getCodigo()));
        tfNome.setText(c.getNome());
        tfCpf.setText(c.getCpf());
        tfDataDeNascimento.setText(c.getNascimento().toString());
    }

}
