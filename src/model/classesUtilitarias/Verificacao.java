package model.classesUtilitarias;

import model.classes.Data;
import model.classes.materia.Bimestre;
import model.classes.materia.Materia;
import model.excecoes.InvalidDateException;
import model.classes.pessoas.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.List;

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

    public static boolean verificarNotas(List<Materia> materias){
        for(Materia materia: materias){
            for(int i = 1; i <= 4; i++){
                Bimestre bimestre = materia.getPrimeiroBimestre();
                switch (i){
                    case 2:
                        bimestre = materia.getSegundoBimestre();
                        break;
                    case 3:
                        bimestre = materia.getTerceiroBimestre();
                        break;
                    case 4:
                        bimestre = materia.getQuartoBimestre();
                        break;
                }
                if(bimestre.getNota1() < 0 || bimestre.getNota1() > 10){
                    return false;
                }
                if(bimestre.getNota2() < 0 || bimestre.getNota2() > 10){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean verificarHoras(int horas){
        return horas >= 0;
    }

    public static boolean verificarSenha(Usuario pessoa){
        return pessoa.getSenha().length() >= 8;
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
