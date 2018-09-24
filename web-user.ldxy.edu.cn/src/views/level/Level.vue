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

        <!--级别列表-->
        <el-table :data="dataSetLevel" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="timestamp" label="日期" width="180" sortable>
            </el-table-column>
            <el-table-column prop="dau" label="日活跃人数" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level1" label="L1级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level2" label="L2级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level3" label="L3级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level4" label="L4级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level5" label="L5级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level6" label="L6级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level7" label="L7级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level8" label="L8级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level9" label="L9级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level10" label="L10级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level11" label="L11级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level12" label="L12级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level13" label="L13级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level14" label="L14级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level15" label="L15级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level16" label="L16级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level17" label="L17级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level18" label="L18级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level19" label="L19级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level20" label="L20级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level21" label="L21级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level22" label="L22级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level23" label="L23级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level24" label="L24级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level25" label="L25级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level26" label="L26级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level27" label="L27级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level28" label="L28级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level29" label="L29级" width="160" sortable>
            </el-table-column>
            <el-table-column prop="level30" label="L30级" width="160" sortable>
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
                dataSetLevel: [],
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
                var url = '/api/security/rank/group/'+ this.serverId + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];

                this.$http.get(url).then(response => {
                    this.dataSetLevel = response.data;
                    for (let i in this.dataSetLevel){
                        this.dataSetLevel[i].timestamp = moment(this.dataSetLevel[i].timestamp.millis).format('YYYY年MM月DD日')
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