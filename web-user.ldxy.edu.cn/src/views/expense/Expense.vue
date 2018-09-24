<template>

    <section>
        <!--工具条-->

        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true">
                <el-form-item prop="entry" label="选择时间范围" width="300" sortable>
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
                <el-button type="primary" @click.native="querySubmit('type')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <!--首充-->
        <el-table :data="dataSetExpense" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
            </el-table-column>
            <el-table-column prop="addPlayer" label="新增玩家数" width="160" sortable>
            </el-table-column>
            <el-table-column prop="rechargeUid" label="充值人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="addRechargeUid" label="新增充值人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="receiveExpense" label="领取首充的玩家" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveProportion" label="领取首充率" width="200" sortable>
            </el-table-column>
            <el-table-column prop="addRechargeUid1" label="新增充值人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="addReceiveExpense" label="新增玩家领取首充人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="addReceiveProportion" label="新增领取率" width="200" sortable>
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
                dataSetExpense: [],
                dataSetCountUid: [],
                dataSetAve:[],
                ave:{
                    date:'',
                    aveFiveH:'0',
                    aveTwoThousandAndFiveH:'0',
                    aveFiveThousand:'0',
                    aveTwentyThousand:'0',
                    aveFiftyThousand:'0'
                },
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
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
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


            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                if(val > 0) val--;
                this.getDataSet(this.size,val,'办公用房')
            },
            //查询
            querySubmit: function (type) {
                this.listLoading = true;
                //date[0]至date[1] 时间段内购买金币次数统计
                var url = '/api/security/lottery/group/type/' + this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1]  + '/';
                //var urlCountUid= '/api/security/coin/group/' + type + '/uid/' + this.sDate + '/' + this.eDate  + '/';

                this.$http.get(url).then(response => {
                    //console.log(response.data)
                    this.dataSetLottery = response.data;
                    for (let i in this.dataSetLottery){
                        this.dataSetLottery[i].timestamp = moment(this.dataSetLottery[i].timestamp).format('YYYY年MM月DD日')
                    }
                }, response => {
                    console.log("拉取数据错误！");
                });


                this.listLoading = false;
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
        },
        mounted() {
            this.getServerList();
            // this.getDataSet(this.size,this.page,'办公用房');
        }
    }

</script>

<style scoped>

</style>