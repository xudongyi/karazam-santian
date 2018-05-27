/**
 * Created by suhao on 2017/6/1.
 */
var formUrl = {
    create : {
        url : ctx + '/admin/posts/taxonomy/create'
    },
    update : {
        url : ctx + '/admin/posts/taxonomy/update'
    }
};

var actionUrl = formUrl.create.url;
var categoryDatagrid, featureDatagrid, tagDatagrid;
var ue;

$(function () {
    if (typeof ue != 'undefined') {
        ue.destroy();
    }
    ue = UE.getEditor('text', {initialFrameWidth: '100%', initialFrameHeight:'600',allowDivTransToP: false});
    var action = $('#contentForm').find('input[name=action]').val();
    categoryDatagrid = getTaxonomyList('categoryDatagrid', 'category');
    featureDatagrid = getTaxonomyList('featureDatagrid', 'feature');
    tagDatagrid = getTaxonomyList('tagDatagrid', 'tag');

    $("input", $("#title").next("span")).blur(function(){
        var title = $(this).val();
        var categoryId = $('#categoryId').val();
        if (title != '') {
            Keasy.get(ctx + '/admin/posts/content/translate', {title:title, categoryId:categoryId}, function (result) {
                $('#slug').textbox('setValue', result.data);
            })
        }

    });

    $("input", $("#slug").next("span")).change(function(){
        var slug = $(this).val();
        var categoryId = $('#categoryId').val();
        if (slug != '') {
            Keasy.get(ctx + '/admin/posts/content/isExistSlug', {slug:slug, categoryId:categoryId}, function (result) {
                $('#slug').textbox('setValue', result.data);
            })
        }

    });

});

function getTaxonomyList(dg, type) {
    var singleSelect = true;
    if (type == 'tag') {
        singleSelect = false;
    }
    return $('#' + dg).treegrid({
        method: "get",
        url: ctx + '/admin/posts/taxonomy/json?type=' + type,
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'id',
        treeField: 'title',
        iconCls: 'icon',
        pageSize: 20,
        pageList: [10, 20, 30, 40, 50],
        fit:true,
        pagination:false,
        animate: true,
        singleSelect: singleSelect,
        cascadeCheck: true,
        striped: true,
        columns: [[
            {field: 'id', title: 'id', hidden: true},
            {field: 'title', title: '名称', width: 100}
        ]],
        onLoadSuccess: function(data) {
            var contentId = $('#contentForm').find('#contentId').val();
            if ('' == contentId) {
                return;
            }
            Keasy.get(ctx + '/admin/posts/content/taxonomy.json', {contentId:contentId}, function (result) {
                var categories = result.data.categories;
                var features = result.data.features;
                var tags = result.data.tags;
                selectData(categories, categoryDatagrid);
                selectData(features, featureDatagrid);
                selectData(tags, tagDatagrid);
            });
        }
    });
}

function pushContent(status) {
    var isValid = $('#contentForm').form('validate');
    if (!isValid) {
        $.messager.alert('提示', '请完善必输选项', 'error');
        return;
    }
    var categoryRow = categoryDatagrid.datagrid('getSelected');
    if (!categoryRow) {
        $.messager.alert('提示', '请选择分类', 'error');
        return;
    }
    var formData = Keasy.serializeObject($('#contentForm'));
    formData.categoryId = categoryRow.id;
    formData.status = status;
    var featureRow = featureDatagrid.datagrid('getSelected');
    if (featureRow) {
        formData.featureId = featureRow.id;
    }

    var tagRows = tagDatagrid.datagrid('getSelections');
    var tagIdList = new Array();
    for (var i = 0, j = tagRows.length; i < j; i++) {
        var items = tagRows[i].id;
        if (items == null) {
            continue;
        }
        tagIdList.push(items);
    }
    var tagIds = "";
    $.each(tagIdList, function (i, v) {
        tagIds = tagIds + v;
        if (i != tagIdList.length-1) {
            tagIds = tagIds + ',';
        }
    });
    if (tagIdList.length > 0) {
        formData.tagIdsStr = tagIds;
    }

    if (formData.categoryId == '') {
        $.messager.alert('提示', '请选择分类', 'error');
        return;
    }

    if (formData.action == 'create') {
        Keasy.post('/admin/posts/content/create', formData, function (result) {
            if (result.status == 'success') {
                $('#contentForm').form('clear');
                ue.setContent('');
                parent.$.messager.show({title: "提示", msg: result.message, position: "bottomRight"});
                var tabs = parent.$('#index_tabs');
                var currentTab = tabs.tabs('getSelected');
                var index = tabs.tabs('getTabIndex',currentTab);
                var isExistsContTab = tabs.tabs('exists','所有文章');
                if (isExistsContTab) {
                    tabs.tabs('select','所有文章');
                    window.top.reloadContentsDatagrid.call();
                }
                tabs.tabs('close', index);
                return true;
            } else {
                $.messager.alert('提示',result.message,'error');
                return false;
            }
        })
    }
    if (formData.action == 'update') {
        Keasy.post('/admin/posts/content/update', formData, function (result) {
            if (result.status == 'success') {
                $('#contentForm').form('clear');
                ue.setContent('');
                parent.$.messager.show({title: "提示", msg: result.message, position: "bottomRight"});
                var tabs = parent.$('#index_tabs');
                var currentTab = tabs.tabs('getSelected');
                var index = tabs.tabs('getTabIndex',currentTab);
                var isExistsContTab = tabs.tabs('exists','所有文章');
                if (isExistsContTab) {
                    tabs.tabs('select','所有文章');
                    window.top.reloadContentsDatagrid.call();
                }
                tabs.tabs('close', index);
                return true;
            } else {
                $.messager.alert('提示',result.message,'error');
                return false;
            }
        })
    }
}

function selectData(datas, grid) {
    $.each(datas, function (i, d) {
        grid.treegrid('select', d.id);
    })
}
