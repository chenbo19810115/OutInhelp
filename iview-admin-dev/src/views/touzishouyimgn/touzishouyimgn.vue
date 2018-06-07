<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
                <Button type="primary"  :loading="save_touzishouyi_loading" @click="refreshusertouzishouyi">获取最新投资收益账目</Button>
            </Col>
        </Row>
        <Row><br/></Row>
        <Row :gutter="10">
            <Col span="24">
                <Row>
                    <Input v-model="searchConName" placeholder="请输入身份证搜搜..." style="width: 200px" />
                    <span @click="handleSearch" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    <Button @click="handleCancel" style="margin: 0 10px;" type="primary" >取消搜索</Button>
                    <Button @click="handleAdd" style="margin: 0 10px;" type="primary" >新增记账</Button>
                    <Button @click="handlePLAdd" style="margin: 0 10px;" type="primary" >批量收益记账</Button>
                </Row>
                <Row><br/></Row>
                <Table ref="touziTable" border :columns="columns" :data="data" :height="heightVar"></Table>
            </Col>
        </Row>

        <Modal v-model="bShowXinzeng" :mask-closable=false width="350">
            <h3 slot="header" style="color:#2D8CF0">投资收益记账</h3>
            <Form  ref="addForm"  :model="addForm" :label-width="100" label-position="right" :rules="addFormValidate">
                <FormItem label="投资人：" prop="id">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="addForm.id"></Input>
                    </div>
                </FormItem>
                <FormItem label="记账日期：" prop="jizhangriqi">
                    <div style="display:inline-block;width:150px;">
                        <DatePicker v-model="addForm.jizhangriqi" format="yyyy年M月d日"  placeholder="请选择日期" style="width: 150px"></DatePicker>
                    </div>
                </FormItem>
                <FormItem label="新增投资：" prop="xinzengtouzie">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="addForm.xinzengtouzie" placeholder="请输入当日新增投资"></Input>
                    </div>
                </FormItem>
                <FormItem label="投资天数：" prop="touzitianshu">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="addForm.touzitianshu" placeholder="请输入新增投资天数"></Input>
                    </div>
                </FormItem>
                <FormItem label="提现额: " prop="tixiane">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="addForm.tixiane" placeholder="请输入当日提现金额"></Input>
                    </div>
                </FormItem>
                <FormItem label="收益额: " prop="shouyie">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="addForm.shouyie" placeholder="请输入当日收益金额"></Input>
                    </div>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" style="width: 50px;" @click="cancelAddjizhang">取消</Button>
                <Button type="primary" style="width: 100px;" :loading="add_loading" @click="addjizhangInfo">提交</Button>
            </div>
       </Modal>

       <Modal v-model="bShowPLXinzengShouyi" :mask-closable=false width="350">
            <h3 slot="header" style="color:#2D8CF0">批量收益记账</h3>
            <Form  ref="pLAddForm"  :model="pLAddForm" :label-width="100" label-position="right">
                <FormItem label="投资人：" prop="id">
                    <div style="display:inline-block;width:150px;">
                        <Input type="text" disabled="true" value="所有用户"></Input>
                    </div>
                </FormItem>
                <FormItem label="记账日期：" prop="jizhangriqi">
                    <div style="display:inline-block;width:150px;">
                        <DatePicker v-model="pLAddForm.jizhangriqi" format="yyyy年M月d日"  placeholder="请选择日期" style="width: 150px"></DatePicker>
                    </div>
                </FormItem>
                <FormItem label="收益额: " prop="shouyie">
                    <div style="display:inline-block;width:150px;">
                        <Input v-model="pLAddForm.shouyie" placeholder="请输入当日收益金额"></Input>
                    </div>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" style="width: 50px;" @click="cancelPLAddShouyi">取消</Button>
                <Button type="primary" style="width: 100px;" :loading="addPL_loading" @click="addPLShouyiInfo">提交</Button>
            </div>
       </Modal>
    </div>
</template>

