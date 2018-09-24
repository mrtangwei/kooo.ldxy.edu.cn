<template>
    <section>
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true">
                <el-form-item><el-input type="text" v-model="username" placeholder="请输入姓名"></el-input></el-form-item>
                <el-form-item>

                    <el-button type="primary" @click="queryUsers">查询</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="index" width="60" sortable>
            </el-table-column>
            <el-table-column prop="realName" label="姓名" width="150" sortable>
            </el-table-column>
            <el-table-column prop="username" label="账号" width="160" sortable>
            </el-table-column>
            <el-table-column prop="sex" label="性别" width="160" sortable>
            </el-table-column>
            <el-table-column prop="departmentName" label="学院" width="160" sortable>
            </el-table-column>
            <el-table-column prop="major" label="专业" width="160" sortable>
            </el-table-column>
            <el-table-column>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template scope="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :total="totalElements">
            </el-pagination>
        </div>
        <!--编辑界面-->
        <el-dialog title="学生信息编辑" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
                <el-form-item label="姓名" prop="realName">
                    <el-input v-model="editForm.realName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="账号名" prop="username">
                    <el-input v-model="editForm.username" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item prop="sex" label="性别">
                    <el-input v-model="editForm.sex" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item prop="departmentName" label="学院">
                    <el-input v-model="editForm.departmentName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item prop="major" label="专业">
                    <el-input v-model="editForm.major" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" >
                    <el-input type="password" v-model="editForm.password" auto-complete="off" placeholder="不修改密码请置空"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" >
                    <el-input type="password" v-model="editForm.password2" auto-complete="off" placeholder="不修改密码请置空"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--新增界面-->
        <el-dialog title="新增学生账号" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="addForm.name" auto-complete="off" placeholder="使用者姓名，不允许为空"></el-input>
                </el-form-item>
                <el-form-item label="账号名" prop="username">
                    <el-input v-model="addForm.username" auto-complete="off" placeholder="登录账户名，不允许为空"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="addForm.password" auto-complete="off" placeholder="登录密码，不允许为空"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="password2">
                    <el-input type="password" v-model="addForm.password2" auto-complete="off" placeholder="重复登录密码，不允许为空"></el-input>
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
                currentPage: 1,
                totalElements:2,
                size:20,
                page:0,
                filters: {
                    name: ''
                },
                users: [],
                listLoading: false,
                sels: [],//列表选中列
                username:'',

                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '请输入账号名', trigger: 'blur' }
                    ],

                },

                //编辑界面数据
                editForm: {



                },

                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '请输入账号名', trigger: 'blur' }
                    ],
                    title: [
                        { required: true, message: '请选择设计师级别', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ],
                    password2: [
                        { required: true, message: '请再次输入密码', trigger: 'blur' }
                    ]
                },
                //新增界面数据
                addForm: {
                    name: '',
                    username:'',
                    title:'',
                    password:'',
                    password2:'',

                }

            }
        },
        methods: {
            queryUsers(){
                this.$http.get('/api/security/admin/users/query/' + this.username).then(response => {
                    this.users = response.data.content;
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
            //获取用户列表
            getUsers(size,page) {
                this.listLoading = true;
                this.$http.get('/api/security/admin/user/role/STUDENT' + '?size=' + size + '&page=' + page + '&sort=date,desc').then(response => {
                    this.users = response.data.content;
                    this.totalElements = response.data.totalElements;
                    this.listLoading = false;
                }, response => {
                    if(response.status == 401){
                        this.$message({
                            message: '请登录！',
                            type: 'error'
                        });
                        this.$router.push('/login');
                    } else if(response.status == 403){
                        this.$message({
                            message: '权限不够，请联系管理员！！',
                            type: 'error'
                        });
                        this.$router.push('/login');
                    }
                })
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                if(val > 0) val--;
                this.getUsers(this.size,val)
            },
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    let para = row.id;
                    this.$http.post('/api/security/admin/user/delete/',{
                        'id': para
                    },{emulateJSON: true}).then(response => {
                        console.log(response);
                        this.listLoading = false;
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getUsers();
                    }, response => {
                        if(response.status == 401){
                            this.$message({
                                message: '请登录！',
                                type: 'error'
                            });
                            this.$router.push('/login');
                        } else if(response.status == 403){
                            this.$message({
                                message: '权限不够，请联系管理员！！',
                                type: 'error'
                            });
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
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {
                    name: '',
                    username: '',
                    password: '',
                    password2: '',
                    title:''
                };
            },
            //编辑
            editSubmit: function () {

                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        // 修改资料
                        this.editLoading = true;
                        this.$http.post('/api/security/admin/user/add/',this.editForm).then(response => {
                            console.log(response)
                            this.editLoading = false;
                            if(response == true){

                                this.$message({
                                    message: '编辑成功！',
                                    type: 'success'
                                });
                            }else {
                                this.$message({
                                    message: '编辑失败！',
                                    type: 'error'
                                });
                            }
                            this.$refs['editForm'].resetFields();
                            this.editFormVisible = false;
                            this.getUsers(this.size,this.page);
                        }, response => {
                            if(response.status == 401){
                                this.$message({
                                    message: '请登录！',
                                    type: 'error'
                                });
                                this.$router.push('/login');
                            } else if(response.status == 403){
                                this.$message({
                                    message: '权限不够，请联系管理员！！',
                                    type: 'error'
                                });
                                this.$router.push('/login');
                            }
                        })
                });
            },
            //新增
            addSubmit: function () {
                if (this.addForm.password != this.addForm.password2) {
                    this.$message({
                        message: '两次输入密码输入不一致，请重新输入！',
                        type: 'error'
                    });
                }else {
                    this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        this.addLoading = true;
                        this.$http.post('/api/security/designer/add', {
                            'name': this.addForm.name,
                            'username': this.addForm.username,
                            'password': this.addForm.password,
                            'title': this.addForm.title,
                        }, {emulateJSON: true}).then(response => {
                            if (response.data.error!=0){
                                this.addLoading = false;
                                this.$message({
                                    message: response.data.message,
                                    type: 'error'
                                });
                            }else{
                                console.log(response)
                                this.addLoading = false;
                                this.$message({
                                    message: '添加成功',
                                    type: 'success'
                                });
                                this.$refs['addForm'].resetFields();
                                this.addFormVisible = false;
                                this.getUsers();
                            }
                        }, response => {
                            if(response.status == 401){
                                this.$message({
                                    message: '请登录！',
                                    type: 'error'
                                });
                                this.$router.push('/login');
                            } else if(response.status == 403){
                                this.$message({
                                    message: '权限不够，请联系管理员！！',
                                    type: 'error'
                                });
                                this.$router.push('/login');
                            }
                        })
                    });
                }
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
        },
        mounted() {
            this.getUsers(this.size,this.page);
        }
    }

</script>

<style scoped>

</style>