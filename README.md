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

> PiDoh7QqKuWkp+i/nuS4nOi9r+afkOafkOWtpumZoioq55qE5p+Q5L2N6ICB5biI77yM5aaC5p6c5oKo6K6k5Li65LiA5Liq5ZCO56uv6aG555uu77yM5Y+q5pyJICoqQ1JVRDxzdXA+MTwvc3VwPioq44CB5Y+q55SoIGBDdHJsK0Ng44CBYEN0cmwrVmDlsLHog73lrozmiJDnmoTor53vvIzpgqPmiJHnnJ/mmK/nibnliKvmrKPotY/mgqjnmoTmlZnlrabog73lipvvvIzluIzmnJvog73mgqjog73lpJ/lpJrlj5HooajkuIDkupvlrabmnK/orrrmlofvvIzor4TliLDmm7Tpq5jnuqfnmoTogYznp7DvvIzml6nml6XmmYvljYfvvIzkuLrlpKfov57kuJzova/mn5Dmn5DlrabpmaLnmoTmlZnogrLkuovkuJrlgZrlh7rljZPotornmoTotKHnjK7jgIIKPiDlnKjmraTvvIzmiJHopoHlkJHmgqjor7TkuIDlo7DigJzmirHmrYnigJ3vvIzku6XmiJHnmoTmsLTlubPog73lipvov5jkuI3otrPku6XmiJDkuLrmgqjnmoTlrabnlJ/vvIzmiJHkuZ/kuI3otrPku6XlrozmiJDkuIDkuKoqKuacrOenkSoq55qE5q+V5Lia6K6+6K6h77yM57uZ5oKo55qE5Y+R5bGV5ouW5ZCO6IW/5LqG77yM5Zug5q2k5oiR5YaN5qyh5ZCR5oKo6YOR6YeN55qE6YGT5q2J77yM4oCc5a+55LiN6LW34oCd44CCCj4KPiAqKumZhOiogCblhY3otKPlo7DmmI46Kiog5Lul5LiK5oOF6IqC5Li66Jma5p6E77yM5bm25YyF5ZCr5LqGfn7oibrmnK/liqDlt6V+fuOAgX5+5re75rK55Yqg6YaLfn48c3VwPjI8L3N1cD7nmoTlhoXlrrnjgIIKPiDlhbbnm67nmoTku4Xku4XkuLrlkJDmp73vvIzor7fli7/lr7nlj7flhaXluqfvvIznu5noh6rlt7Hmib7kuI3lv4XopoHnmoTpurvng6bjgIIKPgo+Cj4gIFsxXSBb5b6Q5ZCv5rGfLiDln7rkuo5KMkVF55qEQ1JVROe7hOWQiOe7hOS7tueahOiuvuiuoeS4juWunueOsFtEXS7kuJzljJflpKflraYsMjAxNi5dKGh0dHBzOi8va25zLmNua2kubmV0L2tjbXMvZGV0YWlsL2RldGFpbC5hc3B4P0ZpbGVOYW1lPTEwMTgwNzQwODcubmgmRGJOYW1lPUNNRkQyMDE5KQo+IAo+IFsyXSBb5re75rK55Yqg6YaLX+eZvuW6pueZvuenkV0oaHR0cHM6Ly9iYWlrZS5iYWlkdS5jb20vaXRlbS8lRTYlQjclQkIlRTYlQjIlQjklRTUlOEElQTAlRTklODYlOEIvNDI5NjM5MSk=
>
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

3. 修改 `/maine-common-config/src/main/resources` 下的配置文件

4. 使用 **[Gradle](https://gradle.org/)** 进行构建

    * Linux or macOS

      ```shell
      ./gradlew build
      ```

    * Windows

      ```shell
      .\gradlew.bat build
      ```

