import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) 
    {
        int col = 0;
        int lin = 0;
        char[][] lab;
        Scanner scanInput = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo labirinto(EXATAMENTE igual):  ");
        String fileName = scanInput.nextLine();
        File rawfile = new File(fileName);
        //scanInput.close();
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
                
            }catch(Exception e){
                System.out.print("Erro ao ler o labirinto, verifique se o formato do arquivo esta correto");
            }
                
            //scan.close();
        }catch (FileNotFoundException e){
                System.out.print("Arquivo nao encontrado");
            } 
        try
        {

            Pilha<Coordenada> caminho = new Pilha<Coordenada>(lin*col);
            Pilha<FilaParada<Coordenada>> possibilidades = new Pilha<FilaParada<Coordenada>>(lin*col);
            FilaParada<Coordenada> fila = new FilaParada<Coordenada>(3);
            Coordenada atual;

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
                caminho.guardeUmItem(atual);
        }   
        catch(Exception e){}
        

    }
}