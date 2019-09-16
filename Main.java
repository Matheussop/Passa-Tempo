import java.util.Scanner;

public class Main{
    public static void main(String[]args){
        int hInicio = 0, hFim = 0, mInicio = 0, mFim =0 ;
        int horas = 0;
        int minutos = 0;
        Scanner sc = new Scanner(System.in);
        hInicio = sc.nextInt();
        mInicio = sc.nextInt();
        hFim = sc.nextInt();
        mFim = sc.nextInt();
        if(hInicio == hFim && mInicio == mFim){
            System.out.println("O JOGO DUROU 24 HORA(S) E 0 MINUTO(S)");
        }else if(hInicio == hFim & mInicio != mFim){
            if(mInicio < mFim){
                horas =0;
                minutos = mFim - mInicio;
            }else{
               horas = 23;
               minutos = 60 - (mInicio - mFim);
            }
            System.out.println("O JOGO DUROU "+ horas +" HORA(S) E " + minutos + " MINUTO(S)");
        }else if (hInicio < hFim){
            horas = hFim - hInicio;
            if(mInicio < mFim){
              minutos = mFim - mInicio;
            }else if(mInicio > mFim){
              horas--;
              minutos = 60 - (mInicio - mFim);
            }
            System.out.println("O JOGO DUROU " + horas + " HORA(S) E " + minutos + " MINUTO(S)");
        }else{
           horas = 24 - (hInicio - hFim);
            if(mInicio < mFim){
              minutos = mFim - mInicio;
            }else if(mInicio > mFim){
              horas--;
              minutos = 60 - (mInicio - mFim);
            }
            System.out.println("O JOGO DUROU " + horas + " HORA(S) E " + minutos + " MINUTO(S)");
        }
    }
}