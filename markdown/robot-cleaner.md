# Robot Cleaner

## Background

### 问题提出

扫地机器人 (robot cleaner),又称自动打扫机、智能吸尘器等。能凭借一定的人工智能,自动在房间内完成地板清理工作。Bosch 公司是领先的扫地机器人制造商。Bosch 的工程师研发了一款机器人,它接受远端遥感指令, 并完成一些简单的动作。

为了方便控制机器人的导航,工程师使用三元组 (x, y, d) 来表示机器人的位置信息;其中 (x,y) 表 示机器人的坐标位置,d 表示机器人的方向 (包括 East, South, West, North 四个方向)。

假设机器人初始位置为 (0, 0, N),表示机器人处在原点坐标、并朝向北。


### 技能要求

在实现需求的前提下,增加如下一些技能要求。- 每个迭代 45 分钟- 结对编程- 不允许使用鼠标- 不允许使用 IDE- 坚持使用 TDD 开发 
- 持续重构代码- 坚持良好的代码提交习惯- 点评的坏味道,下一迭代必须纠正 
- 互相点评- 互相重构不同 pair 的代码


## TurnTo

### 需求一

- 当 Robot 收到 LEFT 指令后,Robot 向左转 90 度;- 当 Robot 收到 RIGHT 指令后,Robot 向右转 90 度;例如:- Robot 原来在 (0, 0, N),执行 LEFT 指令后,新的位置在 (0, 0, W);
- Robot 原来在 (0, 0, N),执行 RIGHT 指令后,新的位置在 (0, 0, E)。

### 破冰之旅

第一个测试用例开始, `Robot`在初始位置`(0, 0, N)`，收到`left`指令，之后在`(0, 0, W)`的位置上。

```java
public class RobotCleanerTest {
  @Test
  public void left_instruction() {
    assertThat(robot.turnLeft(), equalTo(new Position(0, 0, WEST)));
  }
  
  private RobotCleaner robot = new RobotCleaner();
}
```

为了通过编译，新建一个`RobotCleaner`，`Position`，`Orientation`的类。

```java
public class Position {
  public Position(int x, int y, Orientation orientation) {
  }
}
```

```java
public enum Orientation {
  EAST, SOUTH, WEST, NORTH;
}
```

```java
public class RobotCleaner {
  public RobotCleaner() {
  }
  
  public Position turnLeft() {
    return new Position(0, 0, Orientation.NORTH);
  }
}
```

`RobotCleaner.turnLeft`被`Hard Code`，这有些唐突，因为我想尽快通过编译，而且此刻我暂时没有`turnLeft`的实现有太多的想法，更重要的是我为了尽快看到测试的反馈，干脆先将`turnLeft`的实现返回一个初始的位置。

下一步，为了让测试尽快通过，可以快速实现`Position`的`equals`方法。

```java
public class Position {
  public Position(int x, int y, Orientation orientation) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position other = (Position) obj;
      return point.equals(other.point) && orientation == other.orientation;
    }
    return false;
  }

  private int x, y;
  private Orientation orientation;
}
```

此刻直接实现`Position.equals`，因为他太简单了。但我们发现此刻测试是失败的，因为之前`RobotCleaner.turnLeft`的实现被简单地打桩实现了。

接下来，我们进一步思考`RobotCleaner.turnLeft`该如何实现？



