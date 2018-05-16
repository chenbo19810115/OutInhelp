<style lang="less">
@import "./reg.less";
</style>

<template>
    <div class="regwnd"> 
        <p slot="title">
            <Icon type="person"></Icon>
            个人信息
        </p>
        <br/>
        <div>
            <Form ref="userForm" :model="userForm" :label-width="100" label-position="right" :rules="inforValidate">
                <FormItem label="用户姓名：" prop="name">
                    <div style="display:inline-block;width:100px;">
                        <Input v-model="userForm.name" :disabled="bDisableName"></Input>
                    </div>
                </FormItem>
                <FormItem label="用户手机：" prop="cellphone" >
                    <div style="display:inline-block;width:204px;">
                        <Input v-model="userForm.cellphone" :disabled="bDisablePhone"></Input>
                    </div>
                </FormItem>
                <FormItem label="身份证：" prop="id">
                    <div style="display:inline-block;width:204px;">
                        <Input v-model="userForm.id" :disabled="bDisableId"></Input>
                    </div>
                </FormItem>
                <FormItem label="邮箱：" prop="email">
                    <div style="display:inline-block;width:204px;">
                        <Input v-model="userForm.email" :disabled="bDisableEmail"></Input>
                    </div>
                    <div style="display:inline-block;position:relative;">
                        <Button @click="getIdentifyCode"  type="primary" :disabled="bDisableGetCodeBtn" v-if="bGetSecurityCode">{{ CodeBtnTitle }}</Button>
                    </div>
                </FormItem>
            </Form>
            <Form  ref="editPasswordForm" v-if="bShowPWWindow"  :model="editPasswordForm" :label-width="100" label-position="right" :rules="passwordValidate">
                <FormItem label="注册邀请码：" prop="securityCode">
                    <div style="display:inline-block;width:300px;">
                        <Input v-model="editPasswordForm.securityCode" :disabled="bDisableCode"></Input>
                    </div>
                </FormItem>
                <FormItem label="设置登录密码：">
                </FormItem>
                <FormItem label="密码" prop="newPass">
                    <div style="display:inline-block;width:204px;">
                        <Input v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符" ></Input>
                    </div>
                </FormItem>
                <FormItem label="确认密码" prop="rePass">
                    <div style="display:inline-block;width:204px;">
                        <Input v-model="editPasswordForm.rePass" placeholder="请再次输入新密码" ></Input>
                    </div>
                </FormItem>
                <br/>
                <div>
                    <Button type="text" style="width: 100px;" @click="cancelEditUserInfor">取消</Button>
                    <Button type="primary" style="width: 100px;" :loading="save_regInfo_loading" @click="saveRegInfo">注册</Button>
                </div>
            </Form>
        </div>
    </div>
</template>

<script>
import { requestCode, requestReg } from "../api/api";

