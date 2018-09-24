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
<!--            <el-tab-pane label="外务" name="first">
                <el-table :data="dataSetForeign" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="newUid" label="新增玩家人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="uidCount" label="参与外务人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="uidTimes" label="参与外务次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="rate" label="参与占比率" width="200" :formatter="doubleFilter" sortable>
                    </el-table-column>
                    <el-table-column prop="ave" label="平均参与次数" :formatter="intFilter" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="todayUid" label="当日新增参与外务人数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="todayTimes" label="当日新增参与外务次数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="newRate" label="新增参与率" :formatter="doubleFilter" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="newAve" label="新增平均参与次数" :formatter="intFilter" width="220" sortable>
                    </el-table-column>
                </el-table>
            </el-tab-pane>-->
            <el-tab-pane label="通告" name="second">
                <el-table :data="dataSetAnnouncement" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="newUid" label="新增玩家人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="uid" label="参与通告人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="times" label="参与通告次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="rate" label="参与占比率" width="200" :formatter="doubleFilter" sortable>
                    </el-table-column>
                    <el-table-column prop="ave" label="平均参与次数" width="200"  :formatter="intFilter" sortable>
                    </el-table-column>
                    <el-table-column prop="sameDayUid" label="当日新增参与通告人数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="sameTimes" label="当日新增参与通告次数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="sameRate" label="新增参与率" :formatter="doubleFilter" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="sameAve" label="新增平均参与次数" :formatter="intFilter" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondUidNew" label="钻石新增刷新人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondTimesNew" label="钻石新增刷新次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondUid" label="通告钻石刷新人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondTimes" label="通告钻石刷新次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond1" label="通告钻石刷新1次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond2" label="通告钻石刷新2次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond3" label="通告钻石刷新3次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond4" label="通告钻石刷新4次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond5" label="通告钻石刷新5次人数" width="180" sortable>
                    </el-table-column>


                </el-table>
            </el-tab-pane>
            <el-tab-pane label="生活中心" name="third">
                <el-table :data="dataSetLifeCenter" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="newUid" label="新增玩家人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="uid" label="参与生活中心人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="times" label="参与生活中心次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="rate" label="参与占比率" :formatter="doubleFilter" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="ave" label="平均参与次数" :formatter="intFilter" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="sameDayUid" label="当日新增参与生活中心人数" width="240" sortable>
                    </el-table-column>
                    <el-table-column prop="sameTimes" label="当日新增参与生活中心次数" width="240" sortable>
                    </el-table-column>
                    <el-table-column prop="sameRate" label="新增参与率" :formatter="doubleFilter" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="sameAve" label="新增平均参与次数" :formatter="intFilter" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondUidNew" label="钻石新增刷新人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondTimesNew" label="钻石新增刷新次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondUid" label="钻石刷新人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamondTimes" label="钻石刷新次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond1" label="钻石刷新1次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond2" label="钻石刷新2次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond3" label="钻石刷新3次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond4" label="钻石刷新4次人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="diamond5" label="钻石刷新5次人数" width="180" sortable>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="宿舍" name="fourth">
                <el-table :data="dataSetDormitory" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
                    <el-table-column label="序号" type="index" width="80" sortable>
                    </el-table-column>
                    <el-table-column prop="timestamp" label="日期" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="newUid" label="新增玩家人数" width="160" sortable>
                    </el-table-column>
                    <el-table-column prop="uidCount" label="参与宿舍人数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="uidTimes" label="参与宿舍次数" width="180" sortable>
                    </el-table-column>
                    <el-table-column prop="rate" label="参与占比率" :formatter="doubleFilter" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="ave" label="平均参与次数" :formatter="intFilter" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="todayUid" label="当日新增参与宿舍人数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="todayTimes" label="当日新增参与宿舍次数" width="200" sortable>
                    </el-table-column>
                    <el-table-column prop="newRate" label="新增参与率" :formatter="doubleFilter" width="220" sortable>
                    </el-table-column>
                    <el-table-column prop="newAve" label="新增平均参与次数" :formatter="intFilter" width="220" sortable>
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
                dataSetForeign:[],                  //外务
                dataSetAnnouncement:[],            //通告
                dataSetLifeCenter:[],              //生活中心
                dataSetDormitory:[],               //宿舍
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

            },
            //查询
            querySubmit: function (type) {
                this.dataSetAnnouncement = [];
                this.dataSetLifeCenter = [];
                this.dataSetDormitory = [];
                this.listLoading = true;

                var url = '/api/security/affairs/group/'+ this.serverId +'/timestamp/' + this.rangeDate[0] + '/' + this.rangeDate[1];


                this.$http.get(url).then(response => {
                    console.log(response.data)
                   // this.dataSetForeign = response.data.affairs;
                    this.dataSetAnnouncement = response.data.notices;
                    this.dataSetLifeCenter = response.data.lives;
                    this.dataSetDormitory = response.data.dormitories;
/*                    for (let i in this.dataSetForeign){
                        this.dataSetForeign[i].timestamp = moment(this.dataSetForeign[i].timestamp).format('YYYY年MM月DD日')
                    }*/
                    for (let i in this.dataSetAnnouncement){
                        this.dataSetAnnouncement[i].timestamp = moment(this.dataSetAnnouncement[i].timestamp).format('YYYY年MM月DD日')
                    }
                    for (let i in this.dataSetLifeCenter){
                        this.dataSetLifeCenter[i].timestamp = moment(this.dataSetLifeCenter[i].timestamp).format('YYYY年MM月DD日')
                    }
                    for (let i in this.dataSetDormitory){
                        this.dataSetDormitory[i].timestamp = moment(this.dataSetDormitory[i].timestamp).format('YYYY年MM月DD日')
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