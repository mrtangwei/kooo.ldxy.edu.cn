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
                <el-form-item prop="entry" label="选择进度" width="200" sortable>
                    <el-select v-model="level" placeholder="选择进度">
                        <el-option
                                v-for="item in levelList"
                                :key="item"
                                :label="item.name"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-button type="primary" @click.native="querySubmit('type')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <!--合约-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="150" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数" width="130" sortable>
            </el-table-column>
            <el-table-column prop="enter" label="进入合约的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="contractUid" label="参与合约的人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="contractTimes" label="参与合约的次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="ave" label="合约平均参与次数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="levelUid" label="合约达到进度的人数" width="230" sortable>
            </el-table-column>
            <el-table-column prop="levelTimes" label="合约达到进度的人次" width="230" sortable>
            </el-table-column>
            <el-table-column prop="diamondRefreshUid" label="使用钻石刷新的人数" width="230" sortable>
            </el-table-column>
            <el-table-column prop="diamondRefreshTimes" label="使用钻石刷新的次数" width="230" sortable>
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
                level:'',

                levelList: [{
                    value: '1',
                    label: '进度1'
                },  {
                    value: '2',
                    label: '进度2'
                }, {
                    value: '3',
                    label: '进度3'
                }, {
                    value: '4',
                    label: '进度4'
                }, {
                    value: '5',
                    label: '进度5'
                }, {
                    value: '6',
                    label: '进度6'
                }, {
                    value: '7',
                    label: '进度7'
                }, {
                    value: '8',
                    label: '进度8'
                }, {
                    value: '9',
                    label: '进度9'
                }, {
                    value: '10',
                    label: '进度10'
                }, {
                    value: '11',
                    label: '进度12'
                },{
                    value: '12',
                    label: '进度12'
                }],

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
                var url = '/api/security/contract/group/'+ this.serverId + '/' + this.level +'/' + this.rangeDate[0] + '/' + this.rangeDate[1];


                this.$http.get(url).then(response => {
                    this.dataSet = response.data;
                    for (let i in this.dataSet){
                        this.dataSet[i].timestamp = moment(this.dataSet[i].timestamp.millis).format('YYYY年MM月DD日')

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