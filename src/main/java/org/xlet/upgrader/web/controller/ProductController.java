package org.xlet.upgrader.web.controller;

import org.xlet.upgrader.domain.Product;
import org.xlet.upgrader.service.ProductService;
import org.xlet.upgrader.vo.PaginationRequest;
import org.xlet.upgrader.vo.Response;
import org.xlet.upgrader.vo.dashboard.ProductDTO;
import org.xlet.upgrader.vo.dashboard.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 上午11:09
 * Summary:
 */
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<ProductDTO> list(PaginationRequest request){
        return productService.list(new PageRequest(request.getPage(), request.getSize()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductDTO get(@PathVariable("id") Long id){
        ProductDTO product = productService.getProduct(id);
        return product;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ProductDTO> all(){
        return productService.all();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@Valid Product product, UriComponentsBuilder uriComponentsBuilder){
        productService.saveProduct(product);

        Long id = product.getId();
        URI uri = uriComponentsBuilder.path("/api/v1/product/"+ id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@Valid ProductForm product, @PathVariable("id") Long id){
        productService.updateProduct(product, id);
    }

    @RequestMapping(value="/check", method = RequestMethod.POST)
    public Response<?> checkCode(@RequestParam(value="code")String code){
        return new Response().success(productService.checkCode(code));
    }






}
