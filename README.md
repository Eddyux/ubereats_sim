# Uber Eats 复刻项目

这是一个使用 Android Jetpack Compose 开发的 Uber Eats 应用复刻项目。

## 项目结构

```
app/src/main/java/com/example/ubereats_sim/
├── model/              # 数据模型层
│   ├── CartItem.kt
│   ├── Order.kt       # Order + OrderItem
│   ├── Restaurant.kt   # Restaurant + NearbyStore
│   ├── UserProfile.kt  # UserProfile + MenuItem
│   └── DataLoader.kt
├── presenter/          # MVP Presenter层
│   ├── CartPresenter.kt
│   ├── HomePresenter.kt
│   ├── OrderPresenter.kt
│   └── ProfilePresenter.kt
├── view/              # View层（Compose UI）
│   ├── HomeScreen.kt       # 首页主框架
│   ├── HomeComponents.kt   # 首页组件
│   ├── CartScreen.kt
│   └── ProfileScreen.kt
├── ui/theme/          # 主题配置
└── MainActivity.kt    # 主Activity
```

## 已实现功能

### 1. 首页 (HomeScreen)
- 绿色顶部导航栏（城市选择+通知图标）
- Tab切换（全部、行程、生鲜杂货、便利店）
- 横向业务分类（外出就餐、浏览、寿司、球赛日、披萨）
- 筛选标签（Uber One、自取、优惠、30分）
- Uber Eats 优食推荐商家（双列卡片）
- 经济实惠的美食（双列卡片）
- 查看附近店铺（图标网格）
- 快速派送（双列卡片）
- 所在街区的热门商家（双列卡片）
- 底部绿色促销横幅

### 2. 购物车页面 (CartScreen)
- 顶部标题和订单按钮
- 商家卡片展示：店铺头像、商品数量、总金额、派送地址
- 查看购物车按钮（黑色）
- 查看店铺按钮（灰色边框）

### 3. 个人中心页面 (ProfileScreen)
- 用户信息展示（用户名、验证状态、头像）
- 快捷功能按钮：我的收藏、钱包、订单
- 完整功能菜单列表（18项）：
  家庭和青少年、列表、行程、优惠、发送礼品、帮助、已保存群组、
  设置您的商务账户、合作伙伴奖励、Uber One、隐私、无障碍服务、
  通讯、提供接载或派送服务以赚取收入、语音指令设置、管理优步账号、
  过敏设置、简介
- 底部版本号显示

### 4. 底部导航栏
- 首页、定位（占位）、搜索（占位）、购物车（带角标）、我的

## 数据文件

数据存储在 `app/src/main/assets/data/` 目录：
- `restaurants.json` - 商家数据（含附近店铺）
- `orders.json` - 订单数据
- `cart.json` - 购物车数据
- `user_profile.json` - 用户信息

## 技术栈

- Kotlin + Jetpack Compose + Material Design 3
- MVP 架构模式
- Gson (JSON解析)

## 构建项目

```bash
./gradlew assembleDebug
```

## 更新日志

### 2026-03-19
- 初始化项目结构
- 实现首页（绿色顶栏、Tab、多section商家展示、附近店铺、促销横幅）
- 实现购物车页面
- 实现个人中心页面（完整18项菜单、版本号）
- 实现底部导航栏
- 拆分HomeScreen为HomeScreen+HomeComponents，控制文件行数
- 所有可点击元素（商家卡片、分类图标、筛选标签、section标题、附近店铺、个人中心菜单项、快捷按钮、购物车按钮等）均跳转到"开发中"页面
- 开发中页面含返回按钮，底部导航栏在子页面时隐藏


## 2026-03-22 New Pages

### 18. Payment Screen (PaymentScreen.kt)
- Pay with 页面，顶部关闭按钮和大标题
- Personal / Business 账户类型标签切换
- Uber balances 区域，含开关和 Uber Cash 余额显示
- Payment methods 区域，含 Add payment method 入口
- Vouchers 区域，含 Add voucher code 入口
- 从个人中心 Wallet 入口进入

