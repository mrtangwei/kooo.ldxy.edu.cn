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

        <!--购买金币列表-->
        <el-table :data="dataSetCountTimes" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="count" label="活跃用户数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countFiveH" label="购买500金币人数" width="260" sortable>
            </el-table-column>
            <el-table-column prop="countTwoThousandAndFiveH" label="购买2500金币人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countFiveThousand" label="购买5000金币人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countTwentyThousand" label="购买20000金币人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countFiftyThousand" label="购买50000金币人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="numFiveH" label="购买500金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numTwoThousandAndFiveH" label="购买2500金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numFiveThousand" label="购买5000金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numTwentyThousand" label="购买20000金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numFiftyThousand" label="购买50000金币次数" width="200" sortable>
            </el-table-column>


            <el-table-column>
            </el-table-column>
        </el-table>




        <!--购买金币占比&平均 -->
        <el-table :data="dataSetCountTimes" v-model="coinRateAve" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateFiveH" label="购买500金币占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="rateTwoThousandAndFiveH" label="购买2500金币占比"  :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateFiveThousand" label="购买5000金币占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateTwentyThousand" label="购买20000金币占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateFiftyThousand" label="购买50000金币占比" :formatter="doubleFilter"  width="200" sortable>
            </el-table-column>
            <el-table-column prop="aveFiveH" label="购买500金币平均次数" width="220"  :formatter="intFilter" sortable>
            </el-table-column>
            <el-table-column prop="aveTwoThousandAndFiveH" label="购买2500金币平均次数"   :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="aveFiveThousand" label="购买5000金币平均次数"    :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="aveTwentyThousand" label="购买20000金币平均次数"  :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="aveFiftyThousand" label="购买50000金币平均次数"   :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column>
            </el-table-column>
        </el-table>


        <!--新手购买金币列表-->
        <el-table :data="dataSetCountTimes" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countNew" label="新增用户" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countFiveH" label="购买500金币人数" width="260" sortable>
            </el-table-column>
            <el-table-column prop="countTwoThousandAndFiveHNew" label="购买2500金币人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countFiveThousandNew" label="购买5000金币人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countTwentyThousandNew" label="购买20000金币人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countFiftyThousandNew" label="购买50000金币人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="numFiveHNew" label="购买500金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numTwoThousandAndFiveHNew" label="购买2500金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numFiveThousandNew" label="购买5000金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numTwentyThousandNew" label="购买20000金币次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="numFiftyThousandNew" label="购买50000金币次数" width="200" sortable>
            </el-table-column>


            <el-table-column>
            </el-table-column>
        </el-table>




        <!--新手购买金币占比&平均 -->
        <el-table :data="dataSetCountTimes" v-model="coinRateAve" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateFiveHNew" label="购买500金币占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="rateTwoThousandAndFiveHNew" label="购买2500金币占比"  :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateFiveThousandNew" label="购买5000金币占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateTwentyThousandNew" label="购买20000金币占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateFiftyThousandNew" label="购买50000金币占比" :formatter="doubleFilter"  width="200" sortable>
            </el-table-column>
            <el-table-column prop="aveFiveHNew" label="购买500金币平均次数" width="220"  :formatter="intFilter" sortable>
            </el-table-column>
            <el-table-column prop="aveTwoThousandAndFiveHNew" label="购买2500金币平均次数"   :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="aveFiveThousandNew" label="购买5000金币平均次数"    :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="aveTwentyThousandNew" label="购买20000金币平均次数"  :formatter="intFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="aveFiftyThousandNew" label="购买50000金币平均次数"   :formatter="intFilter" width="220" sortable>
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
                currentPage: 1,
                totalElements:2,
                page:0,
                size:15,
                sDate :'',
                eDate :'',
                dataSetCountTimes: [],
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
                rangeDate:'',
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
                var urlCountTimes= '/api/security/coin/group/'+ this.serverId +'/' + type + '/item/' + this.rangeDate[0] + '/' + this.rangeDate[1]  + '/';
                this.$http.get(urlCountTimes).then(response => {

                    this.dataSetCountTimes = response.data;
                    for (let i in this.dataSetCountTimes){
                        this.dataSetCountTimes[i].date = moment(this.dataSetCountTimes[i].date).format('YYYY年MM月DD日')
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