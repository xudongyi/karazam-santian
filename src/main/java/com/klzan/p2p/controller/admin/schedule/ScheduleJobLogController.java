package com.klzan.p2p.controller.admin.schedule;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.model.ScheduleJobLog;
import com.klzan.p2p.service.schedule.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 定时任务日志
 */
@Controller
@RequestMapping("admin/scheduleLog")
public class ScheduleJobLogController extends BaseAdminController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	@RequiresPermissions("admin:schedule:log:list")
	public String index(Model model, HttpServletRequest request) {
		return TEMPLATE_ADMIN_PATH + "schedule/log/index";
	}

	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("list")
    @RequiresPermissions("admin:schedule:log:list")
    @ResponseBody
	public PageResult<ScheduleJobLog> list(PageCriteria criteria){
		return scheduleJobLogService.findJobLogPage(criteria);
	}

	@RequiresPermissions("admin:schedule:log:view")
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("jobLog", scheduleJobLogService.queryObject(id));
		model.addAttribute("action", "view");
		return TEMPLATE_ADMIN_PATH + "schedule/log/form";
	}

}
