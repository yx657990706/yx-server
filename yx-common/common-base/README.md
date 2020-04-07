#### 1.JWT适用场景
a.向Web应用传递一些非敏感信息(如加好友的操作，下订单的操作)
b.用于设计用户认证和授权系统
C.Web应用的单点登录

#### 2.JEW的token刷新
1.刷新时，需要传入oldoken，并且oldToken未过期。刷新后会生成一个newToken。前端存储newToken，之后的操作token
均需要使用新的token。（存在一个问题。a。不缓存token，则在token有效期内，新旧token都有效 b。缓存token并判断，
则其他未到期的oldToken均会失效。根据需要抉择。）

2.过期token需要刷新时。可采用验签的方式。即产生token时，同时根据用户id生成一个消息摘要。
刷新token时，需要传入该摘要信息。


#### 3.自定义token生成
1.选取加密算法，将用户信息加密生成token。服务器端缓存token和过期时间，token续期方便。
续期方案采用heartbeat方式。（例如登录后每5分钟检测一次，token重置存活时间为30分钟）

#### 4.个人总结
1.token需要在意token生成新的，可用自定义toekn（微服务整体访问还是采用自定义，心跳续期）
2.15分钟内有效类似这种场景可以使用jwt

### 参考

[Token - 服务端身份验证的流行方案](https://www.jianshu.com/p/e0ac7c3067eb)

[JSON Web Token - 在Web应用间安全地传递信息](http://blog.leapoahead.com/2015/09/06/understanding-jwt/)

[JWT怎么给Token续期](https://segmentfault.com/q/1010000020925901)
