# spring-boot-demo
spring-boot-demo

1、多环境切换
application.properties 是 springboot 在运行中所需要的配置信息。

当我们在开发阶段，使用自己的机器开发，测试的时候需要用的测试服务器测试，上线时使用正式环境的服务器。

这三种环境需要的配置信息都不一样，当我们切换环境运行项目时，需要手动的修改多出配置信息，非常容易出错。

为了解决上述问题，springboot 提供多环境配置的机制，让开发者非常容易的根据需求而切换不同的配置环境。

2、热部署
当我们修改文件和创建文件时，都需要重新启动项目。这样频繁的操作很浪费时间，配置热部署可以让项目自动加载变化的文件，省去的手动操作。

在 pom.xml 文件中添加如下配置：

<!-- 热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
    <scope>true</scope>
</dependency>

3、为什么要自定义静态资源映射？
SpringBoot默认将静态资源映射到resources目录下的public、resources、static等目录。 
如果是web静态资源例如html、css、js、网站插图等静态资源，是可以放在这些目录下的。

然而，对于用户上传来的文件，放在resources目录下是有问题的。如果程序是以jar包形式运行的话，总不能每次发布打包都要把用户上传的文件也一并打进jar吧？这显然不是一个好方法。

那能不能将一些动态维护的文件，放在服务器磁盘的某个目录下（项目目录之外），并且通过SpringBoot服务进行访问呢？答案是可以的。

http://blog.csdn.net/xichenguan/article/details/52794862

4、Spring boot中使用aop详解

https://www.cnblogs.com/bigben0123/p/7779357.html

5、投产上线

一般分为两种；一种是打包成jar包直接执行，另一种是打包成war包放到tomcat服务器下。

打成jar包
如果你使用的是maven来管理项目，执行以下命令既可以

cd 项目跟目录（和pom.xml同级）
mvn clean package
## 或者执行下面的命令
## 排除测试代码后进行打包
mvn clean package  -Dmaven.test.skip=true
打包完成后jar包会生成到target目录下，命名一般是 项目名+版本号.jar

启动jar包命令

java -jar  target/spring-boot-scheduler-1.0.0.jar
这种方式，只要控制台关闭，服务就不能访问了。下面我们使用在后台运行的方式来启动:

nohup java -jar target/spring-boot-scheduler-1.0.0.jar &
也可以在启动的时候选择读取不同的配置文件

java -jar app.jar --spring.profiles.active=dev
也可以在启动的时候设置jvm参数

java -Xms10m -Xmx80m -jar app.jar &

打成war包
打成war包一般可以分两种方式来实现，第一种可以通过eclipse这种开发工具来导出war包，另外一种是使用命令来完成，这里主要介绍后一种

1、maven项目，修改pom包

将

<packaging>jar</packaging>  
改为

<packaging>war</packaging>
2、打包时排除tomcat.

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-tomcat</artifactId>
	<scope>provided</scope>
</dependency>
在这里将scope属性设置为provided，这样在最终形成的WAR中不会包含这个JAR包，因为Tomcat或Jetty等服务器在运行时将会提供相关的API类。

3、注册启动类

创建ServletInitializer.java，继承SpringBootServletInitializer ，覆盖configure()，把启动类Application注册进去。外部web应用服务器构建Web Application Context的时候，会把启动类添加进去。

public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
最后执行

mvn clean package  -Dmaven.test.skip=true
会在target目录下生成：项目名+版本号.war文件，拷贝到tomcat服务器中启动即可。


生产运维
查看JVM参数的值
可以根据java自带的jinfo命令：

jinfo -flags pid
来查看jar 启动后使用的是什么gc、新生代、老年代分批的内存都是多少，示例如下：

-XX:CICompilerCount=3 -XX:InitialHeapSize=234881024 -XX:MaxHeapSize=3743416320 -XX:MaxNewSize=1247805440 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=78118912 -XX:OldSize=156762112 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps -XX:+UseParallelGC
-XX:CICompilerCount ：最大的并行编译数
-XX:InitialHeapSize 和 -XX:MaxHeapSize ：指定JVM的初始和最大堆内存大小
-XX:MaxNewSize ： JVM堆区域新生代内存的最大可分配大小
…
-XX:+UseParallelGC ：垃圾回收使用Parallel收集器
如何重启
简单粗暴

直接kill掉进程再次启动jar包

ps -ef|grep java 
##拿到对于Java程序的pid
kill -9 pid
## 再次重启
Java -jar  xxxx.jar
当然这种方式比较传统和暴力，所以建议大家使用下面的方式来管理

脚本执行

如果使用的是maven,需要包含以下的配置

<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
如果使用是gradle，需要包含下面配置

springBoot {
    executable = true
}
启动方式：

1、 可以直接./yourapp.jar 来启动

2、注册为服务

也可以做一个软链接指向你的jar包并加入到init.d中，然后用命令来启动。

init.d 例子:

ln -s /var/yourapp/yourapp.jar /etc/init.d/yourapp
chmod +x /etc/init.d/yourapp
这样就可以使用stop或者是restart命令去管理你的应用。

/etc/init.d/yourapp start|stop|restart
或者

service yourapp start|stop|restart
到此 springboot项目如何测试、联调和打包投产均已经介绍完，以后可以找时间研究一下springboot的自动化运维，以及spring boot 和docker相结合的使用。
