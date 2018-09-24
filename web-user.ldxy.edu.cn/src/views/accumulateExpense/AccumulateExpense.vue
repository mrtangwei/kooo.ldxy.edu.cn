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
            <el-tab-pane label="累计充值(新增)" name="first">
                <el-table :data="dataSetAddAccumulate" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addPlayer" label="新增玩家人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="sixUid" label="新增充值6元人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyUid" label="新增充值30元人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="ninetyUid" label="新增充值98元人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="threeUid" label="新增充值300元人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveUid" label="新增充值500元人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="oneThousandUid" label="新增充值1000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="twoThousandUid" label="新增充值2000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="threeThousandUid" label="新增充值3000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="sixDau" label="新增领取充值6元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyDau" label="新增领取充值30元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="ninetyDau" label="新增领取充值98元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="threeDau" label="新增领取充值300元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveDau" label="新增领取充值500元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="oneThousandDau" label="新增领取充值1000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="twoThousandDau" label="新增领取充值2000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="threeThousandDau" label="新增领取充值3000元人数" width="220" sortable>
                    </el-table-column>

                </el-table>
            </el-tab-pane>
            <el-tab-pane label="累计充值(占比)" name="second">
                <el-table :data="dataSetAccumulateProportion" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="addSixRate" label="新增领取6元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addNinetyRate" label="新增领取30元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addNinetyRate" label="新增领取98元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addThreeRate" label="新增领取300元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addFiveRate" label="新增领取500元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addOneThousandRate" label="新增领取1000元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addTwoThousandRate" label="新增充值2000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="addThreeThousandRate" label="新增充值3000元人数" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="sixRate" label="领取6元率" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="thirtyRate" label="领取30元率" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="ninetyRate" label="领取98元率" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="threeRate" label="领取300元率" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="fiveRate" label="领取500元率" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="oneThousandRate" label="领取1000元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="twoThousandRate" label="领取2000元率" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="threeThousandRate" label="领取3000元率" width="220" sortable>
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
                dataSetAddAccumulate:[],                   //新增
                dataSetAccumulateProportion:[],            //占比
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
                var url = '/api/security/affairs/group/timestamp/' + this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];
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