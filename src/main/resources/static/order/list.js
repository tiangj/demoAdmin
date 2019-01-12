var height = $(document.body).height() * 0.8;

layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
        elem: '#LAY_table_order'
        , url: ctxPath + 'wwqOrder/listData'
        , cols: [[
            {field: 'userId', hide: true}
            ,{field: 'productId', hide: true}
            ,{field: 'orderId', hide: true}
            , {field: 'nickName', title: '用户昵称', width: '10%', sort: true}
            , {field: 'productName', title: '商品名称', width: '15%', sort: true}
            , {field: 'orderNum', title: '商品数', width: '10%'}
            , {field: 'orderTotalNum', title: '订单总金额', width: '10%'}
            , {field: 'orderStatus', title: '订单状态', width: '10%'}
            , {field: 'message', title: '买家留言', width: '15%'}
            , {field: 'createDate', title: '创建时间', width: '15%', sort: true}
            , {
                filed: 'cz', title: '操作', width: '15%', templet: function (d) {
                    var html = "";
                    html += '<a class="layui-btn layui-btn-xs" lay-event="viewOrderDetail">查看详情</a>';
                    return html;
                }
            }
        ]]
        , id: 'testReload'
        , page: true
        , height: height
        , done: function (res, curr, count) {
            $("[data-field='userId']").css('display', 'none');
            $("[data-field='productId']").css('display', 'none');
            $("[data-field='orderId']").css('display', 'none');
        }
    });
    //监听工具条
    table.on('tool(order)', function (obj) {
        var data = obj.data;
         if(obj.event==='viewOrderDetail'){
            x_admin_show('查看订单信息', 'wwqOrder/showOrderDetail?orderId=' + data.orderId, 800, 500);
        }
    });

    var $ = layui.$, active = {
        reload: function () {
            var productName = $('#productName').val();
            var data = {
                productName: productName
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

    $('.orderTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
})