<script>
import util from '../../libs/util';
import { GetJizhangSumInfo, AddUserJizhangDayInfo, DeleteUserSumJizhang, AddPLShouyiInfo} from '../../api/api'
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

    const validetouzie = (rule, value, callback) =>
    {
        var regBasic = /^(\+)?\d+(\.\d+)?$/;
        if(!regBasic.test(value)) { //格式不正确
            callback(new Error("请输入正确的金额"));
            return;
        } else {
            if(Number(value) > 0){ //投资额大于0
               if(Number(this.addForm.touzitianshu) <= 0){ // 投资天数小于等于0
                   callback(new Error("请输入投资天数！"));
                   return;
               }else{ //投资天数大于0
                   callback();
                   return;
               }
            }else //投资额小于等于0
            {
                if(Number(this.addForm.touzitianshu) > 0){// 投资天数大于0
                   callback(new Error("请重置投资天数为0！"));
                   return;
                }else{ // 投资天数小于等于0
                    callback();
                    return;
                }
            }
        }
    };

    const validinter = (rule, value, callback) =>{
        var regBasic = /^[0-9]+$/ ;
        if(!regBasic.test(value)) {
            callback(new Error("请输入正确的投资天数"));
            return;
        } else { //如果填写了

            callback();
            return;
        }
    };

    const validTouzitianshu = (rule, value, callback) => {
        var regBasic = /^[0-9]+$/ ;
        if(!regBasic.test(value)) { //格式不正确
            callback(new Error("请输入正确的投资天数"));
            return;
        } else { //如果填写了
            if(Number(value) > 0){ //天数大于0
               if(Number(this.addForm.xinzengtouzie) <= 0){ //投资额小于等于0
                   callback(new Error("请重置投资天数为0！"));
                   return;
               }else{ //投资额大于0
                   callback();
                   return;
               }
            }else //天数小于等于0
            {
                if(Number(this.addForm.xinzengtouzie) > 0){ //投资额大于0
                   callback(new Error("请输入投资天数！"));
                   return;
                }else{
                    callback();
                    return;
                }
            }
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
          title: "总投资",
          key: "zongtouzie",
          width: 100
        },
        {
          title: "当天收益",
          key: "shouyie",
          width: 100
        },
        {
          title: "总收益",
          key: "zongshouyie",
          width: 100
        },
        {
            title: "年化收益率",
            key: "nianhuashouyilv",
            width: 150
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
                            type: 'error',
                            size: 'small'
                        },
                        on: {
                            click: () => {
                                this.deleteTouzi(params.index);
                            }
                        }
                    }, '删除'),
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
                                this.$router.push({
                                    name: 'touzishouyixiangdan_index',
                                    query: {userid: params.row.id}
                               });
                            }
                        }
                    }, '记账详单')
                ])
            }
        }
      ],

      addForm:{
          jizhangid: "",
          jizhangriqi: "",
          id: "",
          xinzengtouzie: "",
          touzitianshu: "",
          tixiane: "",
          shouyie: "",
      },

      pLAddForm:{
          jizhangriqi: "",
          shouyie: ""
      },
        
        save_touzishouyi_loading: false,
        searchConName: "",
        data: [],
        initTable: [],
        bShowXinzeng: false,
        add_loading: false,
        datamap: null,
        bSerache: false,
        bShowPLXinzengShouyi: false,
        addPL_loading: false,
        
        addFormValidate: {
        // 验证用户信息
        id: [
          { required: true, message: "请输入正确身份证号", trigger: "blur" },
          { validator: valideID}
        ],
        touzitianshu: [
          { required: true, message: "请输入投资天数", trigger: "change" }, 
          { validator: validTouzitianshu}
        ],
        xinzengtouzie: [
          { required: true, message: "请输入正确的金额", trigger: "change" },
          { validator: validetouzie }
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
       this.bShowXinzeng = false;
       this.add_loading = false;
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
    handleAdd(){
        this.bShowXinzeng = true;
        this.addForm.id = this.searchConName;
        this.addForm.xinzengtouzie = "0";
        this.addForm.touzitianshu = "0";
        this.addForm.tixiane = "0";
        this.addForm.shouyie = "0";
        this.addForm.jizhangriqi = util.formatDate.format(new Date(), 'yyyy-MM-dd');
    },
    cancelAddjizhang(){
        this.add_loading = false;
        this.bShowXinzeng = false;
        this.$refs['addForm'].resetFields();
    },
    addjizhangInfo(){
        this.$refs.addForm.validate(valid => {
             if (valid) {
                if(this.addForm.jizhangriqi === undefined || this.addForm.jizhangriqi === 0)
                {
                    this.$Message.error("必须选择记账日期！");
                    reutrn;
                }
                this.add_loading = true;
                var newTouzi = {
                    id: this.addForm.id,
                    jizhangriqi: util.formatDate.format(new Date(this.addForm.jizhangriqi), 'yyyy-MM-dd'),
                    xinzengtouzie: this.addForm.xinzengtouzie,
                    touzitianshu: this.addForm.touzitianshu,
                    tixiane: this.addForm.tixiane,
                    shouyie: this.addForm.shouyie
                }
                AddUserJizhangDayInfo(newTouzi).then ( (res)=> {
                    let{retCode, msg} = res;
                    if(retCode !== 500)
                    {
                        this.$Message.info({
                            content: msg,
                            duration: 3,
                            closable: true
                        });
                    }else { //sucess
                        this.getusertouzishouyisum(newTouzi.id);
                    }

                    this.$refs['addForm'].resetFields();
                    this.add_loading = false;
                    this.bShowXinzeng = false;
                });
             }
        });
    },

    handlePLAdd()
    {
        this.bShowPLXinzengShouyi = true;
    },

    cancelPLAddShouyi()
    {
        this.addPL_loading = false;
        this.bShowPLXinzengShouyi = false;
        this.$refs['pLAddForm'].resetFields();
    },

    addPLShouyiInfo(){
        this.addPL_loading = true;
        var plnewshouyi = {
            jizhangriqi: util.formatDate.format(new Date(this.pLAddForm.jizhangriqi), 'yyyy-MM-dd'),
            shouyie: this.pLAddForm.shouyie
        }

        AddPLShouyiInfo(plnewshouyi).then ( (res)=> {
            let{retCode, msg} = res;
            if(retCode !== 500)
            {
                this.$Message.info({
                    content: msg,
                    duration: 3,
                    closable: true
                });
            }else { //sucess
                this.getusertouzishouyisum(undefined);
            }
        });

        this.$refs['pLAddForm'].resetFields();
        this.addPL_loading = false;
        this.bShowPLXinzengShouyi = false;
    },

    deleteTouzi(index){
        var deleteTouzi = {
            jizhangid: this.data[index].jizhangid,
            id: this.data[index].id,
            jizhangriqi: this.data[index].jizhangriqi
        }
         
        DeleteUserSumJizhang(deleteTouzi).then( (res) =>{
            let{retCode} = res;
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

   getusertouzishouyisum(userid) {
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
      GetJizhangSumInfo(useridinfo).then( (res) =>{
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
      this.getusertouzishouyisum(undefined);
    }
  },
  mounted() {
      this.init();
      this.heightVar = window.innerHeight - this.$refs.touziTable.$el.offsetTop - 140;
      var userid = this.$route.query.userid;
      this.getusertouzishouyisum(userid);
  },
};
</script>
