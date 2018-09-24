<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true">
                <el-form-item prop="entry" label="选择时间范围" width="200" sortable>
                    <el-date-picker
                            v-model="rangeDate"
                            type="daterange"
                            align="right"
                            unlink-panels
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            :picker-options="quickRangeDate">
                    </el-date-picker>
                </el-form-item>
                <el-form-item prop="entry" label="选择服务器" width="200" sortable>
                    <el-select v-model="serverId" placeholder="选择服务器">
                        <el-option
                                v-for="item in serverList"
                                :key="item.serverId"
                                :label="item.name + item.serverId"
                                :value="item.serverId">
                        </el-option>
                    </el-select>

                </el-form-item>
                <el-button type="primary" @click.native="querySubmit('all')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>



        <!-- 商演关卡分析列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃用户数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step1" label="过关卡1的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step2" label="过关卡2的人数" width="180" sortable>
            </el-table-column>

            <el-table-column prop="step3" label="过关卡3的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step4" label="过关卡4的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step5" label="过关卡5的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step6" label="过关卡6的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step7" label="过关卡7的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step8" label="过关卡8的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step9" label="过关卡9的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step10" label="过关卡10的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step11" label="过关卡11的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="step12" label="过关卡12的人数" width="180" sortable>
            </el-table-column>
 

            <el-table-column>
            </el-table-column>
        </el-table>

        <!-- 商演关卡过关率分析列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃用户数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep1" label="关卡1过关率" :formatter="doubleFilter"  width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep2" label="关卡2过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>

            <el-table-column prop="rateStep3" label="关卡3过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep4" label="关卡4过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep5" label="关卡5过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep6" label="关卡6过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep7" label="关卡7过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep8" label="关卡8过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep9" label="关卡9过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep10" label="关卡10过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep11" label="关卡11过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateStep12" label="关卡12过关率" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>


            <el-table-column>
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
    </section>
</template>

<script>
    var moment = require('moment');
    export default {
        data() {
            return {
                serverList:[],
                serverId:'',
                coinRateAve:false,
                currentPage: 1,
                totalElements:2,
                page:0,
                size:15,
                rangeDate:'',
                sDate :'',
                eDate :'',
                dataSet: [],


                id:0,
                date:'',
                activeName:'',
                listLoading: false,
                sels: [],//列表选中列
                quickRangeDate: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 6);
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

            }
        },
        methods: {
            getServerList() {
                this.$http.get('/api/security/server/all').then(response => {
                    this.serverList = response.data;
                    for (let i in this.serverList){
                        this.serverList[i].date = moment(this.serverList[i].dateTime).format('YYYY年MM月DD日')
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
            doubleFilter(row,column) {
                var value = row[column.property] * 100;
                var toFixedNum = Number(value).toFixed(3);
                var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1) + '%';
                return realVal;
            },
            intFilter(row,column) {
                var value = row[column.property];
                if(typeof(value)=="undefined") { return 0};
                var toFixedNum = Number(value).toFixed(3);
                var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
                return realVal;
            },
            handleClick(tab, event) {
                console.log(tab.name);
                var type = '';
                if(tab.name == "first"){
                    type='所有用户购买金币';
                    this.querySubmit('all');
                }else if(tab.name == "second"){
                    type='新手购买金币';
                    this.querySubmit('new');

                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                if(val > 0) val--;

            },
            //查询
            querySubmit: function (type) {
                this.listLoading = true;
                //date[0]至date[1] 时间段内购买金币次数统计
                var url= '/api/security/businessShow/group/'+this.serverId+'/' + this.rangeDate[0] + '/' + this.rangeDate[1];

                this.$http.get(url).then(response => {
                    //console.log(response.data)
                    this.dataSet = response.data;
                    for (let i in this.dataSet){
                        this.dataSet[i].timestamp = moment(this.dataSet[i].timestamp.millis).format('YYYY年MM月DD日')
                    }
                    this.listLoading = false;
                }, response => {
                    console.log("拉取数据错误！");
                    this.listLoading = false;
                });

            },
            selsChange: function (sels) {
                this.sels = sels;
            },
        },
        mounted() {
            this.getServerList();
        }
    }

</script>

<style scoped>

</style>