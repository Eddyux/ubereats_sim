# Uber Eats 复刻项目

这是一个使用 Android Jetpack Compose 开发的 Uber Eats 应用复刻项目。

## 项目结构

```
app/src/main/java/com/example/ubereats_sim/
├── model/              # 数据模型层
│   ├── CartItem.kt
│   ├── Restaurant.kt   # Restaurant + NearbyStore
│   ├── UserProfile.kt  # UserProfile + MenuItem
│   └── DataLoader.kt
├── presenter/          # MVP Presenter层
│   ├── CartPresenter.kt
│   ├── HomePresenter.kt
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
