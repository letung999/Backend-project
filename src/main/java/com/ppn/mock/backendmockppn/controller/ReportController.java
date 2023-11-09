package com.ppn.mock.backendmockppn.controller;


import com.ppn.mock.backendmockppn.jasper.JasperReportService;
import com.ppn.mock.backendmockppn.repository.PaymentRepository;
import com.ppn.mock.backendmockppn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JasperReportService jasperReportService;

    @GetMapping("user-report/{format}")
    public ResponseEntity<Resource> getItemReport(@PathVariable String format) {
        byte[] reportContent = jasperReportService.getUserReport(userRepository.findAll(), format);
        ByteArrayResource resource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("user-report." + format)
                                .build().toString())
                .body(resource);
    }

    @GetMapping("payment-report/{format}")
    public ResponseEntity<Resource> getPaymentReport(@PathVariable String format) {
        byte[] reportContent = jasperReportService.getPaymentReport(paymentRepository.findAll(), format);
        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("payment-report." + format)
                                .build().toString())
                .body(resource);
    }

}
