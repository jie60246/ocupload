CREATE TABLE `t_area` (
  `C_ID` varchar(20) NOT NULL,
  `C_PROVINCE` varchar(20) DEFAULT NULL,
  `C_CITY` varchar(20) DEFAULT NULL,
  `C_DISTRICT` varchar(20) DEFAULT NULL,
  `C_POSTCODE` varchar(20) DEFAULT NULL,
  `C_CITYCODE` varchar(20) DEFAULT NULL,
  `C_SHORTCODE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-Dfile.encoding=UTF-8

Http协议中数字错误的定义：
Status状态代码有三位数字组成，第一个数字定义了响应的类别，且有五种可能取值：

1xx:指示信息--表示请求已接收，继续处理。

2xx:成功--表示请求已被成功接收、理解、接受。

3xx:重定向--要完成请求必须进行更进一步的操作。

4xx:客户端错误--请求有语法错误或请求无法实现。

5xx:服务器端错误--服务器未能实现合法的请求



常见状态代码说明：

200 OK：客户端请求成功

400 Bad Request：客户端请求有语法错误，不能被服务器所理解

401 Unauthorized：请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用

403 Forbidden：服务器收到请求，但是拒绝提供服务

404 Not Found：请求资源不存在

500 Internal Server Error：服务器发生不可预期的错误

503 Server Unavailable：服务器当前不能出来客户端的请求，一段时间后可能恢复正常。
