from ..base import AppTasks, TaskItem

from .eval_1 import validate_task_one
from .eval_2 import validate_task_two
from .eval_3 import validate_task_three
from .eval_4 import validate_task_four
from .eval_5 import validate_task_five
from .eval_6 import validate_task_six
from .eval_7 import validate_task_seven
from .eval_8 import validate_task_eight
from .eval_9 import validate_task_nine
from .eval_10 import validate_task_ten
from .eval_11 import validate_task_eleven
from .eval_12 import validate_task_twelve
from .eval_13 import validate_task_thirteen
from .eval_14 import validate_task_fourteen
from .eval_15 import validate_task_fifteen
from .eval_16 import validate_task_sixteen
from .eval_17 import validate_task_seventeen
from .eval_18 import validate_task_eighteen
from .eval_19 import validate_task_nineteen
from .eval_20 import validate_task_twenty
from .eval_21 import validate_task_twenty_one
from .eval_22 import validate_task_twenty_two
from .eval_23 import validate_task_twenty_three
from .eval_24 import validate_task_twenty_four
from .eval_25 import validate_task_twenty_five
from .eval_26 import validate_task_twenty_six
from .eval_27 import validate_task_twenty_seven
from .eval_28 import validate_task_twenty_eight
from .eval_29 import validate_task_twenty_nine
from .eval_30 import validate_task_thirty


UBEREATS_TASKS = AppTasks(
    package_name="com.example.ubereats_sim",
    task_items=[
        TaskItem(
            instruction="我想吃麦当劳，搜一下麦当劳看附近有没有",
            verify_func=validate_task_one,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="帮我买一份麦当劳的薯饼，配送信息默认",
            verify_func=validate_task_two,
            human_steps=5,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="新增一个house信息，地点为jianghanlu",
            verify_func=validate_task_three,
            human_steps=5,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="把购物车的商品都买了，配送信息默认",
            verify_func=validate_task_four,
            human_steps=3,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="打开livelocation选项",
            verify_func=validate_task_five,
            human_steps=4,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="帮我打一个从jianghanlu到jiedaokou的车，车型选最便宜的",
            verify_func=validate_task_six,
            human_steps=6,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="我想到店吃麦当劳，搜一下附近有pickup点吗",
            verify_func=validate_task_seven,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="帮我点一份麦当劳的薯饼和双吉汉堡，时间选择明天中午12点，其他配送信息默认",
            verify_func=validate_task_eight,
            human_steps=8,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="打开hearing中的hard of hearing选项",
            verify_func=validate_task_nine,
            human_steps=4,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="看一下附近麦当劳最便宜的商品是什么",
            verify_func=validate_task_ten,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="把购物车里的商品全买下来要花多少钱",
            verify_func=validate_task_eleven,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下3.29之前我点外卖花了多少钱",
            verify_func=validate_task_twelve,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="首页前八个商家中，免配送费的商家有几个",
            verify_func=validate_task_thirteen,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下我uber账户里还有多少钱",
            verify_func=validate_task_fourteen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下我以前点过的商家里，有几个是卖汉堡披萨的",
            verify_func=validate_task_fifteen,
            human_steps=5,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="问一下我刚下的订单预计多少送达",
            verify_func=validate_task_sixteen,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下我3.17-3.22吃汉堡披萨的店花了多少钱",
            verify_func=validate_task_seventeen,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="算一下我从3.17点饮品至今花了多少钱",
            verify_func=validate_task_eighteen,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="感觉少了一点功能，我现在app的版本号是多少？",
            verify_func=validate_task_nineteen,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="首页的麦当劳里，哪个汉堡最便宜",
            verify_func=validate_task_twenty,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="首页Matchaful最便宜的商品比HAWA SMOOTHIES最便宜的商品价格相差多少？我现在很贫穷。",
            verify_func=validate_task_twenty_one,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="把麦当劳的Hash Brown和Double Cheeseburger加入购物车，再把汉堡王的Whopper加入购物车",
            verify_func=validate_task_twenty_two,
            human_steps=9,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="帮我点一份中午12点送达的午餐，我要喝HAWA SMOOTHIES的Mango Pineapple Smoothie和一份7-Eleven的Turkey Sandwich",
            verify_func=validate_task_twenty_three,
            human_steps=10,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="看看我past purchases，我最喜欢吃的食物是Burger还是Pizza?",
            verify_func=validate_task_twenty_four,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="将首页前八家显示的店铺里有卖汉堡的店铺加入收藏",
            verify_func=validate_task_twenty_five,
            human_steps=6,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="算一算我past purchases买汉堡花了多少钱，不算运费。",
            verify_func=validate_task_twenty_six,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下我past purchases买披萨花了多少钱，不算运费。",
            verify_func=validate_task_twenty_seven,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看下我past purchases中买饮品花了多少钱",
            verify_func=validate_task_twenty_eight,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看下我past purchases中买抹茶食品花了多少钱",
            verify_func=validate_task_twenty_nine,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction='给刚下的订单的配送员发消息问"How long will it take to arrive?"',
            verify_func=validate_task_thirty,
            human_steps=5,
            is_reasoning=False,
        ),
    ],
)
