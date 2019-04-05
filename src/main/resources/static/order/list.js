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
            , {field: 'nickName', title: '用户昵称', width: '8%', sort: true}
            , {field: 'userLevel', title: '用户等级', width: '8%', sort: true,templet: function (d) {
                    if(d.userLevel==1){
                        return "泉人";
                    }
                    if(d.userLevel==2){
                        return "泉仙";
                    }
                    if(d.userLevel==3){
                        return "泉祖";
                    }

                }
              }
            , {field: 'productName', title: '商品名称', width: '10%', sort: true}
            , {field: 'orderNum', title: '商品数', width: '6%'}
            , {field: 'orderTotalNum', title: '订单总金额', width: '8%'}
            , {field: 'orderStatus', title: '订单状态', width: '8%', templet: function (d) {
                        //100：未支付；200：已支付；300：门店备货；400：发货；500：评价；600：订单完成
                        if(d.orderStatus==100){
                            return "未支付";
                        }else if(d.orderStatus==200){
                            return "已支付";
                        }else if(d.orderStatus==300){
                            return "门店备货";
                        }else if(d.orderStatus==400){
                            return "发货";
                        }else if(d.orderStatus==500){
                            return "评价";
                        }else if(d.orderStatus==600){
                            return "订单完成";
                        }
                    }
                }
            , {field: 'payStatus', title: '支付状态', width: '8%', templet: function (d) {
                        if(d.payStatus==100){
                            return "未支付";
                        }else if(d.payStatus==200){
                            return "已支付";
                        }
                   }
               }
            , {field: 'message', title: '买家留言', width: '15%'}
            , {field: 'createDate', title: '创建时间', width: '10%', sort: true}
            , {
                filed: 'cz', title: '操作', width: '20%', templet: function (d) {
                    var html = "";
                    html += '<a class="layui-btn layui-btn-xs" lay-event="viewOrderDetail">查看详情</a>';
                    if(d.payStatus==200 && (d.orderStatus==200 || d.orderStatus==300)){
                        html += '<a class="layui-btn layui-btn-xs" lay-event="sendProduct">发货</a>';
                    }
                    if(d.userLevel==1){
                        html += '<a class="layui-btn layui-btn-xs" lay-event="levelUp">晋级泉仙</a>';
                    }
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
        }else if(obj.event=='sendProduct'){
            showTip('sendProduct',"确认对"+data.nickName+"的"+data.productName+"进行发货",ctxPath+'wwqOrder/sendProduct?orderId='+data.orderId);
         }else if(obj.event=='levelUp'){
            showTip('levelUp',"确认对"+data.nickName+"升级为泉仙",ctxPath+'wwqOrder/levelUp?userId='+data.userId);
        }
    });

    var $ = layui.$, active = {
        reload: function () {
            var productName = $('#productName').val();
            var userName=$("#userName").val();
            var data = {
                productName: productName,
                userName:userName
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
});


function showTip(id,content,url) {
    layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,closeBtn: false
        ,area: '300px;'
        ,shade: 0.8
        ,id: id //设定一个id，防止重复弹出
        ,btn: ['确认', '关闭']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: content
        ,yes: function(){
            $.ajax({
                url:url,
                method:'post',
                dataType:'JSON',
                async:true,
                success:function(res){
                    if(res.code==1){
                        layer.msg(res.msg,{icon:6});
                        layer.closeAll();
                    }else{
                        layer.msg(res.msg,{icon:5});
                    }

                }
            })
        }

    });
}