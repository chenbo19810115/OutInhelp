import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import {
    LoginUsers, regInfo, userInfo
} from './data/user';

export default {
    Init () {
        let mock = new MockAdapter(axios);

        // 登录
        mock.onGet('/loginAPI').reply(config => {
            let {username, password} = config.params;
            return new Promise((resolve, reject) => {
                let user = null;
                setTimeout(() => {
                    let hasUser = LoginUsers.some(u => {
                        if (u.name === username && u.password === password) {
                            user = JSON.parse(JSON.stringify(u));
                            user.password = undefined;
                            return true;
                        }
                    });

                    if (hasUser) {
                        resolve([200, {
                            code: 200,
                            msg: '请求成功',
                            user
                        }]);
                    } else {
                        resolve([200, {
                            code: 500,
                            msg: '账号或密码错误'
                        }]);
                    }
                }, 1000);
            });
        });

        // 注册
        mock.onPost('/getCode').reply(config => {
            let {
                name,
                cellphone,
                email,
                id
            } = JSON.parse(config.data);
            return new Promise((resolve, reject) => {
                let hasUser = false;// 查询此用户是否已经注册
                if (!hasUser) {
                    resolve([200, {
                        retCode: 500,
                        msg: '等待系统管理员发送注册激活页面到邮箱！请注意查收！'
                    }]);
                } else {
                    resolve([200, {
                        retCode: 501,
                        msg: '此用户已经注册，请直接登录！'
                    }]);
                }
            });
        });

        mock.onGet('/getRegInfo').reply(config => {
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    resolve([200, {
                        regInfo: regInfo
                    }]);
                }, 1000);
            });
        });

        mock.onGet('/getUserInfo').reply(config => {
            return new Promise((resolve, reject) => {
                setTimeout(() => {
                    resolve([200, {
                        userInfo: userInfo
                    }]);
                }, 1000);
            });
        });               
    }
};
