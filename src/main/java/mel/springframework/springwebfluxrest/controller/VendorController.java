package mel.springframework.springwebfluxrest.controller;

import mel.springframework.springwebfluxrest.domain.Category;
import mel.springframework.springwebfluxrest.domain.Vendor;
import mel.springframework.springwebfluxrest.repository.CategoryRepository;
import mel.springframework.springwebfluxrest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController
{
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
    @GetMapping("/api/v1/vendors")
    Flux<Vendor> list()
    {
        return vendorRepository.findAll();
    }
    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id)
    {
        return vendorRepository.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream)
    {
        return vendorRepository.saveAll(vendorStream).then();
    }
    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor)
    {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }
}
