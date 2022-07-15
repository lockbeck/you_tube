package com.company.controller;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.report.ReportDTO;
import com.company.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Report Controller")
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;
    @ApiOperation(value = "Create", notes = "Method to create report")
    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody @Valid ReportDTO dto) {
        log.info("Request to create tag{}", dto);
        String response = reportService.create(dto);
        return ResponseEntity.ok(response);
    }

//    @ApiOperation(value = "Update tag", notes = "Method to update tag(only ADMIN)")
//    @GetMapping("/pagination")
//    public ResponseEntity<PageImpl> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                  @RequestParam(value = "size", defaultValue = "5") int size) {
//        PageImpl response = reportService.pagination(page, size);
//        return ResponseEntity.ok().body(response);
//    }

    @ApiOperation(value = "Delete report", notes = "Method to delete report(only ADMIN)")
    @GetMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        log.info("Request to delete tag{}", id);
        String response = reportService.delete(id);
        return ResponseEntity.ok(response);
    }
    @ApiOperation(value = "REPORT LIST pagination", notes = "Method to get all report list as pagination (only ADMIN)")
    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl> pagination(@RequestParam(value = "page",defaultValue = "0")int page,
                                        @RequestParam(value = "size",defaultValue = "0")int size) {
        log.info("Request to get pagination list of reports ");
        PageImpl response = reportService.pagination(page ,size);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "REPORT LIST by profile id", notes = "Method to get all report list by profile id(only ADMIN)")
    @PostMapping("/adm/{profileId}")
    public ResponseEntity<?> getReportsByUserId(@PathVariable("profileId") Integer profileId) {
        log.info("Request to get pagination list of reports ");
        List<ReportDTO> response = reportService.getReportsByProfileId(profileId);
        return ResponseEntity.ok(response);
    }
}
