<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
                <Button type="primary"  :loading="get_sumbangdan_loading" @click="getSumbangdan">获取最新收益总榜单</Button>
            </Col>
        </Row>
        <Row><br/></Row>
        <Row :gutter="10">
            <Col span="24">
                <Table ref="tixianTable" border :columns="columns" :data="data" :height="heightVar" ></Table>
            </Col>
        </Row>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import {GetSumbangdanRec} from '../../api/api'
export default {
  data() {
    return {
      columns: [
            {
                title: '姓名',
                key: 'name',
            },
            {
                title: '总投资',
                key:'zongtouzie',
            },
            {
                title: '当日收益',
                key: 'shouyie',
            },
            {
                title: '半年收益',
                key: 'bannianshouyie'
            },
            {
                title: '总收益',
                key: 'zongshouyie',
            }
        ],

        data: [],
        get_sumbangdan_loading: false,
    };
  },
  methods: {

    init() {

    },

    getSumbangdan()
    {
        this.gget_sumbangdan_loading = true;
        let userid ={
            id: Cookies.get('id'),
        }
        GetSumbangdanRec(userid).then((res) => {
            let { retCode, sumbangdanlist } = res;
            if (retCode !== 500) {
                this.$Message.info({
                    content: '没有总榜单信息！',
                    duration: 3,
                    closable: true
                });
            } else {
                this.data = sumbangdanlist;
            }
            this.get_sumbangdan_loading = false;
		});
    }

  },

  mounted() {
    this.heightVar = window.innerHeight - this.$refs.tixianTable.$el.offsetTop - 50;
    this.getSumbangdan();
  }
};
</script>
