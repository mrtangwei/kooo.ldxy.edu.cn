const Login = resolve => require(['./views/Login.vue'], resolve)
const NotFound = resolve => require([ './views/404.vue'], resolve)
const Home = resolve => require([ './views/Home.vue'], resolve)
const Main = resolve => require([ './views/Main.vue'], resolve)

const data = resolve => require([ './views/my.vue'], resolve)



const echarts = resolve => require([ './views/echarts.vue'], resolve)
const Welcome = resolve => require([ './views/Welcome.vue'], resolve)

const System = resolve => require([ './views/system/system.vue'], resolve)
const Role = resolve => require([ './views/system/role.vue'], resolve)
const Resource = resolve => require([ './views/system/resource.vue'], resolve)
const Setting = resolve => require([ './views/system/setting.vue'], resolve)
const Version = resolve => require(['./views//system/version.vue'], resolve)


const Admin = resolve => require([ './views/user/admin.vue'], resolve)

//充值
const Recharge = resolve => require([ './views/recharge/Recharge.vue'], resolve)
const Receive = resolve => require([ './views/recharge/Receive.vue'], resolve)

//购买金币
const BuyCoin = resolve => require([ './views/coin/Coin.vue'], resolve)

//抽奖活动
const Lottery = resolve => require([ './views/lottery/Lottery.vue'], resolve)
const LotteryTimeLimit = resolve => require([ './views/lottery/LotteryTimeLimit.vue'], resolve)

//演出表演： 巡回演出、商业演出、演出排练室、公演
const TourShow = resolve => require([ './views/show/TourShow.vue'], resolve)
/*const BusinessShow = resolve => require([ './views/show/BusinessShow.vue'], resolve)*/
const Room = resolve => require([ './views/show/Room.vue'], resolve)
const PublicShow = resolve => require([ './views/show/PublicShow.vue'], resolve)

//故事： 故事、星梦档案、日记
const Story = resolve => require([ './views/story/Story.vue'], resolve)
const Archives = resolve => require([ './views/story/Archives.vue'], resolve)
const Diary = resolve => require([ './views/story/Diary.vue'], resolve)
//歌曲
const Song = resolve => require([ './views/song/Song.vue'], resolve)
//卡牌
const Card = resolve => require([ './views/card/Card.vue'], resolve)

const log = resolve => require([ './views/log/log.vue'], resolve)
//助手
const Assistant = resolve => require([ './views/assistant/Assistant.vue'], resolve)
/*//7日登录
const Log7days = resolve => require([ './views/log7days/Log7days.vue'], resolve)*/
//外务
const ForeignAffairs = resolve => require([ './views/foreignAffairs/ForeignAffairs.vue'], resolve)
//应援团
const SupportGroups = resolve => require([ './views/supportGroups/SupportGroups.vue'], resolve)
//好友
const Friends = resolve => require([ './views/friends/Friends.vue'], resolve)
//级别
const Level = resolve => require([ './views/level/Level.vue'], resolve)
//新手引导
const Newcomer = resolve => require([ './views/newcomer/Newcomer.vue'], resolve)
//充值 consumption
const Consumption = resolve => require([ './views/consumption/Consumption.vue'], resolve)
//首充
const Expense = resolve => require([ './views/expense/Expense.vue'], resolve)
//合约
const Contract = resolve => require([ './views/contract/Contract.vue'], resolve)
//累计充值 accumulateExpense
const AccumulateExpense = resolve => require([ './views/accumulateExpense/AccumulateExpense.vue'], resolve)

//七日登录领取
const SevenLoginReceive = resolve => require([ './views/loginRegister/SevenLoginReceive.vue'], resolve)
//礼包商店
const Gift = resolve => require([ './views/gift/Gift.vue'], resolve)
//服务器管理
const ServerManage = resolve => require([ './views/server/ServerManage.vue'], resolve)

//活跃活动  活跃度 领取
const Activity = resolve => require([ './views/activity/Activity.vue'], resolve)

//聊天室
const Chat = resolve => require([ './views/chat/Chat.vue'], resolve)

//准点在线
const OnlineTime = resolve => require([ './views/loginRegister/OnlineTime.vue'], resolve)
//累计在线
const OnlineTotal = resolve => require([ './views/loginRegister/OnlineTotal.vue'], resolve)

var $role_super_admin = true;

var user = sessionStorage.getItem('user');
if (user) {
    user = JSON.parse(user);
    if (user.role == 'ROLE_SUPER_ADMIN') {
        $role_super_admin = false;
    }

}


