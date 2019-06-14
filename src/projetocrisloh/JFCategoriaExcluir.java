package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import projetocrisloh.ConexaoComercio;

public class JFCategoriaExcluir extends JFrame implements ActionListener{
    
    private JPanel pnSul;
    private JFormattedTextField tfCodigo;
    private JButton btExcluir;
            
    public JFCategoriaExcluir(){
        setTitle("Excluir Categoria");
        setSize(250, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pnSul = new JPanel();
        tfCodigo = new JFormattedTextField(new Integer(0));
        btExcluir = new JButton("Excluir");
        pnSul.add(btExcluir);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tfCodigo, BorderLayout.CENTER);
        getContentPane().add(pnSul, BorderLayout.SOUTH);
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dm.width - getWidth()) / 2, (int) ((dm.getHeight() - getHeight()) / 2));
        
        btExcluir.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ConexaoComercio c = new ConexaoComercio();
            PreparedStatement pst = c.getConexao().prepareStatement(
            "DELETE FROM CATEGORIA WHERE CODIGO = ?");
            pst.setInt(1, Integer.parseInt(tfCodigo.getText().toString()));
            pst.executeUpdate();
            c.confirmarTransacao();
            
            if(pst.getUpdateCount() == 0){
                JOptionPane.showMessageDialog(this, "Registro nÃ£o existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            pst.close();
            
            JOptionPane.showMessageDialog(this, "Registro excluÃ­do!");
            tfCodigo.setValue(null);
            tfCodigo.requestFocus();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   /* public static void main(String[] args){
        new JFCategoriaExcluir().setVisible(true);
    }*/
}

