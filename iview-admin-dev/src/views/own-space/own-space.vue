<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
                <Button type="primary"  :loading="save_touzishouyi_loading" @click="refreshusertouzishouyi">刷新收益信息</Button>
            </Col>
        </Row>
        <Row><br/></Row>
        <Row :gutter="10">
            <Col span="24">
                <Table ref="touziTable" border :columns="columns" :data="data" :height="heightVar"></Table>
            </Col>
        </Row>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import { GetUsrJizhangInfo} from '../../api/api'
export default {
  data() {
    return {
      columns: [
        {
          title: "身份证",
          key: "id"
        },
        {
          title: "日期",
          key: "jizhangriqi"
        },
        {
          title: "新增投资",
          key: "xinzengtouzie",
        },
        {
          title: "提现",
          key: "tixiane"
        },
        {
          title: "收益",
          key: "shouyie"
        }
      ],
        
        id:0,
        save_touzishouyi_loading: false,
        data:[]
    };
  },
  methods: {

    init() {
       this.save_touzishouyi_loading = false;
       this.id = Cookies.get('id');
    },

    getusertouzishouyi(userid) {

      var useridinfo = {
        id: this.id
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
      this.getusertouzishouyi(Cookies.get('id'));
  },
};
</script>
