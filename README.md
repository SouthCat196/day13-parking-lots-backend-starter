## ParkingManager

你是一个资深的后端开发工程师，我需要生成一个ParkingManager类，具体要求如下：



属性：

parking lots(List):初始加入三个parking lots

● The Plaza Park (9 parking capacity)

● City Mall Garage (12 parking capacity)

● Office Tower Parking (9 parking capacity)

parking boys(List): 初始加入三个不同策略的parking boys

1. Standard parking strategy
2. Smart parking strategy
3. Super Smart parking strategy



方法：

Parking Manager有park方法、fetch方法和getAllParkingLots方法

park：他会根据传进来的Car和策略类型(String, 暂时可能有三个值：Standard/Smart/Super)来判断交给哪个Parking Boy来执行停车，并返回一个新的ticket

fetch：会接受一个ticket，然后遍历Parking lots来取出车并返回

getAllParkingLots：返回所有ParkingLots



开发代码要求：

- 抽取所有可以抽取的常量
- 属性的初始化放在构造方法中，而不是直接在定义属性时直接new出来
- 不要有重复的代码，尽量地抽取通用的方法
- 对集合操作使用stream流函数式编程方法
- 不要多加修改我给你的属性名和方法名，你可以把我的命名进行驼峰格式化或者下划线拼接等，但是不要修改我给你的命名
- 没有给你的命名，你要保证有意义的命名
- 只需要生成我需要的类的代码，不要给我其他的类的代码

---

## ParkingManagerTest

我需要生成一个ParkingManagerTest类用来测试Parking Manager，具体要求如下：



初始化测试：验证ParkingManager正确初始化了三个停车场和三个停车男孩。

获取所有停车场测试：验证getAllParkingLots（）方法是否返回所有停车场。

停车测试：验证Park （String plateNumber, String strategy）方法请求正确的泊车员停车并返回有效的Ticket。

取车测试：验证Fetch （String ticket）方法使用ticket从相应的停车场取车，并返回正确的车。



测试代码要求：

- 基本的要求和开发代码要求相同
- 所有的测试方法名使用should_XXX_when_XXX_given_XXX来命名
- 只有参数不同的测试case使用@ParameterizedTest注解来合并
- 在代码块加上Given When Then注释
- 如果你觉得需要更多的测试用例，请在生成前先询问我要不要添加

---

## ParkingController

我需要一个ParkingController，提供三个API：

1. getParkingData：
    1. 没有入参
    2. response

```JSON
{
    "id" : 1,
    "name" : "xxx",
    "tickets" : [
        {
            "plateNumber" : "xxxx",
            "position" : 0,
            "parkingLot" : 1
        }
        ............
    ]
}
```
2. park：
    1. 入参为一个Car和一个String类型的停车策略
    2. response为Ticket
3. fetch：
    1. 入参为一个Ticket
    2. response为一个Ticket对应的Car



Controller要求：

- 使用RESTful风格
- 使用构造器注入，而不是使用注解注入
- 不要改变我给你的api路径名称

---

## ParkingControllerTest

我需要一个完整的ParkingControllerTest，用于API集成测试ParkingController类

测试代码要求：

- 所有的测试方法名使用should_XXX_when_XXX_given_XXX来命名
- 只有参数不同的测试case使用@ParameterizedTest注解来合并
- 在代码块加上Given When Then注释
- 如果我没有提供测试用例或者你觉得需要更多的测试用例，请在生成前先询问我要不要添加
- 不要给我加多余的注释



按照我这个以前测试的方式来生成：

---

## GlobalExceptionHandle

帮我写一个全局异常处理捕获在controller会发生的这两个异常，并返回错误状态码和信息

