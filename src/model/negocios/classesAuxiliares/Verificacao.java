package model.negocios.classesAuxiliares;

import model.classes.datas.Data;
import model.classes.excecoes.InvalidDateException;

import java.time.LocalDateTime;

/**
 * Classe utilitária para verificação de informações das Pessoas
 * @author Pedro Vinícius
 */

public abstract class Verificacao {

    public static boolean verificarEmail(String email) {
        if(email != null){
            return email.contains("@") && email.contains(".com");
        }
        return false;
    }

    public static boolean verificarDataDeNascimento(Data dataDeNascimento) throws InvalidDateException {
        if(dataDeNascimento != null){
            LocalDateTime hoje = LocalDateTime.now();
            return dataDeNascimento.vemAntes(new Data(hoje.getYear(), hoje.getMonthValue(), hoje.getDayOfMonth()));
        }
        return false;
    }

    public static boolean verificarCpf(String cpf){
        if(cpf != null){
            return verificarCampos(cpf);
        }
        return false;
    }

    public static boolean verificarNumeroParaContato(String numeroParaContato){
        if(numeroParaContato != null){
            return verificarCampos(numeroParaContato);
        }
        return false;
    }

    //Remove caracteres especiais e de espaço
    private static String removerCaracteres(String cpf){
        return cpf.replace(".", "").replace("-", "").replace("(", "").replace(")", "").trim();
    }

    //Verifica se uma string é um inteiro
    private static boolean eInteiro(String s) {
        char[] c = s.toCharArray();
        for (char value : c)
            if (!Character.isDigit(value)) {
                return false;
            }
        return true;
    }

    //Verifica se o cpf ou número é válido
    private static boolean verificarCampos(String dado){
        dado = removerCaracteres(dado);

        return dado.length() == 11  && eInteiro(dado);
    }


}
