package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projetocrisloh.ConexaoComercio;

public class JFCategoriaAlterar extends JFrame implements ActionListener{

    private JPanel pnSul;
    private JPanel pnOeste;
    private JPanel pnCentro;
    private JFormattedTextField tfCodigo;
    private JTextField tfDescricao;
    private JButton btAlterar;
    
    public JFCategoriaAlterar(){
        setTitle("Alterar Categoria");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pnSul = new JPanel();
        pnOeste = new JPanel();
        pnCentro = new JPanel();
        tfCodigo = new JFormattedTextField(new Integer(0));
        tfDescricao = new JTextField();
        btAlterar = new JButton("Alterar");
        
        btAlterar.setMnemonic('A');
        
        pnOeste.setLayout(new GridLayout(2, 1));
        pnOeste.add(new JLabel("CÃ³digo: "));
        pnOeste.add(new JLabel("DescriÃ§Ã£o: "));
        pnCentro.setLayout(new GridLayout(2, 1));
        pnCentro.add(tfCodigo);
        pnCentro.add(tfDescricao);
        pnSul.add(btAlterar);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnOeste, BorderLayout.WEST);
        getContentPane().add(pnCentro, BorderLayout.CENTER);
        getContentPane().add(pnSul, BorderLayout.SOUTH);
        
         Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dm.width - getWidth()) / 2, (int) ((dm.getHeight() - getHeight()) / 2));
        
        btAlterar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ConexaoComercio c = new ConexaoComercio();
            PreparedStatement pst = c.getConexao().prepareStatement(
            "UPDATE CATEGORIA SET DESCRICAO = ? WHERE CODIGO = ?");
            pst.setString(1, tfDescricao.getText().toString());
            pst.setInt(2, Integer.parseInt(tfCodigo.getText().toString()));
            pst.executeUpdate();
            c.confirmarTransacao();
            
            if (pst.getUpdateCount() == 0){
                JOptionPane.showMessageDialog(this, "Registro não existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            pst.close();
            
            JOptionPane.showMessageDialog(this, "Registro Alterado");
            tfCodigo.setValue(null);
            tfDescricao.setText("");
            tfCodigo.requestFocus();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*public static void main(String[] args){
            new JFCategoriaAlterar().setVisible(true);
        }*/
}

