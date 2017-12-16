# Alibaba-OSS-Statistic
Alibaba-OSS-Statistic 是一款用于统计阿里云OSS使用费用的工具

### 软件说明
- 软件名称：阿里巴巴OSS统计工具
- 版本号：1.0.0
- 开发者：www.minsx.com
- 语言：Java
- 功能：统计指定时间段指定IP所使用的OSS详情与费用
- 开源协议：Apache License Version 2.0 
				http://www.apache.org/licenses/
        
### 开发说明
- 该版本是仅用于统计指定IP请求数与外网流量
- 如果您需要统计更多信息比如：请求类型，HTTP类型，请求所使用的工具，状态码，时间等
  请自行根据cc.openstring.oss.statistic.service.LogEntity.java 
  扩展cc.openstring.oss.statistic.service.LogStatistics.java
- 该工具的需求由于阿里云暂未开放OSS指定AccessKeyID,指定IP所使用产生的费用接口
- 该工具的实现是读取OSS系统日志进行解析，所以使用前请在阿里云后台开启OSS系统日志
- 其他问题请参考/doc/doc.txt
					
### 工具截图
- 环境变量配置
![2](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/evironment-var.png "evironment-var")
- 帮助
![3](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/help.png "help.png")
- config.properties文件accesskeyid配置错误
![0](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/accesskeyid-error.png "accesskeyid-error")
- config.properties文件bucket配置错误
![1](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/bucket-error.png "bucket-error")
- 默认统计当前所有费用
![6](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/today-all-cost.png "today-all-cost")
- 统计当天指定IP费用
![7](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/today-ip-cost.png "today-ip-cost")
- 统计指定开始时间与结束时间总费用
![4](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/start-end-all-cost.png "start-end-all-cost")
- 统计指定开始时间与结束时间以及指定IP总费用
![5](https://raw.githubusercontent.com/MinsxFramework/alibaba-oss-statistic/master/image/start-end-ip-all-cost.png "start-end-ip-all-cost")
