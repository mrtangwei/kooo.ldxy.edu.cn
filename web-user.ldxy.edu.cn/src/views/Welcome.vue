<template>
    <div class="dashboard-editor-container">
        <div class="clearfix">
            <div class="info-container">
                <span class="display_name">{{sysRealName}}</span>
                <span style='font-size:20px;padding-top:20px;display:inline-block;'>{{sysUserRole}}</span>
            </div>
        </div>
        <div>
            <img class='emptyGif' :src="emptyGif" >
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3',
                sysRealName: '',
                sysUserAvatar: '',
                sysUserRole: '',
                reflash: true
            }
        },
        mounted() {
            var user = JSON.parse(sessionStorage.getItem('user'));
            this.userId = user.id;
            if (user) {
                this.sysRealName = user.realName;
                this.sysUserAvatar = user.avatar || '//src.qylhs.com/user.jpg';
                this.sysUserRole = user.roleName;
            }
            if (this.reflash){
//                window.location.reload()
                this.reflash = false
            }
        }
    }
</script>

<style lang="scss" scoped>
    .el-menu-vertical-demo {
        overflow-y: scroll;
    }
    .emptyGif {
        display: block;
        width: 45%;
        margin: 0 auto;
    }
    .dashboard-editor-container {
        background-color: #e3e3e3;
        width: 106%;
        margin: -50px;
        padding: 100px 0px 10%;
        .pan-info-roles {
            font-size: 12px;
            font-weight: 700;
            color: #333;
            display: block;
        }
        .info-container {
            position: relative;
            margin-left: 190px;
            height: 150px;
            line-height: 200px;
            .display_name {
                font-size: 48px;
                line-height: 48px;
                color: #212121;
                position: absolute;
                top: 25px;
            }
        }
    }
</style>
