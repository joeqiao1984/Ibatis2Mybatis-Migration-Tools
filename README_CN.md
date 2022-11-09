# iBatis升级转换工具使用指南

本工具用于将iBatis sqlmap xml配置文件转换为 Mybatis的mapper xml配置文件

## 实现思路和特性

- 本工具通过对ibatis和mybatis配置文件对应dtd文件进行拉网式排查，梳理所有需要转换的标签语法
- 针对多个大量使用ibatis框架的项目源代码进行分析、提取，梳理ibatis常见使用场景和复杂语句样例
- 对于单个标签转换逻辑，编写大量单元测试进行覆盖（转换逻辑单元测试覆盖率100%，整体单元测试覆盖率91%）
- 根据实际项目源代码中收集的复杂配置片段编写集成测试，并建立集成测试工程，保证转换后的mybatis配置文件执行后的数据与原始ibatis实现一致
- 对于可以转换的ibatis标签进行自动转换，对于不能转换或已知转换可能存在风险的情况，进行告警，提示用户进行人工处理
- 支持CDATA节点
- 对于某些生僻语法例如cacheModel,flushInterval等未做处理，这类生僻语法在mybatis dtd校验中将形成报错，用户可以根据报错信息，自行人工处理。语法支持情况详见后续章节
- <font color=red>限于能力，本工具肯定存在考虑不周或转换错误的情况，请务必对转换结果进行全面覆盖测试。</font>



## 使用方法

- 本工具使用JDK 1.8编译，请确认运行环境中已安装JDK 1.8

- 获取工具jar文件ibatisUpgradeTools.jar

- 在该jar文件目录下执行命令

  ```shell
  java -jar ibatisUpgradeTools.jar c:\source c:\dest
  ```

- 该命令将会对c盘source目录下所有对应的ibatis配置文件进行转换，转换后的文件按照原始目录结构生成在c盘dest目录下

- 生成过程产生的日志信息将会位于执行命令的同一目录下的result.log文件中

## 语法支持列表

### sqlMap标签

- **支持度:** 支持转换
- **处理方式：**转换为mapper标签

|   属性    | 支持/不支持 |    说明    |
| :-------: | :---------: | :--------: |
| xmlns:fo  |    支持     | 与原值一致 |
| namespace |    支持     | 与原值一致 |

### parameterMap标签

- **支持度:** 支持转换
- **处理方式:** 标签不变，转换属性

| 属性  | 支持/不支持 |         说明          |
| :---: | :---------: | :-------------------: |
|  id   |    支持     |      与原值一致       |
| class |    支持     | 转换为type,与原值一致 |

### parameter标签

- **支持度:** 支持转换
- **处理方式：**标签不变，转换属性

|     属性     | 支持/不支持 |       说明        |
| :----------: | :---------: | :---------------: |
|   property   |    支持     |    与原值一致     |
|   javaType   |    支持     |    与原值一致     |
|   jdbcType   |    支持     |    与原值一致     |
|   typeName   |   不支持    | DTD报错，人工处理 |
|  nullValue   |   不支持    | DTD报错，人工处理 |
|     mode     |    支持     |    与原值一致     |
| typeHandler  |    支持     |    与原值一致     |
|  resultMap   |    支持     |    与原值一致     |
| numericScale |   不支持    | DTD报错，人工处理 |

### resultMap标签

- **支持度:** 支持转换
- **处理方式：**标签不变，转换属性
- **注意事项：**
  -  由于mybatis默认会开启autoMapping，此逻辑可能导致转换前后行为不一致。因此转换后，默认加上了autoMapping为false属性
  - groupBy在ibatis中有去重特性，而mybatis无此逻辑，因此当子标签无select/resultMap出现时，可能存在问题。转换过程中会进行警告

|  属性   | 支持/不支持 |          说明          |
| :-----: | :---------: | :--------------------: |
|   id    |    支持     |       与原值一致       |
|  class  |    支持     | 转换为type,与原值一致  |
| extends |    支持     |       与原值一致       |
| xmlName |   不支持    |   DTD报错，人工处理    |
| groupBy |    支持     | 添加id节点，删除原属性 |

### result标签

- **支持度:** 支持转换

|     属性      | 支持/不支持 |          说明          |
| :-----------: | :---------: | :--------------------: |
|   property    |    支持     |       与原值一致       |
|   javaType    |    支持     |       与原值一致       |
|    column     |    支持     |       与原值一致       |
|  colunmIndex  |   不支持    |   DTD报错，人工处理    |
|   jdbcType    |    支持     |       与原值一致       |
|   nullValue   |   不支持    |   DTD报错，人工处理    |
| notNullColumn |   不支持    |   DTD报错，人工处理    |
|    select     |    支持     | 将标签转换为collection |
|   resultMap   |    支持     | 将标签转换为collection |
|  typeHandler  |    支持     |       与原值一致       |

