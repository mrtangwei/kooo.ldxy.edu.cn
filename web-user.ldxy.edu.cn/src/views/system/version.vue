<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true">
                <el-form-item>
                    <el-button type="primary" @click="handleAdd">新增版本信息</el-button>
                </el-form-item>
            </el-form>
        </el-col>
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
                  style="width: 100%;">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="版本号">
                            <span>{{ props.row.name }}</span>
                        </el-form-item>
                        <el-form-item label="发布日期">
                            <span>{{ props.row.date }}</span>
                        </el-form-item>
                        <el-form-item label="更新内容">
                            <span>{{ props.row.content }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="版本号" width="280" sortable>
            </el-table-column>
            <el-table-column prop="date" label="发布日期" width="300" sortable>
            </el-table-column>
            <el-table-column pro="content" label="更新内容" width="300">
            </el-table-column>
            <el-table-column>
            </el-table-column>
        </el-table>

        <!--增加版本信息-->
        <el-dialog title="增加版本信息" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
                <el-form-item label="版本名" prop="name">
                    <el-input v-model="addForm.name" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="更新内容" prop="name">
                    <el-input type="textarea" v-model="addForm.content" auto-complete="off" ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>




</template>

<style>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>

<script>
    var moment = require('moment');
    export default {
        data() {
            return {
                listLoading: false,
                dataSet:[],
                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    name: [
                        {required: true, message: '请输入版本信息', trigger: 'blur'}
                    ]
                },
                //新增界面数据
                addForm: {
                    name: '',
                }
            }
        },
        methods: {
            getData:function (){
                this.$http.get('/api/security/system/version/all?sort=date,desc').then(response => {
                   this.dataSet = response.data.content;
                    for (let i in this.dataSet){
                        this.dataSet[i].date = moment(response.data.content[i].date).format("YYYY-MM-DD HH:MM:SS")
                    }
                }, response => {
                    if(response.status == 504){
                        this.$message({
                            message: '远程服务调用失败！',
                            type: 'error'
                        })
                    }else if(response.status == 401){
                        console.log("请登录！");
                        this.$router.push('/login');
                    } else if(response.status == 403){
                        console.log("权限不够，请联系管理员！");
                        this.$router.push('/login');
                    }
                })
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {
                    name: '',
                    content: ''
                };
            },
            //新增
            addSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    this.addLoading = true;
                    this.$http.post('/api/security/system/version/add/', this.addForm).then(response => {
                        console.log(response)
                        this.editLoading = false;
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                        this.addFormVisible = false;
                        this.getDataSet();
                    }, response => {
                        if(response.status == 401){
                            console.log("请登录！");
                            this.$router.push('/login');
                        } else if(response.status == 403){
                            console.log("权限不够，请联系管理员！");
                            this.$router.push('/login');
                        }
                    })
                })
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
        },
        mounted() {
            this.getData();
        }
    }
</script>