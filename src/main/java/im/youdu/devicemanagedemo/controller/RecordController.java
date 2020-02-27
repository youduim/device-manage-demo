package im.youdu.devicemanagedemo.controller;

import com.alibaba.fastjson.JSONObject;
import im.youdu.devicemanagedemo.entity.ConstDevice;
import im.youdu.devicemanagedemo.entity.Record;
import im.youdu.devicemanagedemo.entity.Response;
import im.youdu.devicemanagedemo.service.RecordServiceI;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("record")
@Controller
public class RecordController {
    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);
    @Resource
    private RecordServiceI recordService;

    @GetMapping("list")
    @ResponseBody
    public JSONObject getAll(String pageNum, String pageSize) {
        Page<Record> page = recordService.getRecordByPage(pageNum, pageSize);
        return Response.getPageResponse(page);
    }

    @GetMapping(value = "excel/export")
    public void export(HttpServletResponse response) throws IOException {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet sheet = workbook.createSheet("登录记录流水表");
            List<Record> list = recordService.getRecordAll();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 设置要导出的文件的名字
            String fileName = "recode " + dateFormat.format(new Date()) + ".xls";

            // 新增数据行，并且设置单元格数据
            int rowNum = 1;

            // headers表示excel表中第一行的表头 在excel表中添加表头
            String[] headers = {"序号", "企业号", "帐号", "名称", "设备id", "设备类型", "Ip地址", "Mac地址", "登录时间", "Email", "手机号", "固定电话"};
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            ConstDevice constDevice=new ConstDevice();

            //在表中存放查询到的数据放入对应的列
            for (Record item : list) {
                HSSFRow row1 = sheet.createRow(rowNum);
                //设置列宽
                sheet.autoSizeColumn(rowNum);
                sheet.setColumnWidth(rowNum, sheet.getColumnWidth(rowNum) * 17 / 10);
                int i = 0;
                row1.createCell(i++).setCellValue(item.getId());
                row1.createCell(i++).setCellValue(String.valueOf(item.getBuin()));
                row1.createCell(i++).setCellValue(item.getAccount());
                row1.createCell(i++).setCellValue(item.getCnName());
                row1.createCell(i++).setCellValue(item.getDeviceId());

                row1.createCell(i++).setCellValue(constDevice.getNameByDeviceType(item.getDeviceType()));
                row1.createCell(i++).setCellValue(item.getIp());
                row1.createCell(i++).setCellValue(item.getMac());
                row1.createCell(i++).setCellValue(timeFormat.format(item.getRecordTime()));
                row1.createCell(i++).setCellValue(item.getEmail());
                row1.createCell(i++).setCellValue(item.getMobile());
                row1.createCell(i).setCellValue(item.getPhone());
                rowNum++;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            logger.error("export recode excel fail because {}", e.getMessage());
            response.sendError(500, e.getLocalizedMessage());
        }
    }
}
