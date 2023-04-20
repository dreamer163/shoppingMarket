/**
 * 分页相关的配置
 **/
const pagination = {
    // 分页方式：[client] 客户端分页，[server] 服务端分页
    sidePagination: "server",
    // 初始化加载第一页，默认第一页
    pageNumber: 1,
    // 每页的记录行数
    pageSize: 10,
    // 可供选择的每页的行数 - (亲测大于1000存在渲染问题)
    pageList: [5, 10, 25, 50, 100],
    // 在上百页的情况下体验较好 - 能够显示首尾页
    paginationLoop: true,
    // 展示首尾页的最小页数
    paginationPagesBySide: 2
};

/**
 * 按钮相关配置
 **/
const button = {
    // 按钮的类
    buttonsClass: 'default',
    // 类名前缀
    buttonsPrefix: 'btn'
}

/**
 * 图标相关配置
 **/
const icon = {
    // 图标前缀
    iconsPrefix: 'mdi',
    // 图标大小
    iconSize: 'mini',
    // 图标的设置
    icons: {
        paginationSwitchDown: 'mdi-door-closed',
        paginationSwitchUp: 'mdi-door-open',
        refresh: 'mdi-refresh',
        toggleOff: 'mdi-toggle-switch-off',
        toggleOn: 'mdi-toggle-switch',
        columns: 'mdi-table-column-remove',
        detailOpen: 'mdi-plus',
        detailClose: 'mdi-minus',
        fullscreen: 'mdi-monitor-screenshot',
        search: 'mdi-table-search',
        clearSearch: 'mdi-trash-can-outline'
    }
};

/**
 * 表格相关的配置
 **/
const table = {
    classes: 'table table-bordered table-hover table-striped lyear-table',
    // 请求地址
    url: ctx + '/api/categories',
    // 唯一ID字段
    uniqueId: 'id',
    // 每行的唯一标识字段
    idField: 'id',
    // 是否启用点击选中行
    //clickToSelect: true,
    // 是否显示详细视图和列表视图的切换按钮(clickToSelect同时设置为true时点击会报错)
    // showToggle: true,
    // 请求得到的数据类型
    dataType: 'json',
    // 请求方法
    method: 'get',
    // 工具按钮容器
    toolbar: '#toolbar',
    // 是否分页
    //pagination: true,
    // 是否显示所有的列
    showColumns: true,
    // 是否显示刷新按钮
    showRefresh: true,
    // 显示图标
    showButtonIcons: true,
    // 显示文本
    showButtonText: false,
    // 显示全屏
    //showFullscreen: true,
    // 开关控制分页
    //showPaginationSwitch: true,
    // 总数字段
    totalField: 'total',
    // 当字段为 undefined 显示
    undefinedText: '-',
    // 排序方式
    sortOrder: "asc",
    ...icon,
    ...pagination,
    ...button
};

/**
 * 用于演示的列信息
 **/
const columns = [{
    field: 'checkbox',
    checkbox: true,
    // 列的宽度
    width: 5,
    // 宽度单位
    widthUnit: 'rem'
}, {
    field: 'id',
    title: '编号',
    // 使用[align]，[halign]和[valign]选项来设置列和它们的标题的对齐方式。h表示横向，v表示垂直
    //align: 'center',
    // 是否作为排序列
    sortable: true,
    switchable: false,
    // 列的宽度
    width: 5,
    // 宽度单位
    widthUnit: 'rem'
}, {
    field: 'name',
    title: '名称'
}, {
    field: 'iconClass',
    title: '字体图标'
}, {
    field: 'parentName',
    title: '父类别'
}, {
    field: 'seq',
    title: '顺序号'
}, {
    field: 'description',
    title: '备注',
}, {
    field: 'operate',
    title: '操作',
    formatter: btnGroup,  // 自定义方法
    events: {
        'click .edit-btn': function (event, value, row, index) {
            event.stopPropagation();
            editUser(row);
        },
        'click .del-btn': function (event, value, row, index) {
            event.stopPropagation();
            delUser(row);
        }
    }
}];

// 自定义操作按钮
function btnGroup() {
    let html =
        '<a href="javascript:void(0)" class="btn btn-sm btn-default me-1 edit-btn" title="编辑" data-bs-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>' +
        '<a href="javascript:void(0)" class="btn btn-sm btn-default del-btn" title="删除" data-bs-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>';
    return html;
}