### discriminator标签

- **支持度:** 不支持
- **处理方式：**人工转换

### subMap标签

- **支持度:** 不支持
- **处理方式：**人工转换

### cacheModel标签

- **支持度:** 不支持
- **处理方式：**人工转换

### flushInterval标签

- **支持度:** 不支持
- **处理方式：**人工转换

### flushOnExecute标签

- **支持度:** 不支持
- **处理方式：**人工转换

### property标签

- **支持度:** 支持转换
- **处理方式：** 不变

| 属性  | 支持/不支持 |    说明    |
| :---: | :---------: | :--------: |
| name  |    支持     | 与原值一致 |
| value |    支持     | 与原值一致 |

### typeAlias标签

- **支持度:** 支持转换
- **处理方式：** 删除，提取打印至日志文件供用户统一配置

| 属性  | 支持/不支持 |    说明    |
| :---: | :---------: | :--------: |
| alias |    支持     | 与原值一致 |
| type  |    支持     | 与原值一致 |

### sql标签

- **支持度:** 支持转换
- **处理方式：** 不变

| 属性 | 支持/不支持 |    说明    |
| :--: | :---------: | :--------: |
|  id  |    支持     | 与原值一致 |

### include标签

- **支持度:** 支持转换
- **处理方式：** 不变

| 属性  | 支持/不支持 |    说明    |
| :---: | :---------: | :--------: |
| refid |    支持     | 与原值一致 |

### statement标签

- **支持度:** 支持转换
- **处理方式：**如果有resultClass属性，则转换为select标签，否则转换为update标签

|      属性      | 支持/不支持 |              说明               |
| :------------: | :---------: | :-----------------------------: |
|       id       |    支持     |           与原值一致            |
|  parameterMap  |    支持     |           与原值一致            |
| parameterClass |    支持     | 转换为parameterType，与原值一致 |
|   resultMap    |    支持     |           与原值一致            |
|  resultClass   |    支持     |  转换为resultType, 与原值一致   |
|   cacheModel   |   不支持    |        DTD报错，人工处理        |
| resultSetType  |    支持     |           与原值一致            |
|   fetchSize    |    支持     |           与原值一致            |
| xmlResultName  |   不支持    |        DTD报错，人工处理        |
|  remapResults  |    支持     |              删除               |
|    timeout     |    支持     |           与原值一致            |

### select标签

- **支持度:** 支持转换
- **处理方式：**标签不变，转换属性

|      属性      | 支持/不支持 |              说明               |
| :------------: | :---------: | :-----------------------------: |
|       id       |    支持     |           与原值一致            |
|  parameterMap  |    支持     |           与原值一致            |
| parameterClass |    支持     | 转换为parameterType，与原值一致 |
|   resultMap    |    支持     |           与原值一致            |
|  resultClass   |    支持     |  转换为resultType, 与原值一致   |
|   cacheModel   |   不支持    |        DTD报错，人工处理        |
| resultSetType  |    支持     |           与原值一致            |
|   fetchSize    |    支持     |           与原值一致            |
| xmlResultName  |   不支持    |        DTD报错，人工处理        |
|  remapResults  |    支持     |              删除               |
|    timeout     |    支持     |           与原值一致            |

### insert标签

- **支持度:** 支持转换
- **处理方式：**标签不变，转换属性

|      属性      | 支持/不支持 |              说明               |
| :------------: | :---------: | :-----------------------------: |
|       id       |    支持     |           与原值一致            |
|  parameterMap  |    支持     |           与原值一致            |
| parameterClass |    支持     | 转换为parameterType，与原值一致 |
|    timeout     |    支持     |           与原值一致            |

### selectKey标签

- **支持度:** 不支持
- **处理方式：**人工转换

### update标签

- **支持度:** 支持转换
- **处理方式：**标签不变，转换属性

|      属性      | 支持/不支持 |              说明               |
| :------------: | :---------: | :-----------------------------: |
|       id       |    支持     |           与原值一致            |
|  parameterMap  |    支持     |           与原值一致            |
| parameterClass |    支持     | 转换为parameterType，与原值一致 |
|    timeout     |    支持     |           与原值一致            |

### delete标签

- **支持度:** 支持转换
- **处理方式：**标签不变，转换属性

|      属性      | 支持/不支持 |              说明               |
| :------------: | :---------: | :-----------------------------: |
|       id       |    支持     |           与原值一致            |
|  parameterMap  |    支持     |           与原值一致            |
| parameterClass |    支持     | 转换为parameterType，与原值一致 |
|    timeout     |    支持     |           与原值一致            |

### procedure标签

- **支持度:** 支持转换
- **处理方式：**转换为update标签，增加statement属性为CALLABLE

