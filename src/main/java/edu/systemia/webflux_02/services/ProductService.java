package edu.systemia.webflux_02.services;

import edu.systemia.webflux_02.dto.ProductDTO;
import edu.systemia.webflux_02.repository.ProductRepository;
import edu.systemia.webflux_02.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public Flux<ProductDTO> getProducts() {
		return productRepository.findAll()
			.map(AppUtils::toDto);
	}

	public Mono<ProductDTO> getProductByID(String id) {
		return productRepository.findById(id)
			.map(AppUtils::toDto);
	}

	public Flux<ProductDTO> getProductInRange(double min, double max) {
		return productRepository.findAllByPriceBetween(Range.closed(min, max));
	}

	public Mono<ProductDTO> createProduct(Mono<ProductDTO> dtoMono) {
		return dtoMono.map(AppUtils::toEntity)
			.flatMap(productRepository::insert)
			.map(AppUtils::toDto);
	}

	public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> dtoMono) {

		return productRepository.findById(id)
			.flatMap(p -> dtoMono.map(AppUtils::toEntity)
				.doOnNext(e -> e.setId(id)))
			.flatMap(productRepository::save)
			.map(AppUtils::toDto);
	}

	public Mono<Void> deleteProduct(String id) {
		return productRepository.deleteById(id);
	}
}
