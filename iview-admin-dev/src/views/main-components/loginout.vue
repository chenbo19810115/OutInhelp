<template>
    <div @click="handleClickCloseBtn"  class="close-app-btn-con">
        <Tooltip content="退出登录" placement="bottom">
            <Icon type= "android-cancel" :size="23"></Icon>
        </Tooltip>
    </div>
</template>

<script>
export default {
    name: 'loginout',
    methods: {
        handleClickCloseBtn () {
            this.$store.commit("clearOpenedSubmenu");
            this.$store.commit("logout", this);
            this.$router.push({
                 name: "login"
        });
        }
    }
};
</script>
