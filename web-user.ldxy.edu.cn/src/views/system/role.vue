<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">

                <el-form-item>
                    <el-button type="primary" @click="handleAdd">新增角色</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
                  style="width: 100%;">
            <el-table-column type="index" label="序号" width="100" sortable>
            </el-table-column>
            <el-table-column prop="roleName" label="角色名" width="280" sortable>
            </el-table-column>
            <el-table-column prop="role" label="角色代码" width="300" sortable>
            </el-table-column>
            <el-table-column>
            </el-table-column>
            <el-table-column label="操作" width="260">
                <template slot-scope="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--编辑界面-->
        <el-dialog title="角色编辑" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
                <el-form-item label="名称" prop="roleName">
                    <el-input v-model="editForm.roleName" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="角色名" prop="role">
                    <el-input v-model="editForm.role" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--新增界面-->
        <el-dialog title="新增角色" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
                <el-form-item label="名称" prop="roleName">
                    <el-input v-model="addForm.roleName" auto-complete="off" placeholder="资源名称，不允许为空"></el-input>
                </el-form-item>
                <el-form-item label="角色代码" prop="role">
                    <el-input v-model="addForm.role" auto-complete="off" placeholder="资源描述，不允许为空"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>

    export default {
        data() {
            return {
                filters: {
                    name: ''
                },
                dataSet: [],
                total: 0,
                page: 1,
                listLoading: false,
                sels: [],//列表选中列
                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ],
                    memo: [
                        {required: true, message: '请输入描述内容', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入URL路径', trigger: 'blur'}
                    ]
                },
                //编辑界面数据
                editForm: {
                    id:'',
                    name: '',
                    memo: '',

                },

                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ],
                    memo: [
                        {required: true, message: '请输入描述内容', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入URL路径', trigger: 'blur'}
                    ]
                },
                //新增界面数据
                addForm: {
                    name: '',
                    memo: '',
                    url: ''
                },
            }
        },
        methods: {
            //获取用户列表
            getData() {
                this.listLoading = true;
                this.$http.get('/api/security/system/roleResources/all').then(response => {
                    this.dataSet = response.data.content;
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
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    let para = row.id;
                    this.$http.post('/api/security/system/roleResources/delete',{
                        'id': para
                    }).then(response => {
                        console.log(response);
                        this.listLoading = false;
                        //NProgress.done();
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
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
            //显示编辑界面
            handleEdit: function (index, row) {
                this.editFormVisible = true;
                this.editForm = Object.assign({}, row);
                /*                this.editForm.pwd = row.password;
                 this.editForm.password = '';
                 this.editForm.password2 = '';
                 this.editForm.id = row.id;*/
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {
                    name: '',
                    memo: '',
                    url: ''
                };
            },
            //编辑
            editSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    // 只修改资料
                    this.editLoading = true;
                    this.$http.post('/api/security/system/roleResources/add', {
                        'id': this.editForm.id,
                        'role': this.editForm.role,
                        'roleName': this.editForm.roleName,
                    }).then(response => {
                        console.log(response)
                        this.editLoading = false;
                        this.$message({
                            message: '编辑成功',
                            type: 'success'
                        });
                        this.$refs['editForm'].resetFields();
                        this.editFormVisible = false;
                        this.getData();
                    }, response => {
                        if(response.status == 401){
                            console.log("请登录！");
                            this.$router.push('/login');
                        } else if(response.status == 403){
                            console.log("权限不够，请联系管理员！");
                            this.$router.push('/login');
                        }
                    })
                });

            },
            //新增
            addSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    this.addLoading = true;
                    this.$http.post('/api/security/system/roleResources/add', {
                        'role': this.addForm.role,
                        'roleName': this.addForm.roleName
                    }).then(response => {
                        console.log(response)
                        this.editLoading = false;
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                        this.addFormVisible = false;
                        this.getData();
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

<style scoped>

</style>