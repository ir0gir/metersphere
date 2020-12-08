package io.metersphere.api.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.metersphere.api.dto.automation.ApiScenarioDTO;
import io.metersphere.api.dto.automation.ApiScenarioRequest;
import io.metersphere.api.dto.automation.RunScenarioRequest;
import io.metersphere.api.dto.automation.SaveApiScenarioRequest;
import io.metersphere.api.dto.definition.RunDefinitionRequest;
import io.metersphere.api.service.ApiAutomationService;
import io.metersphere.base.domain.ApiScenario;
import io.metersphere.base.domain.Schedule;
import io.metersphere.commons.constants.RoleConstants;
import io.metersphere.commons.utils.PageUtils;
import io.metersphere.commons.utils.Pager;
import io.metersphere.commons.utils.SessionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.List;

@RestController
@RequestMapping(value = "/api/automation")
@RequiresRoles(value = {RoleConstants.TEST_MANAGER, RoleConstants.TEST_USER}, logical = Logical.OR)
public class ApiAutomationController {

    @Resource
    ApiAutomationService apiAutomationService;

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<ApiScenarioDTO>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ApiScenarioRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        request.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        return PageUtils.setPageInfo(page, apiAutomationService.list(request));
    }

    @PostMapping(value = "/create")
    public void create(@RequestPart("request") SaveApiScenarioRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        apiAutomationService.create(request, bodyFiles);
    }

    @PostMapping(value = "/update")
    public void update(@RequestPart("request") SaveApiScenarioRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        apiAutomationService.update(request, bodyFiles);
    }

    @PostMapping(value = "/schedule/update")
    public void updateSchedule(@RequestBody Schedule schedule) {
        apiAutomationService.updateSchedule(schedule);
    }

    @PostMapping(value = "/schedule/create")
    public void createSchedule(@RequestBody Schedule schedule) {
        apiAutomationService.createSchedule(schedule);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        apiAutomationService.delete(id);
    }

    @PostMapping("/deleteBatch")
    public void deleteBatch(@RequestBody List<String> ids) {
        apiAutomationService.deleteBatch(ids);
    }

    @PostMapping("/removeToGc")
    public void removeToGc(@RequestBody List<String> ids) {
        apiAutomationService.removeToGc(ids);
    }

    @GetMapping("/getApiScenario/{id}")
    public ApiScenario getScenarioDefinition(@PathVariable String id) {
        return apiAutomationService.getApiScenario(id);
    }

    @PostMapping("/getApiScenarios")
    public List<ApiScenario> getApiScenarios(@RequestBody List<String> ids) {
        return apiAutomationService.getApiScenarios(ids);
    }

    @PostMapping(value = "/run/debug")
    public void runDebug(@RequestPart("request") RunDefinitionRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        apiAutomationService.run(request, bodyFiles);
    }

    @PostMapping(value = "/run")
    public void run(@RequestBody RunScenarioRequest request) {
        apiAutomationService.run(request);
    }

    @PostMapping("/getReference")
    public List<ApiScenario> getReference(@RequestBody ApiScenarioRequest request) {
        return apiAutomationService.getReference(request);
    }

}

