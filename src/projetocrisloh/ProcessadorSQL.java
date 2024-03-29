package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import projetocrisloh.ModeloGrade;

public class ProcessadorSQL extends JFrame 
    implements ListSelectionListener, ActionListener{
    
    private JList liBancos;
    private JTextArea taSQL;
    private JButton btExecutar;
    private JTable tbGrade;
    private JLabel lbStatus;
    private JPanel pnSQL;
    private JPanel pnCentro;
    private Connection conexao;
    
    public ProcessadorSQL() throws SQLException{
        setTitle("Processador de SQL");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        liBancos = new JList(new DefaultListModel());
        liBancos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taSQL = new JTextArea();
        btExecutar = new JButton("Executar");
        btExecutar.setMnemonic('E');
        tbGrade = new JTable(new ModeloGrade());
        
        pnSQL = new JPanel();
        pnSQL.setLayout(new BorderLayout());
        pnSQL.add(new JScrollPane(taSQL,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        pnSQL.add(btExecutar, BorderLayout.EAST);
        pnSQL.setPreferredSize(new Dimension(100, 75));
        
        pnCentro = new JPanel(new BorderLayout());
        pnCentro.add(pnSQL, BorderLayout.NORTH);
        pnCentro.add(new JScrollPane(tbGrade,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),
                BorderLayout.CENTER);
        
        lbStatus = new JLabel("Resultado: ");
        lbStatus.setFont(new Font("Arial", Font.BOLD, 12));
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(liBancos, BorderLayout.WEST);
        getContentPane().add(pnCentro, BorderLayout.CENTER);
        getContentPane().add(lbStatus, BorderLayout.SOUTH);
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dm.width - getWidth()) / 2, (int) ((dm.getHeight() - getHeight()) / 2));
        
        liBancos.addListSelectionListener(this);
        btExecutar.addActionListener(this);
        
        try{
            abrirConexao("");
            
            DatabaseMetaData dbmd = conexao.getMetaData();
            ResultSet rs_bancos = dbmd.getCatalogs();
            
            DefaultListModel dlm = (DefaultListModel) liBancos.getModel();
            
            while(rs_bancos.next()){
                dlm.addElement(rs_bancos.getString("TABLE_CAT"));
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void abrirConexao(String banco){
        try{
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cristiele", "postgres", "12345678");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "ConexÃ£o Falhou!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e){
        String banco = liBancos.getSelectedValue().toString();
        abrirConexao(banco);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        String sql = taSQL.getText();
        
        if (sql.toUpperCase().indexOf("SELECT") == 0){
            consultar(sql);
        }else{
            executar(sql);
        }
    }
    
    public void consultar(String sql){
        try{
            Statement stm = conexao.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            tbGrade.setModel(new ModeloGrade(rs, null));
            int n = tbGrade.getModel().getRowCount();
            lbStatus.setText("Resultado: " + n + " registros recuperados");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void executar(String sql){
        try{
           Statement stm = conexao.createStatement();
           int n = stm.executeUpdate(sql);
           lbStatus.setText("Resultado: " + n + " registros afetados");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*public static void main(String[] args) throws SQLException{
        try{
            UIManager.setLookAndFeel(
            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch(Exception e){}
        
        new ProcessadorSQL().setVisible(true);
    }*/
}

