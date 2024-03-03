package store.stz7750.stz.admin.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * The type Event vo.
 */
@Data
public class EventVO {
    private int eventId;
    private String title;
    private String content;
    private LocalDateTime regDt;
    private String editor;
    private LocalDateTime modifiDt;
    private String images; //json 문자열
    private LocalDate bngnDt;
    private LocalDate endDt;
    private String type;
}
