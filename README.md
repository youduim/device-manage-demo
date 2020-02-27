# 设备管理 Demo

用于对有度用户登录时进行鉴权，随有度登录验证功能的开放，有度为了方便用户更加快捷的使用，特推出此Demo帮助用户快速使用

用户可根据此项目进行二次开发以及与自身其他系统集成

## 获取文件
开发者：请直接clone github项目

用户：请通过github release分支获取最新版本的jar包。

## 部署
>说明：如需修改默认配置文件，请在jar包存放目录新建配置文件`application.properties`并根据完整配置文件进行配置修改
### 创建数据库
在你要连接的mysql数据库中新建数据库，库名为配置文件中`datasource.url`参数值中连接的库名称，默认为`devicemanage`
### jar包
在运行此程序前，请先部署好[JAVA 8版本环境](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

在jar包目录运行cmd，通过命令java -jar [jar名称] 运行项目

### 配置管理后台url
登录有度管理后台，在我的企业-安全管理-设备授权验证设置此项目运行的url

demo项目接口地址: http://ip:port/auth/accept

## 功能说明
### 1. 限定自动授权的规则
当`config.auto-auth`设定为`true`时，会根据规则1.1以及规则1.2进行自动授权
#### 1.1 限定各端设备数（Windows、Linux、Mac、Android、iOS、Pad）
通过设置配置文件字段 `config.device-nums.xx`，限定指定设备端每个用户可以登录的设备数
```
例如：config.device-nums.Windows = 10
```
#### 1.2 限定IP段
通过设置配置文件字段`config.ip-list`，限定指定ip可以访问
```
例如：config.ip-list: 192.168.1.1,192.168.1.2,192.168.1.10-192.168.1.111
```

### 2. 二次验证方式：
**超过限定范围则需通过二次验证进行审核，2.x中的x代表二次验证方式，即`auth-method`中的`value`值**
#### 2.0 通过短信验证码，验证通过方可登录
- 不进行任何操作，直接返回禁止登录
#### 2.1 通过短信验证码，验证通过方可登录
- 没有超过限定设备数
    - 自动批准，返回结果
- 超过限定设备数
    - 返回 ... 有度客户端自动跳转至短信登录界面，进行二次验证

#### 2.2 通过第三方其他验证方式
>说明：使用此验证方式，需要在配置文件先配置验证URL
- 没有超过限定设备数
    - 自动批准，返回结果
- 超过限定设备数
    - 发送请求至指定URL并且等待返回结果，根据返回结果通过或拒绝此次登录请求
- 开发说明
    - 在demo请求配置的URL时会带上参数名account以及account的值，为需要验证的帐号名称
    - 此URL处理完成后返回的值应为`{"code":0,"msg":"ok"}`格式json字符串，`code=0`表示允许登录，其余均为禁止登录

#### 2.3 提交由管理员审批
- 没有超过限定设备数
    - 自动批准，数据库插入通过状态的审批记录，返回结果
- 超过限定设备数，则获取帐号上一次审批情况
    - 没有记录，插入等待状态的审批记录，并且删除登录时间最早的设备审批记录，返回结果
    - 有记录，返回数据库审批结果

### 3. 记录登录流水
包含部分用户信息、设备名称、类型、iP地址、申请登录时间。运行后，可通过访问链接 `http://ip:port/record/list` 查看。

## 完整配置文件
```
server.port = 8001
logging.level.root = info

spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
# 数据库用户名
spring.datasource.username = root
# 数据库密码
spring.datasource.password = root
# 数据库连接字符串，此连接未配置或配置错误，程序无法启动
spring.datasource.url = jdbc:mysql://127.0.0.1:3306/devicemanage?characterEncoding=utf-8&serverTimezone=Asia/Shanghai

spring.jpa.properties.hibernate.hbm2ddl.auto = update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.properties.hibernate.format_sql = true
spring.jpa.properties.show-sql = false

# json日期返回格式
spring.jackson.date-format = yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone = GMT+8

# 服务功能配置项
# 授权方式[0:拒绝登录 1:短信验证码登录 2:第三方验证登录 3:管理员审批]
config.auth-method = 0
# 第三方授权URL地址
config.third-auth-url = https://youdu.im
# 是否自动授权
config.auto-auth = true
# 自动授权 ip过滤规则
config.ip-list = 192.168.31.1-192.168.31.255,10.0.0.1-10.0.0.255
# 自动授权 各平台自动授权设备数
config.device-nums.Windows = 10
config.device-nums.iOS = 10
config.device-nums.Pad = 10
config.device-nums.Android = 10
config.device-nums.Linux = 10
```
