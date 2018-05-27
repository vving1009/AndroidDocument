gson��JavaBean֮���ת��

2015-05-02 00:01 ��վ���� ���(480) 


һ��gson��Ҫ������JavaBean���л��ͷ����л�

gson���԰�JavaBean���л�Ϊ����json��ʽ���ַ�����Ҳ���Խ�json�ַ���������ΪJavaBean

��Ҫ����İ���

gson-2.2.4.jar

��������ĵķ�����

String json = gson.toJson(javaBean);

JavaBean javaBean = gson.fromJson(json, JavaBean.class);

����ʾ��

1��JavaBean

public class Person {
	private String name;
	private int age;
	private String sex;
	
	public Person(String name,int age,String sex){
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
}


2��JavaBeanת��Json��ʽ

Person p = new Person("����",20,"��");
Gson gson = new Gson();
String json = gson.toJson(p);
System.out.println(json);
//�����{"name":"����","age":20,"sex":"��"}


3��Jsonת��ΪJavaBean

String json = "{\"name\":\"����\",\"age\":20,\"sex\":\"��\"}";
Gson gson = new Gson();
Person p = gson.fromJson(json, Person.class);
System.out.println(p.getName());
System.out.println(p.getAge());
System.out.println(p.getSex());
//���������
//20
//��

=======================================================================

gson����������java json������Ĳ�ͬʱgson��Ҫ���л���ʵ���಻��Ҫʹ��annotation����ʶ��Ҫ���л����ֶΣ�ͬʱgson�ֿ���ͨ��ʹ��annotation�����������Ҫ���л����ֶΡ�

������һ���򵥵����ӣ�
Personʵ��

public class Person {

    private String name;
    private int age;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString()
    {
        return name + ":" +age;
    }
}
ʵ��ܼ򵥣������ֶΣ���Ȼʵ���е��ֶ�Ҳ������List����Set���͵ġ�
����Json�ַ���

Gson gson = new Gson();
List<Person> persons = new ArrayList<Person>();
for (int i = 0; i < 10; i++) {
     Person p = new Person();
     p.setName("name" + i);
     p.setAge(i * 5);
     persons.add(p);
}
String str = gson.toJson(persons);
����Ĵ����ص���Gson�������ṩ��toJason()����������ת����Json�ַ�������������str����ֵΪ��

[{"name":"name0","age":0},{"name":"name1","age":5},{"name":"name2","age":10},{"name":"name3","age":15},{"name":"name4","age":20},{"name":"name5","age":25},{"name":"name6","age":30},{"name":"name7","age":35},{"name":"name8","age":40},{"name":"name9","age":45}]

�ܱ�׼��json���ݣ��ܼ򵥰ɣ��Ǻǡ�

����������gson�ķ����л���Gson�ṩ��fromJson()������ʵ�ִ�Json��ض���javaʵ��ķ�����

���ճ�Ӧ���У�����һ�㶼���������������ת�ɵ�һʵ������ת���ɶ����б���������ṹ��

��������һ�֣�

����json�ַ���Ϊ��[{"name":"name0","age":0}]

����:
Person person = gson.fromJson(str, Person.class);
�ṩ�����������ֱ���json�ַ����Լ���Ҫת����������͡�

�ڶ��֣�ת�����б����ͣ�

����:
List<Person> ps = gson.fromJson(str, new TypeToken<List<Person>>(){}.getType());
for(int i = 0; i < ps.size() ; i++)
{
     Person p = ps.get(i);
     System.out.println(p.toString());
}
���Կ�������Ĵ���ʹ����TypeToken������gson�ṩ����������ת����������֧�ָ������ݼ�������ת����

Gson�Ļ���ʹ�þ�����ô�࣬����annotation������Բο�gson�Ĺٷ��ĵ���ϣ���ܶԳ�ѧjava��gson��ͬѧ����������

=======================================================================

public class DemoList {
     private List<Demo> demoList;
     public List<Demo> getDemoList() {
         return demoList;
 }

     public void setDemoList(List<Demo> demoList) {
        this.demoList = demoList;
     }
}
 ���������ڽ���ʱ��Ϳ���һ�д���㶨����Json�ķ����л���������ʱ�������������Gson��ǿ��֮����
 ��ֵ��ע��ľ���DemoList�к�ɫ�ĳ�Ա��������������Ӧ���Ǻ�Json�ַ����м��ϵļ��Ƕ�Ӧ�ģ��Ҿ�����Ϊ��֪��������ұ���ĥ�ѡ�
DemoList demo = new Gson().fromJson(json,DemoList.class);
List<Demo> list = demo.getDemoList();


=======================================================================

Google Gson��ʹ�÷���,ʵ��Json�ṹ���໥ת��  
 

2015-01-16 12:03 11195���Ķ� ����(0) �ղ� �ٱ� 
.

  

 ���ࣺ 

Json  android��43��   


