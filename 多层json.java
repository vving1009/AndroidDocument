Android����Gson����Ƕ�׶���Json 



�����Ƚ�һ���Ƚϼ򵥵������(��򵥵��ҾͲ����������Ϻܶ�)�������������Gson��ʹ�÷�����
                 ��������Ҫ����һ���������ֵ�Json��
                 String json = {"a":"100","b":[{"b1":"b_value1","b2":"b_value2"},{"b1":"b_value1","b2":"b_value2"}]��"c":{"c1":"c_value1","c2":"c_value2"}}
                ����������Ҫ����һ�����л���Bean����������ڲ������ʽ����������Ƚ�����һЩ��
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
             �ܶ�ʱ���Ҷ��ǲ�֪�����Bean�Ǹ���ô���壬��������Ҫע�⼸�㣺
             1���ڲ�Ƕ�׵��������static�ģ�Ҫ��Ȼ���������
             2��������������������Json�ֶ������Key��һģһ���ģ�
             3���ڲ�Ƕ�׵���[]�������Ĳ�����һ��List�����Զ���Ϊ public List<B> b����ֻ��{}Ƕ�׵ľͶ���Ϊ public C c��
                  ����Ĵ�Ҷ���Json�ַ��������������ˣ������׵����ǿ��Ի��ཻ��������Ҳ�ǿ������֣�
              Gson gson = new Gson();
              java.lang.reflect.Type type = new TypeToken<JsonBean>() {}.getType();
              JsonBean jsonBean = gson.fromJson(json, type);
              Ȼ���������ݾͺܼ�����ֱ����jsonBean����ȡ�Ϳ����ˣ�
       �����Ҫ������JsonǶ���˺ܶ�㣬ͬ�����Կ��Զ���һ��Ƕ�׺ܶ���ڲ����Bean����Ҫϸ�ĵĶ���Json�ֶ�������Ŷ��



=======================================================================================================


java���ͨ��json��ȡǶ�׵�json����   .


��ǩ�� jsonjavastringurlnull  

2012-03-27 14:43 7415���Ķ� ����(0) �ղ� �ٱ� 
.

  

 ���ࣺ 

���򿪷���65��   


 . 

��Ȩ����������Ϊ����ԭ�����£�δ������������ת�ء�

java���ͨ��json��ȡǶ�׵�json����

����������������:


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



�ؼ�Java����:
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