let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {

        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-home',
        leaf: true,//只有一个节点
        children: [
            { path: '/home', component: Welcome, name: '首页' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '服务器管理',
        iconCls: 'fa fa-database',
        children: [
            { path: '/serverManage', component: ServerManage, name: '服务器管理' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '充值领取',
        iconCls: 'fa fa-money',
        children: [
            { path: '/recharge', component: Recharge, name: '首充' },
            { path: '/receive', component: Receive, name: '领取' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '购买金币',
        iconCls: 'fa fa-cart-arrow-down',
        children: [
            { path: '/coin', component: BuyCoin, name: '购买金币' }

        ],
        hidden: $role_super_admin
    },

    {
        path: '/',
        component: Home,
        name: '抽奖活动',
        iconCls: 'fa  fa-external-link-square',
        children: [
            { path: '/lottery', component: Lottery, name: '抽奖活动'},
            { path: '/LotteryTimeLimit', component: LotteryTimeLimit, name: '限时抽奖'}

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '卡牌',
        iconCls: 'fa fa-clone',
        children: [
            { path: '/card', component: Card, name: '卡牌' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '演出活动',
        iconCls: 'fa fa-street-view',
        children: [
            { path: '/publicShow', component: PublicShow, name: '公演' },
            { path: '/tourShow', component: TourShow, name: '巡回演出' },
           /* { path: '/businessShow', component: BusinessShow, name: '商业演出' },*/
            { path: '/room', component: Room, name: '演出排练室' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '歌曲使用',
        iconCls: 'fa fa-music',
        children: [
            { path: '/song', component: Song, name: '歌曲使用' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '故事',
        iconCls: 'fa fa-folder-open-o',
        children: [
            { path: '/story', component: Story, name: '故事' },
            { path: '/archives', component: Archives, name: '星梦档案' },
            { path: '/diary', component: Diary, name: '日记' }

        ],
        hidden: $role_super_admin
    },

/*    {
        path: '/',
        component: Home,
        name: '助手',
        iconCls: 'fa fa-address-book-o',
        children: [
            { path: '/assistant', component: Assistant, name: '助手' }

        ],
        hidden: $role_super_admin
    },*/

    {
        path: '/',
        component: Home,
        name: '7日登录领取',
        iconCls: 'fa fa-calendar',
        children: [
            { path: '/sevenLoginReceive', component: SevenLoginReceive, name: '7日登录领取' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '礼包商店',
        iconCls: 'fa fa-gift',
        children: [
            { path: '/gift', component: Gift, name: '礼包商店' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '外务',
        iconCls: 'fa fa-car',
        children: [
            { path: '/foreignAffairs', component: ForeignAffairs, name: '外务' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '应援团',
        iconCls: 'fa fa-sign-language',
        children: [
            { path: '/supportGroups', component: SupportGroups, name: '应援团' }

        ],
        hidden: $role_super_admin
    },

    {
        path: '/',
        component: Home,
        name: '好友',
        iconCls: 'fa fa-group',
        children: [
            { path: '/friends', component: Friends, name: '好友' }

        ],
        hidden: $role_super_admin
    },

    {
        path: '/',
        component: Home,
        name: '等级分布',
        iconCls: 'fa fa-level-up',
        children: [
            { path: '/level', component: Level, name: '等级分布' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '活跃',
        iconCls: 'fa fa-magic',
        children: [
            { path: '/activity', component: Activity, name: '活跃' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '新手引导',
        iconCls: 'fa fa-leaf',
        children: [
            { path: '/newcomer', component: Newcomer, name: '新手引导' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '聊天室',
        iconCls: 'fa fa-street-view',
        children: [
            { path: '/chat', component: Chat, name: '聊天室' }

        ],
        hidden: $role_super_admin
    },
/*
    {
        path: '/',
        component: Home,
        name: '充值',
        iconCls: 'fa fa-cc-visa',
        children: [
            { path: '/consumption', component: Consumption, name: '充值' }

        ],
        hidden: $role_super_admin
    },*/

/*    {
        path: '/',
        component: Home,
        name: '首充',
        iconCls: 'fa fa-tags',
        children: [
            { path: '/expense', component: Expense, name: '首充' }

        ],
        hidden: $role_super_admin
    },*/

    {
        path: '/',
        component: Home,
        name: '合约',
        iconCls: 'fa fa-copy',
        children: [
            { path: '/contract', component: Contract, name: '合约' }

        ],
        hidden: $role_super_admin
    },

    {
        path: '/',
        component: Home,
        name: '累计充值',
        iconCls: 'fa fa-area-chart',
        children: [
            { path: '/accumulateExpense', component: AccumulateExpense, name: '累计充值' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '累计在线',
        iconCls: 'fa fa-area-chart',
        children: [
            { path: '/onlineTotal', component: OnlineTotal, name: '累计在线' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '准点在线',
        iconCls: 'fa fa-area-chart',
        children: [
            { path: '/onlineTime', component: OnlineTime, name: '准点在线' }

        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '系统管理',
        iconCls: 'fa fa-wrench',
        children: [
            { path: '/system', component: System, name: '权限管理' },
            { path: '/role', component: Role, name: '角色管理' },
            { path: '/resource', component: Resource, name: '资源管理' },
            { path: '/setting', component: Setting, name: '系统设置' },
            { path: '/version', component: Version, name: '版本信息' },
        ],
        hidden: $role_super_admin
    },

    {
        path: '/',
        component: Home,
        name: '用户管理',
        iconCls: 'fa fa-user-circle-o fa-fw',
        children: [
            { path: '/admin', component: Admin, name: '管理员账号' }
        ],
        hidden: $role_super_admin
    },




    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-gears fa-fw',
        leaf: true,//只有一个节点
        children: [
            { path: '/log', component: log, name: '操作日志' }
        ],
        hidden: $role_super_admin
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-telegram fa-fw',
        leaf: true,//只有一个节点
        children: [
            { path: '/my', component: data, name: '个人资料' }
        ],
        hidden: true
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;