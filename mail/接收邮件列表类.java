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
12     private String host ; //pop3服务器
13     private String user ; //邮箱
14     private String password ; // 密码
15     
16     /**
17      * 构造函数
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
29      * 取得所有的邮件
30      * @param folderName 文件夹名，例：收件箱是"INBOX"
31      * @return　List<ReciveMail> 放有ReciveMail对象的List
32      * @throws MessagingException
33      */
34     public List<ReciveMail> getAllMail(String folderName) throws MessagingException{
35         List<ReciveMail> mailList=new ArrayList<ReciveMail>();
36         
37         // 连接服务器
38         Session session = Session.getDefaultInstance(
39                 System.getProperties(), null);
40         Store store = session.getStore("pop3");
41         store.connect(host, -1, user, password);
42         
43         // 打开文件夹
44         Folder folder = store.getFolder(folderName);
45         folder.open(Folder.READ_ONLY);
46         
47         // 总的邮件数
48         int mailCount = folder.getMessageCount();
49         
50         if (mailCount==0){
51             folder.close(false);
52             store.close();
53             return null;
54         }else{
55             // 取得所有的邮件
56             Message[] messages = folder.getMessages();
57             for (int i = 0; i < messages.length; i++) {
58                 // 自定义的邮件对象
59                 ReciveMail reciveMail=new ReciveMail((MimeMessage)messages[i]);
60                 reciveMail.setDataFormat("yy年MM月dd日 HH:mm");// 邮件收信时间格式设定
61                 mailList.add(reciveMail);//添加到邮件列表中
62             }
63             // 关闭文件夹
64             folder.close(false);
65             store.close();
66                         // 返回获取到的邮件
67             return mailList;
68         }
69     }
70 }