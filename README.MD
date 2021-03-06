﻿### 当前版本V1.0.2

#### 🍬 介绍

* 基于SpringBoot，后端核心技术采用Sqltoy操作数据库以及Sa-Token鉴权，前端使用Vue3+TS+Element-plus。是一款前后端分离、开箱即用的后台权限管理系统。
* 致力于打造一款不用生成冗赘代码的后台管理系统

#### 🖥️ 在线演示
- 演示地址：http://106.52.139.93  
- 账号&密码：admin/srapwd

#### 🏞️ 图片演示

<table>
    <tr>
        <td><img src="https://vjwss-1257389675.cos.ap-chengdu.myqcloud.com/upload/%E7%99%BB%E5%BD%95%E9%A1%B5.png"/></td>
        <td><img src="https://vjwss-1257389675.cos.ap-chengdu.myqcloud.com/upload/%E4%BB%A3%E7%A0%81%E7%94%9F%E6%88%90.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://vjwss-1257389675.cos.ap-chengdu.myqcloud.com/upload/%E4%BB%AA%E8%A1%A8%E7%9B%98.jpg"/></td>
        <td><img src="https://vjwss-1257389675.cos.ap-chengdu.myqcloud.com/upload/%E8%8F%9C%E5%8D%95.jpg"/></td>
    </tr>
</table>

#### 🧬 软件架构

* 前后端分离，后端采用流行的MVC三层架构，缓存Redis，数据库MySQL

#### 🍋与其它优秀Admin的不同之处
1. 很多优秀的Admin系统都是具备代码生成功能，一套代码生成下来的确能省很多事，但是重复性的代码有很多，所以在本项目将其抽象出一层BaseService；

v1.0.1
```java
public interface IUserService {
    // 注释已省略......
    boolean add(UserAddParam param) throws BusinessException;

    boolean deleteBatch(List<String> idList) throws BusinessException;

    boolean update(UserUpdateParam param) throws BusinessException;

    Page<UserVO> listByPage(Page<UserVO> param) throws BusinessException;

    boolean delete(String id) throws BusinessException;
}
```
v1.0.2
```java
public interface IUserService extends IBaseService<Page<UserVO>, UserPageParam, UserAddParam, UserUpdateParam> {
}
```

2. 使用sqltoy框架的原因主要是不用写mybatits的mapper.java文件，因为mapper也是重复度很高，当然sqltoy还有很多优点本项目尚未用到，有待挖掘；

mybatits
```java
public class UserServiceImpl{
    @Autowired
    private UserMapper userMapper;
    
    public List<User> getAll() {
        return userMapper.getAll();
    }
}
// + 这里还得加一个mapper
public class UserMapper{
    public List<User> getAll();
}
// mapper的xml文件......
```
sqltoy
```java
public class UserServiceImpl{
    @Autowired
    private SqlToyLazyDao sqlToyLazyDao;
    
    public List<User> getAll() {
        return sqlToyLazyDao.findBySql("getAll");
    }
}
// - 这里不用再写一个mapper.java文件

// mapper的xml文件......
```

#### ☘️ 启动说明

* 步骤

1. 运行数据库脚本
2. 启动本地Redis并运行后端服务
3. 安装前端依赖并运行前端项目

* 后端

```text
application.yml配置文件位置：src/main/resources/application.yml
数据库脚本：/doc/db/DB_SRA_V1.sql
Main入口主类：sra-bootstrap/src/main/java/com/jwss/bootstrap/SraApplicationBoot.java
```

- 前端

```bash
$ cd sra-ui                         #切换到前端目录
$ npm install   || yarn             #安装依赖  
$ npm run serve || yarn serve       #开发环境  
$ npm run build || yarn build       #生产环境
```

* 访问地址

1. 后端接口文档：http://localhost:7777/doc.html
2. 前端登录地址：http://localhost:8080/#/login

* 备注

1. 必须安装Redis；
2. db_sra_v1.chnr.json文件可以用pdman编辑；

#### 🍁 开发规范

- 建议使用[《阿里巴巴Java开发手册》](https://files.cnblogs.com/files/han-1034683568/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%E7%BB%88%E6%9E%81%E7%89%88v1.3.0.pdf)

#### 🪐 项目结构

```
├─logs  ------------------------日志文件  
├─doc  -------------------------文档  
│  └─db  -----------------------数据库脚本  
├─sra-bootstrap  ---------------启动模块  
├─sra-common  ------------------常用模块  
├─sra-config  ------------------配置模块  
├─sra-framework  ---------------框架模块  
├─sra-system  ------------------系统模块  
└─sra-ui  ----------------------前端项目  
```

#### 📡 核心技术栈

##### 后端

- [SpringBoot](https://spring.io/projects/spring-boot) ：Spring Boot 可以轻松创建可以“直接运行”的独立的、生产级的基于 Spring 的应用程序。
- [Sqltoy](https://gitee.com/sagacity/sagacity-sqltoy) ：java真正最强大的ORM框架，支持市场上流行数据库。
- [Satoken](https://sa-token.dev33.cn/doc/index.html#/) ：一个轻量级 Java 权限认证框架，让鉴权变得简单、优雅！
- [Hutool](https://www.hutool.cn/) ：小而全的Java工具类库，使Java拥有函数式语言般的优雅，让Java语言也可以“甜甜的”。

##### 前端

- [Vue3开发文档](https://v3.cn.vuejs.org/) ：Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。
- [Element Plus](https://element-plus.gitee.io/zh-CN/) ：基于 Vue 3，面向设计师和开发者的组件库，简洁、美观、稳定、易用。
- [Apache ECharts](https://echarts.apache.org/handbook/zh/get-started/) ：一个基于 JavaScript 的开源可视化图表库，功能非常强大。

#### 🖥️ 软件版本要求

- MySQL：8.0及以上
- JDK：jdk8及以上
- Nodejs：14.0及以上

#### 🍫 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

- [贡献指南](https://gitee.com/gitee-community/opensource-guide/blob/master/%E8%B4%A1%E7%8C%AE%E6%8C%87%E5%8D%97.md)
