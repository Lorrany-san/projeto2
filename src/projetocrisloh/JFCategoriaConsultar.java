package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import projetocrisloh.ConexaoComercio;

public class JFCategoriaConsultar extends JFrame 
        implements ActionListener{

    private JPanel pnSul;
    private JTable tbGrade;
    private JButton btConsultar;
    
    public JFCategoriaConsultar() {
        setTitle("Consultar Categoria");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pnSul = new JPanel();
        tbGrade = new JTable(new DefaultTableModel());
        btConsultar = new JButton("Consultar");
        btConsultar.setMnemonic('C');
        pnSul.add(btConsultar);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(tbGrade), BorderLayout.CENTER);
        getContentPane().add(pnSul, BorderLayout.SOUTH);
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dm.width - getWidth()) / 2, (int) ((dm.getHeight() - getHeight()) / 2));
        
        btConsultar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            ConexaoComercio c = new ConexaoComercio();
            Statement stm = c.getConexao().createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT * FROM CATEGORIA ORDER BY DESCRICAO");
            
            Vector<String> colunas = new Vector<>();
            colunas.add("Código");
            colunas.add("Descrição");
            
            Vector<Object> registros = new Vector<Object>();
            while(rs.next()){
                Vector<Object> linha = new Vector<Object>();
                linha.add(rs.getInt(1));
                linha.add(rs.getString(2));
                registros.add(linha);
            }
            rs.close();
            stm.close();
            
            DefaultTableModel dtm = (DefaultTableModel) tbGrade.getModel();
            dtm.setDataVector(registros, colunas);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   /* public static void main(String[] args){
        new JFCategoriaConsultar().setVisible(true);
    }*/
}

