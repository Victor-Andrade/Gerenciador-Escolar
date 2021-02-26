package model.classes;

import model.excecoes.InvalidDateException;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe responsável por guardar uma data
 * @author : Pedro Vinicius
 */

public class Data implements Serializable {
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

    public Data(LocalDate data) throws InvalidDateException {
        if(verificarData(data.getYear(), data.getMonthValue(), data.getDayOfMonth())){
            this.ano = data.getYear();
            this.dia = data.getDayOfMonth();
            this.mes = data.getMonthValue();
        }
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
        return (dia >= 10 ? this.dia: "0" + this.dia) + "/" + (mes >= 10 ? this.mes : "0" + this.mes) + "/" + this.ano;
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

    public int getAno() {
        return ano;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Data){
            Data data = (Data) obj;
            return data.getDia() == this.dia && data.getMes() == this.mes && data.getAno() == this.ano;
        }
        return false;
    }
}
