package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import projetocrisloh.ModeloGrade;

public class DBExplorer extends JFrame implements ListSelectionListener,
        ItemListener, ActionListener{
    
    private JList liBancos;
    private JComboBox coTabelas, coCampos;
    private JButton btConsultar;
    private JTable tbGrade;
    private JPanel pnCombos, pnCentro;
    private Connection conexao;
    
    public DBExplorer() throws SQLException{
        setTitle("DBExplorer");
        setSize(750, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        liBancos = new JList(new DefaultListModel());
        liBancos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        coTabelas = new JComboBox(new DefaultComboBoxModel());
        coCampos = new JComboBox(new DefaultComboBoxModel());
        
        btConsultar = new JButton("Consultar");
        btConsultar.setMnemonic('C');
        
        tbGrade = new JTable(new ModeloGrade());
        
        pnCombos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnCombos.add(coTabelas);
        pnCombos.add(coCampos);
        pnCombos.add(btConsultar);
        
        pnCentro = new JPanel(new BorderLayout());
        pnCentro.add(pnCombos, BorderLayout.NORTH);
        pnCentro.add(new JScrollPane(tbGrade, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(liBancos), 
                BorderLayout.WEST);
        getContentPane().add(pnCentro, BorderLayout.CENTER);
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((dm.width - getWidth()) / 2, (int) ((dm.getHeight() - getHeight()) / 2));
        
        liBancos.addListSelectionListener(this);
        
        coTabelas.addItemListener(this);
        btConsultar.addActionListener(this);
        
        try{
            abrirConexao("");
            
            DatabaseMetaData dbmd = conexao.getMetaData();
            ResultSet bancos = dbmd.getCatalogs();
            
            DefaultListModel dlm = (DefaultListModel) liBancos.getModel();
            
            while(bancos.next()){
                dlm.addElement(bancos.getString("TABLE_CAT"));
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
        try{
            String banco = liBancos.getSelectedValue().toString();
            abrirConexao(banco);
            DatabaseMetaData dbmd = conexao.getMetaData();
            ResultSet rs_tabelas = dbmd.getTables(banco, null, null, null);
            
            DefaultComboBoxModel dcm = (DefaultComboBoxModel) coTabelas.getModel();
            dcm.removeAllElements();
            Statement stm = conexao.createStatement();
            ResultSet rst = stm.executeQuery("SELECT table_name \n" +
"FROM information_schema.tables\n" +
"WHERE table_schema='public'\n" +
"AND table_type='BASE TABLE';");
            while(rs_tabelas.next()){
                rst.next();
                dcm.addElement(rst.getString("TABLE_NAME"));
            }
        }catch(Exception ex){}
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (coTabelas.getSelectedIndex() == -1){
            return;
        }
        
        String banco = liBancos.getSelectedValue().toString();
        String tabela = coTabelas.getSelectedItem().toString();
        
        try{
            DatabaseMetaData dbmd = conexao.getMetaData();
            ResultSet rs_campos = dbmd.getColumns(banco, null, tabela, null);
            DefaultComboBoxModel dcm = (DefaultComboBoxModel) coCampos.getModel();
            dcm.removeAllElements();
            while(rs_campos.next()){
                dcm.addElement(rs_campos.getString("COLUMN_NAME"));
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Statement stm = conexao.createStatement();
            ResultSet rs = stm.executeQuery(
            "SELECT * FROM " +
                    coTabelas.getSelectedItem() + " ORDER BY " +
                    coCampos.getSelectedItem());
            tbGrade.setModel(new ModeloGrade(rs, null));
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*public static void main(String[] args) throws SQLException {
        try{
            UIManager.setLookAndFeel(
            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
        }catch(Exception ex){}
        
        new DBExplorer().setVisible(true);
    }*/
}

