package com.tools.elf.controller.table;

import com.tools.elf.bean.TableColumn;
import com.tools.elf.bean.TableProperty;
import com.tools.elf.bean.TableRegisterBean;
import com.tools.elf.service.TableRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @RequestMapping(value = "/tableRegister" ,method = RequestMethod.POST)
    @ResponseBody
    public Object registerTable(@RequestBody TableRegisterBean tableRegisterBean){
        return impl.registerTable(tableRegisterBean);
    }

    @RequestMapping(value = "/editTableProperty" ,method = RequestMethod.POST)
    @ResponseBody
    public Object editTableProperty(@RequestBody TableProperty tableProperty){
        return impl.editTableProperty(tableProperty);
    }

    @RequestMapping(value = "/editColumnComment" ,method = RequestMethod.POST)
    @ResponseBody
    public Object editColumnComment(@RequestBody TableColumn column){
        return impl.editColumnComment(column);
    }

}
