1 import java.text.SimpleDateFormat;
  2 import java.util.Date;
  3 
  4 import javax.mail.Flags;
  5 import javax.mail.Message;
  6 import javax.mail.MessagingException;
  7 import javax.mail.Multipart;
  8 import javax.mail.Part;
  9 import javax.mail.internet.InternetAddress;
 10 import javax.mail.internet.MimeMessage;
 11 import javax.mail.internet.MimeUtility;
 12 
 13 /**
 14  * 每封收到的邮件 是一个ReciveMail对象
 15  * 
 16  */
 17 public class ReciveMail {
 18     private MimeMessage mimeMessage = null;
 19     private StringBuffer mailContent = new StringBuffer();// 邮件内容
 20     private String dataFormat = "yy-MM-dd HH:mm";
 21 
 22     /**
 23      * 构造函数
 24      * 
 25      * @param mimeMessage
 26      */
 27     public ReciveMail(MimeMessage mimeMessage) {
 28         this.mimeMessage = mimeMessage;
 29     }
 30 
 31     // MimeMessage设定
 32     public void setMimeMessage(MimeMessage mimeMessage) {
 33         this.mimeMessage = mimeMessage;
 34     }
 35 
 36     /**
 37      * 获得送信人的姓名和邮件地址
 38      * 
 39      * @throws MessagingException
 40      */
 41     public String getFrom() throws MessagingException {
 42         InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
 43 
 44         String addr = address[0].getAddress();
 45         String name = address[0].getPersonal();
 46         if (addr == null) {
 47             addr = "";
 48         }
 49         if (name == null) {
 50             name = "";
 51         }
 52 
 53         String nameAddr = name + "<" + addr + ">";
 54         return nameAddr;
 55     }
 56 
 57     /**
 58      * 根据类型，获取邮件地址 "TO"--收件人地址 "CC"--抄送人地址 "BCC"--密送人地址
 59      * 
 60      * @throws Exception
 61      */
 62     public String getMailAddress(String type) throws Exception {
 63         String mailAddr = "";
 64         String addType = type.toUpperCase();
 65         InternetAddress[] address = null;
 66 
 67         if (addType.equals("TO")) {
 68             address = (InternetAddress[]) mimeMessage
 69                     .getRecipients(Message.RecipientType.TO);
 70 
 71         } else if (addType.equals("CC")) {
 72             address = (InternetAddress[]) mimeMessage
 73                     .getRecipients(Message.RecipientType.CC);
 74         } else if (addType.equals("BCC")) {
 75             address = (InternetAddress[]) mimeMessage
 76                     .getRecipients(Message.RecipientType.BCC);
 77         } else {
 78             System.out.println("error type!");
 79             throw new Exception("Error emailaddr type!");
 80         }
 81 
 82         if (address != null) {
 83             for (int i = 0; i < address.length; i++) {
 84                 String mailaddress = address[i].getAddress();
 85                 if (mailaddress != null) {
 86                     mailaddress = MimeUtility.decodeText(mailaddress);
 87                 } else {
 88                     mailaddress = "";
 89                 }
 90                 String name = address[i].getPersonal();
 91                 if (name != null) {
 92                     name = MimeUtility.decodeText(name);
 93                 } else {
 94                     name = "";
 95                 }
 96                 mailAddr = name + "<" + mailaddress + ">";
 97             }
 98         }
 99         return mailAddr;
100     }
101 
102     /**
103      * 取得邮件标题
104      * 
105      * @return String
106      */
107     public String getSubject() {
108         String subject = "";
109         try {
110             subject = MimeUtility.decodeText(mimeMessage.getSubject());
111             if (subject == null) {
112                 subject = "";
113             }
114         } catch (Exception e) {
115 
116         }
117         return subject;
118     }
119 
120     /**
121      * 取得邮件日期
122      * 
123      * @throws MessagingException
124      */
125     public String getSentData() throws MessagingException {
126         Date sentdata = mimeMessage.getSentDate();
127         if (sentdata!=null){
128         SimpleDateFormat format = new SimpleDateFormat(dataFormat);
129         return format.format(sentdata);
130         }else{
131             return "不清楚";
132         }
133         
134     }
135 
136     /**
137      * 取得邮件内容
138      * @throws Exception 
139      */
140     public String getMailContent() throws Exception {
141         compileMailContent((Part)mimeMessage);
142         return mailContent.toString();
143     }
144 
145     /**
146      * 解析邮件内容
147      * 
148      * @param part
149      * @throws Exception
150      */
151     public void compileMailContent(Part part) throws Exception {
152         String contentType = part.getContentType();
153         int nameIndex = contentType.indexOf("name");
154         boolean connName = false;
155         if (nameIndex != -1) {
156             connName = true;
157         }
158 
159         if (part.isMimeType("text/plain") && !connName) {
160             mailContent.append((String) part.getContent());
161         } else if (part.isMimeType("text/html") && !connName) {
162             mailContent.append((String) part.getContent());
163         } else if (part.isMimeType("multipart/*")) {
164             Multipart multipart = (Multipart) part.getContent();
165             int counts = multipart.getCount();
166             for (int i = 0; i < counts; i++) {
167                 compileMailContent(multipart.getBodyPart(i));
168             }
169         } else if (part.isMimeType("message/rfc822")) {
170             compileMailContent((Part) part.getContent());
171         }
172     }
173 
174     /**
175      * 判定有没有必要回复
176      * 
177      * @throws MessagingException
178      */
179     public boolean getReplySign() throws MessagingException {
180         boolean replySign = false;
181         String needreply[] = mimeMessage
182                 .getHeader("Disposition-Notification-To");
183         if (needreply != null) {
184             replySign = true;
185         }
186         return replySign;
187     }
188 
189     /**
190      * 取得「message-ID」
191      * 
192      * @throws MessagingException
193      */
194     public String getMessageID() throws MessagingException {
195         return mimeMessage.getMessageID();
196     }
197 
198     /**
199      * 是否是新邮件
200      * 
201      * @throws MessagingException
202      */
203     public boolean isNew() throws MessagingException {
204         boolean isnew = false;
205         Flags flags = ((Message) mimeMessage).getFlags();
206         Flags.Flag[] flag = flags.getSystemFlags();
207         for (int i = 0; i < flag.length; i++) {
208             if (flag[i] == Flags.Flag.SEEN) {
209                 isnew = true;
210                 break;
211             }
212         }
213         return isnew;
214     }
215 
216     public void setMailContent(StringBuffer mailContent) {
217         this.mailContent = mailContent;
218     }
219     
220     /**
221      * 设定收信日期格式
222      */
223     public void setDataFormat(String dataFormat) {
224         this.dataFormat = dataFormat;
225     }
226 }