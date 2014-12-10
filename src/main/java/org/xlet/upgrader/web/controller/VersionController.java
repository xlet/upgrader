package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.domain.VersionState;
import org.xlet.upgrader.service.VersionService;
import org.xlet.upgrader.vo.LatestVersion;
import org.xlet.upgrader.vo.PaginationRequest;
import org.xlet.upgrader.vo.Response;
import org.xlet.upgrader.vo.VersionVo;
import org.xlet.upgrader.vo.dashboard.VersionDTO;
import org.xlet.upgrader.vo.dashboard.form.VersionForm;
import org.xlet.upgrader.web.binder.VersionStateBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-2 下午5:15
 * Summary:
 */
@RestController
@RequestMapping("api/v1/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(VersionState.class, new VersionStateBinder());
    }

    @RequestMapping("/{product}/latest")
    public LatestVersion getLatest(@PathVariable("product") String product, @RequestParam(value = "state", required = false) VersionState state, UriComponentsBuilder builder) {
        String context = builder.build().toUriString();
        LatestVersion latestVersion = versionService.getLatestVersion(state, context, product);
        latestVersion.setUrl(context + "/api/v1/version.xml");
        return latestVersion;
    }

    @RequestMapping
    public Response<VersionVo> getChangeLogs(@RequestParam(value = "version") String version, @RequestParam(value = "state", required = false) VersionState state, @RequestParam(value = "product") String product, UriComponentsBuilder builder) {
        Response<VersionVo> response = versionService.checkUpdate(builder.build().toUriString(), state, version, product);
        return response.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<VersionDTO> getByProduct(@RequestParam(value = "productId", required = false) Long productId, UriComponentsBuilder builder) {
        return versionService.getByProduct(builder.build().toUriString(), productId);
    }

    @RequestMapping(value = "/ex", method = RequestMethod.GET)
    public Page<VersionDTO> listVersion(PaginationRequest request, UriComponentsBuilder builder) {
        return versionService.list(builder.build().toUriString(), new PageRequest(request.getPage(), request.getSize(), Sort.Direction.DESC, "version"));
    }


    @RequestMapping(value = "/ex/{id}", method = RequestMethod.GET)
    public VersionDTO getVersion(@PathVariable("id") Long id, UriComponentsBuilder builder) {
        return versionService.getVersion(builder.build().toUriString(), id);
    }

    @RequestMapping(value = "/ex", method = RequestMethod.POST)
    public ResponseEntity<?> createVersion(VersionForm version, UriComponentsBuilder builder) {
        VersionDTO dto = versionService.saveVersion(version);
        Long id = dto.getId();
        URI uri = builder.path("/api/v1/version/ex/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/ex/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersion(VersionForm version, @PathVariable("id") Long id) {
        versionService.update(version, id);
    }

    @RequestMapping(value = "/ex/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVersion(@PathVariable("id") Long id) {
        versionService.delete(id);
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Response<?> check(@RequestParam("productId") Long productId, @RequestParam("version") String version) {
        Response response = new Response();
        return response.success(versionService.check(productId, version));
    }

    @RequestMapping(value = "/ex/state/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVersionState(VersionState state, @PathVariable("id") Long id) {
        versionService.update(state, id);
    }

}
