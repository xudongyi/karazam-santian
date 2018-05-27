/**
 * Created by suhao on 2017/6/5.
 */
var postsConfig = {
    contentUrlPrefix : ctx + '/' + contentUrlPrefix,
    taxonomyUrlPrefix : ctx + '/' + taxonomyUrlPrefix
};

var CMS = {
    getTaxonomy: function (type, slug, whois, callback) {
        var url = postsConfig.taxonomyUrlPrefix + '/' + type + '/' + slug + '/json';
        $.getJSON(url, {whois:whois},
            function (res) {
                if (undefined == callback) {
                    layer.msg('没有回调处理');
                    return;
                }
                if (typeof(callback) != 'function') {
                    layer.msg('回调处理不正确');
                    return;
                }
                if (res.success) {
                    callback(res.data);
                }
            }
        );
    },
    getTaxonomies: function (type, slug, callback) {
        var url = postsConfig.taxonomyUrlPrefix + '/' + type + '/' + slug + '/json';
        $.getJSON(url, {},
            function (res) {
                if (undefined == callback) {
                    layer.msg('没有回调处理');
                    return;
                }
                if (typeof(callback) != 'function') {
                    layer.msg('回调处理不正确');
                    return;
                }
                if (res.success) {
                    callback(res.data);
                }
            }
        );
    },
    getContents: function (type, slug, callback) {
        var url = postsConfig.taxonomyUrlPrefix + '/' + type + '/' + slug + '/contents.json';
        $.getJSON(url, {},
            function (res) {
                if (undefined == callback) {
                    layer.msg('没有回调处理');
                    return;
                }
                if (typeof(callback) != 'function') {
                    layer.msg('回调处理不正确');
                    return;
                }
                if (res.success) {
                    callback(res.data, url);
                }
            }
        );
    },
    getContentsUnder: function (type, slug, callback) {
        var url = postsConfig.taxonomyUrlPrefix + '/' + type + '/' + slug + '/conts.json';
        $.getJSON(url, {whois:'children'},
            function (res) {
                if (undefined == callback) {
                    layer.msg('没有回调处理');
                    return;
                }
                if (typeof(callback) != 'function') {
                    layer.msg('回调处理不正确');
                    return;
                }
                if (res.success) {
                    callback(res.data, url);
                }
            }
        );
    },
    getContent: function (slug, callback) {
        var url = postsConfig.contentUrlPrefix + '/' + slug + '/json';
        $.getJSON(url, {},
            function (res) {
                if (undefined == callback) {
                    layer.msg('没有回调处理');
                    return;
                }
                if (typeof(callback) != 'function') {
                    layer.msg('回调处理不正确');
                    return;
                }
                if (res.success) {
                    callback(res.data);
                }
            }
        );
    },
    getContentById: function (contentId, callback) {
        var url = postsConfig.contentUrlPrefix + '/' + contentId + '.json';
        $.getJSON(url, {},
            function (res) {
                if (undefined == callback) {
                    layer.msg('没有回调处理');
                    return;
                }
                if (typeof(callback) != 'function') {
                    layer.msg('回调处理不正确');
                    return;
                }
                if (res.success) {
                    callback(res.data);
                }
            }
        );
    },
    addViewCount: function (contentId) {
        var url = postsConfig.contentUrlPrefix + '/addViewCount';
        $.getJSON(url, {contentId:contentId});
    }
};