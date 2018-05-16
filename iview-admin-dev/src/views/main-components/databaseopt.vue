<template>
    <div @click="showOptUI" class="database-con">
        <Tooltip content="数据备份还原" placement="bottom">
            <Icon type="soup-can" :size="20"></Icon>
        </Tooltip>
    </div>
</template>

<script>
import util from '@/libs/util.js';
export default {
    name: 'databaseopt',
    methods: {
        showOptUI () {
            util.openNewPage(this, 'databackrestore_index');
            this.$router.push({
                name: 'databackrestore_index'
            });
        }
    }
};
</script>