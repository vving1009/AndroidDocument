　　一、什么是JSON？
　　JSON是一种取代XML的数据结构,和xml相比,它更小巧但描述能力却不差,由于它的小巧所以网络传输数据将减少更多流量从而加快速度。
　　JSON就是一串字符串 只不过元素会使用特定的符号标注。

　　{} 双括号表示对象

　　[] 中括号表示数组

　　"" 双引号内是属性或值

　　: 冒号表示后者是前者的值(这个值可以是字符串、数字、也可以是另一个数组或对象)

　　所以 {"name": "Michael"} 可以理解为是一个包含name为Michael的对象

　　而[{"name": "Michael"},{"name": "Jerry"}]就表示包含两个对象的数组

　　当然了,你也可以使用{"name":["Michael","Jerry"]}来简化上面一部,这是一个拥有一个name数组的对象


　　二、JSON解析之传统的JSON解析
　　1、生成JSOn字符串
　　public static String createJsonString(String key, Object value) {
　　JSONObject jsonObject = new JSONObject();
　　jsonObject.put(key, value);
　　return jsonObject.toString();
　　}

　　2、解析JSON字符串
　　分为以下三种情况，一个JavaBean，一个List数组，一个嵌套Map的List数组：
　　import java.util.ArrayList;
　　import java.util.HashMap;
　　import java.util.Iterator;
　　import java.util.List;
　　import java.util.Map;

　　import org.json.JSONArray;
　　import org.json.JSONObject;

　　import com.android.myjson.domain.Person;

　　/**
　　* 完成对json数据的解析
　　*
　　*/
　　public class JsonTools {

　　public static Person getPerson(String key, String jsonString) {
　　Person person = new Person();
　　try {
　　JSONObject jsonObject = new JSONObject(jsonString);
　　JSONObject personObject = jsonObject.getJSONObject("person");
　　person.setId(personObject.getInt("id"));
　　person.setName(personObject.getString("name"));
　　person.setAddress(personObject.getString("address"));
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return person;
　　}

　　public static List getPersons(String key, String jsonString) {
　　List list = new ArrayList();
　　try {
　　JSONObject jsonObject = new JSONObject(jsonString);
　　// 返回json的数组
　　JSONArray jsonArray = jsonObject.getJSONArray(key);
　　for (int i = 0; i < jsonArray.length(); i++) {
　　JSONObject jsonObject2 = jsonArray.getJSONObject(i);
　　Person person = new Person();
　　person.setId(jsonObject2.getInt("id"));
　　person.setName(jsonObject2.getString("name"));
　　person.setAddress(jsonObject2.getString("address"));
　　list.add(person);
　　}
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return list;
　　}

　　public static List getList(String key, String jsonString) {
　　List list = new ArrayList();
　　try {
　　JSONObject jsonObject = new JSONObject(jsonString);
　　JSONArray jsonArray = jsonObject.getJSONArray(key);
　　for (int i = 0; i < jsonArray.length(); i++) {
　　String msg = jsonArray.getString(i);
　　list.add(msg);
　　}
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return list;
　　}

　　public static List> listKeyMaps(String key,
　　String jsonString) {
　　List> list = new ArrayList>();
　　try {
　　JSONObject jsonObject = new JSONObject(jsonString);
　　JSONArray jsonArray = jsonObject.getJSONArray(key);
　　for (int i = 0; i < jsonArray.length(); i++) {
　　JSONObject jsonObject2 = jsonArray.getJSONObject(i);
　　Map map = new HashMap();
　　Iterator iterator = jsonObject2.keys();
　　while (iterator.hasNext()) {
　　String json_key = iterator.next();
　　Object json_value = jsonObject2.get(json_key);
　　if (json_value == null) {
　　json_value = "";
　　}
　　map.put(json_key, json_value);
　　}
　　list.add(map);
　　}
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return list;
　　}
　　},>,>


三、JSON解析之GSON
　　1、生成JSON字符串
　　import com.google.gson.Gson;

　　public class JsonUtils {

　　public static String createJsonObject(Object obj) {
　　Gson gson = new Gson();
　　String str = gson.toJson(obj);
　　return str;

　　}
　　}


    2、解析JSON
　　import java.util.ArrayList;
　　import java.util.List;
　　import java.util.Map;

　　import com.google.gson.Gson;
　　import com.google.gson.reflect.TypeToken;

　　;
　　public class GsonTools {

　　public GsonTools() {
　　// TODO Auto-generated constructor stub
　　}

　　/**
　　* @param
　　* @param jsonString
　　* @param cls
　　* @return
　　*/
　　public static T getPerson(String jsonString, Class cls) {
　　T t = null;
　　try {
　　Gson gson = new Gson();
　　t = gson.fromJson(jsonString, cls);
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return t;
　　}

　　/**
　　* 使用Gson进行解析 List
　　*
　　* @param
　　* @param jsonString
　　* @param cls
　　* @return
　　*/
　　public static List getPersons(String jsonString, Class cls) {
　　List list = new ArrayList();
　　try {
　　Gson gson = new Gson();
　　list = gson.fromJson(jsonString, new TypeToken>() {
　　}.getType());
　　} catch (Exception e) {
　　}
　　return list;
　　}

　　/**
　　* @param jsonString
　　* @return
　　*/
　　public static List getList(String jsonString) {
　　List list = new ArrayList();
　　try {
　　Gson gson = new Gson();
　　list = gson.fromJson(jsonString, new TypeToken>() {
　　}.getType());
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return list;
　　}

　　public static List> listKeyMaps(String jsonString) {
　　List> list = new ArrayList>();
　　try {
　　Gson gson = new Gson();
　　list = gson.fromJson(jsonString,
　　new TypeToken>>() {
　　}.getType());
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return list;
　　}
　　}

三、JSON解析之FastJSON
　　import java.util.ArrayList;
　　import java.util.List;
　　import java.util.Map;

　　import com.alibaba.fastjson.JSON;
　　import com.alibaba.fastjson.TypeReference;

　　public class JsonTool {

　　public static T getPerson(String jsonstring, Class cls) {
　　T t = null;
　　try {
　　t = JSON.parseObject(jsonstring, cls);
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return t;
　　}

　　public static List getPersonList(String jsonstring, Class cls) {
　　List list = new ArrayList();
　　try {
　　list = JSON.parseArray(jsonstring, cls);
　　} catch (Exception e) {
　　// TODO: handle exception
　　}
　　return list;
　　}

　　public static List> getPersonListMap1(
　　String jsonstring) {
　　List> list = new ArrayList>();
　　try {
　　list = JSON.parseObject(jsonstring,
　　new TypeReference>>() {
　　}.getType());

　　} catch (Exception e) {
　　// TODO: handle exception
　　}

　　return list;
　　}
　　}

　　总结：
　　JSON对于移动设备来说，尤其对于网络环境较差和流量限制的情况下，相对于XML格式的数据传输会更节省流量，传输效率更高。在这三种解析方式中FastJson是效率最高的，推荐使用。