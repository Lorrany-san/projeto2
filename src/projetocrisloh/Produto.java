package projetocrisloh;

import java.text.DecimalFormat;

import projetocrisloh.Produto;

public class Produto {
    private int codigo;
    private String descricao;
    private double preco;
    private int idCategoria;
    
    public Produto(){
        this.codigo = 0;
        this.descricao = "";
        this.preco = 0.0;
        this.idCategoria = 0;
    }
    
    
    public Produto(int codigo, String descricao){
        setCodigo(codigo);
        setDescricao(descricao);
    }
    
    public Produto(int codigo, String descricao, double preco, int idCateg){
        setCodigo(codigo);
        setDescricao(descricao);
        setPreco(preco);
        setidCategoria(idCateg);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setidCategoria(int idDescricao) {
        this.idCategoria = idDescricao;
    }
    
    public String getPrecoFmt(){
        DecimalFormat df = new DecimalFormat("#.##0.00");
        return df.format(preco);
    }
    
    public void setCodigo(String codigo){
        setCodigo(Integer.parseInt(codigo));
    }
    
    public void setPreco(String preco){
        preco = preco.replaceAll("\\.", "").replace(".", ".");
        this.preco = Double.parseDouble(preco);
    }
    
    public void setIdCtegoria(String idCategoria){
        this.idCategoria = Integer.parseInt(idCategoria);
    }
    
    public String toString(){
        return descricao;
    }
    
    public boolean equals(Object obj){
        if (obj == null || getClass() != obj.getClass()) return false;
        if (this == obj) return true;
        final Produto outro = (Produto) obj;
        if (codigo == outro.codigo) return true;
        else return false;
    }
    
    public int hashCode(){
        return 31 + codigo;
    }
}

