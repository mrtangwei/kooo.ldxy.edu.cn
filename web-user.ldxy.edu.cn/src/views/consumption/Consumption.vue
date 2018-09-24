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
                <el-button type="primary" @click.native="querySubmit('type')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
            <el-tab-pane label="充值钻石" name="first">
                <el-table :data="dataSetRecharge" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="rechargeUid" label="充值人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="rate" label="充值占比" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="sixUid" label="充值6元的玩家数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyUid" label="充值30元的玩家数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="nineUid" label="充值980元的玩家数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="oneNineUid" label="充值198元的玩家数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="threeUid" label="充值348元的玩家数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveUid" label="充值548元的玩家数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="sixTimes" label="充值6元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyTimes" label="充值30元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="nineTimes" label="充值980元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="oneNineTimes" label="充值198元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="threeTimes" label="充值348元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveTimes" label="充值548元的次数" width="220" sortable>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="充值(占比/平均)" name="second">
                <el-table :data="dataSetRechargeProportion" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="sixRate" label="6元占比" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyRate" label="30元占比" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="nineRate" label="98元占比" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="threeRate" label="348元占比" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveRate" label="548元占比" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="sixAve" label="6元平均" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyAve" label="30元平均" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="nineAve" label="98元平均" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="threeAve" label="348元平均" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveAve" label="548元平均" width="220" sortable>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="新增充值钻石" name="third">
                <el-table :data="dataSetAddRecharge" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addNumber" label="新增人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="addRechargeUid" label="新增充值人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="rate" label="充值占比" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addSixUid" label="充值6元的新增玩家数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addThirtyUid" label="充值30元的新增玩家数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addNineUid" label="充值980元的新增玩家数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addOneNineUid" label="充值198元的新增玩家数" width="240" sortable>
                    </el-table-column>
                    <el-table-column prop="addThreeUid" label="充值348元的新增玩家数" width="240" sortable>
                    </el-table-column>
                    <el-table-column prop="addFiveUid" label="充值548元的新增玩家数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addSixTimes" label="新增充值6元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addThirtyTimes" label="新增充值30元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addNineTimes" label="新增充值980元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addOneNineTimes" label="新增充值198元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addThreeTimes" label="新增充值348元的次数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addFiveTimes" label="新增充值548元的次数" width="220" sortable>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="新增充值钻石（占比/平均）" name="fourth">
                <el-table :data="dataSetAddRechargeProportion" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addSixRate" label="新增6元占比" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="addThirtyRate" label="新增30元占比" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="addNineRate" label="新增98元占比" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addThreeRate" label="新增348元占比" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addFiveRate" label="新增548元占比" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="addSixAve" label="新增6元平均" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="addThirtyAve" label="新增30元平均" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="addNineAve" label="新增98元平均" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="addThreeAve" label="新增348元平均" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addFiveAve" label="新增548元平均" width="220" sortable>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
        </el-tabs>




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
                dataSetRecharge:[],
                dataSetRechargeProportion:[],
                dataSetAddRecharge:[],
                dataSetAddRechargeProportion:[],
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
                activeName2: 'first',
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
            handleClick(tab, event) {
                console.log(tab, event);
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
                var url = '/api/security/affairs/group/timestamp/' + this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1]  + '/';
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