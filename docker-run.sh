#!/usr/bin/env bash
app_name=$1
image=$2
instance_id=$3
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
docker run --name ${app_instance_name} \
--dns=10.0.16.10 \
-e 'spring.profiles.active'=${profile_active} \
-e 'app.instance.name'=${app_instance_name} \
-e TZ="Asia/Shanghai" \
-v /etc/localtime:/etc/localtime \
-v /appdata/app/${app_instance_name}/logs:/var/logs \
-d ${image}
echo '----start container----'