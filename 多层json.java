Android利用Gson解析嵌套多层的Json 



首先先讲一个比较简单点的例子(最简单的我就不讲啦，网上很多)，帮助新手理解Gson的使用方法：
                 比如我们要解析一个下面这种的Json：
                 String json = {"a":"100","b":[{"b1":"b_value1","b2":"b_value2"},{"b1":"b_value1","b2":"b_value2"}]，"c":{"c1":"c_value1","c2":"c_value2"}}
                首先我们需要定义一个序列化的Bean，这里采用内部类的形式，看起来会比较清晰一些：
                public class JsonBean {
                         public String a;
                         public List<B> b;
                         public C c;

                         public static class B {
                                  public String b1;
                                  public String b2;
                        }
    
                        public static class C {
                                 public String c1;
                                 public String c2;
                       }
              }
             很多时候大家都是不知道这个Bean是该怎么定义，这里面需要注意几点：
             1、内部嵌套的类必须是static的，要不然解析会出错；
             2、类里面的属性名必须跟Json字段里面的Key是一模一样的；
             3、内部嵌套的用[]括起来的部分是一个List，所以定义为 public List<B> b，而只用{}嵌套的就定义为 public C c，
                  具体的大家对照Json字符串看看就明白了，不明白的我们可以互相交流，本人也是开发新手！
              Gson gson = new Gson();
              java.lang.reflect.Type type = new TypeToken<JsonBean>() {}.getType();
              JsonBean jsonBean = gson.fromJson(json, type);
              然后想拿数据就很简单啦，直接在jsonBean里面取就可以了！
       如果需要解析的Json嵌套了很多层，同样可以可以定义一个嵌套很多层内部类的Bean，需要细心的对照Json字段来定义哦。



=======================================================================================================


java如何通过json读取嵌套的json对象   .


标签： jsonjavastringurlnull  

2012-03-27 14:43 7415人阅读 评论(0) 收藏 举报 
.

  

 分类： 

程序开发（65）   


 . 

版权声明：本文为博主原创文章，未经博主允许不得转载。

java如何通过json读取嵌套的json对象

对象数据内容如下:


{
    "str1": 11,
     "strs": [
         {
             "strs11": 111,
            "strs12": 122,
            "strs13": 133
         {
             "strs21": 211,
            "strs22": 222,
            "strs23": 233
         }
     ],
     "str2": 22,
    "str3": 33,
    "str4":44

}



关键Java代码:
     URL url = new URL("http://localhost/test.jspx");
     URLConnection urlconn = url.openConnection();
     BufferedReader reader = new BufferedReader(new InputStreamReader(urlconn.getInputStream(),"utf-8"));
     String repStr = null;
     if ((repStr = reader.readLine()) != null) {
         JSONObject jObj = JSONObject.fromObject(repStr);
         JSONArray jary=jObj.getJSONArray("strs");
         for (int i=0;i<jary.size();i++) {
             JSONObject obj = jary.getJSONObject(i);
             String s2=obj.getString("strs11");
         }

    }
