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
                <el-button type="primary" @click.native="querySubmit('type')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <!--助手列表-->
        <el-table :data="dataSetAssistant" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
            </el-table-column>
            <el-table-column prop="addPlayer" label="新增玩家人数" width="160" sortable>
            </el-table-column>
            <el-table-column prop="assistantNumber" label="使用助手人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="assistantFrequency" label="使用助手次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rewardNumber" label="助手内领取奖励人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="rewardFrequency" label="助手内领取奖励次数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="newlyAdded" label="新增使用助手人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="newlyFrequency" label="新增使用助手次数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="newlyRewardNumber" label="新增助手内领取奖励人数" width="220" sortable>
            </el-table-column>
            <el-table-column prop="newlyRewardFrequency" label="新增助手内领取奖励次数" width="220" sortable>
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
                coinRateAve:false,
                currentPage: 1,
                totalElements:2,
                page:0,
                size:15,
                rangeDate:'',
                sDate :'',
                eDate :'',
                dataSetAssistant: [],
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
                var url = '/api/security/lottery/group/type/' + this.rangeDate[0] + '/' + this.rangeDate[1]  + '/';
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
            // this.getDataSet(this.size,this.page,'办公用房');
        }
    }

</script>

<style scoped>

</style>