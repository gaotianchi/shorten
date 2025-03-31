## 短链接应用

### 项目简介

本项目是一个基于 Spring Boot、MongoDB 和 Redis 的短链接服务，支持短链接生成、查询、管理等功能。我在本项目中主要解决如下三个难点：

1. 确保短链接代码的唯一性；
2. 在高并发情况下维持稳定的访问速度；
3. 清理无效链接；

### 技术栈

- 后端：Spring Boot
- 数据库：MongoDB
- 缓存：Redis
- 消息队列：Kafka
- 分布式架构：Redis Cluster
- 其他：Docker, CI/CD（GitHub Actions ）

### 版本更新

#### V1.0 初始版本，实现基本功能

- 创建，获取以及删除短链接；
- 初步解决短链代码唯一性问题，理想状态下上限为 500 亿以上；
- 初步添加 Redis 缓存层，MongoDB 索引；

**Full Changelog**: https://github.com/gaotianchi/shorten/commits/v1.0

### 运行方式

#### 环境要求

- JDK 17+
- MongoDB 4+
- Redis 6+
- Docker

#### 本地运行

```bash
# 克隆项目
git clone https://github.com/gaotianchi/shorten.git
cd shorten

# 启动服务集合
docker-compose -f docker/latest/compose.yml -p shorten up -d
```

#### API 示例

- 创建短链接：`POST /links?url={url}`
- 查询短链接：`GET /links?code={code}`
- 删除短链接：`DELETE /links?code={code}`

### 贡献指南

欢迎提交 Issue 和 PR 参与贡献！

### 许可证

本项目基于 [MIT](https://mit-license.org/) 许可证开源。
