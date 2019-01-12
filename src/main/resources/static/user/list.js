var height = $(document.body).height() * 0.8;


layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_user'
        , url: ctxPath + 'wwqUser/listData'
        , cols: [[
            {field: 'id', hide: true}
            , {field: 'nickName', title: '用户昵称', width: '10%', sort: true}
            , {field: 'headimgurl', title: '微信图像', width: '10%', templet: function (d) {
                    var html="";
                    html='<a href="'+d.headimgurl+'" target="_blank"><img  src="'+d.headimgurl+'" style="width: 200px;height: 200px"/></a>';
                    return html;
                }
            }
            , {field: 'phone', title: '手机号', width: '15%', sort: true}
            , {field: 'sex', title: '性别', width: '6%', templet: function (d) {
                    if(d.sex==1){
                        return "男";
                    }else if(d.sex==0){
                        return "女";
                    }
                }
             }
            , {field: 'score', title: '可用积分', width: '10%'}
            , {field: 'consumeScore', title: '消费积分', width: '10%'}
            , {field: 'createDate', title: '创建时间', width: '15%', sort: true}
            , {
                filed: 'cz', title: '操作', width: '25%', templet: function (d) {
                    var html = "";
                    // html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="viewUser">查看用户详情详情</a>';
                    html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="viewScore">查看积分详情</a>';
                    return html;
                }
            }
        ]]
        , id: 'testReload'
        , page: true
        , height: height
        , done: function (res, curr, count) {
            $("[data-field='id']").css('display', 'none');
        }
    });
    //监听工具条
    table.on('tool(user)', function (obj) {
        var data = obj.data;
        if (obj.event === 'viewUser') {

        }else if(obj.event==='viewScore'){
            x_admin_show('查看用户积分信息'+"【"+data.nickName+"】", 'wwqUserScore/showScoreInfo?userId=' + data.id, 600, 400);
        }
    });

    var $ = layui.$, active = {
        reload: function () {
            var nickname = $('#nickname').val();
            var data = {
                nickname: nickname
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

    $('.userTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})