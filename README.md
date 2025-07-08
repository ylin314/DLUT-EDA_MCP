## 大连理工大学开发区校区 MCP 服务

[![](https://img.shields.io/badge/Version-1.0.0-brightgreen)]()

![jdk-version](https://img.shields.io/badge/Java-v21.0.7-blue?style=flat-square&logo=java&labelColor=gray)  ![spring-version](https://img.shields.io/badge/SpringBoot-v3.3.0-deepgreen?style=flat-square&logo=spring-boot&labelColor=gray&logoColor=white)  ![Spring AI](https://img.shields.io/static/v1?label=Spring%20AI&message=1.0.0-M6&color=blue&logo=spring)  ![Maven Version](https://img.shields.io/badge/Maven-3.9.9-orange?logo=apachemaven)  ![MySQL Version](https://img.shields.io/badge/MySQL-8.4.5-blue?logo=mysql)  ![Redis Version](https://img.shields.io/static/v1?label=Redis&message=7.4.2&color=brightgreen&logo=redis)

大连理工大学开发区校区 MCP 服务是一款 [MCP Server](https://modelcontextprotocol.io/introduction)，将大连理工大学开发区校区各部门通知、信息与服务封装为云端服务。支持形势与政策开课通知查询、教务通知获取、校历提供、校园网相关服务等

## 快速开始
在 Agent 智能体中添加如下配置

```json
{
  "mcpServers": {
    "DLUT-EDA_MCP": {
      "url": "https://mcp.linyang.ink/sse"
    }
  }
}
```

之后您需要在请求头中加入 Authorization 字段，值为 `Bearer <token>`

## 产品特点

## 能力介绍

