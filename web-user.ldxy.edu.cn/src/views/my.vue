
<template>
	<section>
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form>
				<el-form-item>
					<el-card class="box-card">
						<div  class="text item">
							<span>用户名:&nbsp;</span><span >{{user.username}}</span>
						</div>
						<div  class="text item">
							<span>姓名:&nbsp;</span><span >{{user.realName}}</span>
						</div>
						<div  class="text item">
							<span>联系电话:&nbsp;</span><span >{{user.tel}}</span>
						</div>
						<div  class="text item">
							<span>办公地点:&nbsp;</span><span >{{user.apartment}}</span>
						</div>
					</el-card>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleEdit">编辑个人信息</el-button>
					<el-button type="primary" @click="handlePassword">修改个人密码</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="账号" prop="username">
					<el-input v-model="editForm.username" auto-complete="off" disabled=""></el-input>
				</el-form-item>
				<el-form-item label="姓名" prop="realName">
					<el-input v-model="editForm.realName" auto-complete="off" disabled=""></el-input>
				</el-form-item>
				<el-form-item label="联系电话">
					<el-input v-model="editForm.tel" auto-complete="off"></el-input>
				</el-form-item>
<!--				<el-form-item label="办公地点" prop="apartment">
					<el-cascader v-model="editForm.apartment"
							:options="options"
							v-model="selectedOptions"
							@change="handleChange">
					</el-cascader>
				</el-form-item>-->

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--修改密码-->
		<el-dialog title="修改个人密码" v-model="editPasswordFormVisible" :close-on-click-modal="false">
			<el-form :model="editPasswordForm" label-width="80px" :rules="editPasswordFormRules" ref="editPasswordForm">
				<el-form-item label="旧密码">
					<el-input type="text" v-model="editPasswordForm.oldPassword"   placeholder="请输入旧密码" :focus="type='password'"></el-input>
				</el-form-item>
				<el-form-item label="新密码" >
					<el-input type="password" v-model="editPasswordForm.newPasswordOne"   placeholder="请输入新密码"></el-input>
				</el-form-item>
				<el-form-item label="新密码">
					<el-input type="password" v-model="editPasswordForm.newPasswordTwo"   placeholder="请再次输入新密码"></el-input>
				</el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editPasswordFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editPasswordSubmit" :loading="editPasswordLoading">提交</el-button>
			</div>
		</el-dialog>


	</section>
</template>
<script>
    export default {
   		data() {
            return {
				user:{},

                listLoading: false,
                sels: [],//列表选中列
                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ]
                },
                //编辑界面数据
                editForm: {

                },
                //编辑界面数据
                editPasswordForm: {
                    oldPassword:''
				},
                editPasswordFormVisible: false,//编辑界面是否显示
                editPasswordLoading: false,
                editPasswordFormRules: {
/*                    oldPassword: [
                        { required: true, message: '请输入旧密码', trigger: 'blur' }
                    ],
                    newPasswordOne: [
                        { required: true, message: '请输入新密码', trigger: 'blur' }
                    ],
                    newPasswordTwo: [
                        { required: true, message: '请再次输入新密码', trigger: 'blur' }
                    ]*/
                },
            };
        },
        methods: {
            //显示编辑界面
            handleEdit: function (index, row) {
                this.editFormVisible = true;
                this.editForm = this.user;
            },
            handlePassword: function () {
                this.editPasswordFormVisible = true;
                this.editPasswordForm.oldPassword ='';
                this.editPasswordForm.newPasswordOne ='';
                this.editPasswordForm.newPasswordTwo ='';

            },
            editSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    this.addLoading = true;
                    this.$http.post('/api/security/user/update/tel/', this.editForm).then(response => {
                        this.editLoading = false;
                        this.$message({
                            message: '更新成功',
                            type: 'success'
                        });
                        this.user.tel = this.editForm.tel;
                        sessionStorage.setItem('user', JSON.stringify(this.user));
                        this.editFormVisible = false;

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
            editPasswordSubmit: function () {
                if(this.editPasswordForm.newPasswordOne == this.editPasswordForm.newPasswordTwo){
                    this.$confirm('确认提交吗？', '提示', {}).then(() => {
                        //this.editFormPasswordLoading = true;
                        this.$http.post('/api/security/user/update/password/'+this.editPasswordForm.oldPassword+'/'+this.editPasswordForm.newPasswordOne+'/').then(response => {
							if(response.data == true){
                                //this.editPasswordLoading = false;
                                this.$message({
                                    message: '修改密码成功！',
                                    type: 'success'
                                });
                                this.editPasswordFormVisible = false;
							}else {
                                this.$message({
                                    message: '旧密码输入错误！',
                                    type: 'error'
                                });
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
                    })
				}else {
                    this.$message({
                        message: '两次密码不一致，请重新输入密码！',
                        type: 'error'
                    });
				}

            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        mounted() {

            this.user = JSON.parse(sessionStorage.getItem('user'));
        }
    }
</script>
<style>
	.text {
		font-size: 16px;
	}

	.item {
		margin-left: 18px;
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
		width: 80%;
	}
</style>
