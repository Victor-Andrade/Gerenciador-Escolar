package Classes.datas;

import Classes.interfaces.IDate;
import java.time.LocalDateTime;

public class Calendario {
    int[] dias;

    public Calendario(){
        incializarCalendario();
    }

    private void incializarCalendario(){
        LocalDateTime tempDate = LocalDateTime.now();
        int dias = IDate.eBissexto(tempDate.getYear()) ? 366 : 365;

        this.dias = new int[dias + 1];
        int contador = 0;


        //Finais de semana recebem 3, outros dias recebem 0. Faltas recebem 1, faltas justificadas 2.
        for(int i = 0; i <= dias; i++){
            if(contador == 5 || contador == 6){
                if(contador == 6){
                    contador = 0;
                }
                this.dias[i] = 3;
            }else{
                this.dias[i] = 0;
                contador++;
            }
        }
    }

    public int contarFaltas(){
        int totalFaltas = 0;
        for(int dia: dias){
            if(dia == 1){
                totalFaltas++;
            }
        }
        return totalFaltas;
    }

    public void adicionarFalta(Data data, boolean justificada){
        int posicao = data.contarDias();
        if(justificada){
            this.dias[posicao] = 2;
        }else{
            this.dias[posicao] = 1;
        }

    }

    public void removerFalta(Data data){
        int posicao = data.contarDias();
        this.dias[posicao] = 0;
    }
}


