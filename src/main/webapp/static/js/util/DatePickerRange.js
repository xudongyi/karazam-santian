/**
 * Created by limat on 2017/4/19.
 */
function DatePickerInput(option) {
    var _this = this;

    this.$container = document.getElementById(option.container); //日历容器  // !!必须
    this.beginDayInWeek = option.beginDayInWeek || 0; //0-6起始天，0为周日

    //显示的两个Input控件 **新增
    this.$startInput = option.startInput ? document.getElementById(option.startInput) : null; // !!必须
    this.$endInput = option.endInput ? document.getElementById(option.endInput) : null; // !!必须

    //当前激活input
    this.$activeInput = null;

    //存放选中Span  **新增
    this.startSpan = null;
    this.endSpan = null;
    this.allSpan = []; //Array

    this.activeDom = ''; //所在月份
    this.prevDom = ''; //往前一个月
    this.nextDom = ''; //往后一个月

    this.speed = option.speed || 500; //移动速度 ms
    this.timer = null; //定时器

    //时间 -> object
    this.startDate = null; //选中的开始时间 **新增
    this.endDate = null; //选中的结束时间 **新增
    this.curDate = {}; //今天所在月份
    this.slideDate = {}; //显示层所在月份,用来切换的日期

    this.monthStyle = ''; //特殊样式

    this.selectStyle = 'select'//选中样式
    this.rangeStyle = 'range'//范围样式
    this.disableStyle = 'disable'//不可选样式

    //切换日期触发函数
    this.changeDate = option.changeDate || function() {
            this.canChange = true;
            this.render(this.slideDate.year, this.slideDate.month);
        };

    this.renderdata = []; //当月数据

    this.canChange = true; //切换锁，true -> 打开，可切换， false -> 关闭，不可切换

    this.init(option.data || null);
}

