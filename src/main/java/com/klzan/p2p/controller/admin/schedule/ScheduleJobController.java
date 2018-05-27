package com.klzan.p2p.controller.admin.schedule;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.model.ScheduleJob;
import com.klzan.p2p.service.schedule.ScheduleJobService;
import com.klzan.p2p.vo.schedule.ScheduleJobVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 定时任务
 */
@Controller
@RequestMapping("admin/schedule")
public class ScheduleJobController extends BaseAdminController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin:schedule:job:list")
    public String index(Model model, HttpServletRequest request) {
        return TEMPLATE_ADMIN_PATH + "schedule/index";
    }

    @RequiresPermissions("admin:schedule:job:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("job", scheduleJobService.queryObject(id));
        model.addAttribute("action", "update");
        return TEMPLATE_ADMIN_PATH + "schedule/form";
    }

    @RequiresPermissions("admin:schedule:job:view")
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("job", scheduleJobService.queryObject(id));
        model.addAttribute("action", "view");
        return TEMPLATE_ADMIN_PATH + "schedule/form";
    }

    /**
     * 定时任务列表
     */
    @RequestMapping("list")
    @RequiresPermissions("admin:schedule:job:list")
    @ResponseBody
    public PageResult<ScheduleJob> list(@RequestParam Map<String, Object> params, PageCriteria criteria) {
        return scheduleJobService.findJobPage(criteria);
    }

    /**
     * 保存定时任务
     */
    @RequestMapping("save")
    @RequiresPermissions("admin:schedule:job:save")
    @ResponseBody
    public Result save(@RequestBody ScheduleJob scheduleJob) {
        scheduleJobService.addJob(scheduleJob);
        return Result.success();
    }

    /**
     * 修改定时任务
     */
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @RequiresPermissions("admin:schedule:job:update")
    @ResponseBody
    public Result update(ScheduleJobVo scheduleJob) {
        scheduleJobService.updateJob(scheduleJob);
        return Result.success();
    }

    /**
     * 删除定时任务
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @RequiresPermissions("admin:schedule:job:delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);
        return Result.success();
    }

    /**
     * 立即执行任务
     */
    @RequestMapping(value = "run", method = RequestMethod.POST)
    @RequiresPermissions("admin:schedule:job:run")
    @ResponseBody
    public Result run(@RequestBody Integer[] jobIds) {
        scheduleJobService.run(jobIds);

        return Result.success();
    }

    /**
     * 暂停定时任务
     */
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    @RequiresPermissions("admin:schedule:job:pause")
    @ResponseBody
    public Result pause(@RequestBody Integer[] jobIds) {
        scheduleJobService.pause(jobIds);
        return Result.success();
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping(value = "resume", method = RequestMethod.POST)
    @RequiresPermissions("admin:schedule:job:resume")
    @ResponseBody
    public Result resume(@RequestBody Integer[] jobIds) {
        scheduleJobService.resume(jobIds);
        return Result.success();
    }

}
