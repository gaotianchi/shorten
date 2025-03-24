# Shorten - 高性能短链接系统

## 项目概述

Shorten 是一个高性能、高并发的短链接服务，旨在提供稳定可靠的短链接跳转功能。主要关注于下面三个问题的解决方案：

1. [如何确保短链接代码的唯一性？](docs/如何确保短链接的唯一性？.md)
2. [如何提高短链接的访问速度？](docs/如何提高短链接的访问速度？.md)
3. [如何更好的处理过期链接？](docs/如何更好的处理过期链接？.md)

## 技术选型

| 组件   | 选型                 | 作用              |
|------|--------------------|-----------------|
| 语言   | Java (Spring Boot) | 业务逻辑开发          |
| 缓存   | Redis              | 短链接缓存，加速访问      |
| 数据库  | MongoDB            | 存储短链接，支持 TTL 过期 |
| 消息队列 | Kafka              | 访问日志、异步任务处理     |
| 负载均衡 | Nginx              | 负载均衡，高并发支持      |
| 容器化  | Docker             | 快速部署            |

## 快速启动

### 1. 克隆项目

```bash
git clone https://github.com/gaotianchi/shorten.git
cd shorten
```

### 2. 启动服务（Docker Compose）

```bash
docker-compose up -d
```

## 贡献指南

欢迎提交 Issue 和 PR 进行优化！🚀

