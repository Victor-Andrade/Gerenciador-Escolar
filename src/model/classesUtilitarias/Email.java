package model.classesUtilitarias;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 * Classe responsável por fazer os envios dos emails
 * @author : Pedro Vinicius
 */
public abstract class Email {
    public static void enviarEmailComAnexo(String emailUsuario, String senha, String caminhoArquivo, String destinatario, String mensagem) throws EmailException {
        //Dados padrão do email
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        //Autenticação do usuário
        email.setAuthentication(emailUsuario, senha);

        //Email do remetente
        email.setFrom(emailUsuario);

        //Informações do email
        email.setSubject("Documento solicitado");
        email.setMsg(mensagem);
        email.addTo(destinatario);

        //Anexo do arquivo
        EmailAttachment arquivo = new EmailAttachment();

        arquivo.setPath(caminhoArquivo);
        arquivo.setDisposition(EmailAttachment.ATTACHMENT);

        email.attach(arquivo);

        email.send();
    }

    public static void enviarEmail(String emailUsuario, String senha, String destinatario, String mensagem, String data) throws EmailException {
        //Dados padrão do email
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        //Autenticação do usuário
        email.setAuthentication(emailUsuario, senha);

        //Email do remetente
        email.setFrom(emailUsuario);

        //Informações do email
        email.setSubject("Condulta indesejada do aluno");
        email.setMsg(data + "\n" + mensagem);
        email.addTo(destinatario);

        email.send();
    }
}
