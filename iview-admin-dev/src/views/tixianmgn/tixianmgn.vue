<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
                <Button type="primary"  :loading="get_tixian_loading" @click="gettixianRec">获取最新体现信息</Button>
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
                <Table ref="tixianTable" border :columns="columns" :data="data" :height="heightVar" ></Table>
            </Col>
        </Row>
    </div>
</template>

<script>
import util from '../../libs/util';
import {GetTixianInfo, ExcuteTixianInfo} from '../../api/api'
export default {
  data() {
    return {
      columns: [
            {
                title: '身份证',
                key: 'id',
                render: (h, params) => {
                    return h('div', [
                        h('Icon', {
                            props: {
                             type: 'person'
                            }
                        }),
                        h('strong', params.row.id)
                    ]);
                }
            },
            {
                title: '提现金额',
                key: 'tixiane',
            },
            {
                title: '提现日期',
                key: 'tixianriqi'
            },
            {
                title: '操作',
                key: 'action',
                width: 300,
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
                                    this.excuteTixian(params.index);
                                }
                            }
                        }, '确认用户提现')
                    ])
                }
            }
        ],

        searchConName: "",
        data: [],
        initTable: [],
        datamap: null,
        bSerache: false,
        get_tixian_loading: false,
    };
  },
  methods: {
      
    init() {
       this.datamap = new Map;
       this.bSerache = false;
       this.get_tixian_loading = false;
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

    excuteTixian(index){

        var excuteTixian = {
            tixianid: this.data[index].tixianid,
            id: this.data[index].id,
            tixianriqi: this.data[index].tixianriqi,
            tixiane: this.data[index].tixiane
        }

        ExcuteTixianInfo(excuteTixian).then( (res) =>{
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

    gettixianRec()
    {
        this.get_tixian_loading = true;
        GetTixianInfo().then((res) => {
            let { retCode, tixianlist } = res;
            if (retCode !== 500) {
                this.$Message.info({
                    content: '没有用户提现信息！',
                    duration: 3,
                    closable: true
                });
            } else {
                this.data = tixianlist;
                this.initTable = tixianlist;
            }
            this.get_tixian_loading = false;
		});
    }

  },

  mounted() {
    this.init();
    this.heightVar = window.innerHeight - this.$refs.tixianTable.$el.offsetTop - 140;
    this.gettixianRec();
  }
};
</script>
