import java.util.*;
 2 
 3 import javax.mail.Folder;
 4 import javax.mail.Message;
 5 import javax.mail.MessagingException;
 6 import javax.mail.Session;
 7 import javax.mail.Store;
 8 import javax.mail.internet.MimeMessage;
 9 
10 import com.javamail.common.ReciveMail;
11 public class MailList {
12     private String host ; //pop3������
13     private String user ; //����
14     private String password ; // ����
15     
16     /**
17      * ���캯��
18      * @param popHost
19      * @param userAcount
20      * @param password
21      */
22     public MailList(String popHost,String userAcount,String password){
23         this.host=popHost;
24         this.user=userAcount;
25         this.password=password;
26     }
27     
28     /**
29      * ȡ�����е��ʼ�
30      * @param folderName �ļ������������ռ�����"INBOX"
31      * @return��List<ReciveMail> ����ReciveMail�����List
32      * @throws MessagingException
33      */
34     public List<ReciveMail> getAllMail(String folderName) throws MessagingException{
35         List<ReciveMail> mailList=new ArrayList<ReciveMail>();
36         
37         // ���ӷ�����
38         Session session = Session.getDefaultInstance(
39                 System.getProperties(), null);
40         Store store = session.getStore("pop3");
41         store.connect(host, -1, user, password);
42         
43         // ���ļ���
44         Folder folder = store.getFolder(folderName);
45         folder.open(Folder.READ_ONLY);
46         
47         // �ܵ��ʼ���
48         int mailCount = folder.getMessageCount();
49         
50         if (mailCount==0){
51             folder.close(false);
52             store.close();
53             return null;
54         }else{
55             // ȡ�����е��ʼ�
56             Message[] messages = folder.getMessages();
57             for (int i = 0; i < messages.length; i++) {
58                 // �Զ�����ʼ�����
59                 ReciveMail reciveMail=new ReciveMail((MimeMessage)messages[i]);
60                 reciveMail.setDataFormat("yy��MM��dd�� HH:mm");// �ʼ�����ʱ���ʽ�趨
61                 mailList.add(reciveMail);//��ӵ��ʼ��б���
62             }
63             // �ر��ļ���
64             folder.close(false);
65             store.close();
66                         // ���ػ�ȡ�����ʼ�
67             return mailList;
68         }
69     }
70 }