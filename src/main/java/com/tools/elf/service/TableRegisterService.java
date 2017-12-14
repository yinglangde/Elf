package com.tools.elf.service;

import com.tools.elf.bean.TableProperty;
import com.tools.elf.bean.TableRegisterBean;

/**
 * @author
 * @create 2017-12-07 15:28
 **/
public interface TableRegisterService {

    Object registerTable(TableRegisterBean bean);//创建表

    Object editTableProperty(TableProperty tableProperty);//编辑表属主，注释，有效时长等

    //Object editTableDDL();//编辑字段，分区

    //Object deleteTable(); // 关闭表
    //Object showTable();
}
