package com.tools.elf.controller.table;

import com.tools.elf.bean.TableRequestBody;
import com.tools.elf.service.TableRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bin.zhang
 * @create 2017-12-07 15:27
 **/
@Controller
@RequestMapping(value = "/table")
public class TableRegisterController {
    private Logger logger= LoggerFactory.getLogger(TableRegisterController.class);

    @Autowired
    private TableRegisterService impl;

    @RequestMapping(value = "/createTable")
    @ResponseBody
    public Object createTable(@RequestBody TableRequestBody tableRequestBody){
        return impl.createTable(tableRequestBody);
    }

}
