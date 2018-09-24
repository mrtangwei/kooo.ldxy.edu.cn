<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true">
<!--                <el-form-item>
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="所有用户购买金币" name="first">所有用户购买金币</el-tab-pane>
                        <el-tab-pane label="新手购买金币" name="second">新手购买金币</el-tab-pane>


                    </el-tabs>
                </el-form-item>-->
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
                <el-button type="primary" @click.native="querySubmit('songName')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <!--购买金币列表-->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="songName" label="歌曲名称" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countClassic" label="Classic使用人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countStandard" label="Standard使用人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countHard" label="Hard使用人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countExpert" label="Expert使用人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countMaster" label="Mater使用人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countClassicOneStar" label="Classic 1星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countClassicTwoStar" label="Classic 2星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countClassicThreeStar" label="Classic 3星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countClassicFourStar" label="Classic 4星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countClassicFiveStar" label="Classic 5星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countStandardOneStar" label="Standard 1星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countStandardTwoStar" label="Standard 2星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countStandardThreeStar" label="Standard 3星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countStandardFourStar" label="Standard 4星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countStandardFiveStar" label="Standard 5星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countHardOneStar" label="Hard 1星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countHardTwoStar" label="Hard 2星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countHardThreeStar" label="Hard 3星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countHardFourStar" label="Hard 4星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countHardFiveStar" label="Hard 5星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countExpertOneStar" label="Expert 1星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countExpertTwoStar" label="Expert 2星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countExpertThreeStar" label="Expert 3星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countExpertFourStar" label="Expert 4星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countExpertFiveStar" label="Expert 5星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countMasterOneStar" label="Master 1星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countMasterTwoStar" label="Master 2星" width="180" sortable>
            </el-table-column>
            <el-table-column prop="countMasterThreeStar" label="Master 3星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countMasterFourStar" label="Master 4星" width="200" sortable>
            </el-table-column>
            <el-table-column prop="countMasterFiveStar" label="Master 5星" width="200" sortable>
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
                songName:[],
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
                });
                this.$http.get('/api/security/song/get/songName/all/').then(response => {
                    this.songName = response.data;
                }, response => {
                    if(response.status == 401){
                        console.log("请登录！");
                        this.$router.push('/login');
                    } else if(response.status == 403){
                        console.log("权限不够，请联系管理员！");
                        this.$router.push('/login');
                    }

                });
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
            handleClick(tab, event) {
                console.log(tab.name);
                var type = '';
                if(tab.name == "first"){
                    type='所有用户购买金币';
                    this.querySubmit('all');
                }else if(tab.name == "second"){
                    type='新手购买金币';
                    this.querySubmit('new');

                }
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
                this.dataSet=[];
                for(let i in this.songName){
                    this.listLoading = true;
                    var url = '/api/security/song/group/' + this.serverId + '/' + this.songName[i] + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];
                    this.listLoading = true;
                    this.$http.get(url).then(response => {
                        console.log(response.data);
                        this.dataSet.push(response.data);
                        this.listLoading = false;
                    }, response => {
                        console.log("拉取数据错误！");
                        this.listLoading = false;
                    });
                }
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