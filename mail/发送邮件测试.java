public class SendMailTest {

    /**
     * @param args
     * @throws MessagingException
     * @throws AddressException
     */

    public static void main(String[] args) {
        try {
            String from="xxxxxx@xxxxxx.com";
            String subject="MailTest";
            String content="MailTestMailTestMailTest";
            Mail mail=new Mail("smtp.xxxxxx.com","abctest@xxxxxx.com","xxxxxx");
            mail.create(from, "xxxxxx@xxxxxx.com",subject);
            mail.addContent(content);
            mail.send();
            System.out.println("Send OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}