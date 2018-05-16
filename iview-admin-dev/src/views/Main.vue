<style lang="less">
@import "./main.less";
</style>
<template>
    <div class="main" :class="{'main-hide-text': shrink}">
        <div class="sidebar-menu-con" :style="{width: shrink?'60px':'200px', overflow: shrink ? 'visible' : 'auto'}">
            <shrinkable-menu 
                :shrink="shrink"
                :open-names="openedSubmenuArr"
                :menu-list="menuList">
            </shrinkable-menu>
        </div>
        <div class="main-header-con" :style="{paddingLeft: shrink?'60px':'200px'}">
            <div class="main-header">
                <div class="navicon-con">
                    <Button :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
                        <Icon type="navicon" size="32"></Icon>
                    </Button>
                </div>
                <div class="header-avator-con">
                    <message-tip v-model="mesCount" v-show="bshowMsgTip"></message-tip>
                    <databaseopt v-show="bshowdataopt"></databaseopt>
                    <lock-screen></lock-screen>
                    <loginout></loginout>
                </div>
            </div>
            <div class="tags-con">
                <tags-page-opened :pageTagsList="pageTagsList"></tags-page-opened>
            </div>
        </div>
        <div class="single-page-con" :style="{left: shrink?'60px':'200px'}">
            <div class="single-page">
                <keep-alive :include="cachePage">
                    <router-view></router-view>
                </keep-alive>
            </div>
        </div>
    </div>
</template>
<script>
import shrinkableMenu from "./main-components/shrinkable-menu/shrinkable-menu.vue";
import tagsPageOpened from "./main-components/tags-page-opened.vue";
import breadcrumbNav from "./main-components/breadcrumb-nav.vue";
import lockScreen from "./main-components/lockscreen/lockscreen.vue";
import messageTip from "./main-components/message-tip.vue";
import loginout from "./main-components/loginout.vue"
import databaseopt from "./main-components/databaseopt.vue"
import Cookies from "js-cookie";
import util from "@/libs/util.js";
import {GetTixianMessage} from '../api/api';

export default {
  components: {
    shrinkableMenu,
    tagsPageOpened,
    breadcrumbNav,
    lockScreen,
    messageTip,
    databaseopt,
    loginout
  },
  data() {
    return {
      shrink: false,
      openedSubmenuArr: this.$store.state.app.openedSubmenuArr,
      bshowMsgTip: false,
      bshowdataopt: false,
      timer: null
    };
  },
  computed: {
    menuList() {
      return this.$store.state.app.menuList;
    },
    pageTagsList() {
      return this.$store.state.app.pageOpenedList; // 打开的页面的页面对象
    },
    currentPath() {
      return this.$store.state.app.currentPath; // 当前面包屑数组
    },
    cachePage() {
      return this.$store.state.app.cachePage;
    },

    mesCount() {
      return this.$store.state.app.messageCount;
    }
  },
  methods: {
    init() {
      let pathArr = util.setCurrentPath(this, this.$route.name);
      this.$store.commit("updateMenulist");
      if (pathArr.length >= 2) {
        this.$store.commit("addOpenSubmenu", pathArr[1].name);
      }
      this.checkTag(this.$route.name);
     
    },
    toggleClick() {
      this.shrink = !this.shrink;
    },
    checkTag(name) {
      let openpageHasTag = this.pageTagsList.some(item => {
        if (item.name === name) {
          return true;
        }
      });
      if (!openpageHasTag) {
        //  解决关闭当前标签后再点击回退按钮会退到当前页时没有标签的问题
        util.openNewPage(
          this,
          name,
          this.$route.params || {},
          this.$route.query || {}
        );
      }
    },
  },
  watch: {
    $route(to) {
      this.$store.commit("setCurrentPageName", to.name);
      let pathArr = util.setCurrentPath(this, to.name);
      if (pathArr.length > 2) {
        this.$store.commit("addOpenSubmenu", pathArr[1].name);
      }
      this.checkTag(to.name);
      localStorage.currentPageName = to.name;
    },
  },
  mounted() {
    let accessCode = parseInt(Cookies.get('access'));
    if(1 === accessCode)
    {
        this.bshowMsgTip = true;
        setInterval( ()=> {
            var params = {
                id: Cookies.get('id')
            }
            //查询用户的消息
            GetTixianMessage(params).then( (res) =>{
                let { retCode, messagelist } = res;
                if (retCode === 500) {
                    this.$store.commit('clearUsermessageList');
                    let len = messagelist.length;
                    this.$store.commit('setMessageCount', len);
                    this.$store.commit('setUserMessageList', messagelist);
                    this.$store.commit('setuserhasreadmessagelist', []);
                } else {
                    ;
                }
            });
              
        }, 120000);
    }else{
      this.bshowdataopt = true;
    }

    this.init();
  },
  created() {
    // 显示打开的页面的列表
    this.$store.commit("setOpenedList");
  },
  destoryed()
  {
      // if(this.bshowMsgTip === true)
      // {
      //   if(this.timer !== null)
      //   {
      //       clearInterval(timer);
      //   }
      // }
  }
};
</script>
