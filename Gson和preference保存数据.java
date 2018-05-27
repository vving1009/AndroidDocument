����Gson��SharePreference�洢�ṹ������ 


����ĵ���

--------------------------------------------------------------------------------

 Android��������Ʒͨ�����кܶ�Ľṹ��������Ҫ���棬������ڵ�¼������̣�ͨ���ᱣ������username��profile_pic��access_token�ȵ�֮������ݣ���Щ���ݿ������һ��bean������ͽ���User:


���ƴ���
public class User {
    
    String username;
    String access_token;
    String profile_pic;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}

���ƴ���

 

��½֮�����ǻ���Ҫ�����������ݣ��Թ�����������ʹ�á��������ǿ��ǵĿ��ܾ�����Android SharePreference���洢��Щ���ݣ����Ǽ�������һ��SharePreference�Ĺ�����SPUtils���ṩ�˻�����putString��getString�ķ������������SPUtil�����ôд���Բο����ϱ�����ӣ�����ô����ܻ������Ӵ洢username��access_token��profile_pic֮������ݣ�

SPUtils.putString(mContext, "USERNAME_KEY", username);
SPUtils.putString(mContext, "TOKEN_KEY", access_token);
SPUtils.putString(mContext, "PRO_PIC_URL_KEY", profile_pic);

��ô�������ˣ��������ͻ����ж��˵�¼����������Ҫ�������˵���Ϣ�أ����ʱ�����ô���䣿�ѵ�SPUtils��putString��ʱ��Ū���KEY?

 

����˼��

--------------------------------------------------------------------------------

���ǵ����������ֶΣ�username��access_token��profile_pic����Ȼ���һ��bean����ô���ǿ��Կ��Ǵ洢���bean����ÿ�ε�¼�ɹ�������һ��User Bean����Ȼ�����洢�����������������뵽�����ݿ����洢���Bean������δ���е���С���ˣ��ǲ����б�ķ�ʽ���Դ洢����Bean�أ�

Gson��ʲô��

���ڻ�����Ӧ��ͨ��������Rest�ӿ�ͬʱ�ӵ�JSON���ݸ�ʽ����Ӧ��Gson����������JSON���ݽ�������װ����Ӧ��Bean����ͬʱGson���ṩ�˽�Bean��������JSON���ݵĹ��ܡ���ΪJSON������ʵ����һ������ʽ������String�ִ������뵽SharePreference���ǽ���ֵ�Դ洢��xml�ļ��е�һ�ֻ��ƣ���������ġ�ֵ��Ҳ���ִ�����ô����Ȼ���ǿ��Խ�Bean��������Json�ִ��ŵ�SharePreference�С���ΪJSON�ǿ�����Array�ģ����������ʽ����ͬ��JSON���ݿ������JSON Array���ݣ���ô�洢���ͬ����User��Bean������Ҳ����ˡ������г������Ĳ��裺
1.ÿ�ε�¼�󣬽����username��access_token��profile_pic�����ݴ���ArrayList<User>����
2.����Gson.toJson��������ArrayList<User>����Ķ�Ӧ��Json Array�ַ�������
3.���ַ������ݴ洢��SharePreference��
4.������������Ҫʹ��User����ʱ�����ȴ�SharePreference��ȡ��get����Json array�ַ�������
5.����Gson.fromJson������ArrayList<User>���������ӾͿ��Ե������List��ʹ��User��

 

����Ĳ���

--------------------------------------------------------------------------------

 

�������������User��������һ��ArrayList<User>��


���ƴ���
User user1 = new User("jack", "123456789", "http://www.hello.com/1.png");
User user2 = new User("tom", "45467956456", "http://www.hello.com/2.png");
User user3 = new User("lily", "65465897faf", "http://www.hello.com/3.png");
ArrayList<User> users = new ArrayList<>();
users.add(user1);
users.add(user2);
users.add(user3);

���ƴ���

 

����Gsonת��ArrayList<User>��Json Array���ݣ�

Gson gson = new Gson();
String jsonStr = gson.toJson(users);
SPUtils.put(mContext, "USERS_KEY", jsonStr);

 

�����jsonStr�����ǣ�

[{"access_token":"123456789","profile_pic":"http://www.hello.com/1.png","username":"jack"},{"access_token":"45467956456","profile_pic":"http://www.hello.com/2.png","username":"tom"},{"access_token":"65465897faf","profile_pic":"http://www.hello.com/3.png","username":"lily"}]

 

���ʱ����sharepreference��xml�ļ�������ɶ��


���ƴ���
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
<string name="USERS_KEY">[{&quot;access_token&quot;:&quot;123456789&quot;,&quot;profile_pic&quot;:&quot;http://www.hello.com/1.png&quot;,&quot;username&quot;:&quot;jack&quot;},{&quot;access_token&quot;:&quot;45467956456&quot;,&quot;profile_pic&quot;:&quot;http://www.hello.com/2.png&quot;,&quot;username&quot;:&quot;tom&quot;},{&quot;access_token&quot;:&quot;65465897faf&quot;,&quot;profile_pic&quot;:&quot;http://www.hello.com/3.png&quot;,&quot;username&quot;:&quot;lily&quot;}]</string>
</map>

���ƴ���


����String�����Ѿ��洢����sharepreference�С���ô����ν���������bean�����أ����������Ҫ��jack����˵Ķ�Ӧ��profile_pic��


���ƴ���
String str = (String) SPUtils.get(mContext, "USERS_KEY", "");
users = gson.fromJson(str, new TypeToken<List<User>>() {}.getType());
for (User user : users) {
    if (user.getUsername().equals("jack")) {
        L.d(user.getProfile_pic());
    }
}

���ƴ���

��logcat�п��Կ����ɹ��Ĵ�ӡ����Jack��profile_pic:



 

�����Ҫ�洢�����ݱȽ϶࣬���Խ�ÿ��Bean�����ȡ��һ��key������username���γ�һ��key��ArrayList��ͬʱ�����key��ArrayList�洢��SharePreferecene���������ȡ��bean����
