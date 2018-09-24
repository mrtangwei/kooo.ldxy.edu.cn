<template>
    <section>

        <el-row>
            <el-col :span="24">
                <el-card class="box-card"  >
                    <div slot="header" class="clearfix">
                        <span>系统信息</span>
                    </div>
                    <div  class="text item">
                        <span>系统名称：</span>{{systemName}}
                        </div>
                    <div  class="text item">
                        <span>微信公众号名称：</span> {{weChatName}}
                        </div>
                    <div  class="text item">
                        <span>微信公众号ID：</span> {{weChatId}}
                        </div>
                    <div  class="text item">
                        <span>微信公众号TOKEN：</span> {{weChatToken}}
                        </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-card class="box-card" >
                    <div slot="header" class="clearfix">
                        <span>微信公众号二维码</span>
                    </div>
                    <div  class="text item">
                        <img src="http://web.ldxy.edu.cn/img/weChatPic.jpg" class="image">
                    </div>

                </el-card>
            </el-col>
        </el-row>

        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form>
                <el-form-item>
                    <el-button type="primary" @click="handleEdit">修改系统信息</el-button>
                </el-form-item>
            </el-form>
        </el-col>



        <!--编辑界面-->
        <el-dialog title="修改系统信息" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="160px" :rules="editFormRules" ref="editForm">
                <el-form-item label="系统名称" prop="systemName">
                    <el-input v-model="editForm.systemName" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="微信公众号名称" prop="systemName">
                    <el-input v-model="editForm.weChatName" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="微信公众号ID" prop="weChatId">
                    <el-input v-model="editForm.weChatId" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="微信公众号二维码" prop="weChatPicture">
                    <el-input v-model="editForm.weChatPicture" auto-complete="off" ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>



</template>

<script>
    export default {
       data() {
            return {
                id:'',
                systemName: '',
                weChatName:'',
                weChatId:'',
                weChatPicture:'',
                weChatToken:'',
                dataSet:[],
                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    systemName: [
                        {required: true, message: '请输入姓名', trigger: 'blur'}
                    ]
                },
                //编辑界面数据
                editForm: {
                    id:'',
                    systemName:''
                }
            }
        },
        methods: {
            onSubmit() {
                console.log('submit!');
            },
            getDataSet(){
                this.$http.get('/api/security/system/setting/all').then(response => {
                    console.log(response.data.content)
                    this.editForm = response.data.content[0];

                    this.systemName = response.data.content[0].systemName;
                    this.weChatName = response.data.content[0].weChatName;
                    this.weChatId = response.data.content[0].weChatId;
                    this.weChatPicture = response.data.content[0].weChatPicture;
                    this.weChatToken = response.data.content[0].weChatToken;
                    this.id= response.data.id;
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
            //显示编辑界面
            handleEdit: function () {
                this.editFormVisible = true;

               // this.editForm.id=row.id;
              //  this.editForm.systemName=row.systemName;

            },
            //编辑
            editSubmit: function (index, row) {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    // 只修改资料
                    this.editLoading = true;
                   // this.editForm.id= this.id;
                    //this.editForm.systemName=this.systemName;
                    this.$http.post('/api/security/system/setting/add/', this.editForm).then(response => {
                        console.log(response)
                        this.editLoading = false;
                        this.$message({
                            message: '编辑成功',
                            type: 'success'
                        });
                        this.$refs['editForm'].resetFields();
                        this.editFormVisible = false;
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
                });
            },
        },
        mounted() {
            this.getDataSet();
        }
    }

</script>
<style>
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 800px;
    }
</style>