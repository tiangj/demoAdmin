layui.use('upload', function() {
    var $ = layui.jquery
        , upload = layui.upload;

    //绑定原始文件域
    upload.render({
        elem: '#uploadExcel'
        , url: ctxPath+'wwqUser/importUserExcel'
        ,accept: 'file' //普通文件
        , done: function (res) {
            console.log(res)
            if(res.code==1){
                layer.msg(res.msg,{icon:6});
            }else{
                layer.msg(res.msg,{icon:5});
            }
        }
    });

});
