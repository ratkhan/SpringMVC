package com.web;

import com.Invoice;
import com.InvoiceService;
import com.InvoiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.util.List;

@RestController
@Validated
public class RestSpringController {

    private InvoiceService invoiceService;

    @Autowired
    public RestSpringController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    // equals to @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<Invoice> invoices() {
        return invoiceService.findAll();
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestParam @NotBlank String userId,
                                 @RequestParam @Min(10) @Max(50) Integer amount){
        return invoiceService.create(userId, amount);
    }

    @PostMapping("/invoices2")
    public Invoice createInvoice2(@RequestBody @Valid InvoiceVO invoiceVO){
        return invoiceService.create(invoiceVO.getUserId(), invoiceVO.getAmount());
    }


}
