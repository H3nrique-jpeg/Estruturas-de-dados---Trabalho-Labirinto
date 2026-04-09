
import java.util.Objects;

public class FilaParada<X>
{
    private int qtd = 0;
    private int inicio = 0;
    private int fl = 0;//final
    private int tamInicial;
    private X[] elementos;

    public FilaParada()
    {
        this.tamInicial = 10;
        this.elementos = (X[]) new Object[this.tamInicial];
    }
    public FilaParada(int setTamInicial) throws Exception{
        if (setTamInicial <= 0) {
            throw new Exception("Tamanho inicial nao pode ser menor ou igual a 0");
        }
        this.tamInicial = setTamInicial;
        this.elementos = (X[]) new Object[this.tamInicial];
    }
    protected X clonar(X obj){
        if (obj instanceof Cloneable)
                    return new Clonador<X>().clone(obj);
        return obj;
    }

    protected void redimensiona(){
        if (isCheia()){

            X[]modelo = (X[]) new Object[this.elementos.length*2];

            for(int i = 0; i < this.qtd;i++){
                modelo[i] = clonar(this.elementos[(this.inicio + i) % this.elementos.length]);
            }
            this.fl = this.qtd;
            this.inicio = 0;
            this.elementos = modelo;
        }
        if((float)(this.qtd)/this.elementos.length <= 0.25f && this.elementos.length > this.tamInicial){

            X[]modelo = (X[]) new Object[this.elementos.length/2];

            for(int i = 0; i < this.qtd;i++){
                modelo[i] = clonar(this.elementos[(this.inicio + i) % this.elementos.length]);
            }
            this.fl = this.qtd;
            this.inicio = 0;
            this.elementos = modelo;
        }
    }


    public void guardeUmItem(X i)throws Exception{
        if (i == null) throw new Exception("Elemento ausente");

        if (isCheia())
            redimensiona();

        this.elementos[this.fl] = clonar(i);
        this.fl = (this.fl + 1) % this.elementos.length;
        this.qtd++;
    }

    public X getUmItem()throws Exception{
        if (isVasia())
            throw new Exception("Fila vazia");
        
        X resp;
        resp = clonar(this.elementos[inicio]); 
        return resp;
    }

    public void removaUmItem() throws Exception {
        if (isVasia()) throw new Exception("Fila vazia");

        this.elementos[this.inicio] = null;
        this.inicio = (this.inicio + 1) % this.elementos.length;
        this.qtd--;

        redimensiona();
    }

    public Boolean isCheia(){
        if(this.qtd == this.elementos.length)return true; else return false;
    }
    public Boolean isVasia(){
        if(this.qtd == 0)return true; else return false;
    }

    @Override 
    public int hashCode(){
        int res = 1;
        res = res*2+ ((Integer)this.qtd).hashCode();
        //res = res*2+ ((Integer)this.tamInicial).hashCode();
        for(int i = 0, atual= this.inicio; i<this.qtd;
            i++,atual=atual==this.elementos.length-1?0:atual+1)
            res = res*2+ this.elementos[atual].hashCode();
        if (res < 0) res = -res;
        return res;
    }

    @Override 
    public boolean equals(Object obj){
        if(obj == null)return false;
        if(obj == this)return true;
        if(obj.getClass() != this.getClass())return false;
        
        FilaParada<X> m = (FilaParada<X>)obj;
        if(m.tamInicial != this.tamInicial)return false;
        if(m.qtd != this.qtd)return false;
        for(int i = 0, atualThis = this.inicio, atualm = m.inicio; i<this.qtd; 
            i++,atualThis=atualThis==this.elementos.length-1?0:atualThis+1,atualm=atualm==m.elementos.length-1?0:atualm+1)
                if(!this.elementos[atualThis].equals(m.elementos[atualm]))return false;
        return true;
    }

    @Override 
    public String toString(){
        String res = "";
        res += "FilaParada:";

        for(int i=0; i <= this.qtd-1; i++){
            res += "   "+this.elementos[(this.inicio + i) % this.elementos.length];
        }
        res += "       qtd: "+this.qtd+"      Tamanho:"+this.tamInicial;

        return res;
    }

    public FilaParada(FilaParada<X> modelo) throws Exception{
        if (modelo == null)
            throw new Exception("Modelo ausente");

        this.tamInicial = modelo.tamInicial;
        this.qtd = modelo.qtd;
  

        this.elementos = (X[]) new Object[modelo.elementos.length];

        for(int i = 0, atual = modelo.inicio; i < modelo.qtd;
            i++, atual = atual == modelo.elementos.length - 1 ? 0 : atual + 1){
                this.elementos[i] = modelo.elementos[atual];
            }
            
            /*if(modelo.elementos[i] instanceof Cloneable)
                this.elementos[i] = new Clonador<X>().clone(modelo.elementos[i]);
            else
                this.elementos[i] = modelo.elementos[i];*/
        
        this.inicio = 0;
        this.fl = this.qtd;
        }

    

    public Object clone(){
        FilaParada<X> ret = null;

        try
        {
            ret = new FilaParada<X>(this);
        }
        catch (Exception erro){}

        return ret;
    }
}