// 操作方法 - 编辑
function editUser() {
    alert('跳转修改信息');
}

// 操作方法 - 删除
function delUser() {
    alert('信息删除成功');
}

//初始化表格
$('#table').bootstrapTable({
    ...table,
    // 自定义的查询参数
    queryParams: function (params) {
        //返回的对象的key用作请求参数的名称
        return {
            // 每页数据量
            pageSize: params.limit,
            // sql语句起始索引
            offset: params.offset,
            pageNo: (params.offset / params.limit) + 1,
            // 排序的列名
            sort: params.sort,
            // 排序方式 'asc' 'desc'
            sortOrder: params.order,
            id: $("#id").val(),
            name: $("#name").val()
        };
    },
    columns,
    onLoadSuccess: function (data) {
        $("[data-bs-toggle='tooltip']").tooltip();
    },
    treeShowField: 'name',
    parentIdField: 'parentId',
    onResetView: function (data) {
        // $('#table').treegrid({
        //     treeColumn: 1, //展开折叠图标出现列索引
        //     initialState: 'collapsed'// 所有节点都折叠
        //     //initialState: 'expanded',// 所有节点都展开，默认展开
        // });
    },
    onCheck: function (row) {
        //选中行事件
    },
    onUncheck: function (row) {
        //取消选中行事件
    }

});

//新建
$("#btn_add").click(function () {
    let url = ctx + '/category/add';

    let selected = $("#table").bootstrapTable('getSelections');
    if (selected.length > 0) {
        let id = selected[0].id;
        url += "?parentId=" + id;//向后台传递父类别编号
    }

    layer.open({
        type: 2,
        title: '新增商品类别',
        maxmin: true, //开启最大化最小化按钮
        area: ['600px', '600px'],
        content: url,
        btn: ["确定", "关闭"],
        yes: function (index, obj) {
            obj.find("iframe")[0].contentWindow.submitForm(function (resp) {
                if (resp.error) {
                    layer.msg(resp.error);
                } else {
                    $("#table").bootstrapTable('refresh');//刷新表格
                    layer.close(index);
                }
            });
        },
        btn2: function (index) {
            layer.close(index);
        }
    });
});

//删除
$("#btn_delete").click(function () {
    let selected = $("#table").bootstrapTable('getSelections');
    if (selected.length === 0) {
        layer.msg("请选中您要删除的行");
    } else {
        layer.confirm("是否确认删除选中的行(使用中的类别无法删除)?", function () {
            let ids = [];
            let passed = true;
            selected.forEach(function (item) {
                if (item.isLeaf === false) {
                    layer.msg("你要删除的类别，尚包含子类别，不能删除");
                    passed = false;
                    return false;
                }
                ids.push(item.id);
            });

            if (passed && ids.length > 0) {
                let url = ctx + "/api/categories";
                $.ajax(url, {
                    method: "delete",
                    dataType: "json",
                    data: {
                        ids
                    },
                    traditional: true,
                    success(resp) {
                        layer.msg("删除成功，共删除" + resp.rows + "条记录(使用中的类别无法删除!)");
                        $("#table").bootstrapTable('refresh');
                    },
                    error(resp) {
                        console.log(resp);
                    }
                });
            }
        });
    }
});

//查询
$("#btn_search").click(function () {
    $("#table").bootstrapTable('refresh');
});

//修改
$("#btn_edit").click(function () {
    let selected = $("#table").bootstrapTable('getSelections');
    if (selected.length === 0) {
        layer.msg("请选中您要修改的记录");
    } else if (selected.length > 1) {
        layer.msg("您一次只能选中一条记录进行修改");
    } else {
        let id = selected[0].id;
        let url = ctx + '/category/edit?id=' + id;

        layer.open({
            type: 2,
            title: '修改商品类别',
            maxmin: true, //开启最大化最小化按钮
            area: ['600px', '600px'],
            content: url,
            btn: ["确定", "关闭"],
            yes: function (index, obj) {
                obj.find("iframe")[0].contentWindow.submitForm(function (resp) {
                    if (resp.error) {
                        layer.msg(resp.error);
                    } else {
                        $("#table").bootstrapTable('refresh'); //刷新表格
                        layer.close(index);
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        });
    }
});






