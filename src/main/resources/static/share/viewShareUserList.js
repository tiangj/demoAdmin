
layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_shareUser'
        , url: ctxPath + 'wwqShareCount/shareUserListData'
        , where:{userId:$("#userId").val(),openId:$("#openId").val()}
        , cols: [[
            {field: 'userId', hide: true}
            , {field: 'userName', title: '用户名', width: '20%', sort: true}
            , {field: 'level', title: '直推/间推', width: '20%', sort: true, templet: function (d) {
                    if(d.level==1){
                        return "直推";
                    }else if(d.level==2){
                        return "间推";
                    }
                }
            }
            , {field: 'userLevel', title: '用户级别', width: '20%', sort: true, templet: function (d) {
                    if(d.userLevel==1){
                        return "泉人";
                    }else if(d.userLevel==2){
                        return "泉主";
                    }else if(d.userLevel==3){
                        return "泉仙";
                    }
                }}
            , {field: 'parentUserName', title: '推荐人', width: '20%', sort: true}
        ]]
        , id: 'testReload'
        , page: true
        , done: function (res, curr, count) {
            $("[data-field='userId']").css('display', 'none');
        }
    });

})