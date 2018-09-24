<template>
    <el-card class="box-card">
        <el-form ref="form" :model="form" label-width="80px" @submit.prevent="onSubmit" style="margin:20px auto;width:65%;min-width:780px;">
            <el-form-item label="标题">
                <el-input  v-model="form.title"></el-input>
            </el-form-item>

            <el-form-item label="部门" >
                <el-select v-model="form.department" placeholder="请选择部门" style="width: 100%">
                            <el-option
                                    v-for="item in optionsDepartment"
                                    :key="item.label"
                                    :label="item.label"
                                    :value="item.label"
                                    >
                            </el-option>
                        </el-select>
                    </el-form-item>



            <el-form-item label="内容">
                <el-input type="textarea" v-model="form.content"></el-input>
            </el-form-item>
            <el-form-item style="margin-left: 0px;">
                <el-button type="primary" @click="push('form')" :loading="pushLoading">发布</el-button>
                <el-button @click="buck">取消</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    export default {
        data() {
            return {
                options: [{
                    value: '新闻',
                    label: '新闻'
                }, {
                    value: '公告',
                    label: '公告'
                }],
                optionsDepartment: [{
                    value: '50500',
                    label: '后勤保障处'
                }, {
                    value: '5050001',
                    label: '后勤保障处物业中心'
                }],
                form: {
                    department:'',
                    content:'',
                },

                dynamicTags: [],
                articleType:[],
                inputVisible: false,
                pushLoading: false,
                inputValue: '',


            }
        },
        methods: {
            buck(){
                this.$router.push('/weChat')
            },

/*            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    this.dynamicTags.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },*/
            push(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.pushLoading = true
                            this.$http.post('/api/security/weChat/message/add/',this.form
                            ).then(response => {
                                console.log(response)
                                this.addLoading = false;
                                this.$message({
                                    message: '添加成功',
                                    type: 'success'
                                });
                                this.$router.push('/weChat')
                                this.pushLoading = false
                            }, response => {
                                if(response.status == 401){
                                    this.$message({
                                        message: '请登录系统！',
                                        type: 'error'
                                    });
                                    this.$router.push('/login');
                                }else if(response.status == 403){
                                    this.$message({
                                        message: '权限不够，请联系管理员！',
                                        type: 'error'
                                    });
                                    this.$router.push('/login');
                                }
                            })
                        });
                    } else {
                        return false;
                    }
                })
            },
        },
        mounted() {
        },
/*        components: {
            'v-editor': WangEditor
        }*/
    }

</script>
<style>
    .el-card__body{
        padding: 10px 0;
    }
    .box-card{
        margin: 10px 0 0 0;
    }
    #editor {
        height: 440px;
    }
    .wangEditor-container {
        border-radius: 2px;
        overflow: hidden;
        border: 1px solid #CCC;
    }
</style>