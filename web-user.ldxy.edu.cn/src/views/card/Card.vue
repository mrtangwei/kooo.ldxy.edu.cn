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
                <el-button type="primary" @click.native="querySubmit('all')" :loading="listLoading">查询</el-button>
            </el-form>
        </el-col>

        <!-- 卡牌列表 -->
        <el-table :data="dataSet" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column label="序号" type="index" width="80" sortable>
            </el-table-column>
            <el-table-column prop="roleName" label="角色名" width="180" sortable>
            </el-table-column>
            <el-table-column prop="roleId" label="ID" width="180" sortable>
            </el-table-column>
            <el-table-column prop="one" label="拥有人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="one" label="1星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="two" label="2星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="three" label="3星人数" width="180"  sortable>
            </el-table-column>
            <el-table-column prop="four" label="4星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="five" label="5星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="six" label="6星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="seven" label="7星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="eight" label="8星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="nine" label="9星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="ten" label="10星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="eleven" label="11星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twelve" label="12星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="thirteen" label="13星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="fourteen" label="14星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="fifteen" label="15星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="sixteen" label="16星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="seventeen" label="17星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="eighteen" label="18星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="nineteen" label="19星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twenty" label="20星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyOne" label="21星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyTwo" label="22星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyThree" label="23星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyFour" label="24星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyFive" label="25星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentySix" label="26星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentySeven" label="27星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyEight" label="28星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="twentyNine" label="29星人数" width="180" sortable>
            </el-table-column>
            <el-table-column prop="thirty" label="30星人数" width="180" sortable>
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
                roleName:[],
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

                });
                this.$http.get('/api/security/card/get/roleName/all/').then(response => {
                    this.roleName = response.data;
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
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                if(val > 0) val--;

            },
            //查询
            querySubmit: function (type) {
                this.dataSet = [];
                for(let i in this.roleName){
                     var url = '/api/security/card/group/' + this.serverId + '/' + this.roleName[i] + '/' + this.rangeDate[0] + '/' + this.rangeDate[1];
                     this.listLoading = true;
                        this.$http.get(url).then(response => {

                            console.log(response.data);
/*                            response.data.countUid =  response.data.one + response.data.two + response.data.three + response.data.four
                                + response.data.five + response.data.six + response.data.seven + response.data.eight + response.data.nine
                                + response.data.ten  + response.data.eleven  + response.data.twelve +  + response.data.thirteen +  + response.data.fourteen
                                + response.data.fifteen  + response.data.sixteen  + response.data.seventeen  + response.data.eighteen  + response.data.nineteen
                                + response.data.twenty  + response.data.twentyOne  + response.data.twentyTwo  + response.data.twentyThree  + response.data.twentyFour
                                + response.data.twentyFive + response.data.twentySix + response.data.twentySeven + response.data.twentyEight + response.data.twentyNine
                                + response.data.thirty;*/
                            this.dataSet.push(response.data);
/*                            this.dataSet[i] = response.data;
                            this.dataSet[i].countUid =  this.dataSet[i].one + this.dataSet[i].two + this.dataSet[i].three + this.dataSet[i].four
                                    + this.dataSet[i].five + this.dataSet[i].six + this.dataSet[i].seven + this.dataSet[i].eight + this.dataSet[i].nine
                                    + this.dataSet[i].ten  + this.dataSet[i].eleven  + this.dataSet[i].twelve +  + this.dataSet[i].thirteen +  + this.dataSet[i].fourteen
                                    + this.dataSet[i].fifteen  + this.dataSet[i].sixteen  + this.dataSet[i].seventeen  + this.dataSet[i].eighteen  + this.dataSet[i].nineteen
                                    + this.dataSet[i].twenty  + this.dataSet[i].twentyOne  + this.dataSet[i].twentyTwo  + this.dataSet[i].twentyThree  + this.dataSet[i].twentyFour
                                    + this.dataSet[i].twentyFive + this.dataSet[i].twentySix + this.dataSet[i].twentySeven + this.dataSet[i].twentyEight + this.dataSet[i].twentyNine
                                    + this.dataSet[i].thirty;*/

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
        }
    }

</script>

<style scoped>

</style>