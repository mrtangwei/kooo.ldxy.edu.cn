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

        <!--新手引导列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="7日创建人数" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step1" label="完成第1步20000" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step2" label="完成第2步20001" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step3" label="完成第3步50000" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step4" label="完成第4步90000" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step5" label="完成第5步1500" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step6" label="完成第6步220000" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step7" label="完成第7步220001" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step8" label="完成第8步280000" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step9" label="完成第9步310000" width="130" sortable>
            </el-table-column>

        </el-table>

        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="7日创建人数" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step1Rate" label="完成任务1比例20000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step2Rate" label="完成任务2比例200001" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step3Rate" label="完成任务3比例50000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step4Rate" label="完成任务4比例90000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step5Rate" label="完成任务5比例160000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step6Rate" label="完成任务6比例220000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step7Rate" label="完成任务7比例220001" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step8Rate" label="完成任务8比例280000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step9Rate" label="完成任务9比例310000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>

        </el-table>

        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="7日创建人数" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step1RateKeep" label="完成任务1留存20000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step2RateKeep" label="完成任务2留存20001" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step3RateKeep" label="完成任务3留存50000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step4RateKeep" label="完成任务4留存90000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step5RateKeep" label="完成任务5留存160000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step6RateKeep" label="完成任务6留存220000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step7RateKeep" label="完成任务7留存220001" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step8RateKeep" label="完成任务8留存280000" :formatter="doubleFilter" width="130" sortable>
            </el-table-column>
            <el-table-column prop="step9RateKeep" label="完成任务9留存310000" :formatter="doubleFilter" width="130" sortable>
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
                var url = '/api/security/newcomer/group/'+ this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];


                this.$http.get(url).then(response => {
                    this.dataSet = response.data;
                    for (let i in this.dataSet){
                        //this.dataSet[i].timestamp = moment(this.dataSet[i].timestamp.millis).format('YYYY年MM月DD日')
                        this.dataSet[i].timestamp = moment(this.dataSet[i].timestamp.millis).format('YYYY年MM月DD日') + '--' + moment(this.dataSet[i].etimestamp.millis).format('YYYY年MM月DD日')

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