<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
                <Button type="primary"  :loading="save_touzishouyi_loading" @click="refreshusertouzishouyi">获取最新记账信息</Button>
            </Col>
        </Row>
        <Row><br/></Row>
        <Row :gutter="10">
            <Col span="24">
                <Row>
                    <Input v-model="searchConName" placeholder="请输入身份证搜搜..." style="width: 200px" />
                    <span @click="handleSearch" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    <Button @click="handleCancel" style="margin: 0 10px;" type="primary" >取消搜索</Button>
                </Row>
                <Row><br/></Row>
                <Table ref="touziTable" border :columns="columns" :data="data" :height="heightVar"></Table>
            </Col>
        </Row>

       <Modal v-model="bShowBianji" :mask-closable=false width="350">
                <h3 slot="header" style="color:#2D8CF0">修改{{modifyForm.id}}的记账</h3>
                <Form  ref="modifyForm"  :model="modifyForm" :label-width="100" label-position="right" :rules="addFormValidate">
                    <FormItem label="投资人：" prop="id">
                        <div style="display:inline-block;width:150px;">
                            <Input v-model="modifyForm.id"></Input>
                        </div>
                    </FormItem>
                    <FormItem label="日期：" prop="jizhangriqi">
                        <div style="display:inline-block;width:150px;">
                            <DatePicker v-model="modifyForm.jizhangriqi" format="yyyy年M月d日"  placeholder="请选择日期" style="width: 150px"></DatePicker>
                        </div>
                    </FormItem>
                    <FormItem label="新增投资：" prop="xinzengtouzie">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="modifyForm.xinzengtouzie" placeholder="请输入当日新增投资"></Input>
                    </div>
                </FormItem>

                <FormItem label="投资天数：" prop="touzitianshu">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="modifyForm.touzitianshu" placeholder="请输入新增投资天数"></Input>
                    </div>
                </FormItem>

                <FormItem label="提现额: " prop="tixiane">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="modifyForm.tixiane" placeholder="请输入当日提现金额"></Input>
                    </div>
                </FormItem>
                <FormItem label="收益: " prop="shouyie">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="modifyForm.shouyie" placeholder="请输入当日收益金额"></Input>
                    </div>
                </FormItem>
                </Form>
                <div slot="footer">
                    <Button type="text" style="width: 50px;" @click="cancelModifyForm">取消</Button>
                    <Button type="primary" style="width: 100px;" :loading="modify_loading" @click="modifyFormSave">提交</Button>
                </div>
       </Modal>

    </div>
</template>

