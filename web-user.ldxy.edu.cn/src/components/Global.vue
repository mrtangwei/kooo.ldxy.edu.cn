<script type="text/javascript">
    var serverList = 'bbbb';
    function getServerList() {

         this.$http.get('/api/security/server/all').then(response => {
            this.serverList = response.data;

            return  this.serverList;

        }, response => {
            if(response.status == 401){
                console.log("请登录！");
                this.$router.push('/login');
            } else if(response.status == 403){
                console.log("权限不够，请联系管理员！");
                this.$router.push('/login');
            }

        })
    }
    export default{
         getServerList() {
            return   'aaa'
        },
        serverList

    }

/*var serverList = 'xxxxxx';
exports.install = function (Vue, options) {


    Vue.prototype.getServerList = function() {


        this.$http.get('/api/security/server/all').then(response => {
            this.serverList = response.data;

            return  this.serverList;

        }, response => {
            if(response.status == 401){
                console.log("请登录！");
                this.$router.push('/login');
            } else if(response.status == 403){
                console.log("权限不够，请联系管理员！");
                this.$router.push('/login');
            }

        })
    }


}*/

</script>