DatePickerInput.prototype = {
    //初始化
    init: function(data) {
        var _this = this;

        //获取时间
        var d = new Date()

        //设置时间
        this.curDate.day = this.slideDate.day = d.getDate();
        this.curDate.month = this.slideDate.month = d.getMonth() + 1;
        this.curDate.year = this.slideDate.year = d.getFullYear();

        //创建日历容器
        this.datepicker = document.createElement('div');
        this.datepicker.className = 'paituodai-datepicker';
        this.datepicker.style.width = '100%';
        this.datepicker.style.height = '100%';
        this.datepicker.style.position = 'relative';
        this.datepicker.style.overflow = 'hidden';

        //给input绑定focus事件
        if (document.addEventListener) { //所有主流浏览器，除了 IE 8 及更早 IE版本
            _this.$startInput.addEventListener("focus", function() {
                _this.$activeInput = _this.$startInput;
                _this.render(_this.slideDate.year, _this.slideDate.month);
            });
            _this.$endInput.addEventListener("focus", function() {
                _this.$activeInput = _this.$endInput;
                _this.render(_this.slideDate.year, _this.slideDate.month);
            });
        } else if (document.attachEvent) { // IE 8 及更早 IE 版本
            _this.$startInput.attachEvent("onfocus", function() {
                _this.$activeInput = _this.$startInput;
                _this.render(_this.slideDate.year, _this.slideDate.month);
            });
            _this.$endInput.attachEvent("onfocus", function() {
                _this.$activeInput = _this.$endInput;
                _this.render(_this.slideDate.year, _this.slideDate.month);
            });
        }

        this.render(this.curDate.year, this.curDate.month, data);
    },

    //生成日历数组
    creatDayArray: function(year, month) {
        //日历数组
        var dayArr = [];

        var d = new Date(year, month - 1, 1);
        var weekindex = d.getDay();

        var lenspace = weekindex - this.beginDayInWeek;
        var lenday = this.getDaysByYM(year, month);

        if (lenday + lenspace > 35) {
            this.monthStyle = 'specialMonth';
        } else {
            this.monthStyle = '';
        }

        //填充数组空位
        for (var i = 0; i < lenspace; i++) {
            dayArr.push('');
        }

        //填充数组日期
        for (var i = 0; i < lenday; i++) {
            dayArr.push(i + 1);
        }

        //console.log(dayArr)
        return dayArr;
    },

    //创建 月 View
    creatViewMonth: function(year, month, data) {
        var datepickerMonth = document.createElement('div');
        datepickerMonth.className = 'paituodai-datepicker-month';
        datepickerMonth.style.width = '100%';
        datepickerMonth.style.height = '100%';
        datepickerMonth.style.position = 'absolute';

        var dayArr = this.creatDayArray(year, month);

        var week = '<div class="paituodai-datepicker-week">' +
            '<span>日</span>' +
            '<span>一</span>' +
            '<span>二</span>' +
            '<span>三</span>' +
            '<span>四</span>' +
            '<span>五</span>' +
            '<span>六</span>' +
            '</div>';
        var dayBox = '<div class="paituodai-datepicker-day">';

        this.renderdata = data ? data.slice(0) : [];
        var dataLen = this.renderdata.length;

        var len = dayArr.length;
        for (var i = 0; i < len; i++) {
            if (dayArr[i]) {

                //是否有数据
                var ifhasDate = '';

                //数据索引
                var dataIndex = 'data-index=';

                if (dataLen > 0) {
                    for (var j = 0; j < dataLen; j++) {
                        if (this.renderdata[j].day == dayArr[i]) {
                            ifhasDate = 'hasdata';
                            dataIndex += '"' + j + '"';
                            break;
                        }
                    }
                }

                //是否是今天
                var iftoday = dayArr[i] == this.curDate.day && this.curDate.year == year && this.curDate.month == month;
                var todayClass = iftoday ? 'today' : '';
                var daystr = dayArr[i];

                //class
                var spanClass = this.monthStyle;

                if (todayClass) {
                    spanClass += (' ' + todayClass);
                }

                if (ifhasDate) {
                    spanClass += (' ' + ifhasDate);
                }

                dayBox += '<span class="' + this.monthStyle + '" data-class="' + spanClass + '"' + dataIndex + '><i></i><a>' + daystr + '</a></span>';
            } else {
                dayBox += '<span class="' + this.monthStyle + '"></span>';
            }
        }
        dayBox += '</div>';

        datepickerMonth.innerHTML = this.createYearMonth(year, month) + week + dayBox;

        return datepickerMonth;
    },

    //生成日历View
    render: function(year, month, data) {
        var _this = this;

        _this.canChange = true;

        _this.activeDom = _this.creatViewMonthPro(year, month, data);
        _this.activeDom.style.left = '0';

        _this.prevDom = _this.creatPrevMonth();
        _this.prevDom.style.left = '-100%';

        _this.nextDom = _this.creatNextMonth();
        _this.nextDom.style.left = '100%';

        _this.datepicker.innerHTML = '';
        _this.datepicker.year = +this.slideDate.year;
        _this.datepicker.month = +this.slideDate.month;
        _this.datepicker.day = +this.slideDate.day;

        _this.datepicker.appendChild(_this.activeDom);
        _this.datepicker.appendChild(_this.prevDom);
        _this.datepicker.appendChild(_this.nextDom);

        _this.$container.appendChild(_this.datepicker);

        clearTimeout(_this.timer);

        _this.timer = setTimeout(function() {
            _this.showDayState();
        }, 500)

    },

    //显示day状态
    showDayState: function() {
        del_ff(this.activeDom);
        var daydivArr = this.activeDom.childNodes;
        var daydiv = daydivArr[1];
        del_ff(daydiv);

        var dayspan = daydiv.childNodes;
        var len = dayspan.length;
        for (var i = 0; i < len; i++) {
            var newclass = dayspan[i].getAttribute('data-class');
            if (newclass) {
                dayspan[i].className = newclass;
            }

        }

        function del_ff(elem) {
            var elem_child = elem.childNodes;
            for (var i = 0; i < elem_child.length; i++) {
                if (elem_child[i].nodeName == "#text" && !/\s/.test(elem_child.nodeValue)) {
                    elem.removeChild(elem_child)
                }
            }
        }
    },

    //给某个view渲染数据
    renderDate: function(view, date) {

    },

    //根据年月获取该月天数, month为正常月
    getDaysByYM: function(year, month) {
        var d = new Date(year, month, 0);
        return d.getDate();
    },

    //去某年某月View
    toNewYearAndMonth: function(year, month) {
        this.slideDate.month = month;
        this.slideDate.year = year;

        //锁住切换
        this.canChange = false;

        //更新数据
        this.changeDate && this.changeDate(this.slideDate.year, this.slideDate.month, this.render);
    },

    //前一月View
    creatPrevMonth: function() {
        var prevYear = this.slideDate.year;
        var prevMonth = this.slideDate.month - 1;
        var prevMonthDom = this.creatViewMonthPro(prevYear, prevMonth);

        return prevMonthDom;
    },

    //后一月View
    creatNextMonth: function() {
        var nextYear = this.slideDate.year;
        var nextMonth = this.slideDate.month + 1;
        var nextMonthDom = this.creatViewMonthPro(nextYear, nextMonth);

        return nextMonthDom;
    },

    //去前一月
    toPrevMonth: function() {
        var _this = this;

        if (!_this.canChange) {
            return;
        }

        //锁住切换
        this.canChange = false;

        clearTimeout(_this.timer);

        this.slideDate.month--;
        if (this.slideDate.month == 0) {
            this.slideDate.month = 12;
            this.slideDate.year--;
        }

        this.prevDom.style.transition = 'left ' + (this.speed / 1000) + 's ease 0s';
        this.prevDom.style.left = '0';

        this.activeDom.style.transition = 'left ' + (this.speed / 1000) + 's ease 0s';
        this.activeDom.style.left = '100%';

        this.timer = setTimeout(function() {

            _this.datepicker.removeChild(_this.nextDom);

            //重新设置 当月，下月
            var dom = _this.activeDom;
            _this.activeDom = _this.prevDom;
            _this.nextDom = dom;

            //重新设置 上月
            _this.prevDom = _this.creatPrevMonth();
            _this.prevDom.style.left = '-100%';
            _this.datepicker.appendChild(_this.prevDom);

            //更新数据
            _this.changeDate && _this.changeDate(_this.slideDate.year, _this.slideDate.month, _this.render);

        }, _this.speed);
    },

    //去下一月
    toNextMonth: function() {
        var _this = this;

        if (!_this.canChange) {
            return;
        }

        //锁住切换
        this.canChange = false;

        clearTimeout(_this.timer);

        this.slideDate.month++;
        if (this.slideDate.month == 13) {
            this.slideDate.month = 1;
            this.slideDate.year++;
        }

        this.nextDom.style.transition = 'left ' + (this.speed / 1000) + 's ease 0s';
        this.nextDom.style.left = '0';

        this.activeDom.style.transition = 'left ' + (this.speed / 1000) + 's ease 0s';
        this.activeDom.style.left = '-100%';

        this.timer = setTimeout(function() {

            _this.datepicker.removeChild(_this.prevDom);

            //重新设置 上月，当月
            var dom = _this.activeDom;
            _this.activeDom = _this.nextDom;
            _this.prevDom = dom;

            //重新设置 下月
            _this.nextDom = _this.creatNextMonth();
            _this.nextDom.style.left = '100%';
            _this.datepicker.appendChild(_this.nextDom);

            //更新数据
            _this.changeDate && _this.changeDate(_this.slideDate.year, _this.slideDate.month, _this.render);


        }, _this.speed);
    },

    //创建日历切换组
    createYearMonth: function(year, month) {
        var yearBox = '<div class="yearMonth"><span class="preYear">&lt;&lt;</span><span class="preMonth">&lt;</span><span class="nowYearMonth">' + year + '年' + month + '月</span><span class="nextYear">&gt;&gt;</span><span class="nextMonth">&gt;</span></div>';
        return yearBox;
    },

    //创建日历切换组 升级版
    createYearMonthPro: function(year, month) {
        var yearBox = '<div class="yearMonth"><span class="preYear">&lt;&lt;</span><span class="preMonth">&lt;</span><span class="nowYearMonth">' + year + '年' + month + '月</span><span class="nextYear">&gt;&gt;</span><span class="nextMonth">&gt;</span></div>';
        return this.parseDom(yearBox);
    },

    //创建 月 View 升级版
    creatViewMonthPro: function(year, month, data) {
        var _this = this;

        //创建月份容器div
        var datepickerMonth = document.createElement('div');
        datepickerMonth.className = 'paituodai-datepicker-month';
        datepickerMonth.style.width = '100%';
        datepickerMonth.style.height = '100%';
        datepickerMonth.style.position = 'absolute';

        //获取该月的「天」数组
        var dayArr = this.creatDayArray(year, month);

        var week = '<div class="paituodai-datepicker-week">' +
            '<span>日</span>' +
            '<span>一</span>' +
            '<span>二</span>' +
            '<span>三</span>' +
            '<span>四</span>' +
            '<span>五</span>' +
            '<span>六</span>' +
            '</div>';

        var dayBox, //存放「天」的容器
            daySpan //「天」容器

        week = this.parseDom(week);
        dayBox = this.parseDom('<div class="paituodai-datepicker-day"></div>');

        //把传过来的数组复制出来单独操作，不影响到原数组
        this.renderdata = data ? data.slice(0) : [];
        var dataLen = this.renderdata.length;

        var len = dayArr.length;
        for (var i = 0; i < len; i++) {
            if (dayArr[i]) {

                //是否有数据
                var ifhasDate = '';

                //数据索引
                var dataIndex = 'data-index=';

                if (dataLen > 0) {
                    for (var j = 0; j < dataLen; j++) {
                        if (this.renderdata[j].day == dayArr[i]) {
                            ifhasDate = 'hasdata';
                            dataIndex += '"' + j + '"';
                            break;
                        }
                    }
                }

                //是否是今天
                var iftoday = dayArr[i] == this.curDate.day && this.curDate.year == year && this.curDate.month == month;
                var todayClass = iftoday ? _this.selectStyle : '';
                var daystr = dayArr[i];

                //class
                var spanClass = this.monthStyle;

                if (todayClass) {
                    spanClass += (' ' + todayClass);
                }

                if (ifhasDate) {
                    spanClass += (' ' + ifhasDate);
                }

                daySpan = this.parseDom('<span class="' + this.monthStyle + '" data-class="' + spanClass + '"' + dataIndex + '><i></i><a>' + daystr + '</a></span>');
            } else {
                daySpan = this.parseDom('<span class="' + this.monthStyle + '"></span>');
            }

            //只把当前视图的月份的天绑定事件，然后插入数组
            if (year == _this.slideDate.year && month == _this.slideDate.month) {

                //给「天」绑定点击事件
                daySpan.onclick = function() {
                    if (_this.hasClassName(this, _this.disableStyle)) {
                        return;
                    }

                    //如果当前被激活的是 -> 开始时间的input
                    if (_this.$activeInput == _this.$startInput) {
                        if (_this.startSpan) {
                            _this.removeClassName(_this.startSpan, _this.selectStyle);
                        }
                        _this.startSpan = this;
                        _this.slideDate.day = this.innerText;
                        _this.$startInput.value = _this.slideDate.year + "-" + _this.slideDate.month + "-" + _this.slideDate.day;
                        _this.startDate = {};
                        _this.startDate.year = _this.slideDate.year;
                        _this.startDate.month = _this.slideDate.month;
                        _this.startDate.day = _this.slideDate.day;
                    } else {
                        if (_this.endSpan) {
                            _this.removeClassName(_this.endSpan, _this.selectStyle);
                        }
                        _this.endSpan = this;
                        _this.slideDate.day = this.innerText;
                        _this.$endInput.value = _this.slideDate.year + "-" + _this.slideDate.month + "-" + _this.slideDate.day;
                        _this.endDate = {};
                        _this.endDate.year = _this.slideDate.year;
                        _this.endDate.month = _this.slideDate.month;
                        _this.endDate.day = _this.slideDate.day;
                    }

                    _this.setDaySpanStyle();
                }

                _this.allSpan.push(daySpan);
            }

            dayBox.appendChild(daySpan);
        }

        _this.setDaySpanStyle();

        var yearMonthDom = this.createYearMonthPro(year, month)

        datepickerMonth.appendChild(yearMonthDom);
        datepickerMonth.appendChild(week);
        datepickerMonth.appendChild(dayBox);

        return datepickerMonth;
    },

    setDaySpanStyle: function() {
        var _this = this;
        var len = _this.allSpan.length;

        for (var i = 0; i < len; i++) {

            if (_this.startDate && _this.endDate && ((_this.startDate.year == _this.slideDate.year&&_this.startDate.month == _this.slideDate.month)&&(_this.endDate.year == _this.slideDate.year&&_this.endDate.month == _this.slideDate.month))) {
                if (_this.allSpan[i].innerText && +_this.allSpan[i].innerText > +_this.startDate.day && +_this.allSpan[i].innerText < +_this.endDate.day) {
                    _this.addClassName(_this.allSpan[i], _this.rangeStyle);
                }
            }else{
                if (_this.startDate && _this.endDate && ((_this.startDate.year == _this.slideDate.year&&_this.startDate.month == _this.slideDate.month))) {
                    if (_this.allSpan[i].innerText && +_this.allSpan[i].innerText > +_this.startDate.day) {
                        _this.addClassName(_this.allSpan[i], _this.rangeStyle);
                    }
                }

                if (_this.startDate && _this.endDate && ((_this.endDate.year == _this.slideDate.year&&_this.endDate.month == _this.slideDate.month))) {
                    if (_this.allSpan[i].innerText && +_this.allSpan[i].innerText < +_this.endDate.day) {
                        _this.addClassName(_this.allSpan[i], _this.rangeStyle);
                    }
                }

                if (_this.startDate && _this.endDate && ((_this.startDate.year <= _this.slideDate.year&&_this.startDate.month < _this.slideDate.month)||(_this.endDate.year >= _this.slideDate.year&&_this.endDate.month > _this.slideDate.month))) {
                    _this.addClassName(_this.allSpan[i], _this.rangeStyle);
                }
            }

            if (_this.startDate) {

                if (_this.$activeInput == _this.$endInput) {
                    if (_this.slideDate.year < _this.startDate.year ) {
                        _this.addClassName(_this.allSpan[i], _this.disableStyle);

                    }
                    if (_this.slideDate.year == _this.startDate.year && _this.slideDate.month < _this.startDate.month) {
                        _this.addClassName(_this.allSpan[i], _this.disableStyle);

                    }
                }

                if (_this.slideDate.year == _this.startDate.year && _this.slideDate.month == _this.startDate.month) {

                    if (_this.$activeInput == _this.$endInput) {
                        if (!_this.allSpan[i].innerText || +_this.allSpan[i].innerText < +_this.startDate.day) {
                            _this.addClassName(_this.allSpan[i], _this.disableStyle);
                        }
                    }

                    if (_this.allSpan[i].innerText == _this.startDate.day) {
                        _this.addClassName(_this.allSpan[i], _this.selectStyle);
                    }
                }
            }

            if (_this.endDate) {

                if (_this.$activeInput == _this.$startInput) {
                    if (_this.slideDate.year >= _this.endDate.year && _this.slideDate.month > _this.endDate.month) {
                        _this.addClassName(_this.allSpan[i], _this.disableStyle);
                    }
                }

                if (_this.slideDate.year == _this.endDate.year && _this.slideDate.month == _this.endDate.month) {
                    if (_this.$activeInput == _this.$startInput) {
                        if (!_this.allSpan[i].innerText || +_this.allSpan[i].innerText > +_this.endDate.day) {
                            _this.addClassName(_this.allSpan[i], _this.disableStyle);
                        }
                    }

                    if (_this.allSpan[i].innerText == _this.endDate.day) {
                        _this.addClassName(_this.allSpan[i], _this.selectStyle);
                    }
                }
            }
        }

    },

    /**
     * 以下是工具方法
     */

    //字符串标签转Dom
    parseDom: function(arg) {
        var objE = document.createElement("div");
        objE.innerHTML = arg;
        var dom = objE.childNodes;
        return dom[0];
    },
    //是否有这个类
    hasClassName: function(element, className) {
        if (!element) return;
        var elementClassName = element.className;
        if (elementClassName.length == 0) return false;
        //用正则表达式判断多个class之间是否存在真正的class（前后空格的处理）
        if (elementClassName == className || elementClassName.match(new RegExp("(^|\\s)" + className + "(\\s|$)")))
            return true;
        return false;
    },
    //增加类
    addClassName: function(element, className) {
        if (!element) return;
        var elementClassName = element.className;
        if (elementClassName.length == 0) {
            element.className = className;
            return;
        }
        if (elementClassName == className || elementClassName.match(new RegExp("(^|\\s)" + className + "(\\s|$)")))
            return;
        element.className = elementClassName + " " + className;
    },
    //删除类
    removeClassName: function(element, className) {
        if (!element) return;
        var elementClassName = element.className;
        if (elementClassName.length == 0) return;
        if (elementClassName == className) {
            element.className = "";
            return;
        }
        if (elementClassName.match(new RegExp("(^|\\s)" + className + "(\\s|$)")))
            element.className = elementClassName.replace((new RegExp("(^|\\s)" + className + "(\\s|$)")), " ");
    }
};
