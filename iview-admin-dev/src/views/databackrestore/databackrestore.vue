<template>
<div>
    <Row :gutter="10">
        <Col span="24">
            <Row>
                <!-- <Button type="primary" :loading="get_backlist_loading" @click="getbackrec">刷新最新备份列表</Button> -->
                <Button type="primary" :loading="backdata_optioning" @click="backdata">备份当前数据</Button>
            </Row>
        </Col>
    </Row>
    <Row><br/></Row>
    <Table ref="backrecTable" border :columns="backreccolumns" :data="dataRecInfo" :height="heightVar"></Table>
</div>
</template>
<script>
import {GetbackrecInfo, RestorDataInfo, BackdataInfo} from "../../api/api";
    export default {
        name: 'databackrestore_index',
        data () {
            return {
                get_backlist_loading: false,
                backdata_optioning: false,
                restore_optioning: false,
                dataRecInfo: [],
                backreccolumns: [
                    {
                        title: '数据备份文件',
                        key: 'backfile',
                    }
                    // {
                    //     title: '操作',
                    //     key: 'action',
                    //     align: 'left',
                    //     render: (h, params) => {
                    //         return h('div', [
                    //             h('Button', {
                    //                 style: {
                    //                     margin: '0 5px'
                    //                 },
                    //                 props: {
                    //                     type: 'success',
                    //                     size: 'small'
                    //                 },
                    //                 on: {
                    //                     click: () => {
                    //                         this.restoredata(params.index);
                    //                     }
                    //                 }
                    //             }, '删除'),
                    //         ])
                    //     }
                    // }
                ]
            }
        },
        methods: {
            getbackrec()
            {
                GetbackrecInfo().then( (res) =>{
                    let{retCode, backlist} = res;
                    if(retCode === 500)
                    {
                        this.dataRecInfo = backlist;
                    }else{ //fail
                        ;
                    }
                });
            },

            backdata()
            {
                BackdataInfo().then( (res) =>{
                    let{retCode, backlist} = res;
                    if(retCode === 500)
                    {
                        this.dataRecInfo = backlist;
                    }else{ //fail
                        this.$Message.error('备份数据失败！');
                    }
                });
            },

            restoredata(index){
                var restore = {
                    backid: this.dataRecInfo[index].backid,
                }
         
                RestorDataInfo(restore).then( (res) =>{
                    let{retCode} = res;
                    if(retCode !== 500)
                    {
                        this.$Message.error('还原数据失败！');
                    } else { //sucess
                        this.$Message.error('还原数据成功，请重新登录！');
                    }
                });
            },
        },
        mounted(){
            this.heightVar = window.innerHeight - this.$refs.backrecTable.$el.offsetTop - 120;
            this.getbackrec();
        }
    }
    
</script>