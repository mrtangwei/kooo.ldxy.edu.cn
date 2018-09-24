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

        <!--抽卡列表-->
        <el-table :data="dataSetLottery" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index"   sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期"  sortable>
            </el-table-column>
            <el-table-column prop="dau" label="活跃用户数"  sortable>
            </el-table-column>
            <el-table-column prop="attend" label="参与人数"   sortable>
            </el-table-column>
            <el-table-column prop="attendRate" label="参与率"   :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="oneNum" label="进行‘购买1次’操作的人数"   sortable>
            </el-table-column>
            <el-table-column prop="oneCount" label="进行‘购买1次’操作的次数"   sortable>
            </el-table-column>
            <el-table-column prop="oneRate" label="进行‘购买1次’操作的人数占比率"   :formatter="doubleFilter"  sortable>
            </el-table-column>
            <el-table-column prop="fiveNum" label="进行‘购买5次’操作的人数"   sortable>
            </el-table-column>
            <el-table-column prop="fiveCount" label="进行‘购买5次’操作的次数"  sortable>
            </el-table-column>
            <el-table-column prop="fiveRate" label="进行‘购买5次’操作的人数占比率"  :formatter="doubleFilter"  sortable>
            </el-table-column>


            <el-table-column>
            </el-table-column>
        </el-table>

        <el-table :data="dataSetLottery" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="receiveTotal" label="所有积分奖励总领取人数"   sortable>
            </el-table-column>
            <el-table-column prop="receive50" label="领取50积分档奖励人数"   sortable>
            </el-table-column>
            <el-table-column prop="receive50Rate" label="领取50积分档奖励人数占比率"  :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="receive500" label="领取500积分档奖励人数"  sortable>
            </el-table-column>
            <el-table-column prop="receive500Rate" label="领取500积分档奖励人数占比率"   :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="receive1000" label="领取1000积分档奖励人数"   sortable>
            </el-table-column>

            <el-table-column prop="receive1000Rate" label="领取1000积分档奖励人数占比率"  :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="receive1500" label="领取1500积分档奖励人数"    sortable>
            </el-table-column>
            <el-table-column prop="receive1500Rate" label="领取1500积分档奖励人数占比率"  :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="receive2000" label="领取2000积分档奖励人数"   sortable>
            </el-table-column>
            <el-table-column prop="receive2000Rate" label="领取2000积分档奖励人数占比率"  :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="receive3500" label="领取3500积分档奖励人数"   sortable>
            </el-table-column>
            <el-table-column prop="receive3500Rate" label="领取3500积分档奖励人数占比率"  :formatter="doubleFilter" sortable>
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
                dataSetLottery: [],



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


            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                if(val > 0) val--;
                this.getDataSet(this.size,val,'办公用房')
            },
            //查询
            querySubmit: function (type) {
                let start = new Date();
                this.listLoading = true;

                var url = '/api/security/lotteryTimeLimit/group/'+ this.serverId +'/' + this.rangeDate[0] + '/' + this.rangeDate[1]  + '/';

                this.$http.get(url).then(response => {

                    this.dataSetLottery = response.data;
                    for (let i in this.dataSetLottery){
                        this.dataSetLottery[i].timestamp = moment(this.dataSetLottery[i].timestamp).format('YYYY年MM月DD日')
                    }
                    this.listLoading = false;
                    console.log(new Date() - start);
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