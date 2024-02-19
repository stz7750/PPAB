package store.stz7750.stz.admin.vo;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class NewsVO {
    private int cno;
    private String title;
    private String content;
    private Date regDt;
    private String editor;
    private Date modifiDt;
    private String images;
}
