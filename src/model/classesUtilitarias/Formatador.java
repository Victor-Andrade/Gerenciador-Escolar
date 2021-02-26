package model.classesUtilitarias;

/**
 * Classe responsável por fazer as formatações de dados das pessoas do repositório
 * @author: Victor Hugo
 */

public abstract class Formatador {
    public static String removerCaracteresCpf(String cpf){
        return cpf.replaceAll("[\\p{P}\\p{S}]", "").toLowerCase();
    }

    public static String removerCaracteresNome(String nome){
        return nome.toUpperCase();
    }

    public static String removerCaracteresNumero(String numero){
        return numero.replaceAll("[\\p{P}\\p{S}]", "");
    }

    public static String formatarCpf(String cpf){
        char[] cpfArray = new char[cpf.length() + 3];

        char[] cpfArrayTemp = cpf.toCharArray();

        int posicoes = 0;
        for(int i = 0; i < cpf.length(); i++){
            if(i == 2 || i == 5){
                cpfArray[i + posicoes] = cpfArrayTemp[i];
                posicoes++;
                cpfArray[i + posicoes] = '.';
            }else if(i == 8){
                cpfArray[i + posicoes] = cpfArrayTemp[i];
                posicoes++;
                cpfArray[i + posicoes] = '-';
            }else{
                cpfArray[i + posicoes] = cpfArrayTemp[i];
            }
        }
        return String.copyValueOf(cpfArray);
    }

    public static String formatarNumero(String numero){
        char[] numeroArray = new char[numero.length() + 4];

        char[] numeroArrayTemp = numero.toCharArray();

        int posicoes = 0;
        for(int i = 0; i < numero.length(); i++){
            if(i == 0){
                numeroArray[i + posicoes] = '(';
                posicoes++;
                numeroArray[i + posicoes] = numeroArrayTemp[i];
            }else if(i == 2){
                numeroArray[i + posicoes] = ')';
                posicoes++;
                numeroArray[i + posicoes] = ' ';
                posicoes++;
                numeroArray[i + posicoes] = numeroArrayTemp[i];
            }else if(i == 6){
                numeroArray[i + posicoes] = numeroArrayTemp[i];
                posicoes++;
                numeroArray[i + posicoes] = '-';
            }else{
                numeroArray[i + posicoes] = numeroArrayTemp[i];
            }
        }
        return String.copyValueOf(numeroArray);
    }
}

