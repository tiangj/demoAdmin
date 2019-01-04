layui.use('upload', function() {
    var $ = layui.jquery
        , upload = layui.upload;


    //普通图片上传
    var uploadInst = upload.render({
        elem: '#bannerPic'
        ,url: ctxPath+'common/upload'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#bannerImg').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //上传成功
            if(res.code==1){
                layer.msg(res.msg,{icon:6});
                $("#filePath").val(res.picUrl[0]);
                return;
            //上传失败
            }else{
                layer.msg(res.msg,{icon:5});
                return;
            }

        }
        ,error: function(){
            //演示失败状态，并实现重传
            var bannerText = $('#bannerText');
            bannerText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            bannerText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
});


layui.use(['form','layer'], function() {
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer
    form.verify();

    //监听提交
    form.on('submit(add)', function(data){
        $.ajax({
            url:ctxPath+'wwqBanner/saveBanner',
            method:'post',
            data:data.field,
            dataType:'JSON',
            async:true,
            success:function(res){
                if(res.code==1){
                    layer.msg(res.msg,{icon:6},function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                    });
                }else{
                    layer.msg(res.msg,{icon:5});
                }

            }
        })
        return false;
    })
})