export default {
  name: "ownspace_index",
  data() {
    const valideName = (rule, value, callback) => {
      var re = /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/ 
      if (!re.test(value)) {
        callback(new Error("请输入真实姓名"));
      } else {
        callback();
      }
    };
    const valideID = (rule, value, callback) => {
      var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
      var regBasic = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/
      if(!regBasic.test(value)) {
          callback(new Error("请输入真实身份证号"));
          return;
      } else {
              //验证地方编码
          if(!city[value.substr(0,2)]) {
              callback(new Error("请输入真实身份证号"));
              return;
          } else{
              //18位身份证需要验证最后一位校验位
              if(value.length == 18){
                  value = value.split('');
                  //∑(ai×Wi)(mod 11)
                  //加权因子
                  var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                  //校验位
                  var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                  var sum = 0;
                  var ai = 0;
                  var wi = 0;
                  for (var i = 0; i < 17; i++)
                  {
                      ai = value[i];
                      wi = factor[i];
                      sum += ai * wi;
                  }
                  var last = parity[sum % 11];
                  if(parity[sum % 11] != value[17]){
                      callback(new Error("请输入真实身份证号"));
                      return;
                  }else{
                    callback();
                    return;
                  }
              }
              else{
                  callback(new Error("请输入真实身份证号"));
                  return;
              }
          }
      }
    };

    const validePhone = (rule, value, callback) => {
      var re = /^((1[3456789][0-9]{1})+\d{8})$/;
      if (!re.test(value)) {
        callback(new Error("请输入真实手机号码"));
      } else {
        callback();
      }
    };
    const valideEmail = (rule, value, callback) => {
      var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
      if (!reg.test(value)) {
        callback(new Error("请输入真实邮箱地址"));
      } else {
        callback();
      }
    };
    const valideRePassword = (rule, value, callback) => {
      // passwordValidate
      if (value !== this.editPasswordForm.newPass) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
      }
    };

    return {
      userForm: {
        // 用户信息的数据结构
        name: "",
        cellphone: "",
        email: "",
        id: ""
      },

      bDisableName: true,
      bDisablePhone: true,
      bDisableId: true,
      bDisableEmail: true,
      bGetSecurityCode: false,
      bShowPWWindow: true,
      bDisableCode: true,
      bGetSecurityCode: false, //界面公用，获取验证码和注册
      bShowPWWindow: false, //是否显示注册按钮
      bDisableGetCodeBtn: false, // 是否可点获取验证码
      CodeBtnTitle: "注册申请", // “获取邀请码”按钮的文字

      inforValidate: {
        // 验证用户信息
        name: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { validator: valideName }
        ],
        cellphone: [
          { required: true, message: "请输入真实手机号码" },
          { validator: validePhone }
        ],
        email: [
          { required: true, message: "请输入真实邮箱地址", trigger: "blur" },
          {validator: valideEmail }
        ],
        id: [
          { required: true, message: "请输入真实身份证号", trigger: "blur" },
          { validator: valideID}
        ]
      },
      //密码设置
      editPasswordForm: {
        securityCode: "",
        //密码设置的数据结构
        newPass: "",
        rePass: ""
      },
      passwordValidate: {
        securityCode: [
          {
            required: true,
            message: "请输入邮箱里获取的邀请码",
            trigger: "blur"
          }
        ],
        newPass: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { min: 6, message: "请至少输入6个字符", trigger: "blur" },
          { max: 18, message: "最多输入18个字符", trigger: "blur" }
        ],
        rePass: [
          { required: true, message: "请再次输入新密码", trigger: "blur" },
          { validator: valideRePassword, trigger: "blur" }
        ]
      },

      save_regInfo_loading: false // 是否正在保存邀请码
    };
  },
  methods: {
    init() {
      //
      var bReg = this.$route.query.name;
      if (bReg != undefined) {
        this.bDisableName = true;
        this.bDisablePhone = true;
        this.bDisableId = true;
        this.bDisableEmail = true;
        this.bGetSecurityCode = false;
        this.bShowPWWindow = true;
        this.bDisableCode = true;
        this.bDisableGetCodeBtn = true;
        this.CodeBtnTitle = "";
        this.userForm.name = this.$route.query.name;
        this.userForm.cellphone = this.$route.query.cellphone;
        this.userForm.email = this.$route.query.email;
        this.userForm.id = this.$route.query.id;
        this.editPasswordForm.securityCode = this.$route.query.securityCode;
        this.editPasswordForm.password = "";
      } else {
        this.bDisableName = false;
        this.bDisablePhone = false;
        this.bDisableId = false;
        this.bDisableEmail = false;
        this.bGetSecurityCode = true;
        this.bShowPWWindow = false;
        this.bDisableCode = true;
        this.bDisableGetCodeBtn = false;
        this.CodeBtnTitle = "注册申请";
        this.userForm.name = "";
        this.userForm.cellphone = "";
        this.userForm.email = "";
        this.userForm.id = "";
        this.editPasswordForm.securityCode = "";
        this.editPasswordForm.password = "";
      }
    },
    getIdentifyCode() {
      //发送注册申请
      this.$refs["userForm"].validate(valid => {
        if (valid) {
          this.bDisableGetCodeBtn = true;
          let timeLast = 10; //测试暂时5s
          let timer = setInterval(() => {
            if (timeLast >= 0) {
              this.CodeBtnTitle = timeLast + "秒后重试";
              timeLast -= 1;
            } else {
              clearInterval(timer);
              this.CodeBtnTitle = "注册申请";
              this.bDisableGetCodeBtn = false;
            }
          }, 1000);

          // you can write ajax request here
          requestCode(this.userForm).then(data => {
            let { retCode, msg } = data;
            if (retCode === 500) {
              // OK
              this.$Modal.info({
                title: "发送注册请求",
                content: msg,
                onOk: () => {
                  this.$router.push({ name: "login" });
                }
              });
            } else if (retCode == 501) {
              // Error
              this.$Modal.info({
                title: "发送注册请求",
                content: msg,
                onOk: () => {
                  this.$router.push({ name: "login" });
                }
              });
            } else {
            }
          });
        }
      });
    },

    cancelEditUserInfor() {
      //取消注册
      this.$router.push({ name: "login" });
    },
    saveRegInfo() {
      //保存注册信息
      this.$refs["userForm"].validate(valid1 => {
        if (valid1) {
          this.$refs["editPasswordForm"].validate(valid2 => {
            if (valid2) {
              //组织数据
              var regInfo = {
                name: this.userForm.name,
                cellphone: this.userForm.cellphone,
                id: this.userForm.id,
                email: this.userForm.email,
                securityCode: this.editPasswordForm.securityCode,
                password: this.editPasswordForm.rePass
              };

              this.save_regInfo_loading = true;

              // you can write ajax request here
              requestReg(regInfo).then(data => {
                let { retCode, msg } = data;
                if (retCode === 500) {
                  // OK
                  this.$Modal.info({
                    title: "注册提示",
                    content: msg,
                    onOk: () => {
                      this.$router.push({ name: "login" });
                    }
                  });
                } else if (retCode == 501) {
                  // Error
                  this.$Modal.info({
                    title: "注册提示",
                    content: msg,
                    onOk: () => {
                      this.$router.push({ name: "login" });
                    }
                  });
                } else {
                }
              });
            }
          });
        }
      });
    },
  },
  mounted() {
    this.init();
  }
};
</script>
