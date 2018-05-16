<style lang="less" scoped>
    @import '../../styles/common.less';
    @import '../tables/components/table.less';
</style>

<template>
    <div>
        <Row class="margin-top-10">
            <Card>
                <h4 slot="title">
                    <Icon type="ios-grid-view"></Icon>
                    个人收益记录
                </h4>
                <Row>
                    <Col span="15">
                        <Table :columns="excelColumns" height="390px" :data="table2excelData" size="small" ref="tableExcel"></Table>
                    </Col>
                    <Col span='6' class="padding-left-10">
                         <div class="margin-top-10 margin-left-10">
                            <span>输入文件名：</span>
                            <Input v-model="excelFileName" icon="document" placeholder="请输入文件名" style="width: 190px" />
                        </div>
                        <div class="margin-left-10 margin-top-20">
                            <a id="hrefToExportTable" style="postion: absolute;left: -10px;top: -10px;width: 0px;height: 0px;"></a>
                            <Button type="primary" size="large" @click="exportExcel">下载表格数据</Button>
                        </div>
                    </Col>
                </Row>
            </Card>
        </Row>
    </div>
</template>

<script>
import excelColumns from './data/tablejiegou.js';
import table2excel from '../../libs/table2excel.js';
export default {
    name: 'exportable-table',
    data () {
        return {
            table2excelData: [],
            excelColumns: excelColumns,
            excelFileName: '',
            tableExcel: {},
        };
    },
    methods: {
        exportExcel () {
            table2excel.transform(this.$refs.tableExcel, 'hrefToExportTable', this.excelFileName);
        }
    }
};
</script>
