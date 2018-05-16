<template>
<div>
    <Row :gutter="10">
        <Col span="24">
            <Button type="primary" :loading="save_regreq_loading" @click="getregRec">获取最新用户注册请求</Button>
        </Col>
    </Row>
    <Row><br/></Row>
    <Table ref="regTable" border :columns="regcolumns" :data="regInfo" :height="heightVar"></Table>
</div>
</template>
<script>
import { getRegInfo, sendRegJihuo, deleteReginfo } from "../../api/api";
    export default {
        data () {
            return {
                save_regreq_loading: false,
                regcolumns: [
                    {
                        title: '姓名',
                        key: 'name',
                        render: (h, params) => {
                            return h('div', [
                                h('Icon', {
                                    props: {
                                        type: 'person'
                                    }
                                }),
                                h('strong', params.row.name)
                            ]);
                        }
                    },
                    {
                        title: '手机号码',
                        key: 'cellphone'
                    },
                    {
                        title: '身份证号',
                        key: 'id'
                    },
                    {
                        title: '邮箱',
                        key: 'email',
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 400,
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
                                            
                                        }
                                    }
                                }, '验证用户身份'),
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
                                            this.remove(params.index);
                                        }
                                    }
                                }, '删除用户注册请求'),
                                 h('Button', {
                                     style: {
                                        margin: '0 5px'
                                    },
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.sendRegEmail(params.index)
                                        }
                                    }
                                }, '发送给用户注册邮件')
                            ]);
                        }
                    }
                ],
                regInfo: [
                ]
            }
        },
        methods: {
            getregRec: function (){
                this.save_regreq_loading = true;
                getRegInfo().then((res) => {
                    let { retCode, reglist } = res;
                    if (retCode !== 500) {
                        this.$Message.info({
                            content: '获取用户的注册请求列表失败！',
                            duration: 3,
                            closable: true
                        });
                    } else {
                        this.regInfo = reglist;
                    }
                    this.save_regreq_loading = false;
				});
            },
            remove (index) {
                 var regInfo = {
                    id: this.regInfo[index].id,
                };
                deleteReginfo(regInfo).then((res) => {
                    this.getregRec();
                });
            },
            sendRegEmail(index){
                var regInfo = {
                    id: this.regInfo[index].id,
                };
                //成功再删除
                sendRegJihuo(regInfo).then((res) => {
                    let{retCode, msg} = res;
                    if(retCode !== 500)
                    {
                        this.$Message.info({
                            content: msg,
                            duration: 3,
                            closable: true
                        });
                    }else {
                        this.$Message.info({
                            content: msg,
                            duration: 3,
                            closable: true
                        });
                    }
                    this.getregRec();
                });
            }
        },
        mounted(){
            this.heightVar = window.innerHeight - this.$refs.regTable.$el.offsetTop - 120;
            this.getregRec();
        }

    }
</script>