# BigDataCodeRepo
大数据相关代码库

## 说明
   学习大数据工具软件过程中编写使用的代码。主要是：
      1. API
      2. 惯用法

## 项目结构
   每个项目一个Maven工程

## 编译
```
mvn package
```

## 导入Eclipse
```
mvn eclipse:eclipse
File => Import => General => Existing Project Into WorkSpace => Select Root Directory
```

## 导入Intellij IDEA
```
Open
```


## $MAVEN_HOME/conf/settings 
```
   <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
```
