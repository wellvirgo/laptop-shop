package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadFileService;

@Controller
public class ProductController {
    private final UploadFileService uploadFileService;
    private final ProductService productService;

    public ProductController(UploadFileService uploadFileService, ProductService productService) {
        this.uploadFileService = uploadFileService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Product> productList = this.productService.getProducts(pageable);
        List<Product> products = productList.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productList.getTotalPages());
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(Model model, @ModelAttribute(name = "newProduct") @Valid Product newProduct,
            BindingResult newProductBindingResult, @RequestParam("productFile") MultipartFile file) {
        List<FieldError> errList = newProductBindingResult.getFieldErrors();
        for (FieldError err : errList) {
            System.out.println(err.getField() + " -- " + err.getDefaultMessage());
        }
        if (!errList.isEmpty()) {
            return "admin/product/create";
        } else {
            String productImg = this.uploadFileService.handleSaveUploadFile(file, "products");
            newProduct.setImage(productImg);
            this.productService.handleSaveProduct(newProduct);
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String viewDetailProduct(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.getProduct(id);
        model.addAttribute("currentProduct", currentProduct);
        model.addAttribute("id", id);
        return "admin/product/view";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdatePage(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.getProduct(id);
        model.addAttribute("id", id);
        model.addAttribute("currentProduct", currentProduct);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute(name = "currentProduct") @Valid Product currentProduct,
            BindingResult bindingResult, @RequestParam(name = "productFile") MultipartFile file) {
        List<FieldError> errorList = bindingResult.getFieldErrors();
        for (FieldError error : errorList) {
            System.out.println(error.getField() + " -- " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        } else {
            Product updatedProduct = this.productService.getProduct(currentProduct.getId());
            if (!file.isEmpty()) {
                String productImg = this.uploadFileService.handleSaveUploadFile(file, "products");
                updatedProduct.setImage(productImg);
            }
            if (currentProduct != null) {
                updatedProduct.setName(currentProduct.getName());
                updatedProduct.setPrice(currentProduct.getPrice());
                updatedProduct.setDetailDesc(currentProduct.getDetailDesc());
                updatedProduct.setShortDesc(currentProduct.getShortDesc());
                updatedProduct.setQuantity(currentProduct.getQuantity());
                updatedProduct.setFactory(currentProduct.getFactory());
                updatedProduct.setTarget(currentProduct.getTarget());
                this.productService.handleSaveProduct(updatedProduct);
            }
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeletePage(@PathVariable long id, Model model) {
        Product currentProduct = this.productService.getProduct(id);
        model.addAttribute("id", id);
        model.addAttribute("currentProduct", currentProduct);

        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@ModelAttribute(name = "currentProduct") Product currentProduct) {
        this.productService.handleDeleteProduct(currentProduct);
        return "redirect:/admin/product";
    }
}
