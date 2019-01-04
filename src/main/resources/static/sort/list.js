var height = $(document.body).height() * 0.8;

layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_sort'
        , url: ctxPath + 'wwqSort/listData'
        , cols: [[
            {field: 'id', hide: true}
            , {field: 'name', title: '商品分类名称', width: '15%', sort: true}
            , {field: 'introduction', title: '品牌简介', width: '20%', sort: true}
            , {field: 'logo', title: '图片信息', width: '20%', templet: function (d) {
                    var html="";
                    html='<a href="'+d.logo+'" target="_blank"><img  src="'+d.logo+'" style="width: 300px;height: 300px"/></a>';
                    return html;
                }
            }

            , {field: 'remark', title: '备注', width: '15%'}
            , {field: 'sort', title: '是否显示在首页', width: '10%', templet: function (d) {
                        if(d.sort==0){
                            return "是";
                        }else{
                            return "否";
                        }
                    }
               }
            , {field: 'createDate', title: '创建时间', width: '10%', sort: true}
            , {
                filed: 'cz', title: '操作', width: '10%', templet: function (d) {
                    var html = "";
                    html += '<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>';
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
                    url: ctxPath + 'wwqSort/delSort',
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
        }else if (obj.event === 'edit') {
            x_admin_show('修改商品类型信息', 'wwqSort/toAdd?id=' + data.id, 800, 600);
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
