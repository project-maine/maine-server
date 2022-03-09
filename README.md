<div align="center">
    <h1>
        Maine
    </h1>
    <a href="https://github.com/project-maine/">
        <img alt="Name" src="https://img.shields.io/badge/project-maine-green?style=for-the-badge&logo=github">
    </a>
    <img alt="Love" src="https://img.shields.io/badge/code%20with-love%E2%99%A5%EF%B8%8F-CC0066?style=for-the-badge">
    <img alt="GitHub" src="https://img.shields.io/github/license/project-maine/maine-server?style=for-the-badge">
    <p>
        Maine 是我的毕业设计项目，名字来源于<a href="https://zh.wikipedia.org/wiki/%E7%B7%AC%E5%9B%A0%E8%B2%93">缅因猫（Maine Coon）</a>。<br>至于为什么？因为我喜欢缅因猫，就这么简单。
    </p>
</div>

## 声明

没什么好说的

## 吐槽
> 致**大连东软某某学院**的某位老师，如果您认为一个后端项目来说，只有 **CURD**、只用 `Ctrl+C`、`Ctrl+V`就能完成的话，那我真是特别欣赏您的教学能力，希望能您能够多发表一些学术论文，评到更高级的职称，早日晋升，为大连东软某某学院的教育事业做出卓越的贡献。
> 在此，我要向您说一声“抱歉”，以我的水平能力还不足以成为您的学生，我也不足以完成一个**本科**的毕业设计，给您的发展拖后腿了，因此我再次向您郑重的道歉，“对不起”。
> 
>  ----邓紫旭 2022年3月9日


## 许可证

```text
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2020 Deng.Zixu <admin@dengzixu.com>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
```

本项目全部代码均采用 `WTFPL` 协议开源。So *DO WHAT THE FUCK YOU WANT TO*



## 项目说明

### 目录结构

* `maine-app-api` 对外提供 API 访问
* `maine-common-config` 存放配置文件
* `maine-common-core` 核心业务逻辑
* `maine-common-utils` 通用工具
* `database` 数据库结构
* `document` 相关文档



### 构建代码

1. 环境要求

   * 操作系统：无硬性要求
   * JDK >= 17
   * MySQL >= 5.7

2. clone 本仓库

   ```shell
   git clone https://github.com/mamoe/mirai.git
   ```

3.  修改 `/maine-common-config/src/main/resources` 下的配置文件

4. 使用 **[Gradle](https://gradle.org/)** 进行构建

   * Linux or macOS

     ```shell
     ./gradlew build
     ```

   * Windows

     ```shell
     .\gradlew.bat build

