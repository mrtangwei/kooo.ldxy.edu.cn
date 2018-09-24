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

        <!--活跃度列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="activityUid" label="每日进入活跃度人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="activityRate" label="活跃度参与占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="degreeThirty" label="每日活跃达到30的占比" width="180" sortable>
            </el-table-column>
            <el-table-column prop="degreeSixty" label="每日活跃达到60的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="degreeNinety" label="每日活跃达到90的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="degreeOneHundredThirty" label="每日活跃达到130的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="degreeOneHundredSeventy" label="每日活跃达到170的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="degreeTwoHundredTen" label="每日活跃达到210的人数" width="220" sortable>
            </el-table-column>
            <el-table-column prop="receiveThirty" label="领取活跃达到30奖励的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveSixty" label="领取活跃达到60奖励的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveNinety" label="领取活跃达到90奖励的人数" width="220" sortable>
            </el-table-column>
            <el-table-column prop="receiveOneHundredThirty" label="领取活跃达到130奖励的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveOneHundredSeventy" label="领取活跃达到170奖励的人数" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveTwoHundredTen" label="领取活跃达到210奖励的人数" width="220" sortable>
            </el-table-column>

            <el-table-column>
            </el-table-column>
        </el-table>


        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="activityUid" label="每日进入活跃度人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="activityRate" label="活跃度参与占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="degreeThirtyRate" label="每日活跃达到30的占比" :formatter="doubleFilter" width="180" sortable>
            </el-table-column>
            <el-table-column prop="degreeSixtyRate" label="每日活跃达到60的占比" :formatter="doubleFilter"  width="180" sortable>
            </el-table-column>
            <el-table-column prop="degreeNinetyRate" label="每日活跃达到90的占比"  :formatter="doubleFilter" width="200" sortable>
            </el-table-column>
            <el-table-column prop="degreeOneHundredThirtyRate" label="每日活跃达到130的占比"  :formatter="doubleFilter" width="200" sortable>
            </el-table-column>
            <el-table-column prop="degreeOneHundredSeventyRate" label="每日活跃达到170的占比" :formatter="doubleFilter"  width="200" sortable>
            </el-table-column>
            <el-table-column prop="degreeTwoHundredTenRate" label="每日活跃达到210的占比" :formatter="doubleFilter"  width="220" sortable>
            </el-table-column>
            <el-table-column prop="receiveThirtyRate" label="领取活跃达到30奖励的占比" :formatter="doubleFilter"  width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveSixtyRate" label="领取活跃达到60奖励的占比"  :formatter="doubleFilter" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveNinetyRate" label="领取活跃达到90奖励的占比"  :formatter="doubleFilter" width="220" sortable>
            </el-table-column>
            <el-table-column prop="receiveOneHundredThirtyRate" label="领取活跃达到130奖励的占比"  :formatter="doubleFilter" width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveOneHundredSeventyRate" label="领取活跃达到170奖励的占比" :formatter="doubleFilter"  width="200" sortable>
            </el-table-column>
            <el-table-column prop="receiveTwoHundredTenRate" label="领取活跃达到210奖励的占比" :formatter="doubleFilter"  width="220" sortable>
            </el-table-column>

            <el-table-column>
            </el-table-column>
        </el-table>

        <el-table :data="dataSetWeek" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="activityUid" label="周活跃人数" width="180"   sortable>
            </el-table-column>
            <el-table-column prop="degree250" label="周活跃达到250点的人数" width="180"   sortable>
            </el-table-column>
            <el-table-column prop="degree500" label="周活跃达到500点的人数" width="180"  sortable>
            </el-table-column>
            <el-table-column prop="degree750" label="周活跃达到750点的人数" width="180"  sortable>
            </el-table-column>
            <el-table-column prop="degree1000" label="周活跃达到1000点的人数" width="200"  sortable>
            </el-table-column>
            <el-table-column prop="degree1250" label="周活跃达到1250点的人数" width="200"  sortable>
            </el-table-column>
            <el-table-column prop="degree1500" label="周活跃达到1500点的人数" width="200"  sortable>
            </el-table-column>
            <el-table-column prop="degree1750" label="周活跃达到1750点的人数" width="200"  sortable>
            </el-table-column>
            <el-table-column>
            </el-table-column>
        </el-table>

        <el-table :data="dataSetWeek" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="monday" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="activityUid" label="周活跃人数" width="180"   sortable>
            </el-table-column>
            <el-table-column prop="degree250Rate" label="周活跃达到250点的占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="degree500Rate" label="周活跃达到500点的占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="degree750Rate" label="周活跃达到750点的占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="degree1000Rate" label="周活跃达到1000点的占比" width="200" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="degree1250Rate" label="周活跃达到1250点的占比" width="200" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="degree1500Rate" label="周活跃达到1500点的占比" width="200" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="degree1750Rate" label="周活跃达到1750点的占比" width="200" :formatter="doubleFilter" sortable>
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
                rangeDate:'',
                sDate :'',
                eDate :'',
                dataSet: [],
                dataSetWeek: [],

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

            },
            //查询
            querySubmit: function (type) {
                this.listLoading = true;
              //  console.log(moment().week());

                var url = '/api/security/activity/group/day/'+ this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];
                var week = '/api/security/activity/group/week/'+ this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];


                this.$http.get(url).then(response => {
                    //console.log(response.data)
                    this.dataSet = response.data;
                    for (let i in this.dataSet){
                        this.dataSet[i].timestamp = moment(this.dataSet[i].timestamp).format('YYYY年MM月DD日')
                    }
                   // this.listLoading = false;
                }, response => {
                    console.log("拉取数据错误！");
                });
                this.$http.get(week).then(response => {
                    //console.log(response.data)
                    this.dataSetWeek = response.data;
                    for (let i in this.dataSetWeek){
                        this.dataSetWeek[i].timestamp = moment(this.dataSetWeek[i].timestamp).format('YYYY年MM月DD日');

                        this.dataSetWeek[i].monday = moment(this.dataSetWeek[i].monday).format('YYYY年MM月DD日') + '--' + moment(this.dataSetWeek[i].sunday).format('YYYY年MM月DD日')
                    }
                    this.listLoading = false;
                }, response => {
                    console.log("拉取数据错误！");
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