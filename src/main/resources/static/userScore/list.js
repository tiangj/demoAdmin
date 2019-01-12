
layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_userScore'
        , url: ctxPath + 'wwqUserScore/listData'
        , where:{userId:$("#userId").val()}
        , cols: [[
            {field: 'userId', hide: true}
            , {field: 'integralDetail', title: '积分', width: '20%', sort: true}
            , {field: 'productName', title: '商品名称', width: '20%', sort: true}
            , {field: 'wayType', title: '积分进出方式', width: '20%', sort: true, templet: function (d) {
                        if(d.wayType==1){
                            return "获取";
                        }else if(d.wayType==2){
                            return "消费";
                        }
                    }
               }
            , {field: 'createDate', title: '创建时间', width: '20%', sort: true}
        ]]
        , id: 'testReload'
        , page: true
        , done: function (res, curr, count) {
            $("[data-field='userId']").css('display', 'none');
        }
    });

})