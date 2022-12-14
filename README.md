# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### how to run
```
mvn spring-boot:run
```

### 實作功能
* jackson : 序列化、反序列化、自訂義序列化JSON
* annotation : @ExtendWith、@SpringBootTest、@Autowired、@RequestMapping、@RestController
* File : 使用 BufferedReader 讀檔案、BufferedWriter寫檔案
* spring : spring IoC (ex : @ConditionalOnProperty for switch impl)
* ActiveMQ : producer sends message to ActiveMQ
* WebClient : call api to another server (e.g : demo-app)

#### branch :master_IoC
使用IoC來切換儲存方式

#### branch over-design-example
對工具jackson 做過度的IoC設計

#### How to run activeMQ locally
1. download zip file ( [reference](https://activemq.apache.org/components/artemis/download/))
2. unzip file and go to `<unzipFileName>/bin` cmd to create Instance Name myatemis :
	1. `artemis create myatemis --user admin --password admin --require-login`
3. go to `<unzipFileName>/<instance name>/bin` cmd to run the activeMQ
	1. `./artemis.cmd run`
	
#### another server
project demo-app