# 该镜像需要依赖的基础镜像
FROM java:8
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/wxapp-web-0.0.1-SNAPSHOT.jar /app.jar
# 声明服务运行端口
EXPOSE 8888
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-Xloggc:/var/logs/gc.log", "-jar","/app.jar"]
# 指定维护者的名字
MAINTAINER zhaomingyu