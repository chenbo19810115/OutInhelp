<style lang="less">
    @import './message.less';
</style>

<template>
    <div class="message-main-con">
        <div class="message-mainlist-con">
            <div>
                <Button @click="setCurrentMesType('unread')" size="large" long type="text"><transition name="mes-current-type-btn"><Icon v-show="currentMessageType === 'unread'" type="checkmark"></Icon></transition><span class="mes-type-btn-text">未读消息</span><Badge class="message-count-badge-outer" class-name="message-count-badge" :count="unreadCount"></Badge></Button>
            </div>
            <div>
                <Button @click="setCurrentMesType('hasread')" size="large" long type="text"><transition name="mes-current-type-btn"><Icon v-show="currentMessageType === 'hasread'" type="checkmark"></Icon></transition><span class="mes-type-btn-text">已读消息</span><Badge class="message-count-badge-outer" class-name="message-count-badge" :count="hasreadCount"></Badge></Button>
            </div>
        </div>
        <div class="message-content-con">
            <transition name="view-message">
                <div v-if="showMesTitleList" class="message-title-list-con">
                    <Table ref="messageList" :columns="mesTitleColumns" :data="currentMesList" :no-data-text="noDataText"></Table>
                </div>
            </transition>
            <transition name="back-message-list">
                <div v-if="!showMesTitleList" class="message-view-content-con">
                    <div class="message-content-top-bar">
                        <span class="mes-back-btn-con"><Button type="text" @click="backMesTitleList"><Icon type="chevron-left"></Icon>&nbsp;&nbsp;返回</Button></span>
                        <h3 class="mes-title">{{ mes.title }}</h3>
                    </div>
                    <p class="mes-time-con"><Icon type="android-time"></Icon>&nbsp;&nbsp;{{ mes.time }}</p>
                    <div class="message-content-body">
                        <p class="message-content">{{ mes.content }}</p>
                    </div>
                </div>
            </transition>
        </div>
    </div>
</template>

<script>
import {userAlreadReadmessage} from '../../api/api';
export default {
    name: 'message_index',
    data () {
        return {
            currentMesList: [],
            currentMessageType: 'unread',
            showMesTitleList: true,
            unreadCount: 0,
            hasreadCount: 0,
            noDataText: '暂无未读消息',
            mes: {
                title: '',
                time: '',
                content: ''
            },
            mesTitleColumns: [
                {
                    title: ' ',
                    key: 'title',
                    align: 'left',
                    ellipsis: true,
                    render: (h, params) => {
                        return h('a', {
                            on: {
                                click: () => {
                                    this.showMesTitleList = false;
                                    this.mes.title = params.row.title;
                                    this.mes.time = params.row.time;
                                    this.getContent(params.index);
                                }
                            }
                        }, params.row.title);
                    }
                },
                {
                    title: ' ',
                    key: 'time',
                    align: 'center',
                    width: 180,
                    render: (h, params) => {
                        return h('span', [
                            h('Icon', {
                                props: {
                                    type: 'android-time',
                                    size: 12
                                },
                                style: {
                                    margin: '0 5px'
                                }
                            }),
                            h('span', {
                                props: {
                                    type: 'android-time',
                                    size: 12
                                }
                            }, params.row.time)
                        ]);
                    }
                }
            ]
        };
    },
    methods: {
        backMesTitleList () {
            this.showMesTitleList = true;
        },
        setCurrentMesType (type) {
            if (this.currentMessageType !== type) {
                this.showMesTitleList = true;
            }
            this.currentMessageType = type;
            if (type === 'unread') {
                this.noDataText = '暂无未读消息';
                this.currentMesList = this.unreadMesList;
            } else if (type === 'hasread') {
                this.noDataText = '暂无已读消息';
                this.currentMesList = this.hasreadMesList;
            } else {
                ;
            }
        },
        getContent (index) {
            // you can write ajax request here to get message content
            let mesContent = '';
            if(this.currentMessageType === 'unread')
            {
                mesContent = this.unreadMesList[index].content;
                var tixianidOpt = this.currentMesList[index].tixianid;
                this.hasreadMesList.unshift(this.currentMesList.splice(index, 1)[0]);
                this.$store.commit('setMessageCount', this.unreadMesList.length);
                this.unreadCount  = this.unreadMesList.length;
                var params = {tixianid: tixianidOpt};
                userAlreadReadmessage(params).then( (res) =>{
                    let { retCode} = res;
                    if (retCode === 500) {
                        ;
                    } else {
                        ;
                    }
                });
            }else if(this.currentMessageType === 'hasread'){
                mesContent = this.hasreadMesList[index].content;
            }
            this.mes.content = mesContent;
        }
    },
    mounted () {
        //根据用户ID读取体现消息
        // this.unreadMesList = this.$store.state.app.usermessagelist;
        // this.currentMesList = this.unreadMesList;
        // this.unreadCount = this.unreadMesList.length;
        // this.hasreadCount = 0;
    },
    watch: {
        unreadMesList (arr) {
            this.unreadCount = arr.length;
        },
        hasreadMesList (arr) {
            this.hasreadCount = arr.length;
        }
    },

    computed:{
        unreadMesList()
        {
            return this.$store.state.app.usermessagelist;
        },

        hasreadMesList()
        {
            return this.$store.state.app.userhasreadmessagelist;
        }
    }
};
</script>

