#!/usr/bin/env bash
# 定义应用组名
group_name=$1
# 定义应用名称
app_name=$2
# 定义应用版本
app_version=$3
instance_id=$4
if [ -z "$instance_id" ]; then
  app_instance_name=${app_name}_01
else
  app_instance_name=${app_name}_${instance_id}
fi
# 定义应用环境
profile_active='dev'
echo '----copy jar----'
docker stop ${app_instance_name}
echo '----stop container----'
docker rm ${app_instance_name}
echo '----rm container----'
docker rmi ${group_name}/${app_name}:${app_version}
echo '----rm image----'
docker run -p 9090:9090 --name ${app_instance_name} \
-e 'spring.profiles.active'=${profile_active} \
-e TZ="Asia/Shanghai" \
-v /etc/localtime:/etc/localtime \
-v /appdata/app/${app_instance_name}/logs:/var/logs \
-d ${group_name}/${app_name}:${app_version}
echo '----start container----'