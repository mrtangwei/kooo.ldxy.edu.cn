package cn.edu.ldxy.department;

import cn.edu.ldxy.department.domain.Department;
import com.alibaba.fastjson.JSONObject;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DepartmentApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveTest() {



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
			System.out.println("开始读取数据........" + username.getContents());

			i = 1;

			while (i< 195 ) {


				Department department = new Department();
				department.setCode(sheet.getCell(0,i).getContents());
				department.setName(sheet.getCell(1,i).getContents());
				department.setType(sheet.getCell(2,i).getContents());
				department.setTypeName(sheet.getCell(3,i).getContents());
				department.setParentCode(sheet.getCell(4,i).getContents());
				department.setLevel(sheet.getCell(5,i).getContents());

				String json = JSONObject.toJSONString(department);


				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<>(json, headers);
				// restTemplate.setUriTemplateHandler(U);
				String body = this.restTemplate.postForObject("/security/department/add/", entity, String.class);


				i++;
			}
			System.out.println("total: " + i);
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}



	}

}
