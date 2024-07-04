<p align="center">
  <a href="https://github.com/catalpawoo524/easy-netty">
   <img alt="Logo" height="200" src="./images/CATALPA_ICON_2.png">
  </a>
</p>
<h1 align="center" style="margin: -30px 0 -10px; font-weight: bold;">
Easy-Netty v1.2.0
</h1>

<p align="center">
  Netty操作简化工具包，为降低开发门槛、提升编码效率而生。<br/>
  <a href="https://easynetty.com" target="_blank">在线文档：https://easynetty.com</a>
</p>

<p align="center">
  <a href="https://mvnrepository.com/artifact/io.github.catalpawoo524/easy-netty" target="_blank">
    <img alt="Static Badge" src="https://img.shields.io/badge/maven_central-v1.2.0-blue">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0" target="_blank">
    <img alt="Static Badge" src="https://img.shields.io/badge/liscense-Apache_2-red">
  </a>

  <a href="https://github.com/catalpawoo524/easy-netty" target="_blank">
    <img alt="Static Badge" src="https://img.shields.io/badge/repository-github-green">
  </a>
</p>

---
# 简介

Easy-Netty是一个Netty操作简化工具包，为降低开发门槛、提升编码效率而生。

作者在无数次项目场景中反复搭建Netty的服务端与客户端，在经历了大大小小数十个多服务端与多客户端并存的项目场景后，Easy-Netty应运而生。

多客户端同时运行超过一段时间后无法再次收到消息的问题一直未能解决，故截至当前版本仍未实现Netty的客户端操作。不过在作者的观念中，WebSocket客户端原生与Netty性能无异，而使用Netty往往是需要更高性能的WebSocket服务端，所以发布了当前看似“半成品”的项目。

作者的愿景是帮助更多初、中级阶段的程序员快速上手、快速开发Netty项目，以更好地应对“面向需求”的开发场景，相信经受住了无数个区块链应用场景考验的Easy-Netty，也能对大家有所帮助。

能力一般，水平有限。如有谬误之处，烦请各位批评指正。

# 从这里开始

## 安装

安装集成Easy-Netty要求如下：
- JDK 8+
- SpringBoot 2.0+ 或 SpringBoot 3.0+
- Maven 或 Gradle

**（1）SpringBoot 3.0**
```html
<dependency>
    <groupId>io.github.catalpawoo524</groupId>
    <artifactId>easy-netty-spring-boot3-starter</artifactId>
    <version>1.2.0</version>
</dependency>
```
**（2）SpringBoot 2.0**
```html
<dependency>
    <groupId>io.github.catalpawoo524</groupId>
    <artifactId>easy-netty-spring-boot2-starter</artifactId>
    <version>1.2.0</version>
</dependency>
```
## 测试

以SpringBoot 3.0版本为例。
1. 引入Easy-Netty构建帮助类
```java
@Autowired
private EasyNettyHelper easyNettyHelper;
```
2. 编写基础服务端处理类
```java
package com.catalpawoo.easynetty.starter.handler;

import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ChannelHandler.Sharable
public class EasyNettyServerBaseHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        if (ObjectUtil.isNull(textWebSocketFrame)) {
            return;
        }
        log.info("easy-netty received message, content: {}.", textWebSocketFrame.text());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        this.channelMap.put(channelHandlerContext.channel().id(), channelHandlerContext.channel());
        log.info("easy-netty add a new connection.");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
        log.info("easy-netty event trigger: {}", event);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        this.channelMap.remove(channelHandlerContext.channel().id());
        log.info("easy-netty remove a connection.");
    }

}
```

3. 启动服务端
```java
easyNettyHelper.startNettyServer(easyNettyServerBaseHandler, "/socket", 8082, 1).asShortText();
```

## 进阶操作
以SpringBoot 3.0为例。
### 服务端搭建
1. **基于构造器的搭建方法**
   这里需要沿用到上文的*EasyNettyServerBaseHandler*（基础服务端自定义请求处理类）
```java
EasyNettyServerCreator buildCreator = new EasyNettyServerBuilder()
        .setPort(8083)
        .setPath("/socket")
        .setBossThreadNum(1)
        .setHandler(new EasyNettyServerBaseHandler())
        .build();
```

2. **基于注解的搭建方法**
   参考以下代码，快速开始（注：注解下的方法访问性强制要求设置为public）。
```java
package com.catalpawoo.easynetty.starter.handler;

import com.catalpawoo.easynetty.annotation.*;
import com.catalpawoo.easynetty.common.utils.ObjectUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnServer(path = "/socket", port = 8081)
public class EasyNettyAnnotationServer {

    @PostConstruct
    private void init() {
        log.info("easy-netty server initialization.");
    }

    @EnRead
    public void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        if (ObjectUtil.isNull(textWebSocketFrame)) {
            return;
        }
        log.info("easy-netty received message, content: {}.", textWebSocketFrame.text());
    }

    @EnAdd
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        log.info("easy-netty add a new connection.");
    }

    @EnEventTrigger
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object event) {
        log.info("easy-netty event trigger: {}", event);
    }

    @EnRemove
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) {
        log.info("easy-netty remove a connection.");
    }

}
```

### 服务端销毁
为避免不必要的开销、端口占用，建议在项目停止时统一销毁Netty服务端。
```java
easyNettyHelper.shutdownNettyServer();
```