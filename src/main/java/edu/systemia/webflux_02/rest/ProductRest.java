package edu.systemia.webflux_02.rest;

import edu.systemia.webflux_02.dto.ProductDTO;
import edu.systemia.webflux_02.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRest {

	private final ProductService productService;

	@GetMapping
	public Flux<ProductDTO> getProducts() {
		//		return ServerResponse.ok().body(products, ProductDTO.class);
		return productService.getProducts();
	}

	@GetMapping("/{product-id}")
	public Mono<ProductDTO> getProductByID(@PathVariable("product-id") String id) {
		return productService.getProductByID(id);
	}

	@GetMapping("/between-range")
	public Flux<ProductDTO> getProductByRange(@RequestParam double min, @RequestParam double max) {
		return productService.getProductInRange(min, max);
	}

	@PostMapping
	public Mono<ProductDTO> postCreateProduct(@RequestBody Mono<ProductDTO> dtoMono) {
		return productService.createProduct(dtoMono);
	}

	@PutMapping("/{product-id}")
	public Mono<ProductDTO> puUpdateProduct(
		@PathVariable("product-id") String id,
		@RequestBody Mono<ProductDTO> dtoMono
	) {
		return productService.updateProduct(id, dtoMono);
	}

	@DeleteMapping("/{product-id}")
	public Mono<Void> deleteRemoveProduct(@PathVariable("product-id") String id) {
		return productService.deleteProduct(id);
	}
}
