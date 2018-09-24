package cn.edu.ldxy.data.department;

import cn.edu.ldxy.department.domain.Department;
import cn.edu.ldxy.department.service.DepartmentService;
import cn.edu.ldxy.department.service.impl.DepartmentServiceImpl;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.rmi.RemoteException;

/**
 * @Author: Kooo
 * @Date: Created in 2018/9/19
 * @Modified By:
 * @Decription:
 */
public class DepartmentData {




    public static void  main(String args[])
    {


       DepartmentServiceImpl departmentService = new DepartmentServiceImpl();
        //read excel info
        int i;
        Sheet sheet;
        Workbook book;
        Cell username, userId, fee;

        try {
            // t.xls为要读取的excel文件名
            book = Workbook.getWorkbook(new File(
                    "D:\\sudytech.com\\组织机构信息(WPS).xls"));

            // 获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet = book.getSheet(0);
            // 获取左上角的单元格 第一列 第三行
            username = sheet.getCell(0, 1);
            System.out.println("开始读取数据........" + username);

            i = 0;
            int sum =0;
            while (i< 8 ) {


                Department department = new Department();
                department.setCode(sheet.getCell(0,i).getContents());
                department.setName(sheet.getCell(1,i).getContents());
                department.setType(sheet.getCell(2,i).getContents());
                department.setTypeName(sheet.getCell(3,i).getContents());
                department.setParentCode(sheet.getCell(4,i).getContents());
                department.setLevel(sheet.getCell(5,i).getContents());
                departmentService.save(department);

                i++;
            }
            System.out.println("total: " + i);
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
