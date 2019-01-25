var height = $(document.body).height() * 0.8;

layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_share'
        , url: ctxPath + 'wwqShareCount/listData'
        , cols: [[
            {field: 'userId', hide: true}
            ,{field: 'shareId', hide: true}
            ,{field: 'openId', hide: true}
            , {field: 'userName', title: '用户名', width: '15%', sort: true}
            , {field: 'userLevel', title: '用户分销等级', width: '20%', sort: true, templet: function (d) {
                    if(d.userLevel==1){
                        return "泉人";
                    }else if(d.userLevel==2){
                        return "泉主";
                    }else if(d.userLevel==3){
                        return "泉仙";
                    }
                }
            }
            , {field: 'firstShareNum', title: '直推人数', width: '15%'}
            , {field: 'secondShareNum', title: '间推人数', width: '15%'}
            , {field: 'remark', title: '备注', width: '15%'}
            , {field: 'createDate', title: '创建时间', width: '10%', sort: true}
            , {
                filed: 'cz', title: '操作', width: '10%', templet: function (d) {
                    var html = "";
                    html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="view">查看详情</a>';
                    return html;
                }
            }
        ]]
        , id: 'testReload'
        , page: true
        , height: height
        , done: function (res, curr, count) {
            $("[data-field='userId']").css('display', 'none');
            $("[data-field='shareId']").css('display', 'none');
            $("[data-field='openId']").css('display', 'none');
        }
    });
    //监听工具条
    table.on('tool(share)', function (obj) {
        var data = obj.data;
        if (obj.event === 'view') {
            x_admin_show('查看分销用户详情', 'wwqShareCount/toViewShareUser?userId=' + data.userId+"&openId="+data.openId, 800, 600);
            //图片上传页面
        }
    });

    var $ = layui.$, active = {
        reload: function () {
            var name = $('#name').val();
            var data = {
                name: name
            }
            //执行重载
            table.reload('testReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: data
            });
        }
    };

    $('.sortTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})
