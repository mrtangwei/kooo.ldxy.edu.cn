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
                <el-button type="primary" @click.native="querySubmit()" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <!--购买金币列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃用户数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="uidNew" label="新增玩家数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="uidCount" label="参与故事人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="uidTimes" label="参与故事次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rate" label="参与占比率" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="ave" label="平均参与次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="uidCountNew" label="当日新增参与故事人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="uidTimesNew" label="当日新增参与故事次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="rateNew" label="新增参与率" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="aveNew" label="新增平均参与次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="one" label="参与1节人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="two" label="参与2节人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="three" label="参与3节人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="four" label="参与4节人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="five" label="参与5节人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="oneRate" label="参与1节人数占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="twoRate" label="参与2节人数占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="threeRate" label="参与3节人数占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="fourRate" label="参与4节人数占比" width="180" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="fiveRate" label="参与5节人数占比" width="180" :formatter="doubleFilter" sortable>
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

                id:0,
                date:'',

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

            },
            //查询
            querySubmit: function () {
                this.listLoading = true;

                var url = '/api/security/archives/group/' + this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];
                this.$http.get(url).then(response => {
                    //console.log(response.data)
                    this.dataSet = response.data;
                    for (let i in this.dataSet){
                        this.dataSet[i].date = moment(this.dataSet[i].timestamp.millis).format('YYYY年MM月DD日')
                    };
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


