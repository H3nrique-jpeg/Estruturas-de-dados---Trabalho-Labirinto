public class Pilha<X> implements Cloneable
{
    private int ultimo = -1;
    private int tamanho;
    private X[] elementos;

    public Pilha()
    {
        this.tamanho = 10;
        this.elementos = (X[]) new Object[this.tamanho];
    }

    public Pilha(int setTamanho) throws Exception{
        if (setTamanho <= 0)
            throw new Exception("Tamanho inicial nao pode ser menor ou igual a 0");

        this.tamanho = setTamanho;
        this.elementos = (X[]) new Object[this.tamanho];
    }

    protected void redimensiona(){
        
        // AUMENTAR
        if (isCheia()){
            X[] modelo = (X[]) new Object[this.elementos.length * 2];

            for(int i = 0; i <= this.ultimo; i++){
                if (elementos[i] instanceof Cloneable)
                    modelo[i] = new Clonador<X>().clone(elementos[i]);
                else
                    modelo[i] = this.elementos[i];
            }

            this.elementos = modelo;
        }

        // DIMINUIR
        if((float)(this.ultimo+1)/this.elementos.length <= 0.25f 
           && this.elementos.length > this.tamanho){

            X[] modelo = (X[]) new Object[this.elementos.length / 2];

            for(int i = 0; i <= this.ultimo; i++){
                if (elementos[i] instanceof Cloneable)
                    modelo[i] = new Clonador<X>().clone(elementos[i]);
                else
                    modelo[i] = this.elementos[i];
            }

            this.elementos = modelo;
        }
    }

    public void guardeUmItem(X i)throws Exception{
        if (i==null)
            throw new Exception ("Elemento ausente");

        redimensiona();

        this.ultimo++;

        if (i instanceof Cloneable)
            this.elementos[this.ultimo] = new Clonador<X>().clone(i);
        else
            this.elementos[this.ultimo] = i;
    }

    public X getUmItem()throws Exception{
        if (isVasia())
            throw new Exception("Pilha vazia");

        X resp;

        if (this.elementos[this.ultimo] instanceof Cloneable)
            resp = new Clonador<X>().clone(this.elementos[this.ultimo]);
        else
            resp = this.elementos[this.ultimo];

        return resp;
    }

    public void removaUmItem()throws Exception{
        if(isVasia())
            throw new Exception("Pilha ja esta vazia");

        this.elementos[this.ultimo] = null;
        this.ultimo--;

        redimensiona();
    }

    public Boolean isCheia(){
        return this.ultimo == this.elementos.length - 1;
    }

    public Boolean isVasia(){
        return this.ultimo == -1;
    }

    @Override 
    public int hashCode(){
        int res = 1;

        res = res*2 + ((Integer)this.ultimo).hashCode();
        //res = res*2 + ((Integer)this.tamanho).hashCode();

        for(int i=0; i <= this.ultimo; i++)
            res = res*2 + this.elementos[i].hashCode();

        if (res < 0) res = -res;

        return res;
    }

    @Override 
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != this.getClass()) return false;

        Pilha<X> m = (Pilha<X>)obj;

        if(m.tamanho != this.tamanho) return false;
        if(m.ultimo != this.ultimo) return false;

        for(int i=0; i <= this.ultimo; i++)
            if(!m.elementos[i].equals(this.elementos[i]))
                return false;

        return true;
    }

    @Override 
    public String toString(){
        String res = "Pilha:";

        for(int i=0; i <= this.ultimo; i++)
            res += "   " + this.elementos[i];

        res += "       Ultimo " + this.ultimo + "      Tamanho: " + this.tamanho;

        return res;
    }

    public Pilha(Pilha<X> modelo) throws Exception{
        if (modelo == null)
            throw new Exception("Modelo ausente");

        this.tamanho = modelo.tamanho;
        this.ultimo = modelo.ultimo;

        this.elementos = (X[]) new Object[modelo.elementos.length];

        for(int i = 0; i <= modelo.ultimo; i++)
        {
            this.elementos[i] = modelo.elementos[i];
            /*if(modelo.elementos[i] instanceof Cloneable)
                this.elementos[i] = new Clonador<X>().clone(modelo.elementos[i]);
            else
                this.elementos[i] = modelo.elementos[i];*/
        }
    }

    public Object clone(){
        Pilha<X> ret = null;

        try{
            ret = new Pilha<X>(this);
        }
        catch (Exception erro){}

        return ret;
    }
}
