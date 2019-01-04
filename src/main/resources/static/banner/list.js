var height = $(document.body).height() * 0.8;

layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_banner'
        , url: ctxPath + 'wwqBanner/listData'
        , cols: [[
            {field: 'id', hide: true}
            , {field: 'filePath', title: '图片信息', width: '20%', templet: function (d) {
                    var html="";
                    html='<a href="'+d.filePath+'" target="_blank"><img  src="'+d.filePath+'" style="width: 300px;height: 300px"/></a>';
                    return html;
                 }
               }
            , {field: 'remark', title: '描述', width: '30%', sort: true}
            , {field: 'sort', title: '排序', width: '15%'}
            , {field: 'createDate', title: '创建时间', width: '25%', sort: true}
            , {
                filed: 'cz', title: '操作', width: '10%', templet: function (d) {
                    var html = "";
                    html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
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
    table.on('tool(banner)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {

                $.ajax({
                    url: ctxPath + 'wwqBanner/delBanner',
                    method: 'post',
                    data: {id: data.id},
                    dataType: 'JSON',
                    async: true,
                    success: function (res) {
                        if (res.code == 1) {
                            layer.msg(res.msg, {icon: 6});
                            obj.del();
                            layer.close(index);
                        } else {
                            layer.msg(res.msg, {icon: 2});
                        }
                    }
                })

            });
        }
    });

    var $ = layui.$, active = {
        reload: function () {
            var remark = $('#remark').val();
            var data = {
                remark: remark
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

    $('.bannerTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})
