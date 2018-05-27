利用Gson和SharePreference存储结构化数据 


问题的导入

--------------------------------------------------------------------------------

 Android互联网产品通常会有很多的结构化数据需要保存，比如对于登录这个流程，通常会保存诸如username、profile_pic、access_token等等之类的数据，这些数据可以组成一个bean，比如就叫做User:


复制代码
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

复制代码

 

登陆之后我们会需要保存这类数据，以供后续的流程使用。首先我们考虑的可能就是用Android SharePreference来存储这些数据，我们假设你有一个SharePreference的工具类SPUtils，提供了基本的putString和getString的方法（具体这个SPUtil类改怎么写可以参考网上别的帖子），那么你可能会这样子存储username、access_token、profile_pic之类的数据：

SPUtils.putString(mContext, "USERNAME_KEY", username);
SPUtils.putString(mContext, "TOKEN_KEY", access_token);
SPUtils.putString(mContext, "PRO_PIC_URL_KEY", profile_pic);

那么问题来了，如果这个客户端有多人登录过，而你需要保存多个人的信息呢？这个时候改怎么分配？难道SPUtils在putString的时候弄多个KEY?

 

问题思考

--------------------------------------------------------------------------------

考虑到上述三个字段（username、access_token、profile_pic）既然组成一个bean，那么我们可以考虑存储这个bean对象。每次登录成功后即生成一个User Bean对象，然后将它存储下来。这里可能你会想到用数据库来存储这个Bean，但是未免有点大材小用了，是不是有别的方式可以存储对象Bean呢？

Gson是什么？

现在互联网应用通常会请求Rest接口同时接到JSON数据格式的响应，Gson可以用来将JSON数据解析并组装成相应的Bean对象，同时Gson还提供了将Bean对象生成JSON数据的功能。因为JSON数据其实就是一个“格式化”的String字串，联想到SharePreference就是将键值对存储在xml文件中的一种机制，而它里面的“值”也是字串，那么很显然我们可以将Bean对象生成Json字串放到SharePreference中。因为JSON是可以有Array的，即多个“格式”相同的JSON数据可以组成JSON Array数据，那么存储多个同样是User的Bean的问题也解决了。下面列出基本的步骤：
1.每次登录后，将多对username、access_token、profile_pic等数据创建ArrayList<User>对象
2.利用Gson.toJson方法生成ArrayList<User>对象的对应的Json Array字符串数据
3.将字符串数据存储到SharePreference中
4.后续流程中需要使用User对象时，首先从SharePreference获取（get）出Json array字符串数据
5.利用Gson.fromJson解析成ArrayList<User>对象，这样子就可以迭代这个List而使用User了

 

具体的步骤

--------------------------------------------------------------------------------

 

这个假设有三个User对象生成一个ArrayList<User>：


复制代码
User user1 = new User("jack", "123456789", "http://www.hello.com/1.png");
User user2 = new User("tom", "45467956456", "http://www.hello.com/2.png");
User user3 = new User("lily", "65465897faf", "http://www.hello.com/3.png");
ArrayList<User> users = new ArrayList<>();
users.add(user1);
users.add(user2);
users.add(user3);

复制代码

 

利用Gson转化ArrayList<User>成Json Array数据：

Gson gson = new Gson();
String jsonStr = gson.toJson(users);
SPUtils.put(mContext, "USERS_KEY", jsonStr);

 

这里的jsonStr内容是：

[{"access_token":"123456789","profile_pic":"http://www.hello.com/1.png","username":"jack"},{"access_token":"45467956456","profile_pic":"http://www.hello.com/2.png","username":"tom"},{"access_token":"65465897faf","profile_pic":"http://www.hello.com/3.png","username":"lily"}]

 

这个时候看下sharepreference的xml文件里面有啥：


复制代码
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
<string name="USERS_KEY">[{&quot;access_token&quot;:&quot;123456789&quot;,&quot;profile_pic&quot;:&quot;http://www.hello.com/1.png&quot;,&quot;username&quot;:&quot;jack&quot;},{&quot;access_token&quot;:&quot;45467956456&quot;,&quot;profile_pic&quot;:&quot;http://www.hello.com/2.png&quot;,&quot;username&quot;:&quot;tom&quot;},{&quot;access_token&quot;:&quot;65465897faf&quot;,&quot;profile_pic&quot;:&quot;http://www.hello.com/3.png&quot;,&quot;username&quot;:&quot;lily&quot;}]</string>
</map>

复制代码


发现String数据已经存储到了sharepreference中。那么该如何解析出来成bean对象呢？比如后面需要查jack这个人的对应的profile_pic：


复制代码
String str = (String) SPUtils.get(mContext, "USERS_KEY", "");
users = gson.fromJson(str, new TypeToken<List<User>>() {}.getType());
for (User user : users) {
    if (user.getUsername().equals("jack")) {
        L.d(user.getProfile_pic());
    }
}

复制代码

在logcat中可以看到成功的打印出了Jack的profile_pic:



 

如果需要存储的数据比较多，可以将每个Bean对象抽取出一个key（比如username）形成一个key的ArrayList，同时将这个key的ArrayList存储到SharePreferecene，方便后续取出bean对象。
