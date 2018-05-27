gson和JavaBean之间的转换

2015-05-02 00:01 本站整理 浏览(480) 


一、gson主要用来对JavaBean序列化和反序列化

gson可以把JavaBean序列化为符合json格式的字符串，也可以将json字符串反序列为JavaBean

需要导入的包：

gson-2.2.4.jar

二、最核心的方法：

String json = gson.toJson(javaBean);

JavaBean javaBean = gson.fromJson(json, JavaBean.class);

三、示例

1、JavaBean

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


2、JavaBean转换Json格式

Person p = new Person("张三",20,"男");
Gson gson = new Gson();
String json = gson.toJson(p);
System.out.println(json);
//结果：{"name":"张三","age":20,"sex":"男"}


3、Json转换为JavaBean

String json = "{\"name\":\"张三\",\"age\":20,\"sex\":\"男\"}";
Gson gson = new Gson();
Person p = gson.fromJson(json, Person.class);
System.out.println(p.getName());
System.out.println(p.getAge());
System.out.println(p.getSex());
//结果：张三
//20
//男

=======================================================================

gson和其他现有java json类库最大的不同时gson需要序列化得实体类不需要使用annotation来标识需要序列化得字段，同时gson又可以通过使用annotation来灵活配置需要序列化的字段。

下面是一个简单的例子：
Person实体

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
实体很简单，两个字段，当然实体中的字段也可以是List或者Set类型的。
生成Json字符串

Gson gson = new Gson();
List<Person> persons = new ArrayList<Person>();
for (int i = 0; i < 10; i++) {
     Person p = new Person();
     p.setName("name" + i);
     p.setAge(i * 5);
     persons.add(p);
}
String str = gson.toJson(persons);
上面的代码重点是Gson对象，它提供了toJason()方法将对象转换成Json字符串，上面代码的str对象值为：

[{"name":"name0","age":0},{"name":"name1","age":5},{"name":"name2","age":10},{"name":"name3","age":15},{"name":"name4","age":20},{"name":"name5","age":25},{"name":"name6","age":30},{"name":"name7","age":35},{"name":"name8","age":40},{"name":"name9","age":45}]

很标准的json数据，很简单吧，呵呵。

下面来看看gson的反序列化，Gson提供了fromJson()方法来实现从Json相关对象到java实体的方法。

在日常应用中，我们一般都会碰到两种情况，转成单一实体对象和转换成对象列表或者其他结构。

先来看第一种：

比如json字符串为：[{"name":"name0","age":0}]

代码:
Person person = gson.fromJson(str, Person.class);
提供两个参数，分别是json字符串以及需要转换对象的类型。

第二种，转换成列表类型：

代码:
List<Person> ps = gson.fromJson(str, new TypeToken<List<Person>>(){}.getType());
for(int i = 0; i < ps.size() ; i++)
{
     Person p = ps.get(i);
     System.out.println(p.toString());
}
可以看到上面的代码使用了TypeToken，它是gson提供的数据类型转换器，可以支持各种数据集合类型转换。

Gson的基本使用就是这么多，至于annotation方面可以参考gson的官方文档，希望能对初学java和gson的同学有所帮助。

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
 接下来，在解析时候就可以一行代码搞定整个Json的反序列化操作，这时候才真正看到了Gson的强大之处，
 但值得注意的就是DemoList中红色的成员变量，它的名字应该是和Json字符串中集合的键是对应的，我就是因为不知道这点让我饱经磨难。
DemoList demo = new Gson().fromJson(json,DemoList.class);
List<Demo> list = demo.getDemoList();


=======================================================================

Google Gson的使用方法,实现Json结构的相互转换  
 

2015-01-16 12:03 11195人阅读 评论(0) 收藏 举报 
.

  

 分类： 

Json  android（43）   


 . 

版权声明：本文为博主原创文章，未经博主允许不得转载。来自：http://blog.csdn.net/qxs965266509


在Java开发中，有时需要保存一个数据结构成字符串，可能你会考虑用Json，但是当Json字符串转换成Java对象时，转换成的是JsonObject，并不是你想要的Class类型的对象，操作起来就很不是愉悦，下面说的就可以解决了这种问题。




首先，需要把Google的Gson的Jar包导入到项目中，这个导入包的简单步骤就不展示了，Gson的下载链接：http://download.csdn.net/detail/qxs965266509/8367275


现在，我先自定义一个Class类






[java] view plain copy 
01.public class Student {  
02.    public int id;  
03.    public String nickName;  
04.    public int age;  
05.    public ArrayList<String> books;  
06.    public HashMap<String, String> booksMap;  
07.}  








案例一，案例二，案例三都是把Java的Class对象使用Gson转换成Json的字符串

案例一：


仅包含基本数据类型的数据结构





[java] view plain copy 
01.Gson gson = new Gson();  
02.    Student student = new Student();  
03.    student.id = 1;  
04.    student.nickName = "乔晓松";  
05.    student.age = 22;  
06.    student.email = "965266509@qq.com";  
07.    Log.e("MainActivity", gson.toJson(student));  






 
输出结果是 ：




[java] view plain copy 
01.{"email":"965266509@qq.com","nickName":"乔晓松","id":1,"age":22}  










案例二：

除了基本数据类型还包含了List集合





[java] view plain copy 在CODE上查看代码片派生到我的代码片
01.Gson gson = new Gson();  
02.        Student student = new Student();  
03.        student.id = 1;  
04.        student.nickName = "乔晓松";  
05.        student.age = 22;  
06.        student.email = "965266509@qq.com";  
07.        ArrayList<String> books = new ArrayList<String>();  
08.        books.add("数学");  
09.        books.add("语文");  
10.        books.add("英语");  
11.        books.add("物理");  
12.        books.add("化学");  
13.        books.add("生物");  
14.        student.books = books;  
15.        Log.e("MainActivity", gson.toJson(student));  



输出结果是 ：


[html] view plain copy 在CODE上查看代码片派生到我的代码片
01.{"books":["数学","语文","英语","物理","化学","生物"],"email":"965266509@qq.com","nickName":"乔晓松","id":1,"age":22}  








案例三：

除了基本数据类型还包含了List和Map集合





[java] view plain copy 在CODE上查看代码片派生到我的代码片
01.Gson gson = new Gson();  
02.        Student student = new Student();  
03.        student.id = 1;  
04.        student.nickName = "乔晓松";  
05.        student.age = 22;  
06.        student.email = "965266509@qq.com";  
07.        ArrayList<String> books = new ArrayList<String>();  
08.        books.add("数学");  
09.        books.add("语文");  
10.        books.add("英语");  
11.        books.add("物理");  
12.        books.add("化学");  
13.        books.add("生物");  
14.        student.books = books;  
15.        HashMap<String, String> booksMap = new HashMap<String, String>();  
16.        booksMap.put("1", "数学");  
17.        booksMap.put("2", "语文");  
18.        booksMap.put("3", "英语");  
19.        booksMap.put("4", "物理");  
20.        booksMap.put("5", "化学");  
21.        booksMap.put("6", "生物");  
22.        student.booksMap = booksMap;  
23.        Log.e("MainActivity", gson.toJson(student));  




输出结果是 ：





[java] view plain copy 
01.{"books":["数学","语文","英语","物理","化学","生物"],"booksMap":{"3":"英语","2":"语文","1":"数学","6":"生物","5":"化学","4":"物理"},"email":"965266509@qq.com","nickName":"乔晓松","id":1,"age":22}  










案例四：
把案例三输出的字符串使用Gson转换成Student对象





[java] view plain copy 在CODE上查看代码片派生到我的代码片
01.Gson gson = new Gson();  
02.        Student student = new Student();  
03.        student.id = 1;  
04.        student.nickName = "乔晓松";  
05.        student.age = 22;  
06.        student.email = "965266509@qq.com";  
07.        ArrayList<String> books = new ArrayList<String>();  
08.        books.add("数学");  
09.        books.add("语文");  
10.        books.add("英语");  
11.        books.add("物理");  
12.        books.add("化学");  
13.        books.add("生物");  
14.        student.books = books;  
15.        HashMap<String, String> booksMap = new HashMap<String, String>();  
16.        booksMap.put("1", "数学");  
17.        booksMap.put("2", "语文");  
18.        booksMap.put("3", "英语");  
19.        booksMap.put("4", "物理");  
20.        booksMap.put("5", "化学");  
21.        booksMap.put("6", "生物");  
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



输出结果是 ： 





[html] view plain copy 在CODE上查看代码片派生到我的代码片
01.id:1  
02.nickName:乔晓松  
03.age:22  
04.email:965266509@qq.com  
05.books size:6  
06.booksMap size:6  



 

通过这4个案例我解决你一定就把Gson的基本用法学会了，当然我们的需求可能需要把List或者Map等集合的泛型换成我们自定义个class，这也是可以解决的，请看案例




案例五：

泛型的使用




[html] view plain copy 在CODE上查看代码片派生到我的代码片
01.public HashMap<String,Book> booksMap;  
02.  
03.public class Book{  
04.    public int id;  
05.    public String name;  
06.}  







把booksMap转换成字符串和上面的案例是一样的，但是booksMap的Json字符串换成booksMap的实例对象就有点不同了，因为booksMap有自定义的泛型







[html] view plain copy 在CODE上查看代码片派生到我的代码片
01.HashMap<String, Book> booksMap = gson.fromJson(result, new TypeToken<HashMap<String, Book>>() { }.getType());  



