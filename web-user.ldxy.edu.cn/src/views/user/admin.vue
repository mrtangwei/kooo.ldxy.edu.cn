<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true">
                <el-form-item><el-input type="text" v-model="username" placeholder="请输入姓名"></el-input></el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="queryUser">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleAdd">新增管理员</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
                  style="width: 100%;">
            <el-table-column type="index" width="100" sortable>
            </el-table-column>
            <el-table-column prop="realName" label="姓名" width="150" sortable>
            </el-table-column>
            <el-table-column prop="username" label="账号" width="160" sortable>
            </el-table-column>
            <el-table-column>
            </el-table-column>
            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button size="small" @click="handleEditPassword(scope.$index, scope.row)">修改密码</el-button>
                    <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!--编辑用户密码界面-->
        <el-dialog title="修改用户密码" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="editForm.realName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="账号名" prop="username">
                    <el-input v-model="editForm.username" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input type="password" v-model="editForm.password" auto-complete="off" placeholder="修改密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码">
                    <el-input type="password" v-model="editForm.password2" auto-complete="off" placeholder="修改密码"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--新增界面-->
        <el-dialog title="新增管理员账号" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="100px" :rules="addFormRules" ref="addForm">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="addForm.realName" auto-complete="off" placeholder="使用者姓名，不允许为空"></el-input>
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
                <el-form-item label="部门" prop="username">
                    <el-input v-model="addForm.department" auto-complete="off" placeholder="登录账户名，不允许为空"></el-input>
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
                username:'',
                users: [],
                total: 0,
                page: 1,
                listLoading: false,
                sels: [],//列表选中列
                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    realName: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ],
                    username: [
                        {required: true, message: '请输入账号名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    password2: [
                        {required: true, message: '请再次输入密码', trigger: 'blur'}
                    ]
                },
                //编辑界面数据
                editForm: {
                    id:'',
                    username:'',
                    password: '',
                    password2: ''

                },

                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    realName: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ],
                    username: [
                        {required: true, message: '请输入账号名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    password2: [
                        {required: true, message: '请再次输入密码', trigger: 'blur'},
                    ]
                },
                //新增界面数据
                addForm: {
                    realName: '',
                    username: '',
                    password: '',

                    password2: '',

                },


            }
        },
        methods: {
            queryUser(){
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
            getUsers() {
                this.listLoading = true;
                this.$http.get('/api/security/admin/user/role/ADMIN').then(response => {
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
            //删除
            handleDel: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    let para = row.id;
                    this.$http.post('/api/security/admin/delete/',{
                        'id': para
                    }).then(response => {
                        console.log(response);
                        this.listLoading = false;
                        //NProgress.done();
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getUsers();
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
            //显示修改密码界面
            handleEditPassword: function (index, row) {
                this.editFormVisible = true;
                //this.editForm = Object.assign({}, row);
                this.editForm.username = row.username;
                this.editForm.password = '';
                this.editForm.password2 = '';
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {
                    username: '',
                    password: '',
                    password2: '',
                };
            },
            //编辑
            editSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        // 只修改资料
                        this.editLoading = true;
                        this.$http.post('/api/security/admin/update/password/', {
                            'id': this.editForm.id,
                            'username': this.editForm.username,
                            'password': this.editForm.password,
                        }).then(response => {
                            console.log(response)
                            this.editLoading = false;
                            this.$message({
                                message: '修改密码成功！',
                                type: 'success'
                            });
                            this.$refs['editForm'].resetFields();
                            this.editFormVisible = false;
                            this.getUsers();
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
                        this.$http.post('/api/security/admin/user/add/', this.addForm).then(response => {
                            console.log(response)
                            this.addLoading = false;
                            this.$message({
                                message: '添加成功',
                                type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.addFormVisible = false;
                            this.getUsers();
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
            selsChange: function (sels) {
                this.sels = sels;
            },
        },
        mounted() {
            this.getUsers();
        }
    }

</script>

<style scoped>

</style>