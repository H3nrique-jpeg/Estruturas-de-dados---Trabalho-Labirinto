public class Coordenada 
{
    private int lin, col;

    public Coordenada(int lin, int col)
    {
        this.lin = lin;
        this.col = col; 
    }

    public int getLin() {
        return lin;
    }

    public void setLin(int lin) {
        this.lin = lin;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    @Override
    public String toString() {
        return "Coordenada [Linha=" + lin + ", Coluna=" + col + "]";
    }

    @Override
    public int hashCode() {
        int hash = 17;

        hash = hash * 11 + ((Integer) this.lin).hashCode();
        hash = hash * 11 + ((Integer) this.col).hashCode();

        if(hash < 0) hash =- hash;
        return hash;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this) return true; 
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;

        Coordenada c = (Coordenada) obj;

        if(c.col != this.col) return false;
        if(c.lin != this.lin) return false;

        return true;
    }
}