<script>
import util from '../../libs/util';
import { GetUsrJizhangInfo, ModifyUserJizhangInfo, DeleteUserJizhangInfo} from '../../api/api'
export default {
  data() {
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

    const validenumber = (rule, value, callback) => {
        var regBasic = /^(\+)?\d+(\.\d+)?$/;
        if(!regBasic.test(value)) {
            callback(new Error("请输入正确的金额"));
            return;
        } else {
            callback();
            return;
        }
    };

    return {
      columns: [
        {
          title: "身份证",
          key: "id",
          width: 200
        },
        {
          title: "记账日期",
          key: "jizhangriqi",
          width: 100
        },
        {
          title: "新增投资",
          key: "xinzengtouzie",
          width: 100
        },
        {
          title: "提现",
          key: "tixiane",
          width: 100
        },
        {
          title: "收益",
          key: "shouyie",
          width: 100
        },
        {
            title: '操作',
            key: 'action',
            // width: 300,
            align: 'left',
            render: (h, params) => {
                return h('div', [
                    h('Button', {
                        style: {
                            margin: '0 5px'
                        },
                        props: {
                            type: 'success',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.bShowBianji = true;
                                this.modifyForm.jizhangid = this.data[params.index].jizhangid;
                                this.modifyForm.id = this.data[params.index].id;
                                this.modifyForm.jizhangriqi = this.data[params.index].jizhangriqi;
                                this.modifyForm.xinzengtouzie = this.data[params.index].xinzengtouzie;
                                this.modifyForm.tixiane = this.data[params.index].tixiane;
                                this.modifyForm.shouyie = this.data[params.index].shouyie;
                                this.modifyForm.touzitianshu = this.data[params.index].touzitianshu;
                                this.selecteId = params.index;
                            }
                        }
                    }, '编辑'),
                    h('Button', {
                        style: {
                            margin: '0 5px'
                        },
                        props: {
                            type: 'error',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.deleteTouzi(params.index);
                            }
                        }
                    }, '删除')
                ])
            }
        }
      ],

      modifyForm:{
          jizhangid: "",
          jizhangriqi: "",
          id: "",
          xinzengtouzie: "",
          touzitianshu: "",
          tixiane: "",
          shouyie: "",
      },
        
        save_touzishouyi_loading: false,
        searchConName: "",
        data: [],
        initTable: [],
        bShowBianji: false,
        modify_loading: false,
        selecteId: -1,
        datamap: null,
        bSerache: false,
        
        addFormValidate: {
        // 验证用户信息
        id: [
          { required: true, message: "请输入正确身份证号", trigger: "blur" },
          { validator: valideID}
        ],
        jizhangriqi: [
        //   { required: true, message: "请输入真实的记账日期", trigger: "blur" },
        //   {validator: valideEmail }
        ],
        xinzengtouzie: [
          { required: true, message: "请输入正确的金额", trigger: "blur" },
          { validator: validenumber }
        ],
        tixiane: [
          { required: true, message: "请输入正确的金额", trigger: "blur" },
          { validator: validenumber }
        ],
        shouyie: [
          { required: true, message: "请输入正确的金额", trigger: "blur" },
          { validator: validenumber }
        ]
      },
    };
  },
  methods: {
    init() {
       this.bShowBianji = false;
       this.modify_loading = false;
       this.datamap = new Map;
       this.bSerache = false;
       this.save_touzishouyi_loading = false;
    },
    search(data, argumentObj) {
      let res = data;
      let dataClone = data;
      var searchCn = 0;
      for (let argu in argumentObj) {
        if (argumentObj[argu].length > 0) {
          res = dataClone.filter((d, idx) => {
            var isearch = d[argu].indexOf(argumentObj[argu]) > -1;
            if(isearch){
                this.datamap[searchCn] = idx;
                searchCn = searchCn + 1;
            }
            return isearch;
          });
          dataClone = res;
          this.bSerache = true;
        }
      }
      return res;
    },

    handleSearch() {
      this.data = this.initTable;
      this.data = this.search(this.data, { id: this.searchConName });
    },
    handleCancel() {
      this.data = this.initTable;
      this.searchConName = '';
      this.bSerache = false;
    },
    cancelModifyForm()
    {
        this.modify_loading = false;
        this.bShowBianji = false;
        this.$refs['modifyForm'].resetFields();
    },

    modifyFormSave(){
        this.$refs.modifyForm.validate(valid => {
            if (valid) {
        this.modify_loading = true;
        var newTouzi = {
            jizhangid: this.modifyForm.jizhangid,
            id: this.modifyForm.id,
            jizhangriqi: util.formatDate.format(new Date(this.modifyForm.jizhangriqi), 'yyyy-MM-dd'),
            xinzengtouzie: this.modifyForm.xinzengtouzie,
            tixiane: this.modifyForm.tixiane,
            shouyie: this.modifyForm.shouyie,
            touzitianshu: this.modifyForm.touzitianshu
        }
         
        ModifyUserJizhangInfo(newTouzi).then( (res) => {
             let{retCode, msg} = res;
                    if(retCode !== 500)
                    {
                        this.$Message.info({
                            content: msg,
                            duration: 3,
                            closable: true
                        });
                    }else { //sucess
                        if(this.bSerache === false)
                        {
                            this.data[this.selecteId].id= newTouzi.id;
                            this.data[this.selecteId].jizhangriqi = newTouzi.jizhangriqi;
                            this.data[this.selecteId].xinzengtouzie = newTouzi.xinzengtouzie;
                            this.data[this.selecteId].tixiane = newTouzi.tixiane;
                            this.data[this.selecteId].shouyie = newTouzi.shouyie;
                            this.data[this.selecteId].touzitianshu = newTouzi.touzitianshu;
                        }
                        else{
                            this.data[this.selecteId].id= newTouzi.id;
                            this.data[this.selecteId].jizhangriqi = newTouzi.jizhangriqi;
                            this.data[this.selecteId].xinzengtouzie = newTouzi.xinzengtouzie;
                            this.data[this.selecteId].tixiane = newTouzi.tixiane;
                            this.data[this.selecteId].shouyie = newTouzi.shouyie;
                            this.data[this.selecteId].touzitianshu = newTouzi.touzitianshu;

                            this.initTable[this.selecteId].id= newTouzi.id;
                            this.initTable[this.selecteId].jizhangriqi = newTouzi.jizhangriqi;
                            this.initTable[this.selecteId].xinzengtouzie = newTouzi.xinzengtouzie;
                            this.initTable[this.selecteId].tixiane = newTouzi.tixiane;
                            this.initTable[this.selecteId].shouyie = newTouzi.shouyie;
                            this.initTable[this.selecteId].touzitianshu = newTouzi.touzitianshu;
                        }
                    }
        });
      
        this.$refs['modifyForm'].resetFields();
        this.modify_loading = false;
        this.bShowBianji = false;
            }
        });
    },

    deleteTouzi(index){
        var deleteTouzi = {
            jizhangid: this.data[index].jizhangid,
            id: this.data[index].id,
            jizhangriqi: this.data[index].jizhangriqi
        }
         
        DeleteUserJizhangInfo(deleteTouzi).then( (res) =>{
            let{retCode, msg} = res;
                if(retCode !== 500)
                {
                    this.$Message.info({
                        content: msg,
                        duration: 3,
                        closable: true
                    });
                }else { //sucess
                    if(this.bSerache === false)
                    {
                        this.data.splice(index, 1);
                    }
                    else
                    {
                        this.data.splice(index, 1);
                        this.initTable.splice(this.datamap[index], 1);
                    }
                }
        });
        
    },

   getusertouzishouyi(userid) {
      if(userid !== undefined){
          this.searchConName = userid.toString();
      } 
      else{
          this.searchConName = "";
      }

      var useridinfo = {
        id: userid
      }

      this.save_touzishouyi_loading = true;
      GetUsrJizhangInfo(useridinfo).then( (res) =>{
          let{retCode, msg, userjizhanginfo} = res;
          if(retCode !== 500){
              this.$Message.info({
                    content: msg,
                    duration: 3,
                    closable: true
              });
          }
          else{
              this.data = userjizhanginfo;
              this.initTable = userjizhanginfo;
          }

          this.save_touzishouyi_loading = false;
      });
    },
    
    refreshusertouzishouyi()
    {
      this.getusertouzishouyi(undefined);
    }
  },
  mounted() {
      this.init();
      this.heightVar = window.innerHeight - this.$refs.touziTable.$el.offsetTop - 140;
      var userid = this.$route.query.userid;
      this.getusertouzishouyi(userid);
  },
};
</script>
