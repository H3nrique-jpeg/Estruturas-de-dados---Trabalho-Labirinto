import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Labirinto {
    private int col = 0;
    private int lin = 0;
    private char[][] lab;
    private Pilha<Coordenada> caminho;
    private Pilha<FilaParada<Coordenada>> possibilidades;
    public Labirinto() {
        
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
                        System.out.print(lab[i][j]);
                        if(j==col-1) System.out.println();
                    }
                }  
                scan.close();
            }catch(Exception e){
                System.out.print("Erro ao ler o labirinto, verifique se o formato do arquivo esta correto");
            }
            
            //scan.close();
        }catch (FileNotFoundException e){
            System.out.print("Arquivo nao encontrado");
        }
        scanInput.close();
        
        try{
            this.caminho = new Pilha<Coordenada>(lin*col);
            this.possibilidades = new Pilha<FilaParada<Coordenada>>(lin*col); 
        }catch(Exception e){
            System.out.print("Erro ao criar a fila de possibilidades");
        }



        
    }
    public void resolverLab()
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
            System.out.print("Erro coordenada inicial vasia");
        }

        //RESOLVENDO O LABIRINTO
        
            while (true)
            {
                try{
                    System.out.println("=====================================");
                    

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

                    
                    for(int i = 0; i < lin; i++){
                        for(int j = 0; j < col; j++){
                            System.out.print(lab[i][j]);
                        }
                        System.out.println();
                    }
                }catch(Exception e){
                    System.out.print(e);
                    break;
                }
            } 
            try{
                Pilha<Coordenada> inverso = new Pilha<Coordenada>(this.lin*this.col);
                while (!caminho.isVasia()) {
                    inverso.guardeUmItem(caminho.getUmItem());
                    caminho.removaUmItem();
                }
                System.out.println("Caminho:");
                while (!inverso.isVasia()) {
                    System.out.println(inverso.getUmItem());
                    inverso.removaUmItem();
                    
                }
                System.out.println();
            }catch(Exception e){}

                
     

    }

    private void verificarPossibilidades(Coordenada atual, FilaParada<Coordenada> fila){
        try{
            //Sul
            if (atual.getLin() != this.lin - 1 && (this.lab[atual.getLin()+1][atual.getCol()] == ' '  || this.lab[atual.getLin()+1][atual.getCol()] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin()+1, atual.getCol()));
            }

            //Leste
            if (atual.getCol() != this.col - 1 && (this.lab[atual.getLin()][atual.getCol()+1] == ' '  || this.lab[atual.getLin()][atual.getCol()+1] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin(), atual.getCol()+1));
            }

            //Norte
            if (atual.getLin() != 0 && (this.lab[atual.getLin()-1][atual.getCol()] == ' '  || this.lab[atual.getLin()-1][atual.getCol()] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin()-1, atual.getCol()));
            }

            //Oeste
            if (atual.getCol() != 0 && (this.lab[atual.getLin()][atual.getCol()-1] == ' '  || this.lab[atual.getLin()][atual.getCol()-1] == 'S')){
                fila.guardeUmItem(new Coordenada(atual.getLin(), atual.getCol()-1));
            }
            
        }catch(Exception e){}
    }

    private Coordenada modoRegressivo(Coordenada atual)
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
            
        }catch(Exception e){}
        return null;
    }
  

}



