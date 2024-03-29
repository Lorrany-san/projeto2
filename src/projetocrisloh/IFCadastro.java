package projetocrisloh;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import projetocrisloh.IFCadastro;
import projetocrisloh.ModeloGrade;

public abstract class IFCadastro extends JInternalFrame
        implements ChangeListener, ActionListener {

    protected JTabbedPane tpAbas;
    protected JTable tbDados;
    protected JPanel pnManutencao;
    protected JPanel pnRotulos;
    protected JPanel pnCampos;
    protected JPanel pnBotoes;
    protected JFormattedTextField tfCodigo;
    protected JTextField tfDesc;
    protected JButton btIncluir, btAlterar, btExcluir;

    public IFCadastro(String titulo, int largura, int campos) {
        setTitle(titulo);
        setSize(largura, 100 + 30 * campos);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tbDados = new JTable(new ModeloGrade());
        tbDados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbDados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tbDados.setFocusable(false);
        tbDados.setToolTipText("Clique para selecionar");

        tfCodigo = new JFormattedTextField(new Integer(0));
        tfCodigo.setEnabled(false);
        tfDesc = new JTextField();

        btIncluir = new JButton("Incluir");
        btAlterar = new JButton("Alterar");
        btExcluir = new JButton("Excluir");

        btIncluir.setMnemonic('I');
        btAlterar.setMnemonic('A');
        btAlterar.setMnemonic('E');

        pnManutencao = new JPanel();
        pnRotulos = new JPanel();
        pnCampos = new JPanel();
        pnBotoes = new JPanel();

        pnManutencao.setLayout(new BorderLayout());
        pnRotulos.setLayout(new GridLayout(campos, 1));
        pnCampos.setLayout(new GridLayout(campos, 1));
        pnRotulos.add(new JLabel(" Codigo: "));
        pnRotulos.add(new JLabel(" Descrição: "));
        pnCampos.add(tfCodigo);
        pnCampos.add(tfDesc);
        pnBotoes.add(btIncluir);
        pnBotoes.add(btAlterar);
        pnBotoes.add(btExcluir);
        pnManutencao.add(pnRotulos, BorderLayout.WEST);
        pnManutencao.add(pnCampos, BorderLayout.CENTER);
        pnManutencao.add(pnBotoes, BorderLayout.SOUTH);

        JScrollPane spDados = new JScrollPane(tbDados);
        spDados.setFocusable(false);

        tpAbas = new JTabbedPane();
        tpAbas.add("Seleção", spDados);
        tpAbas.add("Manutenção", pnManutencao);
        tpAbas.setMnemonicAt(0, 'S');
        tpAbas.setMnemonicAt(1, 'M');

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tpAbas, BorderLayout.CENTER);

        tpAbas.addChangeListener(this);
        tbDados.addMouseListener(new MouseHandler());
        btIncluir.addActionListener(this);
        btAlterar.addActionListener(this);
        btExcluir.addActionListener(this);
        atualizarGrade();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (tpAbas.getSelectedIndex() != 0) {
            return;
        }
        for (int i = 0; i < pnCampos.getComponentCount(); i++) {
            if (pnCampos.getComponent(i) instanceof JTextComponent) {
                ((JTextComponent) pnCampos.getComponent(i)).setText("");
            }
            if (pnCampos.getComponent(i) instanceof JComboBox) {
                ((JComboBox) pnCampos.getComponent(i)).setSelectedIndex(0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btIncluir) {
            incluir();
        } else if (e.getSource() == btAlterar) {
            alterar();
        } else if (e.getSource() == btExcluir) {
            excluir();
        }
    }

    protected abstract void atualizarGrade();

    protected abstract void incluir();

    protected abstract void alterar();

    protected abstract void excluir();

    protected abstract void carregarRegistro(String codigo) throws Exception;

    class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() != MouseEvent.BUTTON1) {
                return;
            }
            JTable tb = (JTable) e.getSource();
            if (tb.getSelectionModel().isSelectionEmpty()) {
                return;
            }
            int lin = tb.getSelectionModel().getMinSelectionIndex();
            String codigo = tb.getModel().getValueAt(lin, 0).toString();

            try {
                carregarRegistro(codigo);
                tpAbas.setSelectedComponent(pnManutencao);
                tfDesc.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(IFCadastro.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
