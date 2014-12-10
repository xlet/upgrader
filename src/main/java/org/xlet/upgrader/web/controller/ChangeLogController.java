package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.service.ChangeLogService;
import org.xlet.upgrader.vo.PaginationRequest;
import org.xlet.upgrader.vo.dashboard.ChangeLogDTO;
import org.xlet.upgrader.vo.dashboard.form.ChangeLogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-16 上午10:40
 * Summary:
 */
@RestController
@RequestMapping("/api/v1/changelog")
public class ChangeLogController {

    @Autowired
    private ChangeLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ChangeLogDTO> getByVersion(@RequestParam(value = "versionId", required = false) Long versionId, UriComponentsBuilder builder) {
        return logService.getByVersion(builder.build().toUriString(), versionId);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createChangeLog(ChangeLogForm changelog, UriComponentsBuilder builder) {
        ChangeLogDTO dto = logService.save(changelog);
        Long id = dto.getId();
        URI uri = builder.path("/api/v1/changelog/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateChangeLog(ChangeLogForm changelog, @PathVariable("id") Long id) {
        logService.update(changelog, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChangeLog(@PathVariable("id") Long id) {
        logService.delete(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<ChangeLogDTO> listChangeLog(PaginationRequest request, UriComponentsBuilder builder) {
        return logService.list(builder.build().toUriString(), new PageRequest(request.getPage(), request.getSize()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ChangeLogDTO getChangeLog(@PathVariable("id") Long id, UriComponentsBuilder builder) {
        return logService.getChangeLog(builder.build().toUriString(), id);
    }
}
