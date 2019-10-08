import java.util.Scanner;
public class PA{
   public static void main(String[]args){
   Scanner teclado = new Scanner(System.in);
   int n,c = 0, pa, x, contadorx = 0;
   double razao, termo1;
   char resposta;
   boolean ERRO;
   do{
   System.out.print("Ola usuario este programa ira contar quantos numeros divisiveis do numero desejado tem na PA desejada.");
   System.out.print("\nDigite o numero que deseja saber os divisiveis: ");
      x = teclado.nextInt();
   System.out.print("Digite o primeiro termo da PA: ");
      termo1 = teclado.nextInt();
   System.out.print("Digite o numero de termos da PA: ");
      n = teclado.nextInt();
   System.out.print("Digite a razao da PA: ");
      razao = teclado.nextDouble();
   for(;c<n;c++){
      if(termo1%x==0){
         contadorx++;
      }//fim if divisiveis
      termo1 = termo1 + razao;
   }//fim for
   System.out.print("O numero de termos divisiveis por " + x + " e: "  + contadorx);
   do{
   System.out.print("\nSe deseja fazer um novo calculo digite [S], se deseja encerrar o programa digite [N]: ");
      resposta = teclado.next().charAt(0);
      resposta = Character.toUpperCase(resposta);
      ERRO = resposta!='S' && resposta!='N';
         if(ERRO){
            System.out.print("Digite [S] ou [N].");
         }//fim if ERRO
   }while(ERRO);//fim while ERRO
   }while(resposta == 'S');
      System.out.print("Obrigado por usar meu programa.");
   }//fim main
}//fim class
