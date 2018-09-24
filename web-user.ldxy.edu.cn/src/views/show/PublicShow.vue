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

        <!-- 公演经分列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃用户数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="enterUid" label="进入公演的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countUid" label="参与公演的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="timesUid" label="参与公演的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="ave" label="公演平均参与次数" :formatter="intFilter"  width="180"   sortable>
            </el-table-column>
            <el-table-column prop="diamondUid" label="钻石刷新人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="diamondTimes" label="钻石刷新次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="diamond1" label="钻石刷新1次人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="diamond2" label="钻石刷新2次人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="diamond3" label="钻石刷新3次人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="diamond4" label="钻石刷新4次人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="diamond5" label="钻石刷新5次人数" width="180" sortable>
            </el-table-column>



            <el-table-column>
            </el-table-column>
        </el-table>


        <!-- 公演进程分析列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃用户数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepOne" label="进入公演进程1的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepOneTimes" label="进入公演进程1的次数" width="180" sortable>
            </el-table-column>

            <el-table-column prop="stepTwo" label="进入公演进程2的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepTwoTimes" label="进入公演进程2的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepThree" label="进入公演进程3的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepThreeTimes" label="进入公演进程3的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepFour" label="进入公演进程4的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepFourTimes" label="进入公演进程4的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepFive" label="进入公演进程5的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepFiveTimes" label="进入公演进程5的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepSix" label="进入公演进程6的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepSixTimes" label="进入公演进程6的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepSeven" label="进入公演进程7的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepSevenTimes" label="进入公演进程7的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepEight" label="进入公演进程8的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="stepEightTimes" label="进入公演进程8的次数" width="180" sortable>
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
                dataStep: [],

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
                var url= '/api/security/publicShow/group/'+this.serverId+'/' + this.rangeDate[0] + '/' + this.rangeDate[1];
               // var urlStep= '/api/security/publicShow/group/step/'+ this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];

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

/*                this.$http.get(urlStep).then(response => {
                    //console.log(response.data)
                    this.dataStep = response.data;
                    for (let i in this.dataStep){
                        this.dataStep[i].timestamp = moment(this.dataStep[i].timestamp.millis).format('YYYY年MM月DD日')
                    }
                    this.listLoading = false;
                }, response => {
                    console.log("拉取数据错误！");
                    this.listLoading = false;
                });*/

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