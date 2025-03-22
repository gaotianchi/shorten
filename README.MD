# Shorten - 高性能短链接系统

## 项目概述

Shorten 是一个高性能、高并发的短链接服务，旨在提供稳定可靠的短链接跳转功能。本项目特别关注 **短链接唯一性、高并发支持、短链接过期策略** 这三大核心难点，并采用高效的技术方案进行优化。

## 核心功能

- **短链接生成**：将长 URL 转换为唯一的短链接。
- **短链接跳转**：输入短链接后，快速跳转到原始长链接。
- **短链接过期**：支持自定义短链接有效期，到期自动失效。
- **高并发支持**：优化系统架构，使其支持高并发访问。
- **访问统计**：记录短链接的访问量、来源等信息。
- **RESTFul API**：提供 API 供外部应用调用。

## 项目架构

本项目采用 **Spring Boot + Redis + MongoDB + Kafka** 架构，结合 **Docker & Kubernetes** 进行容器化部署。

### 架构图

```
 用户请求 -> Nginx 负载均衡 -> 短链接服务集群 -> Redis + MongoDB 数据存储
                                    \-> Kafka 异步处理
```

## 核心难点与解决方案

### 1. 短链接唯一性

**问题**：短链接必须唯一，不能与已有的短链重复。

**解决方案**：

- **Base62 编码**：使用 **Redis INCR 生成唯一 ID**，然后转换为 Base62 编码。
- **分布式 ID 生成**：采用 **Snowflake 算法** 生成唯一 ID。
- **唯一性校验**：查询 Redis 和 MongoDB，避免短链接冲突。

### 2. 高并发支持

**问题**：在高并发场景下，如何快速解析短链接并跳转？

**解决方案**：

- **Redis 缓存**：使用 Redis 作为一级缓存，快速查询短链接对应的长链接。
- **MongoDB 持久化**：短链接信息存入 MongoDB，Redis 失效时查询数据库。
- **Nginx 负载均衡**：采用 **Nginx + Kubernetes** 进行负载均衡，支持自动扩容。
- **Kafka 异步日志处理**：短链接访问日志采用 Kafka 异步写入，提升吞吐量。

### 3. 短链接过期策略

**问题**：如何自动清理过期短链接，避免数据膨胀？

**解决方案**：

- **Redis TTL 机制**：在 Redis 存储短链接时设置 TTL，过期自动删除。
- **MongoDB TTL 索引**：MongoDB 配置 TTL 索引，到期自动清除数据。
- **延迟队列批量删除**：使用 **Kafka + Quartz** 定期扫描并批量删除过期短链。

## 技术选型

| 组件   | 选型                 | 作用              |
|------|--------------------|-----------------|
| 语言   | Java (Spring Boot) | 业务逻辑开发          |
| 缓存   | Redis              | 短链接缓存，加速访问      |
| 数据库  | MongoDB            | 存储短链接，支持 TTL 过期 |
| 消息队列 | Kafka              | 访问日志、异步任务处理     |
| 负载均衡 | Nginx              | 负载均衡，高并发支持      |
| 容器化  | Docker, Kubernetes | 容器管理，自动扩展       |

## 快速启动

### 1. 克隆项目

```sh
git clone https://github.com/gaotianchi/shorten.git
cd shorten
```

### 2. 启动服务（Docker Compose）

```sh
docker-compose up -d
```

## 部署方式

### 1. **Docker 部署**

```sh
docker build -t shorten .
docker run -p 8080:8080 shorten
```

### 2. **Kubernetes 部署**

```sh
kubectl apply -f k8s/
```

## 监控与日志

- **Prometheus + Grafana 监控**
- **ELK 日志分析（Elasticsearch + Logstash + Kibana）**

## 贡献指南

欢迎提交 Issue 和 PR 进行优化！🚀

