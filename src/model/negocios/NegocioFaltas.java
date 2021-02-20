package model.negocios;

import model.classes.datas.Data;
import model.classes.excecoes.AlunoNotFoundException;
import model.classes.faltas.Falta;
import model.cruds.CRUDFaltas;
import model.negocios.classesAuxiliares.GerenciadorDeArquivos;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Classe responsável por realizar a funcionalidades que são necessárias na manipulção de faltas
 * @author Victor Hugo
 */


public class NegocioFaltas {
    CRUDFaltas crudFaltas;

    public NegocioFaltas(CRUDFaltas crudFaltas) {
        this.crudFaltas = crudFaltas;
    }

    public boolean alunoPossuiFaltas(CRUDFaltas crud){
        return GerenciadorDeArquivos.existe(crud.getEndereco());
    }

    public void adicionarFalta(String cpf, Data d, boolean justificada, String caminhoDocumento) throws IOException {
        Falta falta = new Falta(d, justificada, caminhoDocumento);
        crudFaltas.adicionarFalta(falta);
    }

    public void removerFalta(String cpf, Data d) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(cpf.equals(crudFaltas.getCpfAluno()) ){
            crudFaltas.removerFalta(d);
        }else{
            throw new AlunoNotFoundException("Aluno não encontrado");
        }
    }

    public void justificarFalta(Data data, String documento) throws IOException, ClassNotFoundException {
        boolean documentoExite = GerenciadorDeArquivos.existe(documento);
        if(documentoExite){
            crudFaltas.justificarFalta(data, documento);
        }else{
            throw new FileNotFoundException("Selecione um arquivo para justificar a falta");
        }
    }
}