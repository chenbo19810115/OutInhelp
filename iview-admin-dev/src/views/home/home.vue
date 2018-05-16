<style lang="less">
@import "./home.less";
</style>

<template>

    <div class="own-space"> 
        <Card>
        <p>
            <Icon type="person"></Icon>
            个人信息
        </p>
        <br/>
        <div>
            <Form 
                ref="userForm"
                :model="userForm" 
                :label-width="100"
                label-position="right" 
                :rules="inforValidate"
            >
                <FormItem label="用户姓名：" prop="name">
                    <div style="display:inline-block;width:100px;">
                        <Input type="text" disabled="true" v-model="userForm.name" /></Input>
                    </div>
                </FormItem>
                <FormItem label="用户手机：" prop="cellphone" >
                    <div style="display:inline-block;width:204px;">
                        <Input type="text" :disabled="disablecellphone" v-model="userForm.cellphone"></Input>
                    </div>
                </FormItem>
                <FormItem label="邮箱：" prop="email">
                    <div style="display:inline-block;width:204px;">
                        <Input type="text" :disabled="disableemail" v-model="userForm.email"></Input>
                    </div>
                </FormItem>
                <FormItem label="身份证：" prop="id">
                    <div style="display:inline-block;width:204px;">
                        <Input type="text" disabled="true" v-model="userForm.id"></Input>
                    </div>
                </FormItem>
                <FormItem label="登录密码：">
                    <Button type="text" size="small" @click="showEditPassword">修改密码</Button>
                </FormItem>
                 <div>
                    <Button type="primary"  @click="EnabeleEditUserInfor">修改用户基本信息</Button>
                    <Button type="primary"  :loading="save_loading" @click="saveEdit">保存用户基本信息</Button>
                </div>
                </Form>
            </div>
            </Card>
            <Modal v-model="editPasswordModal" :closable='false' :mask-closable=false :width="400">
                <h3 slot="header" style="color:#2D8CF0">修改密码</h3>
            <Form ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right" :rules="passwordValidate">
                <FormItem label="原密码" prop="oldPass" :error="oldPassError">
                    <Input v-model="editPasswordForm.oldPass" placeholder="请输入现在使用的密码" ></Input>
                </FormItem>
                <FormItem label="新密码" prop="newPass">
                    <Input v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符" ></Input>
                </FormItem>
                <FormItem label="确认新密码" prop="rePass">
                    <Input v-model="editPasswordForm.rePass" placeholder="请再次输入新密码" ></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelEditPass">取消</Button>
                <Button type="primary" :loading="savePassLoading" @click="saveEditPass">保存</Button>
            </div>
            </Modal>
        </div>
    
    </div>
</template>

<script>

import Cookies from 'js-cookie';
import {modifyuserPW, SaveUserInfo} from '../../api/api'

export default {
  data() {
    const valideRePassword = (rule, value, callback) => {
      if (value !== this.editPasswordForm.newPass) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
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

    return {
      userForm: {
        // 用户信息的数据结构
        name: "",
        cellphone: "",
        email: "",
        id: ""
      },
      disablecellphone:true,
      disableemail:true,
      save_loading:false,
      editPasswordModal: false, // 修改密码模态框显示
      savePassLoading: false, // 密码设置是否成功
      editPasswordForm: {
        //密码设置的数据结构
        oldPass: "",
        newPass: "",
        rePass: ""
      },
      passwordValidate: {
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

      inforValidate: {
        // 验证用户信息
        cellphone: [
          { required: true, message: "请输入真实手机号码", trigger: "blur"  },
          { validator: validePhone }
        ],
        email: [
          { required: true, message: "请输入真实邮箱地址", trigger: "blur" },
          {validator: valideEmail }
        ]
      },
    };
  },
  methods: {
    EnabeleEditUserInfor()
    {
        this.disablecellphone = false;
        this.disableemail = false;
    },
    showEditPassword() {
      this.editPasswordModal = true;
    },
    cancelEditPass() {
      this.editPasswordModal = false;
    },
    saveEditPass() {
      this.$refs["editPasswordForm"].validate(valid => {
        if (valid) {
          this.savePassLoading = true;
          let userid ={
              id: this.userForm.id,
              password:this.editPasswordForm.newPass
          }
          modifyuserPW(userid).then( (res) => {
                let{retCode, msg} = res;
                if(retCode !== 500)
                {
                    this.$Message.info({
                        content: msg,
                        duration: 3,
                        closable: true
                    });
                }else { //sucess
                    this.$Message.info({
                        content: msg,
                        duration: 3,
                        closable: true
                    });
                }
          });
          // you can write ajax request here
          this.savePassLoading = false;
          this.editPasswordModal = false;
        }
      });
    },
    saveEdit(){
        this.$refs["userForm"].validate(valid => {
            if (valid) {
               this.save_loading = true;
               let userid ={
                   id: this.userForm.id,
                   cellphone: this.userForm.cellphone,
                   email: this.userForm.email
                }

                SaveUserInfo(userid).then( (res) =>{
                   let{retCode, msg} = res;
                   if(retCode !== 500)
                   {
                        this.$Message.info({
                            content: msg,
                            duration: 3,
                            closable: true
                        });
                   }else { //sucess
                        this.disablecellphone = true;
                        this.disableemail = true;
                        Cookies.set('cellphone', userid.cellphone);
                        Cookies.set('email', userid.email);
                   }

                   this.save_loading = false;
                })

            }
        })
    },
    
    init() {
      this.userForm.name = Cookies.get('user');
      this.userForm.cellphone = Cookies.get('cellphone');
      this.userForm.email = Cookies.get('email');
      this.userForm.id = Cookies.get('id');
    },
  },
  mounted() {
    this.init();
    this.$store.commit('updateMenulist');//根据权限刷新菜单
  }
};
</script>