|      属性      | 支持/不支持 |              说明               |
| :------------: | :---------: | :-----------------------------: |
|       id       |    支持     |           与原值一致            |
|  parameterMap  |    支持     |           与原值一致            |
| parameterClass |    支持     | 转换为parameterType，与原值一致 |
|   resultMap    |    支持     |           与原值一致            |
|  resultClass   |    支持     |  转换为resultType, 与原值一致   |
|   cacheModel   |   不支持    |        DTD报错，人工处理        |
|   fetchSize    |    支持     |           与原值一致            |
| xmlResultName  |   不支持    |        DTD报错，人工处理        |
|  remapResults  |    支持     |              删除               |
|    timeout     |    支持     |           与原值一致            |

### dynamic标签

- **支持度:** 支持转换
- **处理方式：**转换为trim标签
- **注意事项：**
  - 会收集所有dynamic标签直系内嵌标签的prepend属性，添加到prefixOverrides属性中以|分隔

|  属性   | 支持/不支持 |                      说明                      |
| :-----: | :---------: | :--------------------------------------------: |
| prepend |    支持     |  与open属性取值一起，作为新标签prepend的取值   |
|  class  |    支持     | 与prepend属性取值一起，作为新标签prepend的取值 |
| extends |    支持     |                转换为suffix属性                |

### isNotNull标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：** 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|                    |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isNull标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：** 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isNotPropertyAvailable标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 如存在removeFirstPrepend属性，请进行进一步测试
  - 转换前提为输入参数为map类型，如果输入参数为普通pojo，转换后将出现错误。请注意检查

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isPropertyAvailable标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 如存在removeFirstPrepend属性，请进行进一步测试
  - 转换前提为输入参数为map类型，如果输入参数为普通pojo，转换后将出现错误。请注意检查

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isEqual标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 为防止mybatis test的char测试失效，转换后test使用了toString转换，可根据实际情况调整
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |
|  compareProperty   |    支持     |              取值后加入到test属性表达式中               |
|    compareValue    |    支持     |              取值后加入到test属性表达式中               |

### isNotEqual标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 为防止mybatis test的char测试失效，转换后test使用了toString转换，可根据实际情况调整
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |
|  compareProperty   |    支持     |              取值后加入到test属性表达式中               |
|    compareValue    |    支持     |              取值后加入到test属性表达式中               |

### isGreaterThan标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |
|  compareProperty   |    支持     |              取值后加入到test属性表达式中               |
|    compareValue    |    支持     |              取值后加入到test属性表达式中               |

### isGreaterEqual标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |
|  compareProperty   |    支持     |              取值后加入到test属性表达式中               |
|    compareValue    |    支持     |              取值后加入到test属性表达式中               |

### isLessThan标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |
|  compareProperty   |    支持     |              取值后加入到test属性表达式中               |
|    compareValue    |    支持     |              取值后加入到test属性表达式中               |

### isLessEqual标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |
|  compareProperty   |    支持     |              取值后加入到test属性表达式中               |
|    compareValue    |    支持     |              取值后加入到test属性表达式中               |

### isEmpty标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 将会先判断传入参数是否为null,再判断参数转换为字符串后是否取值为空字符串
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isNotEmpty标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 将会先判断传入参数是否为null,再判断参数转换为字符串后是否取值为空字符串
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
|      property      |    支持     |              取值后加入到test属性表达式中               |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isParameterPresent标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 直接判断_parameter是否为空
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### isNotParameterPresent标签

- **支持度:** 支持转换
- **处理方式：**转换为if标签
- **注意事项：**
  - 直接判断_parameter是否为空
  - 如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                          说明                           |
| :----------------: | :---------: | :-----------------------------------------------------: |
|      prepend       |    支持     |          与open属性取值一起，加入到内嵌文本前           |
|        open        |    支持     |         与prepend属性取值一起，加入到内嵌文本前         |
|       close        |    支持     |                   加入到内嵌文本尾部                    |
| removeFirstPrepend |    支持     | 增加内嵌trim子节点，<font color=red>建议进行测试</font> |

### iterate标签

- **支持度:** 支持转换
- **处理方式：**转换为foreach标签
- **注意事项：**
  - 本标签转换逻辑较为复杂，如存在多层嵌套iterate标签的情况，请务必进行进一步测试
  - 对removeFirstPrepend转换可能存在问题，如存在removeFirstPrepend属性，请进行进一步测试

|        属性        | 支持/不支持 |                         说明                          |
| :----------------: | :---------: | :---------------------------------------------------: |
|      prepend       |    支持     |         与open属性取值一起，加入到内嵌文本前          |
|        open        |    支持     |        与prepend属性取值一起，加入到内嵌文本前        |
|       close        |    支持     |                  加入到内嵌文本尾部                   |
| removeFirstPrepend |    支持     | 增加外包trim节点，<font color=red>建议进行测试</font> |
|      property      |    支持     |           改为collection属性，增加item属性            |
|    conjunction     |    支持     |            转换为separator属性，取值为原值            |

