
layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_order'
        , url: ctxPath + 'wwqOrder/listData'
        , cols: [[
            {field: 'id', hide: true}
            , {field: 'nickName', title: '用户昵称', width: '10%', sort: true}
            , {field: 'headimgurl', title: '微信图像', width: '10%', templet: function (d) {
                    var html="";
                    html='<a href="'+d.headimgurl+'" target="_blank"><img  src="'+d.headimgurl+'" style="width: 200px;height: 200px"/></a>';
                    return html;
                }
            }
            , {field: 'phone', title: '手机号', width: '10%', sort: true}
            , {field: 'sex', title: '性别', width: '10%', templet: function (d) {
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
        ]]
        , id: 'testReload'
        , page: true
        , height: height
        , done: function (res, curr, count) {
            $("[data-field='id']").css('display', 'none');
        }
    });
    //监听工具条
    table.on('tool(banner)', function (obj) {
        var data = obj.data;
        if (obj.event === 'viewUser') {

        }else if(obj.event==='viewScore'){
            x_admin_show('查看订单信息'+"【"+data.nickName+"】", 'wwqUserScore/showScoreInfo?userId=' + data.id, 1000, 800);
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