package projetocrisloh;

public class Categoria {
     private int codigo;
    private String descricao;
    
    public Categoria(){
        this.codigo = 0;
        this.descricao = "";
    }
    
    public Categoria(int codigo, String descricao){
        setCodigo(codigo);
        setDescricao(descricao);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void setCodigo(String codigo){
        setCodigo(Integer.parseInt(codigo));
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString(){
        return this.descricao;
    }
    
    public boolean equals(Object obj){
     if (obj == null || getClass() != obj.getClass())return false;
       if (this == obj) return true;
       final Categoria outra = (Categoria) obj;
       if (codigo == outra.codigo) return true;
       else return false;
    }
    
    public int hashCode(){
        return 31 + codigo;
    }

}
