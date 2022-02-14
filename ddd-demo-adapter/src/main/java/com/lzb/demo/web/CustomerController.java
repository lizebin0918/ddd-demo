package com.lzb.demo.web;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.lzb.demo.api.CustomerServiceI;
import com.lzb.demo.dto.CustomerAddCmd;
import com.lzb.demo.dto.CustomerListByNameQry;
import com.lzb.demo.dto.data.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @GetMapping(value = "/helloworld")
    public String helloWorld(){
        return "Hello, welcome to COLA world!";
    }

}
