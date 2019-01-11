
layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_userScore'
        , url: ctxPath + 'wwqUserScore/listData'
        , cols: [[
            {field: 'id', hide: true}
            , {field: 'nickName', title: '用户昵称', width: '10%', sort: true}
            , {field: 'phone', title: '手机号', width: '10%', sort: true}
            , {field: 'sex', title: '性别', width: '10%'}
            , {field: 'score', title: '可用积分', width: '10%'}
            , {field: 'consumeScore', title: '消费积分', width: '10%'}
            , {field: 'createDate', title: '创建时间', width: '15%', sort: true}
        ]]
        , id: 'testReload'
        , page: true
        , height: height
        , done: function (res, curr, count) {
            $("[data-field='id']").css('display', 'none');
        }
    });

})