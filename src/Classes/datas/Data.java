package Classes.datas;

import Classes.datas.excecoes.InvalidDateException;

public class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) throws InvalidDateException {
        if(verificarData(ano, mes, dia)) {
            this.ano = ano;
            this.dia = dia;
            this.mes = mes;
        }
    }
    public int contarDias(){
        int mes31 = 0, mes30 = 0, mes28 = 0, mes29 = 0;

        if(determinarMetade(this.mes)){
            for(int i = 1; i < this.mes; i++){
                if(i%2 == 1){
                    mes31++;
                }else{
                    if(i == 2){
                        if (eBissexto(this.ano)) {
                            mes29++;
                        } else {
                            mes28++;
                        }
                    }else{
                        mes30++;
                    }
                }
            }
        }else{
            for(int i = 1; i <= 7; i++){
                if(i%2 == 1){
                    mes31++;
                }else{
                    if(i == 2){
                        if (eBissexto(this.ano)) {
                            mes29++;
                        } else {
                            mes28++;
                        }
                    }else{
                        mes30++;
                    }
                }
            }
            for(int i = 8; i < this.mes; i++){
                if(i%2 == 1){
                    mes31++;
                }else{
                    mes30++;
                }
            }
        }
        return mes30 * 30 + mes31 * 31 + mes28 * 28 + mes29 * 29 + this.dia;
    }

    public boolean vemAntes(Data dataComparar){
        if (ano < dataComparar.ano){
            return true;
        }else if(ano > dataComparar.ano){
            return false;
        }else if(mes < dataComparar.mes){
            return true;
        }else if(mes > dataComparar.mes){
            return false;
        }else if(dia < dataComparar.dia){
            return true;
        }else if(dia > dataComparar.dia){
            return false;
        }
        return true;
    }

    public String formatarData(){
        return this.dia + "/" + this.mes + "/" + this.ano;
    }

    private boolean verificarData(int ano, int mes, int dia) throws InvalidDateException{
        if(verificarAno(ano)){
            if(verificarMeses(mes)) {
                int quantidadeDias = determinarQuantidadeDias(determinarMetade(mes), mes, ano);
                if(verificarDias(dia, quantidadeDias)){
                    return true;
                }else{
                    throw new InvalidDateException(dia, "dia");
                }
            }else{
                throw new InvalidDateException(mes, "mes");
            }
        }else{
            throw new InvalidDateException(ano, "ano");
        }
    }

    private boolean verificarMeses(int mes){
        return mes >= 1 && mes <= 12;
    }

    private boolean verificarAno(int ano){
        return ano > 0;
    }

    private boolean verificarDias(int dia, int diasComparar){
        return dia >= 1 && dia <= diasComparar;
    }

    private int determinarQuantidadeDias(boolean metade, int mes, int ano){
        if(metade){
            if(mes == 2){
                return eBissexto(ano)? 29 : 28;
            }else{
                return mes % 2 == 1 ? 31 : 30;
            }
        }else{
            if(mes == 9){
                return 30;
            }else{
                return mes % 2 == 0 ? 31 : 30;
            }
        }
    }

    private boolean determinarMetade(int meses){
        return meses <=7;
    }

    private boolean eBissexto(int ano){
        if(ano%4 == 0){
            if(ano%100 == 0){
                return ano % 400 == 0;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
}
