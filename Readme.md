# Diamond

### 该数据库为本人毕设，有前端和后端，前端使用工具有 Vue + Vuetify + Electron 。后端使用工具有 Spring Boot + Regex 101。前端模块有登录、数据库文件的管理、表文件、数据文件的管理、用户文件的管理、用户权限的管理、日志文件的管理。该项目包含四块：core（核心）、cache（缓存）、frame（框架）、inter（网络接口）。自定义框架（功能还不算完善，可能没和core进行对接）可以直接使用实体类来操作SQL数据，也可以使用链式编程来管理SQL语句。核心是用来解析SQL语句的。缓存是用来存以SQL为键，以查出的数据为值的数据的。网络接口就是Spring Boot写的接口，可以进行限流。

#### 完成功能

#### 1.权限

创建用户，删除用户、设置用户密码、给用户权限、收回用户权限、重命名用户、

#### 2.库文件夹

​	创建库文件夹、删除库文件夹、显示库、指定库文件夹、重命名库文件夹

#### 3.表文件夹

创建表文件、删除表文件、显示表、修改表字段（add、drop、change、modify）、重命名表文件

#### 4.数据文件

​	增（单条、多条）、删（多条件）、改（多字段、多条件）、查（单表、多表（左右内连接）、多条件）

联系：wx SepVrio
