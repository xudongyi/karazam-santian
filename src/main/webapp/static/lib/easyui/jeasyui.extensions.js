(function ($, undefined) {

    $.util.namespace("$.easyui");


    // 显示类似于 easyui-datagrid 在加载远程数据时显示的 mask 状态层；该函数定义如下重载方式：
    //      function ()
    //      function (options)，其中 options 为一个格式为
    //          { topMost: bool, locale: jquerySelectorString|jqueryObject|htmlElement, zindex: number, msg: string }
    //          的 json-object；
    // 上述参数中：
    //      topMost 为一个布尔类型参数，默认为 false，表示是否在顶级页面加载此 mask 状态层。
    //      locale  表示加载的区域，可以是一个 jQuery 对象选择器字符串，也可以是一个 jQuery 对象或者 HTML-DOM 对象；默认为字符串 "body"。
    //      zindex  表示遮蔽层的 css-zindex 属性；
    //      msg     表示加载显示的消息文本内容，默认为 "正在加载，请稍等..."；
    // 返回值：返回表示弹出的数据加载框和层的 jQuery 链式对象。
    $.easyui.loading = function (options) {
        var opts = $.extend({}, $.easyui.loading.defaults, options || {}),
            jq = opts.topMost ? $.util.$ : $,
            locale = jq(opts.locale),
            zindex = opts.zindex;
        if (locale.is(".loading-locale") || locale.children(".loading-container").length) {
            return false;
        }
        if (!zindex) {
            var zindexes = locale.children().map(function () {
                var i = $(this).css("z-index");
                return $.isNumeric(i) ? window.parseInt(i) : 0;
            });
            zindex = zindexes.length ? $.array.max(zindexes) : 1;
        }
        if (!locale.is("body")) {
            locale.addClass("loading-locale");
        }
        var container = jq("<div class=\"loading-container\"></div>").css("z-index", ++zindex).appendTo(locale),
            mask = jq("<div class=\"loading-mask datagrid-mask\"></div>").css("z-index", ++zindex).appendTo(container),
            msg = jq("<div class=\"loading-message datagrid-mask-msg\"></div>").css("z-index", ++zindex).html(opts.msg).appendTo(container);
        msg.css("marginLeft", -msg.outerWidth() / 2);
        return mask;
    };

    // $.easyui.loading 方法的默认参数格式
    $.easyui.loading.defaults = {
        topMost: false,
        locale: "body",
        zindex: null,
        msg: "正在加载，请稍等..."
    };


    // 关闭由 $.easyui.loading 方法显示的 "正在加载..." 状态层；该函数定义如下重载方式：
    //      function ()
    //      function (locale)
    //      function (topMost)
    //      function (locale, topMost)
    //      function (topMost, locale)
    //      function (options)，其中 options 为一个格式为 { locale, topMost } 的 JSON-Object
    // 上述参数中：
    //      topMost 为一个布尔类型参数，默认为 false，表示是否在顶级页面加载此 mask 状态层。
    //      locale  表示加载的区域，可以是一个 jQuery 对象选择器字符串，也可以是一个 jQuery 对象或者 HTML-DOM 对象；默认为字符串 "body"；当指定该参数时，表示只关闭指定区域的状态层。
    $.easyui.loaded = function (locale, topMost) {
        var opts = $.extend({}, $.easyui.loaded.defaults);
        if (arguments.length == 1) {
            var val = arguments[0],
                type = typeof (val);
            if (type == "boolean") {
                $.extend(opts, { topMost: val });
            } else if (type == "string" || $.util.isJqueryDOM(val)) {
                $.extend(opts, { locale: val });
            } else {
                $.extend(opts, val);
            }
        } else if (arguments.length >= 2) {
            var val = arguments[0],
                type = typeof (val);
            if (type == "boolean") {
                $.extend(opts, { locale: arguments[1], topMost: arguments[0] });
            } else if (type == "string" || $.util.isJqueryDOM(val)) {
                $.extend(opts, { locale: arguments[0], topMost: arguments[1] });
            } else {
                $.extend(opts, val);
            }
        }
        var jq = opts.topMost ? $.util.$ : $;
        jq(opts.locale).removeClass("loading-locale").children(".loading-container").remove();
    };

    // $.easyui.loaded 方法的默认参数格式
    $.easyui.loaded.defaults = {
        locale: "body",
        topMost: false
    };



    // 更改 jQuery EasyUI 中部分控件的国际化语言显示。
    $.extend($.fn.panel.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.window.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.dialog.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.datagrid.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.datalist.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.propertygrid.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.combogrid.defaults, { loadingMessage: $.easyui.loading.defaults.msg });
    $.extend($.fn.treegrid.defaults, { loadingMessage: $.easyui.loading.defaults.msg });


    // 获取或更改 jQuery EasyUI 部分组件的通用错误提示函数；该方法定义如下重载方式：
    //      function()      : 获取 jQuery EasyUI 部分组件的通用错误提示函数；
    //      function(error) : 更改 jQuery EasyUI 部分组件的通用错误提示函数；
    // 备注：该方法会设置如下组件的 onLoadError 事件；
    //          easyui-form
    //          easyui-panel
    //          easyui-window
    //          easyui-dialog
    //          easyui-tree
    //          easyui-datagrid
    //          easyui-propertygrid
    //          easyui-treegrid
    //          easyui-combobox
    //          easyui-combotree
    //          easyui-combogrid
    //      同时还会设置 jQuery-ajax 的通用错误事件 error。
    $.easyui.ajaxError = function (error) {
        if (!arguments.length) {
            return $.ajaxSettings.error || $.fn.form.defaults.onLoadError;
        }
        $.fn.form.defaults.onLoadError = error;

        $.fn.panel.defaults.onLoadError = error;
        $.fn.window.defaults.onLoadError = error;
        $.fn.dialog.defaults.onLoadError = error;

        $.fn.tree.defaults.onLoadError = error;
        $.fn.datagrid.defaults.onLoadError = error;
        $.fn.datalist.defaults.onLoadError = error;
        $.fn.propertygrid.defaults.onLoadError = error;
        $.fn.treegrid.defaults.onLoadError = error;

        $.fn.combobox.defaults.onLoadError = error;
        $.fn.combotree.defaults.onLoadError = error;
        $.fn.combogrid.defaults.onLoadError = error;

        $.ajaxSetup({
            error: error
        });
    };


    // 备份 jQuery ajax 方法的默认参数。
    $.util.namespace("$.easyui.ajax");
    $.easyui.ajax.defaults = $.extend({}, $.ajaxSettings);

    // 定义用于处理 jQuery 和 jQuery EasyUI 组件加载远程数据出错时的通用错误提示。
    $.easyui.error = function (XMLHttpRequest, textStatus, errorThrown) {
        $.messager.progress("close");
        if ($.easyui.messager && $.easyui.messager != $.messager) {
            $.easyui.messager.progress("close");
        }
        $.easyui.loaded();
        $.easyui.loaded(true);

        var msg = (XMLHttpRequest && !$.string.isNullOrWhiteSpace(XMLHttpRequest.responseText)
            ? "这个问题都被你遇见了。<br />" +
              "错误号：" + XMLHttpRequest.status + "(" + XMLHttpRequest.statusText + ")"
            : "系统出现了一个未指明的错误，如果该问题重复出现，请联系您的系统管理员并反馈该故障。");
        var win = $.easyui.messager && $.easyui.messager != $.messager
            ? $.easyui.messager.alert("错误提醒", msg, "error")
            : $.messager.alert("错误提醒", msg, "error"),
            opts = win.window("options"),
            panel = win.window("panel"),
            width = panel.outerWidth(),
            height = panel.outerHeight();
        if (width > 800 || height > 800) {
            win.window("resize", {
                width: Math.min(width, 800), height: Math.min(height, 800)
            });
        }
        win.window("center");
    };


    // 更改 jQuery EasyUI 部分组件的通用错误提示。
    $.easyui.ajaxError($.easyui.error);




    // 判断传入的 jQuery 对象或 HTML DOM 对象是否为已初始化的 jQuery EasyUI 组件；该方法定义如下参数：
    //      selector: jQuery 对象选择器，或者 DOM 对象，或者 jQuery 对象均可；
    //      plugin  : 要判断的插件名称，例如 "panel"、"dialog"、"datagrid" 等；
    //      ignoreValid: 是否忽略检查 $.parser.plugins 数组中存在 plugin 指定的插件名；boolean 类型值；该参数可选，默认为 false；
    // 返回值：
    //      如果 selector 所表示的 jQuery 对象中的第一个 DOM 元素为 plugin 参数所示的 easyui 插件且已经被初始化，则返回 true，否则返回 false；
    //      如果 selector 或 plugin 中任意一参数为 null/undefined，或 selector 所示的 jQuery 选择器不包含任意 HTML DOM 对象，则直接返回 false；
    //      如果 plugin 所示的名称不是一个 jQuery EasyUI 组件（没有包含在 $.parser.plugins 数组中），则直接抛出异常。
    $.easyui.isComponent = $.easyui.isEasyUiComponent = $.easyui.isEasyUI = function (selector, plugin, ignoreValid) {
        if (!selector || !plugin)
            return false;

        ignoreValid = ignoreValid == null || ignoreValid == undefined ? false : !!ignoreValid;
        if (!ignoreValid && !$.array.contains($.parser.plugins, plugin))
            $.error($.string.format($.easyui.isComponent.defaults.errorMessage, plugin));

        var t = $(selector);
        if (!t.length)
            return false;

        var state = $.data(t[0], plugin);
        return state && state.options ? true : false;
    };

    // $.easyui.isComponent 方法的默认参数格式
    $.easyui.isComponent.defaults = {
        errorMessage: "传入的参数 plugin: {0} 不是 easyui 插件名。"
    };



    $.extend({

        // 判断传入的 jQuery 对象或 HTML DOM 对象是否为已初始化的 jQuery EasyUI 组件；该方法定义如下参数：
        //      selector: jQuery 对象选择器，或者 DOM 对象，或者 jQuery 对象均可；
        //      plugin  : 要判断的插件名称，例如 "panel"、"dialog"、"datagrid" 等；
        //      ignoreValid: 是否忽略检查 $.parser.plugins 数组中存在 plugin 指定的插件名；boolean 类型值；该参数可选，默认为 false；
        // 返回值：
        //      如果 selector 所表示的 jQuery 对象中的第一个 DOM 元素为 plugin 参数所示的 easyui 插件且已经被初始化，则返回 true，否则返回 false；
        //      如果 selector 或 plugin 中任意一参数为 null/undefined，或 selector 所示的 jQuery 选择器不包含任意 HTML DOM 对象，则直接返回 false；
        //      如果 plugin 所示的名称不是一个 jQuery EasyUI 组件（没有包含在 $.parser.plugins 数组中），则直接抛出异常。
        isEasyUiComponent: function (selector, plugin, ignoreValid) {
            return $.easyui.isComponent(selector, plugin, ignoreValid);
        }
    });

    $.fn.extend({

        // 判断当前的 jQuery 对象中第一个 DOM 对象是否为已初始化的 jQuery EasyUI 组件；该方法定义如下参数：
        //      plugin  : 要判断的插件名称，例如 "panel"、"dialog"、"datagrid" 等；
        //      ignoreValid: 是否忽略检查 $.parser.plugins 数组中存在 plugin 指定的插件名；boolean 类型值；该参数可选，默认为 false；
        // 返回值：
        //      如果 jQuery 对象中的第一个 DOM 元素为 plugin 参数所示的 easyui 插件且已经被初始化，则返回 true，否则返回 false；
        //      如果 plugin 为 null/undefined，或 jQuery 选择器不包含任意 HTML DOM 对象，则直接返回 false；
        //      如果 plugin 所示的名称不是一个 jQuery EasyUI 组件（没有包含在 $.parser.plugins 数组中），则直接抛出异常。
        isEasyUiComponent: function (plugin, ignoreValid) {
            return $.easyui.isComponent(this, plugin, ignoreValid);
        },

        // 同方法 $.fn.isEasyUiComponent
        isEasyUI: function (plugin, ignoreValid) {
            return $.easyui.isComponent(this, plugin, ignoreValid);
        },



        currentDraggable: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("draggable", true);
            });
        },

        currentDroppable: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("droppable", true);
            });
        },

        currentResizable: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("resizable", true);
            });
        },

        currentPagination: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("pagination", true);
            });
        },

        //currentSearchbox: function () { },

        currentProgressbar: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("progressbar", true);
            });
        },

        currentTooltip: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("tooltip", true);
            });
        },


        currentPanel: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("panel", true);
            });
        },

        currentTabs: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("tabs", true);
            });
        },

        currentTabPanel: function () {
            return $.util.closest(this, function () {
                if (!this.isEasyUiComponent("panel", true)) {
                    return false;
                }
                var panel = this.panel("panel"), panels = panel.parent(), container = panels.parent();
                return panels.is(".tabs-panels") && container.is(".tabs-container");
            });
        },

        currentTabIndex: function () {
            var p = this.currentTabPanel();
            return p.length ? p.panel("panel").index() : -1;
        },

        currentAccordion: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("accordion", true);
            });
        },

        currentAccPanel: function () {
            return $.util.closest(this, function () {
                if (!this.isEasyUiComponent("panel", true) || !this.is(".accordion-body")) {
                    return false;
                }
                var panel = this.panel("panel"), accordion = panel.parent();
                return accordion.isEasyUiComponent("accordion", true);
            });
        },

        currentAccIndex: function () {
            var p = this.currentAccPanel();
            return p.length ? p.panel().index() : -1;
        },

        currentLayout: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("layout", true);
            });
        },

        currentRegionPanel: function () {
            return $.util.closest(this, function () {
                if (!this.isEasyUiComponent("panel", true) || !this.is(".layout-body")) {
                    return false;
                }
                var panel = this.panel("panel");
                if (!panel.is(".layout-panel")) {
                    return false;
                }
                var layout = panel.parent();
                return layout.isEasyUiComponent("layout", true);
            });
        },

        currentPanelRegion: function () {
            var p = this.currentRegionPanel();
            if (!p.length) {
                return null;
            }
            var panel = p.panel("panel");
            if (panel.is(".layout-panel-north")) {
                return "north";
            }
            if (panel.is(".layout-panel-south")) {
                return "south";
            }
            if (panel.is(".layout-panel-west")) {
                return "west";
            }
            if (panel.is(".layout-panel-east")) {
                return "east";
            }
            if (panel.is(".layout-panel-center")) {
                return "center";
            }
            return null;
        },


        //currentMenu: function () { },

        currentLinkbutton: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("linkbutton", true);
            });
        },

        currentMenubutton: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("menubutton", true);
            });
        },

        currentSplitbutton: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("splitbutton", true);
            });
        },


        currentForm: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("form", true);
            });
        },

        currentCalendar: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("calendar", true);
            });
        },

        //currentSlider: function () { },


        currentWindow: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("window", true);
            });
        },

        currentDialog: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("dialog", true);
            });
        },


        currentDatagrid: function () {
            var grid = null,
                p = $.util.closest(this, function () {
                    if (!this.isEasyUiComponent("panel", true) || !this.is(".datagrid-wrap")) {
                        return false;
                    }
                    grid = this.find(">.datagrid-view>:eq(2)");
                    return grid.isEasyUiComponent("datagrid", true);
                });
            return p.length && grid && grid.length ? grid : p;
        },

        currentPropertygrid: function () {
            var grid = null,
                p = $.util.closest(this, function () {
                    if (!this.isEasyUiComponent("panel", true) || !this.is(".datagrid-wrap")) {
                        return false;
                    }
                    grid = this.find(">.datagrid-view>:eq(2)");
                    return grid.isEasyUiComponent("propertygrid", true);
                });
            return p.length && grid && grid.length ? grid : p;
        },

        currentTree: function () {
            return $.util.closest(this, function () {
                return this.isEasyUiComponent("tree", true);
            });
        },

        currentTreegrid: function () {
            var grid = null,
                p = $.util.closest(this, function () {
                    if (!this.isEasyUiComponent("panel", true) || !this.is(".datagrid-wrap")) {
                        return false;
                    }
                    grid = this.find(">.datagrid-view>:eq(2)");
                    return grid.isEasyUiComponent("treegrid", true);
                });
            return p.length && grid && grid.length ? grid : p;
        }
    });



    //  更改 jQuery.ajax 函数的部分默认属性。
    //$.ajaxSetup({
    //    dataFilter: function (data, type) {
    //        return String(type).toLowerCase(type) == "json" ? $.string.toJSONString(data) : data;
    //    }
    //    ,beforeSend: function (XMLHttpRequest) {
    //        $.easyui.loading({ msg: "正在将请求数据发送至服务器..." });
    //    }
    //    ,complete: function (XMLHttpRequest, textStatus) {
    //        $.easyui.loaded();
    //    }
    //});

})(jQuery);