<template>
    <div>
        <p class="page-container">404 页面未找到</p>
        <p class="page-footer"><router-link to="/home">返回首页</router-link></p>
    </div>

</template>

<style lang="scss" scoped>
    .page-container {
        font-size: 80px;
        text-align: center;
        color: rgb(192, 204, 218);
    }
    .page-footer{
        width: 100%;
        text-align: center;
    }
    .page-footer a{
        font-size: 25px;
        /*color: #;*/
        color: rgba(28, 141, 224, 0.69);
        text-align: center;
        text-decoration: none;
    }
</style>