### 19. Privacy Screen (PrivacyScreen.kt)
- Privacy Center 隐私中心页面
- Your data and privacy at Uber 双列卡片（查看使用总结、请求个人数据）
- Ads and Data 区域（Personalized Offers）
- Location sharing 区域（Live location）
- Account security 区域（Account Deletion）
- How do we approach privacy at Uber 区域
- 从个人中心 Privacy 入口进入

### 20. Privacy Live Location Screen (PrivacyLiveLocationScreen.kt)
- Live location sharing 实时位置共享设置页面
- 配送员位置共享说明文案
- Live location sharing with couriers 开关设置项
- Learn more 了解更多按钮
- 从 Privacy 页面 Live location 入口进入

### 21. Favorites Feature
- 在主页商家卡片上添加爱心图标（商家名称右边）
- 点击爱心可切换收藏/取消收藏状态
- 收藏状态为红色实心爱心，未收藏为灰色空心爱心

### 22. My Favorites Screen (MyFavoritesScreen.kt)
- Your Favorites 收藏页面
- Recently added 区域展示已收藏的商家卡片
- 商家卡片包含商品图、评分、配送费、预计送达时间、优惠标签
- 无收藏时显示空状态提示
- 从个人中心 Favorites 入口进入

### 23. Accessibility Screen (AccessibilityScreen.kt)
- Accessibility 无障碍设置页面
- Hearing 听力设置项（可跳转详情）
- Communication settings 沟通设置项
- 从个人中心 Accessibility 入口进入

### 24. Hearing Screen (HearingScreen.kt)
- Hearing 听力设置详情页
- 顶部插画区域��说明文案
- 三个单选选项：I'm deaf / I'm hard of hearing / I'm not deaf or hard of hearing
- 默认选中第三项
- 从 Accessibility 页面 Hearing 入口进入

## 2026-03-24 New Pages

### 26. Orders Screen (OrdersScreen.kt) - 重构
- 订单页面重构，参考 orders_yiwang 截图
- 顶部显示"订单"标题，右侧有通知铃铛和购物车图标
- 两个标签切换："曾经买过的商品"和"以往订单"
- "活跃"区域：绿色卡片展示进行中订单，含商家头像、优惠信息、金额、预计到达时间、"追踪"按钮
- "历史记录"区域：列表展示过往订单，每个订单含商家头像、商家名称、日期、金额、商品数、"查看商店"按钮

### 27. Order Detail Screen (OrderDetailScreen.kt)
- 进行中订单配送追踪页面，参考 orderdetail 截图
- 顶部关闭、分享、Help 按钮
- "Picking up your order..." 标题，预计送达时间、最晚送达时间
- 进度条显示配送进度
- 地图区域显示商家位置和配送状态
- 配送员信息卡片：头像、姓名、评分、车型
- 交互按钮：电话、发消息、小费
- 从订单列表页面活跃订单"追踪"按钮进入

### 28. Order History Detail Screen (OrderHistoryDetailScreen.kt)
- 历史订单详情页面，参考 order_yiwang1 截图
- 顶部返回按钮、订单编号、帮助按钮
- 商家信息：Logo、名称、"订单已完成"状态、完成日期时间
- 五星评分区域
- "您的订单"商品明细列表：数量、商品名、价格
- 总计金额
- 从订单列表页面历史订单点击进入

#### 订单数据结构（更新）
```json
{
  "orders": [
    {
      "id": "ORD000",
      "merchantName": "McDonald's",
      "merchantLogo": "🍔",
      "orderDate": "2026-03-24",
      "orderTime": "21:15",
      "totalAmount": 28.50,
      "status": "In Progress",
      "estimatedArrival": "9:45 PM",
      "latestArrival": "10:15 PM",
      "deliveryStatus": "Wrapping up",
      "driverName": "Sayda",
      "driverRating": "92%",
      "driverVehicle": "Toyota Camry",
      "merchantAddress": "262 Canal St",
      "deliveryAddress": "123 Main St, New York",
      "promoMessage": "$5 off $20+",
      "items": [...]
    },
    {
      "id": "ORD001",
      "merchantName": "McDonald's",
      "status": "Delivered",
      ...
    }
  ]
}
```

### 29. 历史订单详情页优化
- 移除右上角铃铛和购物车图标，仅保留返回按钮、订单编号、帮助按钮