 . 

��Ȩ����������Ϊ����ԭ�����£�δ������������ת�ء����ԣ�http://blog.csdn.net/qxs965266509


��Java�����У���ʱ��Ҫ����һ�����ݽṹ���ַ�����������ῼ����Json�����ǵ�Json�ַ���ת����Java����ʱ��ת���ɵ���JsonObject������������Ҫ��Class���͵Ķ��󣬲��������ͺܲ������ã�����˵�ľͿ��Խ�����������⡣




���ȣ���Ҫ��Google��Gson��Jar�����뵽��Ŀ�У����������ļ򵥲���Ͳ�չʾ�ˣ�Gson���������ӣ�http://download.csdn.net/detail/qxs965266509/8367275


���ڣ������Զ���һ��Class��






[java] view plain copy 
01.public class Student {  
02.    public int id;  
03.    public String nickName;  
04.    public int age;  
05.    public ArrayList<String> books;  
06.    public HashMap<String, String> booksMap;  
07.}  








����һ�������������������ǰ�Java��Class����ʹ��Gsonת����Json���ַ���

����һ��


�����������������͵����ݽṹ





[java] view plain copy 
01.Gson gson = new Gson();  
02.    Student student = new Student();  
03.    student.id = 1;  
04.    student.nickName = "������";  
05.    student.age = 22;  
06.    student.email = "965266509@qq.com";  
07.    Log.e("MainActivity", gson.toJson(student));  






 
�������� ��




[java] view plain copy 
01.{"email":"965266509@qq.com","nickName":"������","id":1,"age":22}  










��������

���˻����������ͻ�������List����





[java] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.Gson gson = new Gson();  
02.        Student student = new Student();  
03.        student.id = 1;  
04.        student.nickName = "������";  
05.        student.age = 22;  
06.        student.email = "965266509@qq.com";  
07.        ArrayList<String> books = new ArrayList<String>();  
08.        books.add("��ѧ");  
09.        books.add("����");  
10.        books.add("Ӣ��");  
11.        books.add("����");  
12.        books.add("��ѧ");  
13.        books.add("����");  
14.        student.books = books;  
15.        Log.e("MainActivity", gson.toJson(student));  



�������� ��


[html] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.{"books":["��ѧ","����","Ӣ��","����","��ѧ","����"],"email":"965266509@qq.com","nickName":"������","id":1,"age":22}  








��������

���˻����������ͻ�������List��Map����





[java] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.Gson gson = new Gson();  
02.        Student student = new Student();  
03.        student.id = 1;  
04.        student.nickName = "������";  
05.        student.age = 22;  
06.        student.email = "965266509@qq.com";  
07.        ArrayList<String> books = new ArrayList<String>();  
08.        books.add("��ѧ");  
09.        books.add("����");  
10.        books.add("Ӣ��");  
11.        books.add("����");  
12.        books.add("��ѧ");  
13.        books.add("����");  
14.        student.books = books;  
15.        HashMap<String, String> booksMap = new HashMap<String, String>();  
16.        booksMap.put("1", "��ѧ");  
17.        booksMap.put("2", "����");  
18.        booksMap.put("3", "Ӣ��");  
19.        booksMap.put("4", "����");  
20.        booksMap.put("5", "��ѧ");  
21.        booksMap.put("6", "����");  
22.        student.booksMap = booksMap;  
23.        Log.e("MainActivity", gson.toJson(student));  




�������� ��





[java] view plain copy 
01.{"books":["��ѧ","����","Ӣ��","����","��ѧ","����"],"booksMap":{"3":"Ӣ��","2":"����","1":"��ѧ","6":"����","5":"��ѧ","4":"����"},"email":"965266509@qq.com","nickName":"������","id":1,"age":22}  










�����ģ�
�Ѱ�����������ַ���ʹ��Gsonת����Student����





[java] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.Gson gson = new Gson();  
02.        Student student = new Student();  
03.        student.id = 1;  
04.        student.nickName = "������";  
05.        student.age = 22;  
06.        student.email = "965266509@qq.com";  
07.        ArrayList<String> books = new ArrayList<String>();  
08.        books.add("��ѧ");  
09.        books.add("����");  
10.        books.add("Ӣ��");  
11.        books.add("����");  
12.        books.add("��ѧ");  
13.        books.add("����");  
14.        student.books = books;  
15.        HashMap<String, String> booksMap = new HashMap<String, String>();  
16.        booksMap.put("1", "��ѧ");  
17.        booksMap.put("2", "����");  
18.        booksMap.put("3", "Ӣ��");  
19.        booksMap.put("4", "����");  
20.        booksMap.put("5", "��ѧ");  
21.        booksMap.put("6", "����");  
22.        student.booksMap = booksMap;  
23.        String result = gson.toJson(student);  
24.  
25.        Student studentG = gson.fromJson(result, Student.class);  
26.  
27.        Log.e("MainActivity", "id:" + studentG.id);  
28.        Log.e("MainActivity", "nickName:" + studentG.nickName);  
29.        Log.e("MainActivity", "age:" + studentG.age);  
30.        Log.e("MainActivity", "email:" + studentG.email);  
31.        Log.e("MainActivity", "books size:" + studentG.books.size());  
32.        Log.e("MainActivity", "booksMap size:" + studentG.booksMap.size());  



�������� �� 





[html] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.id:1  
02.nickName:������  
03.age:22  
04.email:965266509@qq.com  
05.books size:6  
06.booksMap size:6  



 

ͨ����4�������ҽ����һ���Ͱ�Gson�Ļ����÷�ѧ���ˣ���Ȼ���ǵ����������Ҫ��List����Map�ȼ��ϵķ��ͻ��������Զ����class����Ҳ�ǿ��Խ���ģ��뿴����




�����壺

���͵�ʹ��




[html] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.public HashMap<String,Book> booksMap;  
02.  
03.public class Book{  
04.    public int id;  
05.    public String name;  
06.}  







��booksMapת�����ַ���������İ�����һ���ģ�����booksMap��Json�ַ�������booksMap��ʵ��������е㲻ͬ�ˣ���ΪbooksMap���Զ���ķ���







[html] view plain copy ��CODE�ϲ鿴����Ƭ�������ҵĴ���Ƭ
01.HashMap<String, Book> booksMap = gson.fromJson(result, new TypeToken<HashMap<String, Book>>() { }.getType());  



