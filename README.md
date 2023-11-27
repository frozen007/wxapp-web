# wxapp-web
微信平台后端服务

## 部署
- [.drone.yml](.drone.yml): DroneCD部署脚本
- [Dockerfile](Dockerfile): Docker镜像构建脚本
- [wxapp-web-deploy.yaml](wxapp-web-deploy.yaml): k8s部署脚本

## 基础服务

### 数据源管理
位于com.myz.inf.datasource包下，可以用注解控制所有dao包下datasource的来源，实现数据源名与业务代码的直接绑定，且与具体的持久层框架完全解耦。

* com.myz.inf.datasource.DataSource: 数据源注解，以数据源名称作为参数，可以标记该dao使用的是具体哪个数据源
* com.myz.inf.datasource.MultiDataSource: 动态设置线程上下文的数据源，用ThreadLocal实现
* com.myz.inf.datasource.MultiDataSourceAspect: 在所有dao包下面的类建立切面，根据DataSource注解的数据源切换本次执行的目标数据源

### Ticket服务
位于com.myz.wxapp.ticket，轻量级的分布式id序列生成服务，基于mysql，支持多主题，支持轻量级并发

* com.myz.wxapp.ticket.service.TicketService: 获取ticket的服务

### 批处理任务管理
位于com.myz.inf.batch，封装了用于开发job任务的组件

* com.myz.inf.batch.BatchJobBeanPostProcessor: 用于初始化job组件，进行job注册，并封装调用底层调度组件
* com.myz.inf.batch.quartz.QuartzSchedulerSupport: 使用quartz调度组件的实现

```java
    @Bean
    public BatchJobBeanPostProcessor batchJobBeanPostProcessor() {
        return new BatchJobBeanPostProcessor();
    }

    @Bean("batchScheduler")
    public QuartzSchedulerSupport quartzScheduleManager(@Qualifier("quartzScheduler") Scheduler quartzScheduler) {
        return new QuartzSchedulerSupport(quartzScheduler);
    }

```
