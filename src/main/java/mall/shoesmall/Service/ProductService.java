package mall.shoesmall.Service;


import lombok.RequiredArgsConstructor;

import mall.shoesmall.Model.Entity.Account;
import mall.shoesmall.Model.Entity.Address;
import mall.shoesmall.Model.Entity.Product;
import mall.shoesmall.Model.dto.AddressDto;
import mall.shoesmall.Model.dto.ProductDto;
import mall.shoesmall.Repository.AccountRepository;
import mall.shoesmall.Repository.AddressRepository;
import mall.shoesmall.Repository.ProductRepository;
import mall.shoesmall.Repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;
    private final SaleRepository saleRepository;

    @Transactional(readOnly = true)
    public List<ProductDto.response> searchList() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto.response> responseList =
                productList.stream()
                .map(ProductDto.response :: new)
                .collect(Collectors.toList());
        return responseList;
    }

    @Transactional(readOnly = true)
    public ProductDto.response find_product_info(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("등록된 상품이 아닙니다."));
        ProductDto.response response = new ProductDto.response(product);
        return response;
    }

    @Transactional(readOnly = true)
    public ProductDto.product_sell_final_response find_product_sell_final_info(Long productId, Long id) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalArgumentException("등록된 상품이 아닙니다."));
        List<Address> addressList = addressRepository.findAlLByUserId(id);
        List<AddressDto.response> address = addressList.stream()
                .map(AddressDto.response::new)
                .collect(Collectors.toList());
        Account account = accountRepository.findByUserId(id);
        ProductDto.product_sell_final_response response = new ProductDto.product_sell_final_response(product,address,account);
        return response;


    }


    public ProductDto.response find_product_buy_info(Long id, String size) {
        Product product = productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("등록된 상품이 아닙니다."));
        saleRepository.findFirstBySizeAndProductId(size,id);

        return null;
    }
}


