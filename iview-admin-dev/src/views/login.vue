<style lang="less">
@import "./login.less";
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                        <FormItem> 
                           <Button @click="gotoReg"  type="primary" long >注册</Button> 
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import { requestLogin } from "../api/api";
export default {
  data() {
    return {
      form: {
        userName: "",
        password: ""
      },
      rules: {
        userName: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },
  methods: {
    handleSubmit() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          //组织用户信息
          var loginParams = {
            username: this.form.userName,
            password: this.form.password
          };

          requestLogin(loginParams).then(data => {
            let { msg, code, user } = data;
            if (code !== 200) {
              this.$Message.info({
                    content: '登录信息输入错误，请重新输入登录！',
                    duration: 3,
                    closable: true
                });
            } else {
              Cookies.set("user", user.name);
              Cookies.set("id", user.id);
              Cookies.set("email", user.email);
              Cookies.set("cellphone", user.cellphone);
              Cookies.set('password', this.form.password);
              this.$store.commit("setAvator", ""); //设置用户头像
              if (user.author === 0) {
                Cookies.set("access", 0);
              } else {
                Cookies.set("access", 1);
              }
              this.$router.push({ name: "home_index" });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    gotoReg() {
      this.$router.push({ name: "reg" });
    }
  }
};
</script>

<style>

</style>
