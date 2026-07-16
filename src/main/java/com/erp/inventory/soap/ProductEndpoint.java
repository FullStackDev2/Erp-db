package com.erp.inventory.soap;

import com.erp.inventory.entity.Product;
import com.erp.inventory.service.ProductService;
import com.erp.inventory.soap.generated.GetProductRequest;
import com.erp.inventory.soap.generated.GetProductResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://erp.example.com/products";

    private final ProductService productService;

    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        Product product = productService.findBySku(request.getSku());

        GetProductResponse response = new GetProductResponse();
        response.setSku(product.getSku());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        return response;
    }
}