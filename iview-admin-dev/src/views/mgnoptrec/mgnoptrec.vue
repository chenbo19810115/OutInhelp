<template>
<div>
    <Row :gutter="10">
        <Col span="24">
            <Row>
                <Button type="primary" :loading="get_optrec_loading" @click="getOptRec">刷新操作日志</Button>
                <Button type="primary" :loading="get_optrec_clearing" @click="clearOptRec">清除操作日志</Button>
            </Row>
        </Col>
    </Row>
    <Row><br/></Row>
    <Table ref="optRecTable" border :columns="optReccolumns" :data="optRecInfo" :height="heightVar"></Table>
</div>
</template>
<script>
import {GetOptRecInfo, ClearOptRecInfo} from "../../api/api";
    export default {
        data () {
            return {
                get_optrec_loading: false,
                get_optrec_clearing: false,
                optRecInfo: [],
                optReccolumns: [
                    {
                        title: '操作时间',
                        key: 'opttime',
                        width: 200
                    },
                    {
                        title: '操作类型',
                        key: 'optdescription',
                        width: 200
                    },
                    {
                        title: '操作内容',
                        key: 'optcontent'
                    },
                ],
            }
        },
        methods: {
            getOptRec()
            {
                GetOptRecInfo().then( (res) =>{
                    let{retCode, Optinfolist} = res;
                    if(retCode === 500)
                    {
                        this.optRecInfo = Optinfolist;
                    }else{ //fail
                        ;
                    }
                });
            },

            clearOptRec()
            {
                ClearOptRecInfo().then( (res) =>{
                    let{retCode} = res;
                    if(retCode === 500)
                    {
                        this.getOptRec();
                    }else { //fail
                        ;
                    }
                });
            }
        },
        mounted(){
            this.heightVar = window.innerHeight - this.$refs.optRecTable.$el.offsetTop - 120;
            this.getOptRec();
        }

    }
</script>