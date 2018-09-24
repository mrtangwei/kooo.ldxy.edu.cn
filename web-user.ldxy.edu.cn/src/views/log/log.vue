<template>
    <section>
        <!--工具条-->
        <div class="toolbar">
            <el-form :inline="true" :model="filters" style="margin: 0 ;padding: 0" >
                <div class="block">
                    <span class="demonstration">选择日志时间范围</span>
                    <el-date-picker
                            v-model="data"
                            type="daterange"
                            align="left"
                            placeholder="选择日期范围"
                            :picker-options="pickerOptions2"
                            @change="handleTimeChange"
                    >
                    </el-date-picker>

                </div>

            </el-form>
        </div>

        <!--列表-->
        <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="index" width="60" sortable>
            </el-table-column>
            <el-table-column prop="user" label="用户" width="150" sortable>
            </el-table-column>
            <el-table-column prop="ip" label="ip地址" width="150" sortable>
                            </el-table-column>
            <el-table-column prop="time" label="操作时间" width="200" sortable>
            </el-table-column>
            <el-table-column prop="exe" label="操作内容" sortable>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-size="20"
                    layout="total, sizes, prev, pager, next, jumper"
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
                page: 0,
                size:20,
                pickerOptions2: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                value6: '',
                data: '',
                filters: {
                    name: ''
                },
                users: [],
                total: 0,
                listLoading: false,
                sels: [],//列表选中列


            }
        },
        methods: {
            handleTimeChange(){
                var url = "/security/log/time/after/";
                 this.$http.get('/api/security/log/time/' + moment(this.data[0]).format('YYYY-MM-DD HH:mm:ss') +'/'
                    + moment(this.data[1]).format('YYYY-MM-DD HH:mm:ss')
                    + '?size=' + this.size + '&page=' + this.page + '&sort=time,desc').then(response => {
                    console.log(response.data)
                    this.users = response.data.content;
                    this.totalElements = response.data.totalElements;
                    this.listLoading = false;
                    for (let i in this.users){
                        this.users[i].time = moment(response.data.content[i].time).format('YYYY-MM-DD HH:mm:ss')
                    }
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
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.getLogs(20,val)
            },
            //获取用户列表
            getLogs(size,page) {
                this.listLoading = true;
                this.$http.get('/api/security/log/all' + '?size=' + size + '&page=' + page + '&sort=time,desc').then(response => {

                    this.users = response.data.content;
                    this.totalElements = response.data.totalElements;
                    this.listLoading = false;
                    for (let i in this.users){
                        this.users[i].time = moment(response.data.content[i].time).format('YYYY-MM-DD HH:mm:ss')
                    }
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
            //删除
/*            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    //NProgress.start();
                    let para = { id: row.id };
                    removeUser(para).then((res) => {
                        this.listLoading = false;
                        //NProgress.done();
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getUsers();
                    });
                }).catch(() => {

                });
            },*/


            selsChange: function (sels) {
                this.sels = sels;
            },
            //批量删除
          /*  batchRemove: function () {
                var ids = this.sels.map(item => item.id).toString();
                this.$confirm('确认删除选中记录吗？', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    //NProgress.start();
                    let para = { ids: ids };
                    batchRemoveUser(para).then((res) => {
                        this.listLoading = false;
                        //NProgress.done();
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getUsers();
                    });
                }).catch(() => {

                });
            }*/
        },
        mounted() {
            this.getLogs(20,0);
        }
    }

</script>

<style scoped>

</style>