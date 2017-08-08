# 学校教务系统
这是一个开源的教务系统，为了方便演示效果，数据全部采用静态的html展示，大家可根据自身学校的教务系统进行针对性修改。
# Demo

<img src="https://github.com/EoniJJ/Gdgm/blob/jw_demo/photo1.jpg" width = "300" height = "512"/><img src="https://github.com/EoniJJ/Gdgm/blob/jw_demo/photo2.jpg" width = "300" height = "512"/><img src="https://github.com/EoniJJ/Gdgm/blob/jw_demo/photo3.jpg" width = "300" height = "512"/><img src="https://github.com/EoniJJ/Gdgm/blob/jw_demo/photo4.jpg" width = "300" height = "512"/><img src="https://github.com/EoniJJ/Gdgm/blob/jw_demo/photo5.jpg" width = "300" height = "512"/><img src="https://github.com/EoniJJ/Gdgm/blob/jw_demo/photo6.jpg" width = "300" height = "512"/>

# 实现原理
实现原理请参考我的这两篇博客
[手把手带你打造一个教务系统客户端](http://www.jianshu.com/p/b561b91edad8)
[Android利用Jsoup抓取数据，再也不怕写App没有数据啦](http://www.jianshu.com/p/b7ee086e6eae)

# 如何快速替换为自己学校的教务系统
如果你的学校教务系统也是采用**正方教务系统**，那么比较简单，步骤如下：
1. 首先，将[com/example/arron/gdgm/utils/CommonUtils.java](https://github.com/EoniJJ/Gdgm/blob/jw_demo/app/src/main/java/com/example/arron/gdgm/utils/CommonUtils.java)中的`isDemo`改为false
```
public class CommonUtils {
    public static boolean isDemo = true; // 改为false
    ....
}
```
2. 将[/app/src/main/res/values/api.xml](https://github.com/EoniJJ/Gdgm/blob/jw_demo/app/src/main/res/values/api.xml)下的url替换为你学校对应的url

3. 运行App，看哪里解析有问题，针对你学校教务系统的html代码，根据css样式等差异进行微调。
