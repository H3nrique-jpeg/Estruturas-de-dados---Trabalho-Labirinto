public class Main {
    public static void main(String[] args) 
    {
        try{
        Labirinto lab = new Labirinto();
        lab.resolverLab();
        System.out.println("=====================================");
        System.out.println("Labirinto resolvido: \n");
        System.out.println(lab);
        System.out.println("Caminho: \n" + lab.getCaminho());
        }catch(Exception e){
            System.out.println("Erro ao resolver o labirinto:  "+e.getMessage()+ "  -- Tente novamente!!!");
        }

    }
}