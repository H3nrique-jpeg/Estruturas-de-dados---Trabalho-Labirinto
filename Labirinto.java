import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Labirinto {
    private int col = 0;
    private int lin = 0;
    private char[][] lab;
    private Pilha<Coordenada> caminho;
    private Pilha<FilaParada<Coordenada>> possibilidades;


    public Labirinto() throws Exception{
        
        Scanner scanInput = new Scanner(System.in);
        
        String fileName = scanInput.nextLine();
        File rawfile = new File(fileName);
        try{
            Scanner scan = new Scanner(rawfile);
            System.out.println("Arquivo encontrado, lendo o labirinto...");
            try{
                col = scan.nextInt();
                lin = scan.nextInt();
                scan.nextLine();
                lab = new char[lin][col];
                for(int i = 0; i<lin;i++){
                    String linha = scan.nextLine();
                    for(int j = 0; j<col; j++){
                        lab[i][j] = linha.charAt(j);
                        //System.out.print(lab[i][j]);
                        //if(j==col-1) System.out.println();
                    }
                }  
                scan.close();
            }catch(Exception e){
                throw new Exception("incoerencia ao ler o labirinto, verifique se o formato do arquivo esta correto");
            }
            
            scanInput.close();
        }catch (FileNotFoundException e){
            scanInput.close();
            throw new Exception("Arquivo nao encontrado");  
        }
        
        
        try{
            this.caminho = new Pilha<Coordenada>(lin*col);
            this.possibilidades = new Pilha<FilaParada<Coordenada>>(lin*col); 
        }catch(Exception e){
            throw new Exception("impossivel criar a fila de possibilidades");
        } 
    }


    public void resolverLab() throws Exception
    {
        Coordenada atual = new Coordenada(0,0);
        for(int i = 0 ; i<lin;i++){
            if(i==0 || i == lin-1){
                for(int j = 0; j<col;j++){
                    if(lab[i][j] == 'E') atual = new Coordenada(i,j);
                }
            }else{
                if(lab[i][0] == 'E') atual = new Coordenada(i,0);
                else if(lab[i][col-1] == 'E') atual = new Coordenada(i,col-1);
            }
        }
        try{
            caminho.guardeUmItem(atual);
        }catch(Exception e){
            throw new Exception("coordenada inicial vazia");
        }

        //RESOLVENDO O LABIRINTO
        
            while (true)
            {
                try{
                    FilaParada<Coordenada> fila = new FilaParada<Coordenada>(3);

                    this.verificarPossibilidades(atual, fila);
                    
                    Coordenada prox;

                    if (fila.isVasia()) {
                        prox = modoRegressivo(atual);
                    } else {
                        prox = fila.getUmItem();
                        fila.removaUmItem();          
                        possibilidades.guardeUmItem(fila);
                    }

                    if (prox == null)
                        throw new RuntimeException("Labirinto sem solução");

                    atual = prox;

                    if (lab[atual.getLin()][atual.getCol()] == 'S') {
                        break;
                    }

                    if (lab[atual.getLin()][atual.getCol()] == ' ')
                        lab[atual.getLin()][atual.getCol()] = '*';
                    caminho.guardeUmItem(atual);

                    if (!fila.isVasia()) {
                        possibilidades.guardeUmItem(fila);
                    }
                }catch(Exception e){
                    throw new Exception(e.getMessage());
                }
            } 
    }


    private void verificarPossibilidades(Coordenada atual, FilaParada<Coordenada> fila) throws Exception{
        try{
            //Leste
            if (atual.getCol() != this.col - 1 && (this.lab[atual.getLin()][atual.getCol()+1] == ' '  || this.lab[atual.getLin()][atual.getCol()+1] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin(), atual.getCol()+1));
            }
              //Oeste
            if (atual.getCol() != 0 && (this.lab[atual.getLin()][atual.getCol()-1] == ' '  || this.lab[atual.getLin()][atual.getCol()-1] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin(), atual.getCol()-1));
            }
            //Sul
            if (atual.getLin() != this.lin - 1 && (this.lab[atual.getLin()+1][atual.getCol()] == ' '  || this.lab[atual.getLin()+1][atual.getCol()] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin()+1, atual.getCol()));
            }
            //Norte
            if (atual.getLin() != 0 && (this.lab[atual.getLin()-1][atual.getCol()] == ' '  || this.lab[atual.getLin()-1][atual.getCol()] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin()-1, atual.getCol()));
            }
        }catch(Exception e){throw new Exception(e.getMessage());}
    }



    private Coordenada modoRegressivo(Coordenada atual) throws Exception
    {
        try{
            while (!possibilidades.isVasia())
            {
                FilaParada<Coordenada> fila = possibilidades.getUmItem();
                if(!fila.isVasia()) {
                    Coordenada resp = fila.getUmItem();
                    fila.removaUmItem();
                    return resp;
                }
                possibilidades.removaUmItem();
                atual = caminho.getUmItem();
                caminho.removaUmItem();
                lab[atual.getLin()][atual.getCol()] = ' ';
                
            }
            
        }catch(Exception e){throw new Exception(e.getMessage());}
        return null;
    }


    
    public String getCaminho()throws Exception{
        String res = "";
                try{
                Pilha<Coordenada> inverso = new Pilha<Coordenada>(this.lin*this.col);
                while (!caminho.isVasia()) {
                    inverso.guardeUmItem(caminho.getUmItem());
                    caminho.removaUmItem();
                }
                while (!inverso.isVasia()) {
                    res += inverso.getUmItem() + "\n";
                    inverso.removaUmItem();
                    
                }
                
            }catch(Exception e){throw new Exception(e.getMessage());}
            return res;
    }


  
    @Override
    public int hashCode(){
        int res = 1;
        res = res*2+ ((Integer)this.lin).hashCode();
        res = res*2+ ((Integer)this.col).hashCode();
        for(int i = 0; i<lin;i++){
            for(int j = 0; j<col; j++){
                res = res*2+ ((Character)this.lab[i][j]).hashCode();
            }
        }
        if (res < 0) res = -res;
        return res;
    }



    @Override
    public String toString(){
        String res = "";
        for(int i = 0; i < lin; i++){
                for(int j = 0; j < col; j++){
                    res = res + this.lab[i][j];
                }
                res = res + "\n";
            }
        return res;
    }

    

    @Override
    public boolean equals(Object obj){
        if(obj == null)return false;
        if(obj == this)return true;
        if(obj.getClass() != this.getClass())return false;
        Labirinto m = (Labirinto)obj;
        if(m.lin != this.lin)return false;
        if(m.col != this.col)return false;
        for(int i = 0; i<lin;i++){
            for(int j = 0; j<col; j++){
                if(this.lab[i][j] != m.lab[i][j])return false;
            }
        }
        return true;
    }
}
