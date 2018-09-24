<template>
    <section>
        <!--工具条-->
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-input v-model="filters.title" placeholder="搜索文章标题"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item style="margin: 0;">
                    <el-button type="primary" @click="addWeChatMsg">发送通知</el-button>
                </el-form-item>

            </el-form>
        </el-col>

        <!--列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="index" width="60" sortable>
            </el-table-column>
            <el-table-column prop="title" label="标题" width="160" sortable>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="180"  sortable >
            </el-table-column>
            <el-table-column prop="department" label="部门" width="160" sortable>
            </el-table-column>
            <el-table-column prop="content" label="内容" width="160" sortable>
            </el-table-column>
            <el-table-column >
            </el-table-column>
<!--            <el-table-column label="操作" width="200">
                <template scope="scope">
                    <el-button type="primary" size="small" @click="editArticle(scope.row)">编辑</el-button>
                    <el-button type="warning" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>-->
        </el-table>
        <div class="block">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :total="totalElements">
            </el-pagination>
        </div>


    </section>
</template>

<script>

    var moment = require('moment');

    export default {
        data() {
            return {
                currentPage: 1,
                totalElements:2,
                page:0,
                size:15,
                dataSet: [],
                url:'/api/security/weChat/message/all' + '?size=' + this.size + '&page=' + this.page + '&sort=date,desc',
                filters:{
                    title:''
                },


                listLoading: false,
                sels: [],//列表选中列

                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
            }
        },
        methods: {

            //获取列表
            getDataSet(size,page) {
                this.listLoading = true;
                this.$http.get('/api/security/weChat/message/all' + '?size=' + size + '&page=' + page + '&sort=date,desc').then(response => {
                    //console.log(response.data)
                    this.dataSet = response.data.content;
                    this.totalElements = response.data.totalElements;
                    for (let i in this.dataSet){
                        this.dataSet[i].date = moment(response.data.content[i].date).format('YYYY-MM-DD HH:mm:ss')
                    }
                    this.listLoading = false;
                }, response => {
                    console.log("拉取数据错误！");
                })
            },
            handleSizeChange(val) {
                this.getDataSet(this.size,val)
            },
            handleCurrentChange(val) {
                if(val > 0) val--;
                this.getDataSet(this.size,val)
            },
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    let para = row.id;
                    this.$http.post('/api/security/weChat/delete/',{
                        'id': para
                    }).then(response => {
                        console.log(response);
                        this.listLoading = false;
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getDataSet(this.size,this.page);
                    }, response => {
                        if(response.status == 401){
                            console.log("请登录！");
                            this.$router.push('/login');
                        } else if(response.status == 403){
                            console.log("权限不够，请联系管理员！");
                            this.$router.push('/login');
                        }
                    })
                }).catch(() => {

                });
            },

            selsChange: function (sels) {
                this.sels = sels;
            },
            //编辑
            editArticle: function (row) {
                let oId = row.id;
                this.$router.push({ name:'编辑文章', params:{ id: oId }})
            },
            addWeChatMsg(){
                this.$router.push('/addWeChatMsg')
            },
            // 搜索
            search(){
                this.btnSwitch = true;
                this.listLoading = true;
                this.$http.get('/api/security/weChat/message/title/'+this.filters.title).then(response => {
                        this.dataSet = response.data.content;
                        this.totalElements = response.data.totalElements;
                        this.numberOfElements = response.data.numberOfElements;
                        for (let i in this.dataSet){
                            this.dataSet[i].date = moment(response.data.content[i].date).format('YYYY-MM-DD HH:mm:ss')
                        }
                        this.listLoading = false;

                }, response => {
                    if(response.status == 401){
                        console.log("请登录！");
                        this.$router.push('/login');
                    } else if(response.status == 403){
                        console.log("权限不够，请联系管理员！");
                        this.$router.push('/login');
                    }
                })
            },
        },
        mounted() {
            this.getDataSet(this.size,this.page);
        }
    }

</script>

<style scoped>

</style>