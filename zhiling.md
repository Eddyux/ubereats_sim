序号	ubereats指令设计	检验方法	是否推理任务
1	我想吃麦当劳，搜一下麦当劳看附近有没有	是否回答"有"yes	1
2	帮我买一份麦当劳的薯饼，配送信息默认	检验是否购买麦当劳薯饼商品，到付款那步
3	新增一个house信息，地点为jianghanlu	检测Address details页面是否添加了jianghanlu的地址并点击save and continue
4	把购物车的商品都买了，配送信息默认	检查购物车是否都空了就行
5	打开livelocation选项	检查livelocation是否开启
6	帮我打一个从jianghanlu到jiedaokou的车，车型选最便宜的	检查是否从jianghanlu打车去jiedaokou并选择share车型点击request share
7	我想到店吃麦当劳，搜一下附近有pickup点吗	是否点击location并搜索mcdonald's或麦当劳,然后是否回答"有"或"yes"，不区分大小写	1
8	帮我点一份麦当劳的薯饼和双吉汉堡，时间选择明天中午12点，其他配送信息默认	检测是否点了薯饼和双吉汉堡，配送信息选明天12点并支付确认
9	打开hearing中的hard of hearing选项	检查是否打开hard of hearing选项
10	看一下附近麦当劳最便宜的商品是什么	是否返回"Hash Browns"	1
11	把购物车里的商品全买下来要花多少钱	检查是否返回"20.19"	1
12	看一下3.29之前我点外卖花了多少钱	检查是否返回"201.22"	1
13	首页前八个商家中，免配送费的商家有几个	检查是否返回"八"或者"8"或者"Eight"或者"eight"	1
14	看一下我uber账户里还有多少钱	检查是否返回"0"	1
15	看一下我以前点过的商家里，有几个是卖汉堡披萨的	检查是否返回“5”或"五"	1
16	问一下我刚下的订单预计多少送达	检查是否返回“9:45”	1
17	看一下我3.17-3.22吃汉堡披萨的店花了多少钱	检查是否返回"116.24"	1
18	算一下我从3.17点饮品至今花了多少钱	检查是否返回"62.98"	1
19	感觉少了一点功能，我现在app的版本号是多少？	检查是否返回“v6.311.10000”	1
20	首页的麦当劳里，哪个汉堡最便宜	检查是否返回"McChiken"	1
21	首页Matchaful最便宜的商品比HAWA SMOOTHIES最便宜的商品价格相差多少？我现在很贫穷。	检查是否返回"1.3"	1
22	把麦当劳的Hash Brown和Double Cheeseburger加入购物车，再把汉堡王的Whopper加入购物车	检查是否添加Hash Brown、Double Cheeseburger和Whopper进购物车
23	帮我点一份中午12点送达的午餐，我要喝HAWA SMOOTHIES的Mango Pineapple Smoothie和一份7-Eleven的Turkey Sandwich	检查订单里是否有这两个商品，配送时间点是明天中午12点
24	看看我past purchases，我最喜欢吃的食物是Burger还是Pizza?	检查是否返回"Burger"	1
25	将首页前八家显示的店铺里有卖汉堡的店铺加入收藏	检查是否收藏McDonald's、VINEYARD、Burger King
26	算一算我past puchases买汉堡花了多少钱，不算运费。	检查是否返回"32.17"	1
27	看一下我past purchases买披萨花了多少钱，不算运费。	检查是否返回"18.99"	1
28	看下我past purchases中买饮品花了多少钱	检查是否返回"26.49"	1
29	看下我past purchases中买抹茶食品花了多少钱	检查是否返回"14"	1
30	给刚下的订单的配送员发消息问"How long will it take to arrive?"	检查是否在send message页面发送"How long will it take to arrive?"		要新写一个sendmessages页面，点击track页面的sendmessages跳转
