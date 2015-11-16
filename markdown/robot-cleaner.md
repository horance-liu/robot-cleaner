# Robot Cleaner

- GitHub Pages：[http://horance-liu.github.io](http://horance-liu.github.io)
- GitHub：[https://github.com/horance-liu](https://github.com/horance-liu)
- Author：刘光聪，程序员，敏捷教练，对`OO`，`FP`，`DSL`，`Big Data`具有浓厚的兴趣
- Email：[horance@outlook.com](horance@outlook.com)

## Background

### 问题提出

扫地机器人(`Robot Cleaner`),又称自动打扫机、智能吸尘器等。能凭借一定的人工智能,自动在房间内完成地板清理工作。`Bosch`公司是领先的扫地机器人制造商。`Bosch`的工程师研发了一款机器人,它接受远端遥感指令, 并完成一些简单的动作。

为了方便控制机器人的导航,工程师使用三元组`(x, y, d)`来表示机器人的位置信息;其中`(x,y)`表示机器人的坐标位置,`d`表示机器人的方向 (包括`East`, `South`, `West`, `North`四个方向)。

假设机器人初始位置为`(0, 0, N)`, 表示机器人处在原点坐标、并朝向北。


### 技能要求

在实现需求的前提下,增加如下一些技能要求。- 每个迭代`45`分钟- 结对编程- 不允许使用鼠标- 不允许使用`IDE`- 坚持使用`TDD`开发 
- 持续重构代码- 坚持良好的代码提交习惯- 点评的坏味道,下一迭代必须纠正 
- 互相点评- 互相重构不同`Pair`的代码


## TurnTo

### 需求一

- 当`Robot`收到`LEFT`指令后, `Robot`向左转`90`度;- 当`Robot`收到`RIGHT`指令后, `Robot`向右转`90`度;例如:- `Robot`原来在`(0, 0, N)`, 执行`LEFT`指令后,新的位置在`(0, 0, W)`;
- `Robot`原来在`(0, 0, N)`, 执行`RIGHT`指令后,新的位置在`(0, 0, E)`。

### 破冰之旅

第一个测试用例开始, `Robot`在初始位置`(0, 0, N)`，收到`left`指令，之后在`(0, 0, W)`的位置上。

```java
public class RobotCleanerTest {
  @Test
  public void left_instruction() {
    robot.turnLeft();
    assertThat(robot.getPosition(), equalTo(new Position(0, 0, WEST)));
  }
  
  private RobotCleaner robot = new RobotCleaner();
}
```

为了通过编译，新建一个`Orientation`的枚举类型。

```java
public enum Orientation {
  EAST, SOUTH, WEST, NORTH;
}
```

然后再建立一个`Position`的类，并快速实现这个类。为了通过测试用例的断言`euqalTo`，需要重写`Position.equals`方法。

```java
public final class Position {
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

最后再创建一个`RobotCleaner`的类。

```java
public class RobotCleaner {
  public void turnLeft() {
  }
  
  public Position getPosition() {
    return new Position(0, 0, Orientation.NORTH);
  }
}
```

`turnLeft, getPosition`被`Hard Code`了，这有些唐突，因为我想尽快通过编译，并且此刻我暂时还没有`turnLeft`实现有太多的想法，更重要的是我为了尽快看到测试的反馈。

至此，代码可以完全编译通过了，但测试失败了。这是一个很重要的里程碑，因为我们建立了一个强有力的反馈机制，为后续代码的重构，设计的改进做了坚实的基础。

接下来，是时候思考`RobotCleaner.turnLeft`该如何实现了？其实我的思路非常简单，将初始位置先保存下来，通过`turnLeft`不断变换为新的位置。

```java
public class RobotCleaner {
  public void turnLeft() {
      position = position.turnLeft();
  }
  
  public Position getPosition() {
    return position;
  }
  
  private Position position = new Position(0, 0, Orientation.NORTH);
}
```

为了让其通过编译，需要为`Position`增加`turnLeft`方法，`Position.turnLeft`只会改变方向，坐标并没有发生变化，为此实现也比较简单。

```java
public class Position {
  public Position(int x, int y, Orientation orientation) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }
  
  private Orientation left() {
    return orientation == NORTH ? WEST : orientation; 
  }
  
  public Position turnLeft() {
    return new Position(x, y, left());
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

虽然`Position.left`的实现有点牵强，但此刻用例毕竟通过了，接下来可以开始下一个测试用例了。需要注意的是，此刻急于重构代码是不明智的，因为`TODO List`上还有很多任务排队处理呢。

### 迭代实现`Left`指令

```java
@Test
public void left_instruction() {
  robot.turnLeft();
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, WEST)));
}
  
@Test
public void double_left_instructions() {
  robot.turnLeft();
  robot.turnLeft();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, SOUTH)));
}  
```

可以将`?:`三元操作符重构为`switch`的实现。

```java
public class Position {
  private Orientation left() {
    switch(orientation) {
    case NORTH: return WEST;
    case WEST:  return SOUTH;
    default:    return orientation;
    }
  }
}
```

依次类推，最终的测试用例，及其`Position.left`的实现可以迭代地被实现出来。

```java
@Test
public void left_instruction() {
  robot.turnLeft();
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, WEST)));
}
  
@Test
public void two_times_left_instructions() {
  robot.turnLeft();
  robot.turnLeft();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, SOUTH)));
}  

@Test
public void three_times_left_instructions() {
  robot.turnLeft();
  robot.turnLeft();
  robot.turnLeft();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, EAST)));
}  

@Test
public void forth_times_left_instructions() {
  robot.turnLeft();
  robot.turnLeft();
  robot.turnLeft();
  robot.turnLeft();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, NORTH)));
}  
```

```java
public class Position {
  private Orientation left() {
    switch(orientation) {
    case NORTH: return WEST;
    case WEST:  return SOUTH;
    case SOUTH: return EAST;
    case EAST:  return NORTH;
    default: return orientation;
    }
  }
}
```

也许与你共鸣,这样的设计非常不理想,已经存在很多明显的坏味道了。- `switch-case` 语句也让设计变得僵化;- `RobotCleaner.turnLeft`具有副作用;- 测试用例存在明显的重复代码;但不管怎么样, 我们依然还要继续, 因为到目前为止, 需求我们只完成了一半。此刻更需要拒绝诱惑, 在测试通过之前就将设计做大做全, 不仅仅损伤自信心, 而且更让设计没完没了, 走不到尽头。 

是的, 让测试通过是压倒一切的中心任务。为了通过测试的过程之中所使用的一切不光彩的行为, 要不了多长时间, 我们终会回来的。


### 快速实现`Right`指令

有了`Left`对领域的探索，实现`Right`指令就非常简单了，为了尽快完成功能，暂时先容忍重复代码的存在吧。

```java
@Test
public void right_instruction() {
  robot.turnRight();
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, EAST)));
}
  
@Test
public void two_times_left_instructions() {
  robot.turnRight();
  robot.turnRight();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, SOUTH)));
}  

@Test
public void three_times_left_instructions() {
  robot.turnRight();
  robot.turnRight();
  robot.turnRight();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, WEST)));
}  

@Test
public void forth_times_left_instructions() {
  robot.turnRight();
  robot.turnRight();
  robot.turnRight();
  robot.turnRight();
    
  assertThat(robot.getPosition(), equalTo(new Position(0, 0, NORTH)));
}  
```

```java
public class RobotCleaner {
  public void turnLeft() {
      position = position.turnLeft();
  }
  
  public void turnRight() {
      position = position.turnLeft();
  }
  
  public Position getPosition() {
    return position;
  }
  
  private Position position = new Position(0, 0, Orientation.NORTH);
}
```

```java
public class Position {
  public Position(int x, int y, Orientation orientation) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }
  
  private Orientation left() {
    switch(orientation) {
    case NORTH: return WEST;
    case WEST:  return SOUTH;
    case SOUTH: return EAST;
    case EAST:  return NORTH;
    default: return orientation;
  }
  
  public Position turnLeft() {
    return new Position(x, y, left());
  }
  
  private Orientation right() {
    switch(orientation) {
    case NORTH: return EAST;
    case EAST:  return SOUTH;
    case SOUTH: return WEST;
    case WEST:  return NOTH;
    default: return orientation;
  }
  
  public Position turnRight() {
    return new Position(x, y, right());
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

到目前为止,设计已经充满着坏味道,已经令人窒息。
- `turnLeft`与`turnRight`的逻辑处理存在重复代码;- `RobotCleaner`的`turnLeft`, `turnRight`具有副作用; 
- 测试代码也存在明显的重复设计;- 两个`switch-case`存在明显的重复代码。

### 改善设计

在这之前所有的实现,都是为了快速地实现需求,即使重构也做得非常小,每次改动都能保证测试 的通过。但在实现需求的路上,也欠下了很多技术债务,为此在测试保护的情况下需要进一步改善即有 的设计。

#### 消除重复

先处理`Position`的两个`switch-case`的重复代码。

```java
public class Position {
  public Position(int x, int y, Orientation orientation) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }
  
  private Orientation left() {
    switch(orientation) {
    case NORTH: return WEST;
    case WEST:  return SOUTH;
    case SOUTH: return EAST;
    case EAST:  return NORTH;
    default: return orientation;
  }
  
  public Position turnLeft() {
    return new Position(x, y, left());
  }
  
  private Orientation right() {
    switch(orientation) {
    case NORTH: return EAST;
    case EAST:  return SOUTH;
    case SOUTH: return WEST;
    case WEST:  return NOTH;
    default: return orientation;
  }
  
  public Position turnRight() {
    return new Position(x, y, right());
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

通过分析代码，可以通过抽象隐藏的算法来消除重复的`switch-case`两个表驱动。`turnLeft`于`turnRight`其共同的抽象操作就是`turnTo`，依次消除两者之间的重复代码。并通过将其职责委托给`Orientation`，方便地可以计算出下一个具体的方向信息，更加符合`OO`职责的分布。

```java
public enum Orientation {
  EAST, SOUTH, WEST, NORTH;

  public Orientation turnTo(int num) {
    return values()[(ordinal() + num) % 4];
  }
}
```

```java
public class Position {
  public Position(int x, int y, Orientation orientation) {
    this.x = x;
    this.y = y;
    this.orientation = orientation;
  }
  
  private Positon turnTo(int num) {
    return new Position(x, y, orientation.turnTo(num)); 
  }
  
  public Position turnLeft() {
    return turnTo(3);
  }
  
  public Position turnRight() {
    return turnTo(1);
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

至此，存在其他更加难受和窒息的坏味道。

- `turnLeft`与`turnRight`犹如病毒一样, 并在调用链上传递, 从而使每一类都不能封闭修改;- `RobotCleaner`的`turnLeft`, `turnRight`具有副作用; 

但此刻没有更好的想法处理，我们接着往下走。



## MoveTo

### 需求

- 当`Robot`收到`FORWARD`指令后, `Robot`保持方向, 并向前移动一个坐标;- 当`Robot`收到`BACKWARD`指令后, `Robot`保持方向, 并向后移动一个坐标;

### forward 指令

迭代实现`forward`，可以得到如下的设计。

```java
@Test
public void forward_instruction() {
  robot.forward();
  
  assertThat(robot.getPosition(), equalTo(new Position(0, 1, NORTH)));
}

@Test
public void left_then_forward_instruction() {
  robot.left();
  robot.forward();
     
  assertThat(robot.getPosition(), equalTo(new Position(1, 0, EAST)));
}

@Test
public void two_times_left_then_forward_instruction() {
  robot.left();
  robot.left();
  robot.forward();
  
  assertThat(robot.getPosition(), equalTo(new Position(0, -1, SOUTH)));
}

@Test
public void three_times_left_then_forward_instruction() {
  robot.left();
  robot.left();
  robot.left();
  robot.forward();
  
  assertThat(robot.getPosition(), equalTo(new Position(-1, 0, WEST)));
}
```

```java
public class RobotCleaner {
  
  ...
  
  public void forward() {
      position = position.forward();
  }
  
  public Position getPosition() {
    return position;
  }
  
  private Position position = new Position(0, 0, Orientation.NORTH);
}
```

```java
public class Position {
  ...
    
  private int forwardX() {
    switch(orientation) {
    case EAST:  return 1;
    case SOUTH: return 0;
    case WEST:  return -1;
    case NORTH: return 0;
    default:    return 0;
    }
  }
  
  private int forwardY() {
    switch(orientation) {
    case EAST:  return 0;
    case SOUTH: return -1;
    case WEST:  return 0;
    case NORTH: return 1; 
    default:    return 0;
    }
  }
    
  public Position forward() {
    return new Position(x+forwardX(), y+forwardY(), orientation);
  }
  
  private int x, y;
  private Orientation orientation;
}
```

### backward 指令

迭代实现`forward`，可以得到如下的设计。

```java
@Test
public void backward_instruction() {
  robot.backward();
  
  assertThat(robot.getPosition(), equalTo(new Position(0, -1, NORTH)));
}

@Test
public void left_then_backward_instruction() {
  robot.left();
  robot.backward();
     
  assertThat(robot.getPosition(), equalTo(new Position(-1, 0, EAST)));
}

@Test
public void two_times_left_then_backward_instruction() {
  robot.left();
  robot.left();
  robot.backward();
  
  assertThat(robot.getPosition(), equalTo(new Position(0, 1, SOUTH)));
}

@Test
public void three_times_left_then_backward_instruction() {
  robot.left();
  robot.left();
  robot.left();
  robot.backward();
  
  assertThat(robot.getPosition(), equalTo(new Position(1, 0, WEST)));
}
```

```java
public class RobotCleaner {
  ...
  
  public void forward() {
      position = position.forward();
  }
  
  public void backward() {
      position = position.backward();
  }
  
  public Position getPosition() {
    return position;
  }
  
  private Position position = new Position(0, 0, Orientation.NORTH);
}
```

```java
public class Position {
  ...
    
  private int forwardX() {
    switch(orientation) {
    case EAST:  return 1;
    case SOUTH: return 0;
    case WEST:  return -1;
    case NORTH: return 0;
    default:    return 0;
    }
  }
  
  private int forwardY() {
    switch(orientation) {
    case EAST:  return 0;
    case SOUTH: return -1;
    case WEST:  return 0;
    case NORTH: return 1; 
    default:    return 0;
    }
  }
    
  public Position forward() {
    return new Position(x+forwardX(), y+forwardY(), orientation);
  }
    
  private int backwardX() {
    switch(orientation) {
    case EAST:  return -1;
    case SOUTH: return 0;
    case WEST:  return 1;
    case NORTH: return 0;
    default:    return 0;
    }
  }
  
  private int backwardY() {
    switch(orientation) {
    case EAST:  return 0;
    case SOUTH: return 1;
    case WEST:  return 0;
    case NORTH: return -1; 
    default:    return 0;
    }
  }
    
  public Position backward() {
    return new Position(x+backwardX(), y+backwardY(), orientation);
  }
  
  private int x, y;
  private Orientation orientation;
}
```

虽然功能实现了，但存在大量的坏味道。

- 存在大量的重复代码
- `turnLeft`与`turnRight`, `forward`于`backward`犹如病毒一样, 并在调用链上传递, 从而使每一类都不能封闭修改;- `RobotCleaner`的`turnLeft`与`turnRight`，`forward`与`backward`具有副作用; 

### 改善设计

#### 消除`forward`与`backward`的重复代码

目前为止`Position`的设计。

```java
public class Position {
  ...
    
  private int forwardX() {
    switch(orientation) {
    case EAST:  return 1;
    case SOUTH: return 0;
    case WEST:  return -1;
    case NORTH: return 0;
    default:    return 0;
    }
  }
  
  private int forwardY() {
    switch(orientation) {
    case EAST:  return 0;
    case SOUTH: return -1;
    case WEST:  return 0;
    case NORTH: return 1; 
    default:    return 0;
    }
  }
    
  public Position forward() {
    return new Position(x+forwardX(), y+forwardY(), orientation);
  }
    
  private int backwardX() {
    switch(orientation) {
    case EAST:  return -1;
    case SOUTH: return 0;
    case WEST:  return 1;
    case NORTH: return 0;
    default:    return 0;
    }
  }
  
  private int backwardY() {
    switch(orientation) {
    case EAST:  return 0;
    case SOUTH: return 1;
    case WEST:  return 0;
    case NORTH: return -1; 
    default:    return 0;
    }
  }
    
  public Position backward() {
    return new Position(x+backwardX(), y+backwardY(), orientation);
  }
  
  private int x, y;
  private Orientation orientation;
}
```

首先将`forward`与`backward`的操作提取抽象的`move`操作，而且观察到`forward`与`backward`的异同，犹如`1`与`-1`的关系。

```java
public class Position {
  ...
    
  private int xOffset() {
    switch(orientation) {
    case EAST:  return 1;
    case SOUTH: return 0;
    case WEST:  return -1;
    case NORTH: return 0;
    default:    return 0;
    }
  }
  
  private int xOffset() {
    switch(orientation) {
    case EAST:  return 0;
    case SOUTH: return -1;
    case WEST:  return 0;
    case NORTH: return 1; 
    default:    return 0;
    }
  }
   
  public Position move(int prefex) {
    return new Position(x+prefex*xOffset(), y+prefex*yOffset(), orientation);
  } 
    
  public Position forward() {
    return move(1);
  }
    
  public Position backward() {
    return move(-1);
  }
  
  private int x, y;
  private Orientation orientation;
}
```

再次发现，这两个表驱动也满足特定的算法，可归纳为一个数学公式；另外`Position.move`的算法实现有些另类，需要进一步处理。为此，接下来开始算法的重构。

首先将`(x, y)`的二元组抽象为`Point`的概念，与`Position`的设计类似，将其设计为一个`Value Object`。其中`Point.move`的提取，将其职责从`Position.move`中剥离出来，这也是将`(x, y)`的二元组抽象为`Point`的一个最大驱动力，让类的职责分布更加合理。

```java
public class Point {
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point move(int xOffset, int yOffset) {
    return new Point(x + xOffset, y + yOffset);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point) {
      Point other = (Point) obj;
      return x == other.x && y == other.y;
    }
    return false;
  }
  
  private int x;
  private int y;
}
```

然后，将**表驱动**移到`Orientation`中去，简化配置，增强表达力，另外使其职责分布更加合理，因为这个配置信息其实是属于`Orientation`相关，而非与`Position`相关的。

```java
public enum Orientation {
  EAST(1, 0), SOUTH(0, -1), WEST(-1, 0), NORTH(0, 1);

  private int xOffset;
  private int yOffset;

  private Orientation(int xOffset, int yOffset) {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public Orientation turnTo(int num) {
    return values()[(ordinal() + num) % 4];
  }

  public Point moveTo(int prefix, Point point) {
    return point.move(prefix * xOffset, prefix * yOffset);
  }
}
```

最终`Position`重构为：

```java
public class Position {
    public Position(int x, int y, Orientation orientation) {
    this(new Point(x, y), orientation);
  }

  public Position(Point point, Orientation orientation) {
    this.point = point;
    this.orientation = orientation;
  }
  
  private Positon turnTo(int num) {
    return new Position(point, orientation.turnTo(num)); 
  }
  
  public Position turnLeft() {
    return turnTo(3);
  }
  
  public Position turnRight() {
    return turnTo(1);
  }
   
  public Position move(int prefix) {
    return new Position(orientation.moveTo(prefix, point), orientation);
  } 
    
  public Position forward() {
    return move(1);
  }
    
  public Position backward() {
    return move(-1);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position other = (Position) obj;
      return point.equals(other.point) && orientation == other.orientation;
    }
    return false;
  }

  private Point point;
  private Orientation orientation;
}
```

`Position`中实现`turnLeft/turnRight`, 及其`forward/backward`随着需求增加，并没有做到封闭修改。另外其污染性也从`RobotCleaner`中传递过来，形成很大的调用链，坏味道使人非常难受，这往往说明对指令的缺乏抽象。

#### 抽象指令集

将所有操作抽象为`Instruction`接口，将所有的操作的行为进行对象化和参数化，让指令的变化得到控制。

```java
public interface Instruction {
  Position exec(Point point, Orientation orientation);
}
```

首先实现`TurnToInstruction`来表示`left`和`right`指令。

```java
public enum TurnToInstruction implements Instruction {
  LEFT(3), RIGHT(1);

  private int numOfTurnTo;
 
  private TurnToInstruction(int numOfTurnTo) {
    this.numOfTurnTo = numOfTurnTo;
  }

  @Override
  Position exec(Point point, Orientation orientation) {
      return new Position(point, orientation.turnTo(numOfTurnTo));
  }
}
```

`Orientation`的`turnTo`实现更加简单。

```java
public enum Orientation {
  EAST(1, 0), SOUTH(0, -1), WEST(-1, 0), NORTH(0, 1);

  ...
  
  public Orientation turnTo(int num) {
    return values()[(ordinal() + num) % 4];
  }
}
```

然后实现`MoveToInstruction`的类。

```java
enum MoveToInstruction implements Instruction {
  FORWARD(1), BACKWARD(-1);

  private int prefix;
 
  private MoveToInstruction(int prefix) {
    this.prefix = prefix;
  }

  @Override
  public Position exec(Point point, Orientation orientation) {
      return new Position(orientation.moveTo(prefix, point), orientation);
  }
}
```

`Orientation`最终实现为：

```java
public enum Orientation {
  EAST(1, 0), SOUTH(0, -1), WEST(-1, 0), NORTH(0, 1);

  private int xOffset;
  private int yOffset;

  private Orientation(int xOffset, int yOffset) {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public Orientation turnTo(int num) {
    return values()[(ordinal() + num) % 4];
  }

  public Point moveTo(int prefix, Point point) {
    return point.move(prefix * xOffset, prefix * yOffset);
  }
}
```

通过提取`TurnToInstruction`及其`MoveToInstruction`，从而可以将`Position`中实现`turnLeft/turnRight`, 及其`forward/backward`的职责彻底被搬迁走了。接下来，将提取的`TurnToInstruction`及其`MoveToInstruction`配置给`RobotCleaner`，并将`Position`中的`turnLeft/turnRight`, 及其`forward/backward`的职责删除掉，让`Position`转变为一个纯粹的`Value Object`。

先回顾`RobotCleaner`既有的设计。

```java
public class RobotCleaner {
  
  public void turnLeft() {
      position = position.turnLeft();
  }
  
  public void turnRight() {
      position = position.turnLeft();
  }
  
  public void forward() {
      position = position.forward();
  }
  
  public void backward() {
      position = position.backward();
  }
  
  public Position getPosition() {
    return position;
  }
  
  private Position position = new Position(0, 0, Orientation.NORTH);
}
```

按照**函数式**的思维，将`RobotCleaner`重构为无状态的；并通过`Instruction`的抽象，彻底将**行为参数化**，从而封闭**RobotCleaner**的修改。

```java
public class RobotCleaner {
  public Position exec(Position position, Instruction instruction) {
    return instruction.exec(position.point(), position.orientation());
  }
}
```

至此，重构的力度有一点大，测试用例也需要进一步修改了，但完全在控制之中。

```java
import static com.bosch.robot.TurnToInstruction.*;
import static com.bosch.robot.MoveToInstruction.*;

public class RobotCleanerTest {
  @Test
  public void left_instruction() {
    exec_instruction_at_initial(LEFT, position(0, 0, WEST));
  }
  
  @Test
  public void right_instruction() {
    exec_instruction_at_initial(RIGHT, position(0, 0, EAST));
  }
  
  @Test
  public void forward_instruction() {
    exec_instruction_at_initial(FORWARD, position(0, 1, NORTH));
  }
  
  @Test
  public void backward_instruction() {
    exec_instruction_at_initial(BACKWARD, position(0, -1, EAST));
  }
  
  ...
  
  private void exec_instruction_at_initial(Instruction instruction, Position newPosition) {
    assertThat(robot.exec(position(0, 0, NORTH), instruction), equalTo(newPosition));
  }

  private static Position position(int x, int y, Orientation orientation) {
    return new Position(x, y, orientation);
  }

  private RobotCleaner robot = new RobotCleaner();
}
```

## Round

### 需求

当`Robot`收到`ROUND`指令后, `Robot``顺时针旋转`180`度掉头; 例`如`Robot`原来在`(0, 0, N)`,执行`ROUND`指令后,新的位置在 `(0, 0, S)`;

## MoveTo(n)

### 需求

- 当`Robot`收到`FORWARD(n)`指令后, `Robot`保持方向,向前移动`n`个坐标; 
- 当`Robot`收到`BACKWARD(n)`指令后, `Robot`保持方向,向后移动`n`个坐标; 
- 其中`n`在`[1..10]`之间;例如:
-`Robot`原来在`(0, 0, N)`,执行`FORWARD(10)`指令后,新的位置在`(0, 10, N)`;-`Robot`原来在`(0, 0, N)`,执行`BACKWARD(10)`指令后,新的位置在`(0, -10, N)`;

## Sequential

### 需求

当`Robot`收到一系列组合指令时,能够依次按指令完成相应的动作。例如收到指令序列:`[LEFT, FORWARD, RIGHT, BACKWARD, ROUND, FORWARD(2)]`,将依次执行:向左转`90`度;保持方向并向前移动一个坐标;向右转`90`度;保持方向并向后退一个坐标;顺时针旋转`180`度掉头;保持方向向前移动`2`个坐标。

## Repeat

### 需求

当`Robot`收到`REAPT(instruction, n)`指令后,它会循环执行`instruction`指令`n`次。原来在`(0, 0, N)`, 执行 `REAPT(FORWARD(2), 2)`指令后, 新的位置为`(0, 4, NORTH)`; 其中`n`在`[1..10]`之间。


