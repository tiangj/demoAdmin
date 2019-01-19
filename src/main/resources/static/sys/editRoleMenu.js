
var setting = {
    //显示复选框
    check: {
        enable: true
    },
};


$(function(){
    var menusList=$("#menusList").val();
    $.fn.zTree.init($("#treeDemo"), setting, JSON.parse(menusList));
});
