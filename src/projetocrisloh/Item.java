package projetocrisloh;

import projetocrisloh.Item;

public class Item {
    private int idPedido;
    private int idProduto;
    private int quantidade;
    private double unitario;
    
    public Item(){
        this.idPedido = 0;
        this.idProduto = 0;
        this.quantidade = 0;
        this.unitario = 0;
    }
    
    public Item(int idPedido, int idProduto, int quantidade, double unitario){
        setIdProduto(idProduto);
        setQuantidade(quantidade);
        setUnitario(unitario);
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getUnitario() {
        return unitario;
    }

    public void setUnitario(double unitario) {
        this.unitario = unitario;
    }
    
    public void setIdPedido(String idPedido){
        this.idPedido = Integer.parseInt(idPedido);
    }
    
    public void setIdProduto(String idProduto) {
        this.idProduto = Integer.parseInt(idProduto);
    }
    
    public void setQuantidade(String quantidade) {
        this.quantidade = Integer.parseInt(quantidade);
    }
    
    public void setUnitario(String unitario) {
        this.unitario = Integer.parseInt(unitario);
    }
    
    public boolean equals(Object obj){
        if (obj == null || getClass() != obj.getClass()) return false;
        if (this == obj) return true;
        final Item outro = (Item) obj;
        if (idProduto == outro.idProduto) return true;
        else return false;
    }
    
    public int hashCode(){
        return 31 + idProduto;
    }
    
}

