import Mock from 'mockjs';
const LoginUsers = [
  {
    id: 1,
    username: 'admin',
    password: '123456',
    avatar: 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
    name: '客服 刘洋'
  }
];

const Users = [];

for (let i = 0; i < 16; i++) {
  Users.push(Mock.mock({
    id: Mock.Random.guid(),
    name: Mock.Random.cname(),
    addr: Mock.mock('@county(true)'),
    'age|18-60': 1,
    'sort|0-16': 1,
    birth: Mock.Random.date(),
    'num|1-3000': 1,
    ip:Mock.Random.ip(),
    username: Mock.Random.word(4, 10),
    sex: Mock.Random.integer(0, 1),
    url: Mock.Random.url('http', 'psqylhs.com'),
    time: Mock.Random.now(),
    'phone': /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
      titile: Mock.Random.ctitle(20, 30)
  }));
}

export { LoginUsers, Users };
