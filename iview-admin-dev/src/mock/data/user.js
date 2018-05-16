import Mock from 'mockjs';

const LoginUsers = [{
    id: 1,
    name: 'admin',
    password: '123456',
    avatar: '',
    author: 0,
    email:'',
    cellphone:''
},
{
    id: 2,
    name: 'a',
    password: '1',
    avatar: '',
    author: 1,
    email:'',
    cellphone:''
}
];

const regInfo = [
    {
        name: 'chenbo',
        phonenum: '1870681177',
        ID: '3168',
        email: '2111@qq.com'
    },
    {
        name: 'chenbo',
        phonenum: '1870681177',
        ID: '3168',
        email: '2111@qq.com'
    },
    {
        name: 'chenbo',
        phonenum: '1870681177',
        ID: '3168',
        email: '2111@qq.com'
    }
];

const userInfo = [
    {
        name: 'chenbo',
        ID: '123',
        touzizonge: 1000,
        touzijilu: [
            {
                riqi: '2018-04-21',
                jine: 100
            },
            {
                riqi: '2018-04-22',
                jine: 100
            },
            {
                riqi: '2018-04-23',
                jine: 100
            }
        ],
        tixianjilu: [
            {
                riqi: '2018-04-21',
                jine: 100
            },
            {
                riqi: '2018-04-22',
                jine: 100
            },
            {
                riqi: '2018-04-23',
                jine: 100
            }
        ]
    },
    {
        name: 'chenbo',
        ID: '123',
        touzizonge: 1000,
        touzijilu: [
            {
                riqi: '2018-04-21',
                jine: 100
            },
            {
                riqi: '2018-04-22',
                jine: 100
            },
            {
                riqi: '2018-04-23',
                jine: 100
            }
        ],
        tixianjilu: [
            {
                riqi: '2018-04-21',
                jine: 100
            },
            {
                riqi: '2018-04-22',
                jine: 100
            },
            {
                riqi: '2018-04-23',
                jine: 100
            }
        ]
    },
    {
        name: 'chenbo',
        ID: '123',
        touzizonge: 1000,
        touzijilu: [
            {
                riqi: '2018-04-21',
                jine: 100
            },
            {
                riqi: '2018-04-22',
                jine: 100
            },
            {
                riqi: '2018-04-23',
                jine: 100
            }
        ],
        tixianjilu: [
            {
                riqi: '2018-04-21',
                jine: 100
            },
            {
                riqi: '2018-04-22',
                jine: 100
            },
            {
                riqi: '2018-04-23',
                jine: 100
            }
        ]
    }
];

// const Users = [];

// for (let i = 0; i < 86; i++) {
//     Users.push(Mock.mock({
//         id: Mock.Random.guid(),
//         name: Mock.Random.cname(),
//         addr: Mock.mock('@county(true)'),
//         'age|18-60': 1,
//         birth: Mock.Random.date(),
//         sex: Mock.Random.integer(0, 1)
//     }));
// }

export {
    LoginUsers, regInfo,userInfo
};
