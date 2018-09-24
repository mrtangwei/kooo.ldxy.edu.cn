<template class="login-body">
  <el-form :model="ruleForm2" v-on:keyup.13="handleSubmit2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
    <h3 class="title">{{systemName}}</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="ruleForm2.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" @keyup.enter="handleSubmit2"  :loading="logining">登录</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>

<script>


    //import NProgress from 'nprogress'
    export default {
        data() {
            return {
                systemName:'',
                logining: false,
                ruleForm2: {
                    username: '',
                    password: '',
                },
                rules2: {
                    username: [
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ]
                },
                checked: true
            };
        },
        methods: {
            handleReset2() {
                this.$refs.ruleForm2.resetFields();
            },
            handleSubmit2(ev) {
                var _this = this;
                this.$refs.ruleForm2.validate((valid) => {
                    if (valid) {
                        this.logining = true;
                        this.$http.get('/api/api-users/users/query/'+this.ruleForm2.username + '/'+this.ruleForm2.password).then(response=>{
                                if(response.data == "1"){
                                    this.$message({
                                        message: '用户名错误！',
                                        type: 'error'
                                    })
                                    this.logining = false;
                                }else if(response.data =="2"){
                                                this.$message({
                                                    message: '密码错误！',
                                                    type: 'error'
                                    })
                                    this.logining = false;
                                }else if(response.data =="3") {
                                    //var formData = JSON.stringify(this.ruleForm2);
                                    this.$http.post('/api/api-users/login?username='+this.ruleForm2.username + '&password='+this.ruleForm2.password).then(response => {
                                        console.log("login info 1 : " + JSON.stringify(response.data))

                                        sessionStorage.setItem('user', JSON.stringify(response.data.data));
                                        this.$router.push({ path: '/home' });
                                        console.log("xxxx : " + JSON.stringify(response.data.data))
                                        }, response => {
                                          if(response.status == 504){
                                              this.$message({
                                                  message: '服务没有响应，请检查服务器运行状态！',
                                                  type: 'error'
                                              })

                                          }
                                        this.logining = false;
                                        })
                                    }
                            }, response => {
                            this.logining = false;
                              if(response.status == 504){
                                  this.$message({
                                      message: '服务没有响应，请检查服务器运行状态！',
                                      type: 'error'
                                  })

                              }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            }
        },
        mounted() {
            this.$http.get('/api/system/setting/systemName/').then(response => {
              //  console.log(response)
               this.systemName = response.data.systemName;
            }, response => {
                if(response.status == 404){
                    console.log("系统核心业务没有启动！");
                    this.$router.push('/login');
                } else if(response.status == 403){
                    console.log("权限不够，请联系管理员！");
                    this.$router.push('/login');
                }
            })

        }
    }

</script>

<style lang="scss" scoped>
  .login-body{
    background: #1c8de0 url("../assets/tile.png") 0 0 no-repeat;
  }
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;

    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>