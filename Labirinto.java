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
        try{
            this.caminho = new Pilha<Coordenada>(lin*col);
            this.possibilidades = new Pilha<FilaParada<Coordenada>>(lin*col); 
        }catch(Exception e){
            System.out.print("Erro ao criar a fila de possibilidades");
        }
        
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
                    for(int j = 0; j<col;j++){
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
        
            while (lab[atual.getCol()][atual.getLin()] != 'S')
            {
                try{
                    FilaParada<Coordenada> fila = new FilaParada<Coordenada>(3);

                    this.verificarPossibilidades(atual, fila);
                    
                    atual = fila.isVasia()?modoRegressivo(atual):fila.getUmItem();

                    fila.removaUmItem();

                    lab[atual.getCol()][atual.getLin()] = '*';

                    caminho.guardeUmItem(atual);

                    possibilidades.guardeUmItem(fila);

                    System.out.println("=====================================");
                    System.out.println(lab);
                }catch(Exception e){System.out.print(e);}
            } 
            try{
                Pilha<Coordenada> inverso = new Pilha<Coordenada>(this.lin*this.col);
                while (!caminho.isVasia()) {
                    inverso.guardeUmItem(caminho.getUmItem());
                    caminho.removaUmItem();
                }
                while (!inverso.isVasia()) {
                    System.out.print(inverso);
                    System.out.print("  ->  ");
                    inverso.removaUmItem();
                }
            }catch(Exception e){}
                
     

    }

    private void verificarPossibilidades(Coordenada atual, FilaParada<Coordenada> fila){
        try{
            if (this.lab[atual.getCol()+1][atual.getLin()] == ' ' && atual.getCol() != this.col - 1 || this.lab[atual.getCol()+1][atual.getLin()] == 'S'){
                fila.guardeUmItem(new Coordenada(atual.getCol()+1, atual.getLin()));
            }
            if (this.lab[atual.getCol()][atual.getLin()+1] == ' ' && atual.getLin() != this.lin - 1 || this.lab[atual.getCol()+1][atual.getLin()] == 'S'){
                fila.guardeUmItem(new Coordenada(atual.getCol()+1, atual.getLin()));
            }
            if (this.lab[atual.getCol()-1][atual.getLin()] == ' ' && atual.getCol() != 0 || this.lab[atual.getCol()+1][atual.getLin()] == 'S'){
                fila.guardeUmItem(new Coordenada(atual.getCol()+1, atual.getLin()));
            }
            if (this.lab[atual.getCol()][atual.getLin()-1] == ' ' && atual.getLin() != 0 || this.lab[atual.getCol()+1][atual.getLin()] == 'S'){
                fila.guardeUmItem(new Coordenada(atual.getCol()+1, atual.getLin()));
            }
            
        }catch(Exception e){}
    }

    private Coordenada modoRegressivo(Coordenada atual)
    {
        try{
            while (possibilidades.getUmItem().isVasia())
            {
                atual = caminho.getUmItem();
                caminho.removaUmItem();
                lab[atual.getCol()][atual.getLin()] = ' ';
                possibilidades.removaUmItem();
            }
            Coordenada resp = possibilidades.getUmItem().getUmItem();
            possibilidades.getUmItem().removaUmItem();
            return resp;

        }catch(Exception e){}
        return null;
    }
  

}



