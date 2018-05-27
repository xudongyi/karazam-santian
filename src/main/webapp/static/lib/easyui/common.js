var Keasy = $.extend({}, Keasy);
$(function () {
    Keasy.get = function(url, data, successCallback, errorCallback) {
        data._method="GET";
        $.ajax({
            type: "GET",
            url: ctx + url,
            data: data,
            dataType: "json",
            contentType:"application/x-www-form-urlencoded",
            success: function(_data,textStatus, xhr){
                successCallback(_data,textStatus, xhr);
            },
            error:function(xhr, errorText, e) {
                if(xhr.responseText != null && xhr.responseText != "") {
                    var result = eval("("+xhr.responseText+")");
                    if(errorCallback != null) {
                        errorCallback(result, e);
                    } else {
                        $.messager.alert('提示信息',result.message,'error');
                    }
                }
            }
        });
    };

    Keasy.post = function(url, data, successCallback, errorCallback) {
        if(data != null) data._method="POST";
        $.ajax({
            type: "POST",
            url: ctx + url,
            data: data,
            dataType: "json",
            contentType:"application/x-www-form-urlencoded",
            success: function(_data,textStatus, xhr){
                successCallback(_data,textStatus, xhr);
            },
            error:function(xhr, errorText, e) {
                if(xhr.responseText != null && xhr.responseText != "") {
                    var result = eval("("+xhr.responseText+")");
                    if(errorCallback != null) {
                        errorCallback(result, e);
                    } else {
                        $.messager.alert('提示信息',result.message,'error');
                    }
                }
            }
        });
    };

    Keasy.delete = function(url, data, successCallback, errorCallback) {
        if(data != null) data._method="DELETE";
        $.ajax({
            type: "POST",
            url: ctx + url,
            data: data,
            dataType: "json",
            contentType:"application/x-www-form-urlencoded",
            success: function(_data,textStatus, xhr){
                successCallback(_data,textStatus, xhr);
            },
            error:function(xhr, errorText, e) {
                if(xhr.responseText != null && xhr.responseText != "") {
                    var result = eval("("+xhr.responseText+")");
                    if(errorCallback != null) {
                        errorCallback(result, e);
                    } else {
                        $.messager.alert('提示信息',result.message,'error');
                    }
                }
            }
        });
    };

    Keasy.put = function(url, data, successCallback, errorCallback) {
        if(data != null) data._method="PUT";
        $.ajax({
            type: "POST",
            url: ctx + url,
            data: data,
            dataType: "json",
            success: function(_data,textStatus, xhr){
                successCallback(_data,textStatus, xhr);
            },
            error:function(xhr, errorText, e) {
                if(xhr.responseText != null && xhr.responseText != "") {
                    var result = eval("("+xhr.responseText+")");
                    if(errorCallback != null) {
                        errorCallback(result, e);
                    } else {
                        $.messager.alert('提示信息',result.message,'error');
                    }
                }
            }
        });
    };

    /**
     * 格式化金额
     * @param value
     * @param scale
     * @returns {*}
     */
    Keasy.formatToMoney = function (value, scale) {
        if ('-' == value) {
            return '-';
        }
        scale = scale > 0 && scale <= 20 ? scale : 2;
        value = parseFloat((value + "").replace(/[^\d\.-]/g, "")).toFixed(scale) + "";
        var l = value.split(".")[0].split("").reverse(), r = value.split(".")[1];
        var t = "";
        for (var i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        return t.split("").reverse().join("") + "." + r;
    };

    /**
     * 金额格式化为数字
     * @param value
     * @returns {Number}
     */
    Keasy.formatToNum = function rmoney(value) {
        return parseFloat(value.replace(/[^\d\.-]/g, ""));
    };

    /**
     * 将FORM表单元素的值序列化成对象
     * @param form
     * @returns {{}}
     */
    Keasy.serializeObject = function(form) {
        var o = {};
        var data = form.serializeArray();
        $.each(data, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    Keasy.formSubmit = function(form, successCallback, beforeSubmitCallback, errorCallback) {
        $(form).ajaxForm({
            dataType : 'json',
            beforeSubmit : function(formData, targetForm, options) {
                if(beforeSubmitCallback != null && beforeSubmitCallback != undefined) {
                    beforeSubmitCallback(formData, targetForm, options);
                }
            },
            success : function(data) {
                data = JSON.parse(data);
                console.log("1:" +data);
                if(successCallback != null && successCallback != undefined) {
                    successCallback(data);
                } else {
                    console.log("2:" +data);
                    if (data.status == 'success') {
                        $.messager.show({title: "提示", msg: data.message, position: "bottomRight"});
                    } else {
                        $.messager.alert(data.message);
                    }
                }
            },
            error: function(e) {
                $.messager.alert('提示', '提交失败，请重试或联系管理员!', "error");
                if(errorCallback != null && errorCallback != undefined) {
                    errorCallback(e);
                }
            }
        });
        $(form).ajaxSubmit();
    };

    /**
     * easyui dialog 封装
     * @param options 参数
     * @param callback 页面处理回调
     * @param confirmCallback 提交数据处理回调
     */
    Keasy.dialog = function(options, callback, confirmCallback) {
        var dialogId = options.dialogId;
        var opts = $.extend({
            id: dialogId + "_dialog",
            title: '新增',
            iconCls: "fa fa-plus",
            width: 600,
            height: 300,
            modal: true,
            collapsible: false,
            maximizable: false,
            closable: true,
            draggable: true,
            resizable: true,
            shadow: true,
            minimizable: false,
            href: options.url,
            buttons: [
                {
                    id: "dialog_confirm_btn",
                    iconCls: 'fa fa-check-square-o',
                    text: '确定',
                    handler: function () {
                        var form = $(dialogDomId).find("form");
                        var confirmBtn = this;
                        $(confirmBtn).linkbutton('disable');
                        if (!$(form).form('validate')) {
                            $(confirmBtn).linkbutton('enable');
                            return;
                        }
                        var formData = Keasy.serializeObject(form);
                        if (confirmCallback) {
                            confirmCallback(formData);
                            $(confirmBtn).linkbutton('enable');
                        } else {
                            if (typeof doConfirmDialog === "function") {
                                doConfirmDialog(formData);
                            } else {
                                Keasy.formSubmit(form);
                            }
                            $(confirmBtn).linkbutton('enable');
                        }
                    }
                }, {
                    iconCls: 'fa fa-close',
                    text: '取消',
                    handler: function () {
                        $(_dialog).dialog('destroy');
                    }
                }
            ],
            onLoad: function () {
                if (callback) {
                    callback();
                }
            },
            onClose:function () {
                $(_dialog).dialog('destroy');
            }
        }, options);
        var _dialog = $('<div></div>');
        var dialogDomId = "#" + dialogId +"_dialog";
        _dialog.attr("id", dialogDomId);
        _dialog.appendTo("body");
        _dialog.dialog(opts);
        return _dialog;
    };

    /**
     * datagrid封装
     * @param options
     */
    Keasy.datagrid = function(options, dgObj) {
        var dgId = options.id;
        var frozenColumns = [];
        if (options.frozenEnable && options.frozenCallback && typeof options.frozenCallback === "function") {
            if (!options.frozenWidth) {
                options.frozenWidth = 100;
            }
            frozenColumns = [[{
                field:'oprations',title:'操作',align:'center',width:options.frozenWidth,
                formatter:function(value,row,rowIndex){
                    return options.frozenCallback(row);
                }
            }]];
        }

        var opts = $.extend({
            id: dgId + "_dialog",
            method: "get",
            url: options.url,
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            striped: true,
            pagination: true,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            singleSelect: true,
            enableHeaderClickMenu: false,
            enableHeaderContextMenu: false,
            enableRowContextMenu: false,
            frozenColumns:frozenColumns,
            toolbar: options.toolbar
        }, options);
        if (dgObj) {
            return $(dgObj).datagrid(opts);
        } else {
            var _dg = $('<div></div>');
            var dgDomId = "#" + dgId +"_dg";
            _dg.attr("id", dgDomId);
            _dg.appendTo("body");
            return _dg.datagrid(opts);
        }
    };

    /**
     * 刷新表格
     * @param grid
     * @param params
     */
    Keasy.reloadGrid = function (grid, params) {
        grid.datagrid('unselectAll');
        if (params) {
            grid.datagrid('load', params);
        } else {
            grid.datagrid('load');
        }
    };

    Keasy.showWarn = function (msg, position) {
        if (typeof position === "string") {
            $.messager.show({title: "提示", msg: msg, icon:'warning', position: position});
            return;
        }
        $.messager.show({title: "提示", msg: msg, icon:'warning', position: "bottomRight"});
    };

    Keasy.showInfo = function (msg, position) {
        if (typeof position === "string") {
            $.easyui.messager.show({title: "提示", msg: msg, icon:'info', position: position});
            return;
        }
        $.messager.show({title: "提示", msg: msg, icon:'info', position: "bottomRight"});
    };

    Keasy.showError = function (msg, position) {
        if (typeof position === "string") {
            $.easyui.messager.show({title: "提示", msg: msg, icon:'error', position: position});
            return;
        }
        $.messager.show({title: "提示", msg: msg, icon:'error', position: "bottomRight"});
    };

    Keasy.alertWarn = function (msg, handler) {
        if (typeof handler === "function") {
            $.messager.alert("提示", msg, 'warning', handler);
            return;
        }
        $.messager.alert("提示", msg, 'warning');
    };

    Keasy.alertInfo = function (msg, handler) {
        if (typeof handler === "function") {
            $.messager.alert("提示", msg, 'info', handler);
            return;
        }
        $.messager.alert("提示", msg, 'info');
    };

    Keasy.alertError = function (msg, handler) {
        if (typeof handler === "function") {
            $.messager.alert("提示", msg, 'error', handler);
            return;
        }
        $.messager.alert("提示", msg, 'error');
    };

    Keasy.confirm = function (msg, handler) {
        if (typeof handler === "function") {
            $.messager.confirm("提示", msg, handler);
            return;
        }
        $.messager.confirm("提示", msg, handler);
    };

    /**
     * 客户选择弹框
     * @param hide
     * @param show
     * @param singleChoose 是否单选
     */
    var chooseUserDatagrid;
    Keasy.chooseUser = function (hide, show, singleChoose,userType) {
        var dialogId = "show_user_select";
        var opts = {
            id: dialogId + "_dialog",
            title:'会员选择',
            iconCls:"icon-hamburg-suppliers",
            width:500,
            height:300,
            modal:true,
            collapsible: false,
            maximizable: false,
            closable: true,
            draggable: true,
            resizable: true,
            shadow: true,
            minimizable: false,
            href: ctx + "/admin/user/choose",
            onClose: function() {
                $dialog.remove();
            },
            onLoad: function () {
                 chooseUserDatagrid = $('#chooseUserDatagrid');
                singleChoose = undefined==singleChoose||singleChoose?true:false;
                chooseUserDatagrid = chooseUserDatagrid.datagrid({
                    id: "chooseUserDatagrid",
                    method: "get",
                    url: ctx + '/admin/user/choose/json/'+userType,
                    modal:true,
                    collapsible: false,
                    maximizable: false,
                    closable: true,
                    draggable: true,
                    resizable: true,
                    fit: true,
                    fitColumns: true,
                    shadow: true,
                    minimizable: false,
                    idField: 'id',
                    striped: true,
                    pagination: true,
                    rownumbers: true,
                    pageNumber: 1,
                    pageSize: 20,
                    pageList: [10, 20, 30, 40, 50],
                    singleSelect: singleChoose,
                    columns: [[
                        {field: 'userInfoId', title: 'userInfoId', hidden: true},
                        {field: 'id', title: 'id', hidden: true},
                        {field: 'loginName', title: '登录名', sortable: true},
                        {field: 'name', title: '昵称', sortable: true},
                        {field: 'mobile', title: '手机号', sortable: true}
                    ]],
                    toolbar:[
                        {
                            id: "_dialog_confirm_btn",
                            iconCls:'fa fa-check',
                            text:'确定',
                            handler:function(){
                                var rows = getDatagridSelectRows(chooseUserDatagrid);
                                if(!rows){
                                    return false;
                                }
                                var ids = new Array();
                                var names = new Array();
                                $.each(rows, function(i, n){
                                    ids.push(rows[i].id);
                                    names.push(rows[i].name);
                                });
                                $('#'+show).textbox('setValue', names);
                                $('#'+hide).textbox('setValue', ids);
                                // parent.$($('#'+show)).textbox('setValue', names);
                                // parent.$($('#'+hide)).val(ids);
                                $dialog.dialog('close');
                                $dialog.remove();
                                chooseUserDatagrid.remove();
                            }
                        },{
                            iconCls:'fa fa-remove',
                            text:'清除',
                            handler:function(){
                                $('#'+show).textbox('setValue', "");
                                $('#'+hide).textbox('setValue', "");
                                $dialog.dialog('close');
                                $dialog.remove();
                            }
                        }
                    ],
                    onDblClickRow: function(index, row){
                        var ids = new Array();
                        var names = new Array();
                        ids.push(row.id);
                        names.push(row.name);
                        $('#'+show).textbox('setValue', names);
                        $('#'+hide).textbox('setValue', ids);
                        $dialog.dialog('close');
                        $dialog.remove();
                        chooseUserDatagrid.remove();
                    }

                });
            }

        };
        opts = $.extend({}, $.fn.window.defaults, opts);
        var $dialog = $('<div></div>');
        var $dialogDomId = "#" + dialogId +"_dialog";
        $dialog.attr("id", $dialogDomId);
        $dialog.appendTo("body");
        $dialog.dialog(opts);
        function getDatagridSelectRows(obj) {
            var selections = $(obj).datagrid('getSelections');
            if(selections == null || selections == ''){
                $.messager.alert('提示信息','请先选择一行数据！','info');
                return false;
            }
            return selections;
        }

    };


    $(document).on("click", ".keasy-choose-user", function () {
        var $this = $(this);
        var values = $this.attr("data-options");
        var text = $.util.likeArrayNotString(values)? values : [values];
        text = '{' +text.join(",") + '}';
        text = eval("(" + text + ")");
        Keasy.chooseUser(text.id, text.show, text.singleSelect,text.userType);
    });

});
function querymobile() {
    var obj = $("#userformsearch").serializeObject();
    $('#chooseUserDatagrid').datagrid('load', obj);

}

/**
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
    var opt = $(this).data().tree.options;
    var idFiled, textFiled, parentField, iconCls;
    if(opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        iconCls = opt.iconCls || 'iconCls';
        var i, l, treeData = [],
            tmpMap = [];
        for(i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for(i = 0, l = data.length; i < l; i++) {
            if(tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if(!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                data[i]['iconCls'] = data[i][iconCls];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                data[i]['iconCls'] = data[i][iconCls];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

/**
 * 扩展treegrid，使其支持平滑数据格式
 */
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
    var opt = $(this).data().treegrid.options;
    var idFiled, textFiled, parentField;
    if(opt.parentField) {
        idFiled = opt.idFiled || 'id';
        textFiled = opt.textFiled || 'text';
        parentField = opt.parentField;
        iconCls = opt.iconCls || 'iconCls';
        var i, l, treeData = [],
            tmpMap = [];
        for(i = 0, l = data.length; i < l; i++) {
            tmpMap[data[i][idFiled]] = data[i];
        }
        for(i = 0, l = data.length; i < l; i++) {
            if(tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                if(!tmpMap[data[i][parentField]]['children'])
                    tmpMap[data[i][parentField]]['children'] = [];
                data[i]['text'] = data[i][textFiled];
                data[i]['iconCls'] = data[i][iconCls];
                tmpMap[data[i][parentField]]['children'].push(data[i]);
            } else {
                data[i]['text'] = data[i][textFiled];
                data[i]['iconCls'] = data[i][iconCls];
                treeData.push(data[i]);
            }
        }
        return treeData;
    }
    return data;
};

/**
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 * 扩展树表格级联勾选方法：
 * @param {Object} container
 * @param {Object} options
 * @return {TypeName}
 */
$.extend($.fn.treegrid.methods,{
    /**
     * 级联选择
     * @param {Object} target
     * @param {Object} param
     *      param包括两个参数:
     *          id:勾选的节点ID
     *          deepCascade:是否深度级联
     * @return {TypeName}
     */
    cascadeCheck : function(target,param){
        var opts = $.data(target[0], "treegrid").options;
        if(opts.singleSelect)
            return;
        var idField = opts.idField;//这里的idField其实就是API里方法的id参数
        var status = false;//用来标记当前节点的状态，true:勾选，false:未勾选
        var selectNodes = $(target).treegrid('getSelections');//获取当前选中项
        for(var i=0;i<selectNodes.length;i++){
            if(selectNodes[i][idField]==param.id)
                status = true;
        }
        //级联选择父节点
        selectParent(target[0],param.id,idField,status);
        selectChildren(target[0],param.id,idField,param.deepCascade,status);
        /**
         * 级联选择父节点
         * @param {Object} target
         * @param {Object} id 节点ID
         * @param {Object} status 节点状态，true:勾选，false:未勾选
         * @return {TypeName}
         */
        function selectParent(target,id,idField,status){
            var parent = $(target).treegrid('getParent',id);
            if(parent){
                var parentId = parent[idField];
                if(status)
                    $(target).treegrid('select',parentId);
                else
                    $(target).treegrid('unselect',parentId);
                selectParent(target,parentId,idField,status);
            }
        }
        /**
         * 级联选择子节点
         * @param {Object} target
         * @param {Object} id 节点ID
         * @param {Object} deepCascade 是否深度级联
         * @param {Object} status 节点状态，true:勾选，false:未勾选
         * @return {TypeName}
         */
        function selectChildren(target,id,idField,deepCascade,status){
            //深度级联时先展开节点
            if(!status&&deepCascade)
                $(target).treegrid('expand',id);
            //根据ID获取下层孩子节点
            var children = $(target).treegrid('getChildren',id);
            for(var i=0;i<children.length;i++){
                var childId = children[i][idField];
                if(status)
                    $(target).treegrid('select',childId);
                else
                    $(target).treegrid('unselect',childId);
                selectChildren(target,childId,idField,deepCascade,status);//递归选择子节点
            }
        }
    }
});

/**
 * 是否选择行数据
 */
function rowIsNull(row){
    if(row){
        return false;
    }else{
        parent.$.messager.show({ title : "提示",msg: "请选择行数据！", position: "bottomRight" });
        return true;
    }
}

/**
 * ajax返回提示
 * @param data	返回的数据
 * @param dg datagrid
 * @param d	弹窗
 * @returns {Boolean} ajax是否成功
 */
function successTip(data, dg, d) {
    if (data.status == 'success') {
        if (dg != null)
            dg.datagrid('unselectAll');
            dg.datagrid('reload');
        if (d != null)
        {
            if ($(d).length == 1) {
                d.panel('close');
            } else {
                d.panel('destroy');
            }
        }
        parent.$.messager.show({title: "提示", msg: data.message, position: "bottomRight"});
        return true;
    } else {
        $.messager.alert('提示',data.message,'error');
        return false;
    }
}

var mainTabs = {
    addModule: function (title, url, icon, closable) {
        var defaultOpts = {
            title : '选项卡',
            border : false,
            closable : true,
            fit : true,
            content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
        };

        $.extend(defaultOpts, {
            title : title,
            icon : icon,
            closable : closable
        });

        var t = $('#index_tabs');
        if (t.tabs('exists', defaultOpts.title)) {
            t.tabs('select', defaultOpts.title);
        } else {
            t.tabs('add', defaultOpts);
        }
    }
};

beforeSubmit = function(that) {
    var isValid = $(that).form('validate');
    if (!isValid) {
        $.messager.alert('提示', '请完善必输选项', 'error');
    } else {
        $.messager.progress({
            title: '执行中',
            msg: '努力加载中...',
            text: '{value}%',
            interval: 100
        });
    }
    return isValid;	// 返回false终止表单提交
};
afterSubmit = function(that, data, dgrid) {
    $(that).form('clear');
    data = JSON.parse(data);
    if (data.status == 'success') {
        dgrid.datagrid('unselectAll');
        dgrid.datagrid('reload');
        parent.$.messager.show({title: "提示", msg: data.message, position: "bottomRight"});
    } else {
        $.messager.alert('提示',data.message,'error');
    }
    $.messager.progress('close');
};