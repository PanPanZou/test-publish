#### 项目简介

该项目是在阿里云产品 ESA（版本：2024-09-10）服务下的 Record 资源操作的完整工程示例。
此示例向您展示：
测试Record资源，记录类型为CNAME


#### 注意事项

- **操作费用**：
  - 运行示例代码可能对当前账号发起线上资源操作产生费用，请小心操作

- **依赖关系**：

  - 资源 Site 依赖资源：[RatePlanInstance]
  - 资源 Record 依赖资源：[Site]


#### 执行步骤
- *最低要求Java 8*
```sh
mvn clean package
java -jar target/sample-1.0.0-jar-with-dependencies.jar
```

#### 更多示例
更多示例请查看 https://github.com/aliyun/alibabacloud-java-sdk-samples