# 该镜像需要依赖的基础镜像
FROM registry.cn-hangzhou.aliyuncs.com/zmy-repo/openjdk-jre:openjdk-17-jre

# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/wxapp-web-0.0.1-SNAPSHOT.jar /wxapp-web.jar

# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-Xms128m", "-Xmx200m", "-Xloggc:/var/gc.log", "-jar","/wxapp-web.jar"]
# 指定维护者的名字
MAINTAINER zhaomingyu
