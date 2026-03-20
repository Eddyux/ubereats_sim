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


## ???????

### McDonald's
- Hash Browns
- Steak & Egg McMuffin
- Big Breakfast with Hotcakes
- Sausage Egg McMuffin Meal
- Egg McMuffin
- Big Mac Meal
- Quarter Pounder w/ Cheese Meal
- 10 pc Chicken McNuggets
- McChicken
- Filet-O-Fish
- Medium French Fries
- Oreo McFlurry
- Hash Browns Combo
- Steak & Egg McMuffin Combo
- Big Breakfast with Hotcakes Combo
- Sausage Egg McMuffin Meal Combo

### VINEYARD
- Grilled Chicken Sandwich
- Steak Frites
- Seared Salmon Bowl
- Classic Cheeseburger
- Roasted Veggie Flatbread
- Buffalo Wings
- Mac & Cheese
- Tomato Basil Soup
- Chocolate Brownie
- Lemon Iced Tea
- Grilled Chicken Sandwich Combo
- Steak Frites Combo
- Seared Salmon Bowl Combo
- Classic Cheeseburger Combo
- Roasted Veggie Flatbread Combo
- Buffalo Wings Combo

### Yunnan Rice Noodle
- Crossing-the-Bridge Noodles
- Spicy Beef Rice Noodles
- Tomato Broth Noodles
- Pickled Pepper Noodles
- Braised Beef Rice
- Pork Dumplings
- Scallion Pancake
- Cold Noodle Salad
- Cucumber Salad
- Soy Milk
- Crossing-the-Bridge Noodles Combo
- Spicy Beef Rice Noodles Combo
- Tomato Broth Noodles Combo
- Pickled Pepper Noodles Combo
- Braised Beef Rice Combo
- Pork Dumplings Combo

### Benvenuto Cafe
- Margherita Pizza
- Penne alla Vodka
- Chicken Parmigiana
- Fettuccine Alfredo
- Truffle Mushroom Risotto
- Caesar Salad
- Minestrone Soup
- Garlic Bread
- Tiramisu
- Espresso
- Margherita Pizza Combo
- Penne alla Vodka Combo
- Chicken Parmigiana Combo
- Fettuccine Alfredo Combo
- Truffle Mushroom Risotto Combo
- Caesar Salad Combo

### Burger King
- Whopper
- Whopper Meal
- Bacon King
- Double Cheeseburger
- Chicken Fries
- Original Chicken Sandwich
- Impossible Whopper
- Mozzarella Fries
- Onion Rings
- Hershey Pie
- Whopper Combo
- Whopper Meal Combo
- Bacon King Combo
- Double Cheeseburger Combo
- Chicken Fries Combo
- Original Chicken Sandwich Combo

### 7-Eleven
- Spicy Chicken Roller
- Big Bite Hot Dog
- Nachos & Cheese
- Mozzarella Sticks
- Taquito Combo
- Buffalo Chicken Pizza Slice
- Turkey Sandwich
- Chocolate Chip Cookie
- Slurpee Large
- Cold Brew Coffee
- Spicy Chicken Roller Combo
- Big Bite Hot Dog Combo
- Nachos & Cheese Combo
- Mozzarella Sticks Combo
- Taquito Combo
- Buffalo Chicken Pizza Slice Combo

### Matchaful
- Ceremonial Matcha Latte
- Strawberry Matcha
- Yuzu Matcha Tonic
- Coconut Matcha Cloud
- Ube Matcha Latte
- Matcha Banana Smoothie
- Matcha Chia Pudding
- Blueberry Oat Bowl
- Avocado Toast
- Sparkling Water
- Ceremonial Matcha Latte Combo
- Strawberry Matcha Combo
- Yuzu Matcha Tonic Combo
- Coconut Matcha Cloud Combo
- Ube Matcha Latte Combo
- Matcha Banana Smoothie Combo

### HAWA SMOOTHIES
- Acai Power Bowl
- Mango Pineapple Smoothie
- Strawberry Banana Smoothie
- Green Detox Smoothie
- Peanut Butter Protein
- Dragon Fruit Bowl
- Tropical Granola Bowl
- Fresh Orange Juice
- Cold Pressed Green Juice
- Iced Hibiscus Tea
- Acai Power Bowl Combo
- Mango Pineapple Smoothie Combo
- Strawberry Banana Smoothie Combo
- Green Detox Smoothie Combo
- Peanut Butter Protein Combo
- Dragon Fruit Bowl Combo

### Domino's
- Hand Tossed Pepperoni Pizza
- Brooklyn Style Cheese Pizza
- BBQ Chicken Pizza
- Philly Cheese Steak Pizza
- Spinach & Feta Pizza
- Chicken Alfredo Pasta
- Boneless Chicken
- Stuffed Cheesy Bread
- Parmesan Bread Bites
- Chocolate Lava Crunch Cakes
- Hand Tossed Pepperoni Pizza Combo
- Brooklyn Style Cheese Pizza Combo
- BBQ Chicken Pizza Combo
- Philly Cheese Steak Pizza Combo
- Spinach & Feta Pizza Combo
- Chicken Alfredo Pasta Combo

## Changelog
### 2026-03-21
- Merchant page hero image now fills the entire top frame.

## Grocery and Convenience Merchants (Data)

### Grocery
- Whole Foods Market
- Trader Joe's
- ALDI
- H Mart
- Mitsuwa Marketplace
- Eataly Market
- Fairway Market
- FreshDirect

### Convenience
- 7-Eleven
- CVS
- Walgreens
- Duane Reade
- GoPuff
- Rite Aid
- Family Dollar
- Dollar General

### Data Files
- app/src/main/assets/data/grocery_merchants.json
- app/src/main/assets/data/convenience_merchants.json

## Data Integration (2026-03-21)
- Added `app/src/main/assets/data/grocery_merchants.json`.
- Added `app/src/main/assets/data/convenience_merchants.json`.
- Grocery tab now reads grocery merchants from `grocery_merchants.json`.
- Convenience tab now reads merchants from `convenience_merchants.json`.
- Product images from `app/src/main/assets/McDonald's` and `app/src/main/assets/VINEYARD` are now used in merchant item cards and product detail page.
- Grocery merchant images in `app/src/main/assets/Grocery` are now displayed in Grocery cards and Featured deals.
