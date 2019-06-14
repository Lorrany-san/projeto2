package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projetocrisloh.ConexaoComercio;

public class JFCategoriaIncluir  extends JFrame implements ActionListener {

    private JPanel pnSul;
    private JTextField tfDescricao;
    private JButton btGravar;
    
    public JFCategoriaIncluir(){
        setTitle("Incluir Categoria");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        pnSul = new JPanel();
        tfDescricao = new JTextField();
        btGravar = new JButton("Gravar");
        btGravar.setMnemonic('G');
        pnSul.add(btGravar);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tfDescricao, BorderLayout.CENTER);
        getContentPane().add(pnSul, BorderLayout.SOUTH);
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dm.width - getWidth()) / 2, (int) ((dm.getHeight() - getHeight()) / 2));
        
        btGravar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ConexaoComercio cc = new ConexaoComercio();
            PreparedStatement pst = cc.getConexao().prepareStatement(
            "INSERT INTO CATEGORIA (DESCRICAO) VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, tfDescricao.getText().toString());
            pst.executeUpdate();
            cc.confirmarTransacao();
            
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            
            JOptionPane.showMessageDialog(this, "Registro Gravado: " + rs.getInt(1)
            + " - " + tfDescricao.getText());
            rs.close();
            pst.close();
            
            tfDescricao.setText("");
            tfDescricao.requestFocus();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   /* public static void main(String[] args){
        new ConexaoComercio().getConexao();
        new JFCategoriaIncluir().setVisible(true);
    }*/
}
