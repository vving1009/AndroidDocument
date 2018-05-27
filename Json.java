����һ��ʲô��JSON��
����JSON��һ��ȡ��XML�����ݽṹ,��xml���,����С�ɵ���������ȴ����,��������С���������紫�����ݽ����ٸ��������Ӷ��ӿ��ٶȡ�
����JSON����һ���ַ��� ֻ����Ԫ�ػ�ʹ���ض��ķ��ű�ע��

����{} ˫���ű�ʾ����

����[] �����ű�ʾ����

����"" ˫�����������Ի�ֵ

����: ð�ű�ʾ������ǰ�ߵ�ֵ(���ֵ�������ַ��������֡�Ҳ��������һ����������)

�������� {"name": "Michael"} �������Ϊ��һ������nameΪMichael�Ķ���

������[{"name": "Michael"},{"name": "Jerry"}]�ͱ�ʾ�����������������

������Ȼ��,��Ҳ����ʹ��{"name":["Michael","Jerry"]}��������һ��,����һ��ӵ��һ��name����Ķ���


��������JSON����֮��ͳ��JSON����
����1������JSOn�ַ���
����public static String createJsonString(String key, Object value) {
����JSONObject jsonObject = new JSONObject();
����jsonObject.put(key, value);
����return jsonObject.toString();
����}

����2������JSON�ַ���
������Ϊ�������������һ��JavaBean��һ��List���飬һ��Ƕ��Map��List���飺
����import java.util.ArrayList;
����import java.util.HashMap;
����import java.util.Iterator;
����import java.util.List;
����import java.util.Map;

����import org.json.JSONArray;
����import org.json.JSONObject;

����import com.android.myjson.domain.Person;

����/**
����* ��ɶ�json���ݵĽ���
����*
����*/
����public class JsonTools {

����public static Person getPerson(String key, String jsonString) {
����Person person = new Person();
����try {
����JSONObject jsonObject = new JSONObject(jsonString);
����JSONObject personObject = jsonObject.getJSONObject("person");
����person.setId(personObject.getInt("id"));
����person.setName(personObject.getString("name"));
����person.setAddress(personObject.getString("address"));
����} catch (Exception e) {
����// TODO: handle exception
����}
����return person;
����}

����public static List getPersons(String key, String jsonString) {
����List list = new ArrayList();
����try {
����JSONObject jsonObject = new JSONObject(jsonString);
����// ����json������
����JSONArray jsonArray = jsonObject.getJSONArray(key);
����for (int i = 0; i < jsonArray.length(); i++) {
����JSONObject jsonObject2 = jsonArray.getJSONObject(i);
����Person person = new Person();
����person.setId(jsonObject2.getInt("id"));
����person.setName(jsonObject2.getString("name"));
����person.setAddress(jsonObject2.getString("address"));
����list.add(person);
����}
����} catch (Exception e) {
����// TODO: handle exception
����}
����return list;
����}

����public static List getList(String key, String jsonString) {
����List list = new ArrayList();
����try {
����JSONObject jsonObject = new JSONObject(jsonString);
����JSONArray jsonArray = jsonObject.getJSONArray(key);
����for (int i = 0; i < jsonArray.length(); i++) {
����String msg = jsonArray.getString(i);
����list.add(msg);
����}
����} catch (Exception e) {
����// TODO: handle exception
����}
����return list;
����}

����public static List> listKeyMaps(String key,
����String jsonString) {
����List> list = new ArrayList>();
����try {
����JSONObject jsonObject = new JSONObject(jsonString);
����JSONArray jsonArray = jsonObject.getJSONArray(key);
����for (int i = 0; i < jsonArray.length(); i++) {
����JSONObject jsonObject2 = jsonArray.getJSONObject(i);
����Map map = new HashMap();
����Iterator iterator = jsonObject2.keys();
����while (iterator.hasNext()) {
����String json_key = iterator.next();
����Object json_value = jsonObject2.get(json_key);
����if (json_value == null) {
����json_value = "";
����}
����map.put(json_key, json_value);
����}
����list.add(map);
����}
����} catch (Exception e) {
����// TODO: handle exception
����}
����return list;
����}
����},>,>


����JSON����֮GSON
����1������JSON�ַ���
����import com.google.gson.Gson;

����public class JsonUtils {

����public static String createJsonObject(Object obj) {
����Gson gson = new Gson();
����String str = gson.toJson(obj);
����return str;

����}
����}


    2������JSON
����import java.util.ArrayList;
����import java.util.List;
����import java.util.Map;

����import com.google.gson.Gson;
����import com.google.gson.reflect.TypeToken;

����;
����public class GsonTools {

����public GsonTools() {
����// TODO Auto-generated constructor stub
����}

����/**
����* @param
����* @param jsonString
����* @param cls
����* @return
����*/
����public static T getPerson(String jsonString, Class cls) {
����T t = null;
����try {
����Gson gson = new Gson();
����t = gson.fromJson(jsonString, cls);
����} catch (Exception e) {
����// TODO: handle exception
����}
����return t;
����}

����/**
����* ʹ��Gson���н��� List
����*
����* @param
����* @param jsonString
����* @param cls
����* @return
����*/
����public static List getPersons(String jsonString, Class cls) {
����List list = new ArrayList();
����try {
����Gson gson = new Gson();
����list = gson.fromJson(jsonString, new TypeToken>() {
����}.getType());
����} catch (Exception e) {
����}
����return list;
����}

����/**
����* @param jsonString
����* @return
����*/
����public static List getList(String jsonString) {
����List list = new ArrayList();
����try {
����Gson gson = new Gson();
����list = gson.fromJson(jsonString, new TypeToken>() {
����}.getType());
����} catch (Exception e) {
����// TODO: handle exception
����}
����return list;
����}

����public static List> listKeyMaps(String jsonString) {
����List> list = new ArrayList>();
����try {
����Gson gson = new Gson();
����list = gson.fromJson(jsonString,
����new TypeToken>>() {
����}.getType());
����} catch (Exception e) {
����// TODO: handle exception
����}
����return list;
����}
����}

����JSON����֮FastJSON
����import java.util.ArrayList;
����import java.util.List;
����import java.util.Map;

����import com.alibaba.fastjson.JSON;
����import com.alibaba.fastjson.TypeReference;

����public class JsonTool {

����public static T getPerson(String jsonstring, Class cls) {
����T t = null;
����try {
����t = JSON.parseObject(jsonstring, cls);
����} catch (Exception e) {
����// TODO: handle exception
����}
����return t;
����}

����public static List getPersonList(String jsonstring, Class cls) {
����List list = new ArrayList();
����try {
����list = JSON.parseArray(jsonstring, cls);
����} catch (Exception e) {
����// TODO: handle exception
����}
����return list;
����}

����public static List> getPersonListMap1(
����String jsonstring) {
����List> list = new ArrayList>();
����try {
����list = JSON.parseObject(jsonstring,
����new TypeReference>>() {
����}.getType());

����} catch (Exception e) {
����// TODO: handle exception
����}

����return list;
����}
����}

�����ܽ᣺
����JSON�����ƶ��豸��˵������������绷���ϲ���������Ƶ�����£������XML��ʽ�����ݴ�������ʡ����������Ч�ʸ��ߡ��������ֽ�����ʽ��FastJson��Ч����ߵģ��Ƽ�ʹ�á