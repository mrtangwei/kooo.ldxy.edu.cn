<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        </el-col>

        <!--编辑界面-->
        <el-col title="分配资源">
            <el-form :model="editForm" label-width="100px"  ref="editForm">
                <el-form-item  label="请选择角色">
                    <el-select v-model="editForm.roleResources" placeholder="请选择角色">
                        <el-option
                                v-for="item in roleResources"
                                :key="item"
                                :label="item.roleName"
                                :value="item"
                                value-key
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="角色添加资源">
                    <el-select v-model="editForm.resources" placeholder="请选择资源">
                        <el-option
                                v-for="item in this.resources"
                                :key="item"
                                :label="item.name"
                                :value="item"
                                value-key
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div >
                <el-button type="primary" @click.native="editSubmit">提交</el-button>
            </div>
        </el-col>
        <!--列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
                  style="width: 100%;">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="角色名代码">
                            <span>{{ props.row.role }}</span>
                        </el-form-item>
                        <el-form-item label="角色名称">
                            <span>{{ props.row.roleName }}</span>
                        </el-form-item>
                        <el-form-item label="操作路径">
                            <span>{{ props.row.urlJson }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="roleName" label="角色名" width="280" sortable>
            </el-table-column>
            <el-table-column prop="role" label="角色代码" width="300" sortable>
            </el-table-column>
            <el-table-column pro="urlJson" label="操作" width="260">
            </el-table-column>
            <el-table-column>
            </el-table-column>
        </el-table>
    </section>
</template>

<script>
    export default {

        data() {
            return {
                resources: [],
                roleResources: [],
                dataSet : [],
                listLoading: false,
                sels: [],//列表选中列
                editFormVisible: true,//编辑界面是否显示
                editLoading: true,
                //编辑界面数据
                editForm: {
                    id:'',
                    resources: [],
                    roleResources: [],
                },
            }
        },
        methods: {
            //获取用户列表
            getRoleResources() {

                this.$http.get('/api/security/system/roleResources/all').then(response => {
                    this.roleResources = response.data.content;
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
            getResources() {
                this.$http.get('/api/security/system/resource/all').then(response => {
                    this.resources = response.data.content;
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

            //新增
            editSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    this.addLoading = true;
                    //这里处理角色和资源关系
                    this.editForm.roleResources.urlJson[this.editForm.resources.id] = this.editForm.resources.url;
                    //console.log("urlJson : " + this.editForm.roleResources);
                    this.$http.post('/api/security/system/roleResources/add', this.editForm.roleResources
                    ).then(response => {
                        // console.log(response)
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
                        this.getRoleResources();
                        this.getResources();
                        this.start();

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
            start:function () {
                this.$http.get('/api/security/system/roleResources/all').then(response => {
                    console.log(response.data.content)
                    this.dataSet = response.data.content;

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
            selsChange: function (sels) {
                this.sels = sels;
            },

        },
        mounted() {
            this.getRoleResources();
            this.getResources();
            this.start()
        }
    }

</script>

<style scoped>

</style>