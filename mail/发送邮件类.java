import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.javamail.common.MailAuthenticator;

public class Mail {
    private String host;//smtp������
    private String auth;//��֤
    
    private String mailAcount;//�ʼ��û���
    private String password;//����
    
    //��ͬ����
    private MimeMessage mimeMessage;
    private Properties props;
    private MailAuthenticator mailAuth;
    private Session session;
    /**
     * ���캯��
     */
    public Mail(String host,String mailAcount,String password){
        this.host=host;
        this.auth="true";
        this.mailAcount=mailAcount;
        this.password=password;
    }

    /**
     * ������ʼ��
     */
    protected void initialize(){
        props = System.getProperties();
        // ָ��smtp������
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        // �½�һ������smtp�Ự��MimeMessage����
        session=Session.getDefaultInstance(props,null);
        mimeMessage=new MimeMessage(session);
    }
    
    /**
     * �趨�ʼ���Ϣ
     * @param from,to,subject
     * @throws MessagingException 
     * @throws AddressException 
     * @throws UnsupportedEncodingException 
     */
    public void create(String from,String to,String subject) throws AddressException, MessagingException, UnsupportedEncodingException{
        //��ʼ��
        initialize();
        if (from.equals("") || to.equals("") ||subject.equals(""))
        {
            System.out.println("��������");
        }else{
            //ָ��������
            mimeMessage.setFrom(new InternetAddress(from));
            //�Է��ʼ���ַ
            mimeMessage.setRecipients(Message.RecipientType.TO, to);
            //�ʼ�����
            mimeMessage.setSubject(subject,"GBK");
        }
    }
    
    /**
     * �ʼ���ʽ��������ָ��
     * @param content
     * @throws MessagingException 
     */
    public void addContent(String content) throws MessagingException{
        // ָ���ʼ���ʽ
        mimeMessage.setHeader("Content-Type", "text/html");
        // �ʼ�����
        mimeMessage.setText(content);
    }
    
    /**
     * ����
     * @throws MessagingException
     */
    public void send() throws MessagingException{
// ָ����������
        mimeMessage.setSentDate(new Date());
        // ����
        Transport transport = session.getTransport("smtp");
        transport.connect(host,mailAcount,password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
}