### 30. 进行中订单地图图片
- 订单追踪页面的地图区域使用 `assets/orderlocation.png` 图片替代占位符
- 商家名称和配送状态浮层覆盖在地图底部

### 31. Wallet Screen (WalletScreen.kt)
- Wallet 钱包页面，参考 wallet.jpg
- 顶部返回按钮和 "Wallet" 大标题
- Uber balances 区域，显示 CN¥0.00 余额，带跳转箭头
- Send a gift 区域，含说明文案和 "Send a gift" 按钮
- Payment methods 区域，含 "Add payment method" 按钮
- Vouchers 区域，显示 Received vouchers 数量，含 "Add voucher code" 入口
- In-Store Offers 区域，含 "Offers" 入口
- 从个人中心 Wallet 按钮进入
- 原 PaymentScreen（Pay with）改为从 checkout 流程进入

### 32. Promotions Screen (PromotionsScreen.kt)
- Promotions 优惠页面，参考 promotions.jpg
- 顶部返回按钮和 "Promotions" 标题
- "Enter promo code" 输入框
- "$12 Meal Deals" 优惠卡片，含 "$0 Delivery Fee" 标签、说明文案、红色 PROMO 标识
- "Shop now" 和 "Details" 按钮
- 底部绿色提示栏："Get the best deals in your city in a matter of seconds"
- 从首页促销横幅入口进入

### 33. Orders pages language update
- 订单页面顶部加入返回按钮
- OrdersScreen、OrderHistoryDetailScreen 中文改为英文

### 34. Checkout Screen (CheckoutScreen.kt)
- Checkout 结账页面，参考 checkout.jpg
- 顶部返回按钮和 "Checkout" 标题
- Delivery details 区域（地址、配送时间）
- Payment 区域，显示 yx666 账户，可跳转 Pay with 页面
- Order summary 区域（Subtotal、Delivery Fee、Service Fee、Total）
- "Place order" 下单按钮
- PaymentScreen 中 Payment methods 新增 yx666 账户，点击跳转 Checkout
- ViewCartScreen "Go to checkout" 跳转到 Checkout 页面

### 35. Order flow integration
- 下单后自动创建新订单并添加到订单列表（状态为 In Progress）
- 下单后清空对应商家的购物车商品
- 下单后自动跳转到订单列表页面
- 订单数据通过 LocalOrders CompositionLocal 在全局共享
- 购物车页面商家图片使用 `assets/dianpu/` 中的图片

## 2026-03-25 New Pages

### 36. Bell icon navigates to Promotions
- 首页绿色导航栏和 Rides Tab 顶部的铃铛图标点击后跳转到 Promotions 页面

### 37. Ride Location Screen (RideLocationScreen.kt)
- 从 Rides Tab 点击 "Pickup location" 进入
- 顶部返回按钮和搜索输入框（可输入/清除）
- Saved places 区域：Home（含地址）、Work（Add work）
- Suggested 区域：推荐上车点列表（Times Square、Penn Station、Grand Central Terminal 等）

### 38. Settings Screen (SettingsScreen.kt)
- 从 Profile 页面右上角头像点击进入
- 顶部返回按钮和 "Settings" 大标题
- 用户头像、姓名居中展示，"EDIT ACCOUNT" 编辑按钮
- Saved places 区域：Home（Add Home）、Work（Add Work），点击可跳转
- Switch account 切换账户选项
- 底部红色 Sign out 退出登录选项

### 39. Settings Home Screen (SettingsHomeScreen.kt)
- 从 Settings 页面 Home 项进入
- 顶部返回按钮和 Skip 按钮
- 建筑类型插画区域
- "Choose your building" 标题和说明文案
- 五个建筑类型选项：House、Apartment、Office、Hotel、Other
- 每个选项含图标、名称、描述和跳转箭头
- 点击选项跳转到 SettingsHomeSet 地址详情页

### 40. Settings Home Set Screen (SettingsHomeSetScreen.kt)
- 从 SettingsHome 页面选择建筑类型后进入
- 顶部返回按钮和 "Address details" 标题
- 地图区域展示（使用 location_map.png），含 "Edit pin" 按钮
- 完整地址信息展示
- Building type、Apt/Suite/Floor、Business/Building name 输入框
- Dropoff options 配送选项（Meet at my door）
- Delivery instructions 配送说明输入框
- Add photos 添加照片选项
- Address label 地址标签选择器（Home/Work/Other）
- 底部 "Save and continue" 黑色保存按钮

