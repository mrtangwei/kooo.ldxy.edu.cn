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

        <!--抽卡列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index"   sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="200" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数"  sortable>
            </el-table-column>
            <el-table-column prop="attend" label="参与人数"  sortable>
            </el-table-column>
            <el-table-column prop="attendRate" label="参与率"   :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="attendTotal" label="总领取人数"  sortable>
            </el-table-column>
            <el-table-column prop="attendOne" label="第一档领取人数"  sortable>
            </el-table-column>
            <el-table-column prop="attendOneRate" label="第一档领取人数占比率"   :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="attendTwo" label="第二档领取人数"   sortable>
            </el-table-column>
            <el-table-column prop="attendTwoRate" label="第二档领取人数占比率" :formatter="doubleFilter"  sortable>
            </el-table-column>
            <el-table-column prop="attendThree" label="第三档领取人数"  sortable>
            </el-table-column>
            <el-table-column prop="attendThreeRate" label="第三档领取人数占比率" :formatter="doubleFilter" sortable>
            </el-table-column>
            <el-table-column prop="attendFour" label="第四档领取人数"     sortable>
            </el-table-column>
            <el-table-column prop="attendFourRate" label="第四档领取人数占比率" :formatter="doubleFilter" sortable>
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
                if(typeof row[column.property] === 'number'){
                    var value = row[column.property] * 100;
                    var toFixedNum = Number(value).toFixed(3);
                    var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1) + '%';
                }else
                {
                    var realVal = 0;
                }

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
                var url = '/api/security/onlineTime/group/' + this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];
                this.$http.get(url).then(response => {

                    this.dataSet = response.data;
                    for (let i in this.dataSet){

                        this.dataSet[i].timestamp = moment(this.dataSet[i].timestamp).format('YYYY年MM月DD日')

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