/*
 * Copyright 2014-2015 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 *
 * JavaScript - jQuery Validate Method
 * Version: 3.0
 */
jQuery.validator.addMethod("decimal", function(value, element, param) {
	return this.optional(element) || new RegExp("^-?\\d{1," + (param.integer != null ? param.integer : "") + "}" + (param.fraction != null ? (param.fraction > 0 ? "(\\.\\d{1," + param.fraction + "})?$" : "$") : "(\\.\\d+)?$")).test(value);
}, "numeric value out of bounds");
jQuery.validator.addMethod("pattern", function(value, element, param) {
	return this.optional(element) || param.test(value);
}, "Invalid format");
jQuery.validator.addMethod("extension", function(value, element, param) {
	return this.optional(element) || ($.trim(param) != "" && new RegExp("^\\S.*\\.(" + param.replace(/,/g, "|") + ")$", "i").test(value));
}, "Invalid extension");
jQuery.validator.addMethod("zipCode", function(value, element) {
	return this.optional(element) || /^[0-9]{6}$/.test(value);
}, "邮政编码格式错误");
jQuery.validator.addMethod("imageFile", function(value, element) {
	return this.optional(element) || /(.jpg|.jpeg|.gif|.bmp|.png)$/i.test(value);
}, "图片文件格式错误");
jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	return this.optional(element) || value != $(param).val();
}, "必须为不相同的字符");
jQuery.validator.addMethod("positive", function(value, element) {
	return this.optional(element) || /^(([1-9]\d*)|(0.\d+)|([1-9]\d*.\d+))$/.test(value);
}, "必须为正数");
jQuery.validator.addMethod("negative", function(value, element) {
	return this.optional(element) || /^((-[1-9]\d*)|(-0.\d+)|(-[1-9]\d*.\d+))$/.test(value);
}, "必须为负数");
jQuery.validator.addMethod("nonnegativeNumber", function(value, element) {
	return this.optional(element) || /^((0)|([1-9]\d*)|(0.\d+)|([1-9]\d*.\d+))$/.test(value);
}, "必须为零或正数");
jQuery.validator.addMethod("integer", function(value, element) {
	return this.optional(element) || /^((-[1-9]\d*)|(0)|([1-9]\d*))$/.test(value);
}, "必须为整数");
jQuery.validator.addMethod("positiveInteger", function(value, element) {
	return this.optional(element) || /^([1-9]\d*)$/.test(value);
}, "必须为正整数");
jQuery.validator.addMethod("nonnegativeInteger", function(value, element) {
	return this.optional(element) || /^((0)|([1-9]\d*))$/.test(value);
}, "必须为零或正整数");
jQuery.validator.addMethod("multiples", function(value, element, param) {
	if(this.optional(element)) {
		return true;
	}
	switch(typeof param) {
		case "number": {
			return value % param == 0;
		}
		case "string": {
			var $target = $(param);
			if($target && $target.val() != "") {
				return value % $target.val() == 0;
			} else {
				return true;
			}
		}
		default: {
			return true;
		}
	}
}, "必须为倍数");
jQuery.validator.addMethod("aliquot", function(value, element, param) {
	if(this.optional(element)) {
		return true;
	}
	switch(typeof param) {
		case "number": {
			return param % value == 0;
		}
		case "string": {
			var $target = $(param);
			if($target && $target.val() != "") {
				return $target.val() % value == 0;
			} else {
				return true;
			}
		}
		default: {
			return true;
		}
	}
}, "必须能被整除");
jQuery.validator.addMethod("lessThan", function(value, element, param) {
	if(this.optional(element)) {
		return true;
	}
	switch(typeof param) {
		case "number": {
			return value < param;
		}
		case "string": {
			var $target = $(param);
			if($target && $target.val() != "") {
				return value < $target.val() || value * 1 < $target.val() * 1;
			} else {
				return true;
			}
		}
		default: {
			return true;
		}
	}
}, "必须小于");
jQuery.validator.addMethod("lessThanOrEqualTo", function(value, element, param) {
	if(this.optional(element)) {
		return true;
	}
	switch(typeof param) {
		case "number": {
			return value <= param;
		}
		case "string": {
			var $target = $(param);
			if($target && $target.val() != "") {
				return value <= $target.val() || value * 1 <= $target.val() * 1;
			} else {
				return true;
			}
		}
		default: {
			return true;
		}
	}
}, "必须小于或等于");
jQuery.validator.addMethod("greaterThan", function(value, element, param) {
	if(this.optional(element)) {
		return true;
	}
	switch(typeof param) {
		case "number": {
			return value > param;
		}
		case "string": {
			var $target = $(param);
			if($target && $target.val() != "") {
				return value > $target.val() || value * 1 > $target.val() * 1;
			} else {
				return true;
			}
		}
		default: {
			return true;
		}
	}
}, "必须大于");
jQuery.validator.addMethod("greaterThanOrEqualTo", function(value, element, param) {
	if(this.optional(element)) {
		return true;
	}
	switch(typeof param) {
		case "number": {
			return value >= param;
		}
		case "string": {
			var $target = $(param);
			if($target && $target.val() != "") {
				return value >= $target.val() || value * 1 >= $target.val() * 1;
			} else {
				return true;
			}
		}
		default: {
			return true;
		}
	}
}, "必须大于或等于");