## 2026-03-26 Updates

### Task 40. Profile avatar navigates to Settings
- 个人中心页面右上角头像改为可点击，点击跳转到 Settings 页面
- 从 ProfilePresenter 菜单列表中移除底部 Settings 选项

### Task 41. SettingsHomeScreen redesigned as "Choose your building"
- 重新设计为建筑类型选择页面，含 5 种建筑类型选项
- 顶部增加 Skip 按钮

### Task 42. SettingsHomeSetScreen redesigned as "Address details"
- 重新设计为地址详情编辑页面
- 拆分为独立文件 SettingsHomeSetScreen.kt
- 新增 Building type、Dropoff options、Address label 等字段

### Task 43. SettingsScreen redesigned
- 重新设计为简洁的设置页面：用户信息 + Saved places + Sign out

### Task 45. Remove bell and cart icons from Orders
- 移除订单页面顶部右侧的通知铃铛和购物车图标，仅保留返回按钮和 "Orders" 标题

### Task 46. Past purchases tab with data
- 订单页面 "Past purchases" 标签页新增内容，展示历史订单中的商品
- 每个商品显示：商品图片（使用已有产品图片）、商品名、商家名、价格、"Add" 按钮
- 新增更多历史订单数据：VINEYARD、Matchaful、HAWA SMOOTHIES、Benvenuto Cafe 的订单
- 所有商品图片（assets 中 127 张）等比例压缩至原来 1/4 大小（2048x2048 → 1024x1024）

### Task 47. Use merchant images for Past Orders
- 订单列表页 "Past orders" 中的商家图标从 emoji 改为使用 `dianpu/`、`Grocery/`、`Convenience/` 中的实际商家图片
- 历史订单详情页 (OrderHistoryDetailScreen) 中的商家图标同步替换为实际商家图片
- 无图片的商家回退到 emoji 显示

### Task 48. CheckoutScreen redesigned
- 重新设计结账页面，参考 checkout.jpg
- 顶部地图区域显示配送地址 "New York, NY"
- "Meet at my door" 配送方式选项
- 联系电话显示
- Delivery time 配送时间选择：Standard（含时间范围）/ Schedule 两个选项
- Order Summary 订单摘要：商家名 + 商品数量，带跳转箭头
- Add promo code 优惠码输入
- 价格明细：Subtotal、Delivery Fee、Taxes & Other Fees、Total（动态计算）
- Personal 账户类型标识
- 隐私协议提示文案
- 底部优惠横幅 + "Next" 按钮（跳转到支付页面）

### Task 49. PaymentScreen flow update
- 结账流程改为：ViewCart → Checkout（Next）→ Pay with → 点击 yx666 → 支付成功弹窗 → Confirm → 下单并跳转 Orders
- PaymentScreen 点击 yx666 账户弹出 "Payment Successful" 对话框
- 对话框 "Confirm" 按钮确认后自动下单并跳转到订单列表页
- 移除 PaymentScreen 中直接跳转回 Checkout 的旧逻辑

### Task 50. Fix Rides tab losing state after location selection
- 修复 Rides 页面选择 Pickup/Dropoff 地点后自动跳回 All 标签的 bug
- 将 HomeScreen 内部的 selectedTab 状态提升到 MainScreen 级别
- 导航返回后 Home 的 tab 选中状态得以保持

### Task 51. Choose a ride screen (ChooseRideScreen.kt)
- 新增叫车选择页面，参考 chooseride.jpg
- 从 Rides 标签选择 Dropoff location 后自动跳转到此页面
- 顶部返回按钮 + 地图区域显示目的地和预计到达时间
- "Pickup now" 和 "For me" 下拉选项
- 车型选择列表，按 Popular / Economy / Premium / More 分类
- 各车型含图标、名称、价格、等待时长、行程时长、标签（如 Faster）
- 选中车型高亮显示（黑色边框）
- 底部 Personal 账户标识 + "Request [车型]" 确认按钮
- 点击确认弹出 "Ride Requested" 对话框，确认后返回

