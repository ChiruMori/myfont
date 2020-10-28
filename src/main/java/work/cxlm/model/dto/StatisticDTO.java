package work.cxlm.model.dto;

import lombok.Data;

/**
 * 统计数据传输对象
 * created 2020/10/21 15:21
 *
 * @author cxlm
 */
@Data
public class StatisticDTO {

    // 上传的字体文件数量
    private Integer fontCount;

    // 建立的字体系统数量
    private Integer createdFontCount;

    // 创建的汉字数量
    private Integer kanjiCount;

    // 上传的附件总数
    private Integer attachCount;
}
