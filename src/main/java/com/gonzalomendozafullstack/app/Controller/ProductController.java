package com.gonzalomendozafullstack.app.Controller;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gonzalomendozafullstack.app.Service.ProductAsyncService;
import com.gonzalomendozafullstack.app.Service.ProductService;
import com.gonzalomendozafullstack.app.model.Product;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductAsyncService productAsyncService;
	
	
	
	@GetMapping
	public List<Product> getAllProducts() throws Exception{
		
		long startTime = System.currentTimeMillis();
		List<Product> list1 = productService.getProducts1();
		List<Product> list2 = productService.getProducts2();
		List<Product> list3 = productService.getProducts3();
		
		List<Product> lists = Stream.of(list1, list2, list3)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		long endTime = System.currentTimeMillis();
		log.info("Tiempo de ejecucion: {}", endTime - startTime);
		
		return lists;

	}
	
	//A sync Method
	@GetMapping("/async")
	public List<Product> getAllProductsAsync() throws Exception{
		CompletableFuture<List<Product>> c1 = productAsyncService.getProducts1();
		CompletableFuture<List<Product>> c2 = productAsyncService.getProducts2();
		CompletableFuture<List<Product>> c3 = productAsyncService.getProducts3();
		
		CompletableFuture.allOf(c1,c2,c3).join();
		
		List<Product> lists = Stream.of(c1.get(),c2.get(),c3.get())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		return lists;

	}

}
