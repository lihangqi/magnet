# magnet

[![Build Status](https://travis-ci.com/lihangqi/magnet.svg?branch=master)](https://travis-ci.com/lihangqi/magnet)
![Java CI with Maven](https://github.com/lihangqi/magnet/workflows/Java%20CI%20with%20Maven/badge.svg)
[![CodeFactor](https://www.codefactor.io/repository/github/lihangqi/magnet/badge)](https://www.codefactor.io/repository/github/lihangqi/magnet)

快速搭建流式处理应用！简单实用的分布式大数据处理框架。

### 软件架构

![iamge](./.github/image/1.png)

项目目前由core、xmlbuilder、jsonbuilder、spark1、spark2、flink1和client七个模块组成。

- core模块封装了各个部分的抽象组件。
- xmlbuilder和jsonbuilder模块是以xml或json解析为执行配置来源的读取模块。
- spark1、spark2和flink1模块为大数据引擎模块，目前spark1模块开发比较完整，是基于spark1.6版本。
- client模块是客户端调用模块。

Magnet提供统一的配置接口，只需要配置一次就可以在任何引擎上处理大数据，真正做到“ **一次编写，到处执行** ”的效果。项目具备优秀的可扩展性，设计良好的接口可以兼容任何大数据引擎。架构图中蓝色部分为框架的核心模块，非蓝色部分均为可扩展模块。你可以扩展配置文件的格式，可以是xml配置、json配置或自定义格式；你也可以扩展大数据处理引擎，Spark、Flink、Hive、Kafka或MapReduce；你也可以扩展各种标签组件；你还可以扩展每个标签组件的包装wrapper，用于处理每个标签组件的前置和后置操作。