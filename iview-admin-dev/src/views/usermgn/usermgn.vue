<template>
    <div>
        <Row :gutter="10">
            <Col span="24">
                <Button type="primary"  :loading="save_usermgn_loading" @click="getUserRec">获取最新用户信息</Button>
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
                <Table ref="userTable" border :columns="columns" :data="userInfo" :height="heightVar"></Table>
            </Col>
        </Row>
    </div>

</template>
<script>
import { getUserInfo, removeUser} from "../../api/api";
export default {
  data() {
    return {
      save_usermgn_loading: false,
      columns: [
        {
          title: "姓名",
          key: "name",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "person"
                }
              }),
              h("strong", params.row.name)
            ]);
          }
        },
        {
          title: "身份证",
          key: "id"
        },
        {
          title: "电话号码",
          key: "cellphone"
        },
        {
          title: "邮箱",
          key: "email"
        },
        {
          title: "投资收益详单",
          key: "touzishouyi",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                       this.$router.push({
                          name: 'touzishouyimgn_index',
                          query: {userid: params.row.id}
                      });
                    }
                  }
                },
                "详单"
              )
            ]);
          }
        },
        {
          title: "操作",
          key: "action",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small"
                  },
                  on: {
                    click: () => {
                      this.remove(params.index);
                    }
                  }
                },
                "删除用户"
              )
            ]);
          }
        }
      ],

      userInfo: [],
      inituserInfo: [],
      touziren: "",
      searchConName: "",
    };
  },
  methods: {
    search(data, argumentObj) {
      let res = data;
      let dataClone = data;
      for (let argu in argumentObj) {
        if (argumentObj[argu].length > 0) { //删除用户暂时不能用，需要map操作
          res = dataClone.filter((d, idx) => {
            return d[argu].indexOf(argumentObj[argu]) > -1;
          });
          dataClone = res;
        }
      }
      return res;
    },

    handleCancel() {
      this.userInfo = this.inituserInfo;
      this.searchConName = '';
    },

    handleSearch() {
      this.userInfo = this.inituserInfo;
      this.userInfo = this.search(this.userInfo, { id: this.searchConName });
    },

    getUserRec() {
      this.save_usermgn_loading = true;
      getUserInfo().then(res => {
        this.userInfo = res.userInfo;
        this.inituserInfo = res.userInfo;
        this.save_usermgn_loading = false;
      });
    },
    remove(index) {
      this.touziren = this.userInfo[index].name;
      var n = this.$Modal.confirm({
        title: "提示",
        content: "确认删除用户 " + this.touziren + " 吗?",
        onOk: () => {
          let para = { id: this.userInfo[index].id };
          removeUser(para).then(res => {
            this.$Message.success({
              content: "删除成功",
              duration: 3,
              closable: true
            });
            this.getUserRec();
          });
        }
      });
      this.getUserRec();
    }
  },
  mounted() {
    this.heightVar =
      window.innerHeight - this.$refs.userTable.$el.offsetTop - 140;
      this.getUserRec();
  }
};
</script>