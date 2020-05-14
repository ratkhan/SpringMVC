package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.UUID;

@Component
public class InvoiceService {

    @Value("${cdn.url}")
    private String cdnUrl;
    private JdbcTemplate jdbcTemplate;

    //protected List<Invoice> invoices = new CopyOnWriteArrayList<>();
    protected Path s3Template;
    private UserService userService; //

    @PostConstruct
    public void init(){
        System.out.println("Fetching S3 PDF Template...");
        s3Template = null;
    }

    @PreDestroy
    public void shutdown(){
        System.out.println("Deleting S3 PDF template...");
        if (s3Template != null){
            try {
                Files.delete(s3Template);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Autowired
    public InvoiceService(UserService userService, JdbcTemplate jdbcTemplate) { //

        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Invoice> findAll() {
        return jdbcTemplate.query("select id, user_id, pdf_url, amount from invoices",
                (resultSet, rowNum) -> {
                    Invoice invoice = new Invoice();
                    invoice.setId(resultSet.getObject("id").toString());
                    invoice.setPdfUrl(resultSet.getString("pdf_url"));
                    invoice.setUserId(resultSet.getString("user_id"));
                    invoice.setAmount(resultSet.getInt("amount"));
                    return invoice;
                });
    }

    public Invoice create(String userId, Integer amount) {
            String staticPdfUrl = "https://www.example.com/some.pdf";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
               PreparedStatement ps = connection.prepareStatement(
                       "insert into invoices(user_id, pdf_url, amount) values (?,?,?)",
                       Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, userId);
               ps.setString(2, staticPdfUrl);
               ps.setInt(3, amount);
               return ps;

            }, keyHolder);

            UUID uuid = (UUID) keyHolder.getKeys().values().iterator().next();

            Invoice invoice = new Invoice();
            invoice.setId(uuid.toString());
            invoice.setPdfUrl(staticPdfUrl);
            invoice.setAmount(amount);
            invoice.setUserId(userId);
            return invoice;

    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
}

